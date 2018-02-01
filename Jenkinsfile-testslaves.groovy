try {
    timeout(time: 20, unit: 'MINUTES') {
        def project = ""
        def ocCmd = "oc " +
                " --server=https://openshift.default.svc.cluster.local" +
                " --certificate-authority=/run/secrets/kubernetes.io/serviceaccount/ca.crt"

        stage("Test master") {
            node {
                def token = sh(script: 'cat /var/run/secrets/kubernetes.io/serviceaccount/token', returnStdout: true)
                ocCmd = ocCmd + " --token=" + token
                project = env.PROJECT_NAME
                ocCmd = ocCmd + " -n ${project}"
            }
        }
        stage("Test jos-m3-openjdk8") {
            node("jos-m3-openjdk8") {
                // check of maven en jdk aanwezig zijn
                sh(script: "mvn --version")
                // check of een git checkout werkt op de slave (met ssh key)
                git branch: 'master', url: 'ssh://git@git.belastingdienst.nl:7999/~wolfj09/os-fase2-jeeapp.git'
            }
        }
        stage("Test jos-m3-ibmjdk8") {
            node("jos-m3-ibmjdk8") {
                // check of maven en jdk aanwezig zijn
                sh(script: "mvn --version")
                // check of een git checkout werkt op de slave (met ssh key)
                git branch: 'master', url: 'ssh://git@git.belastingdienst.nl:7999/~wolfj09/os-fase2-jeeapp.git'
            }
        }
        stage("Test jos-robotframework") {
            node("jos-robotframework") {
                // check of robotframework geinstalleerd is.
                sh(script: "pybot --version || ERROR=true")
            }
        }
    }
} catch (err) {
    echo "in catch block"
    echo "Caught: ${err}"
    currentBuild.result = 'FAILURE'
    throw err
}