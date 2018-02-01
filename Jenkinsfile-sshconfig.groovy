stage("jenkins master") {
    node() {
        sh "id"
        sh "echo $HOME"
        sh "git ls-remote ssh://git@git.belastingdienst.nl:7999/~wolfj09/continuous-delivery.git HEAD || true"
    }
}
stage("jenkins slave") {
    node("maven-jos-openjdk8") {
        sh "id"
        sh "echo $HOME"
        sh "git ls-remote ssh://git@git.belastingdienst.nl:7999/~wolfj09/continuous-delivery.git HEAD || true"
    }
}
