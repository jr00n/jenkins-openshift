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
        }
    }
} catch (err) {
    echo "in catch block"
    echo "Caught: ${err}"
    currentBuild.result = 'FAILURE'
    throw err
}