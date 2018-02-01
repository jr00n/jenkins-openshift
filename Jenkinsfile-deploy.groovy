try {
    timeout(time: 20, unit: 'MINUTES') {
        def appName = "cta-ioc"
        def project = ""
        def ocCmd = "oc " +
                " --server=https://openshift.default.svc.cluster.local" +
                " --certificate-authority=/run/secrets/kubernetes.io/serviceaccount/ca.crt"
        def mvnCmd = "mvn"

        node {
            stage("Initialize") {
                def token = sh(script: 'cat /var/run/secrets/kubernetes.io/serviceaccount/token', returnStdout: true)
                ocCmd = ocCmd + " --token=" + token
                project = env.PROJECT_NAME
                ocCmd = ocCmd + " -n ${project}"
            }
        }
        node("maven-jos-openjdk8") {
            stage("Git Checkout") {
                git branch: 'develop', url: 'ssh://git@git.belastingdienst.nl:7999/~wolfj09/cta-ioc.git'
            }
            stage("Build WAR") {
                sh "${mvnCmd} clean package -Popenshift"
                stash name: "war", includes: "target/ROOT.war"
            }

            stage('deploy') {
                sh "rm -rf oc-build && mkdir -p oc-build/deployments"
                sh "cp target/ROOT.war oc-build/deployments/ROOT.war"
                // clean up. keep the image stream
                openshiftDeleteResourceByLabels(types: 'svc,route,bc,dc,build', keys: 'app', values: '$appName')
                // configure a s2i build with WildFly and the generated WAR
                sh "${ocCmd} new-build --name=${appName} --image-stream=wildfly:10.0 --binary=true --labels=app=${appName} || true"
                // start the image build
                sh "${ocCmd} start-build ${appName} --from-dir=oc-build --build-loglevel=3 --wait=true"
                // deploy image in project
                sh "${ocCmd} new-app ${appName}:latest --labels=app=${appName}"
                sh "${ocCmd} expose svc/javaee7-angular --labels=app=${appName}"
            }
        }
    }
} catch (err) {
    echo "in catch block"
    echo "Caught: ${err}"
    currentBuild.result = 'FAILURE'
    throw err
}