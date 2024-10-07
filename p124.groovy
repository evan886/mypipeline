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

        stage('CodeScan'){
            steps{
               
                script{
                    println "CodeScan"
                    //env.projectName = "${JOB_NAME}".split('/')[-1].split('_')[0]   // demo3/demo3-maven-service_CI
                    withCredentials([usernamePassword(credentialsId: '2523cbac-a621-4815-b7f8-e68e03e14cba',
                                 passwordVariable: 'PASSWORD',
                                 usernameVariable: 'USERNAME' )]) {
                    //withSonarQubeEnv{"SonarQube"}                
                    sh "/home/evan/data/apps/sonar-scanner/bin/sonar-scanner  \
                        -Dsonar.login=admin \
                        -Dsonar.password=evan2240881 "
                    }
                    
                }
            }
        }
    }

}

// withCredentials([usernamePassword(credentialsId: '2523cbac-a621-4815-b7f8-e68e03e14cba', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
//     // some block
// }