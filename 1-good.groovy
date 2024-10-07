
currentBuild.description = "branchName: ${params.branchName}"


pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo '下载代码'
                script {
                    checkout scmGit(branches: [[name: "${params.branchName}"]], extensions: [], userRemoteConfigs: [[credentialsId: '42c79e35-fc3e-4067-8bbf-a5e3cc0669a8', url: "${env.srcUrl}"]])
                }
            }
        }
    }
}
