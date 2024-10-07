


currentBuild.description = "banchName: ${env.branchName}"


pipeline {
    agent any

    stages {
        stage('CheckOut') {
            steps {
                echo '下载代码'
                script {
                    checkout([$class: 'GitSCM', 
                            branches: [[name: "${params.branchName}"]], 
                            extensions: [], 
                            userRemoteConfigs: [[credentialsId: '42c79e35-fc3e-4067-8bbf-a5e3cc0669a8', 
                            url: "${env.srcUrl}"]]]) 
                    sh "ls -al"
                }
            }
        }

        stage('Build'){
            steps{
                echo "构建"
                script{
                    sh "${params.buildShell}"
                }
            }
        }
        stage('PushArtifact'){
            steps {
                script{
                    println("PushArtifact")
                    PushArtifactByPlugin()
                }
            }

        }


    }
}



def PushArtifactByPlugin(){
    nexusArtifactUploader artifacts: [[artifactId: 'demo', classifier: '', file: 'target/demo-0.0.1-SNAPSHOT.jar',  type: 'jar']], credentialsId: 'f26c7693-66d8-41d9-8ad4-bfb20eaa4634', groupId: 'com.devops6',  nexusUrl: '192.168.10.105:8081', nexusVersion: 'nexus3', protocol: 'http',  repository: 'maven-devops6-release',version: '0.0.1'


    //nexusArtifactUploader credentialsId: 'f26c7693-66d8-41d9-8ad4-bfb20eaa4634', groupId: 'com.devops6', nexusUrl: '192.168.10.105:8081', nexusVersion: 'nexus2', protocol: 'http', repository: 'maven-devops6-release', version: 'nexus3'





}

// Uploading: http://192.168.10.105:8081/repository/maven-devops6-release/com/devops6/demo/1.1.1/demo-1.1.1.jar
//Failed to deploy artifacts: Could not transfer artifact com.devops6:demo:jar:1.1.1 from/to maven-devops6-release (http://192.168.10.105:8081/repository/maven-devops6-release): transfer failed for http://192.168.10.105:8081/repository/maven-devops6-release/com/devops6/demo/1.1.1/demo-1.1.1.jar, status: 400 Repository version policy: SNAPSHOT does not allow version: 1.1.1
