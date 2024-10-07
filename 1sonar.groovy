
currentBuild.description = "branchName: ${params.branchName}"
// currentBuild.displayName = GetCommitID

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





        stage('Build') {
            steps {
                echo '构建编译代码'
                script {
                    sh "mvn clean package -DskipTests"
                }
            }
        }

        stage("SonarQube Analysis"){
            steps{
                echo "SonarQube Analysis"
                echo "${JOB_NAME}"
                script {
                    env.projectName = "${JOB_NAME}".split('/')[-1] //  demo3/demo3-maen-service
                    echo "projectName: ${env.projectName}"  // sh 中 转化有变量的没成功   38分 day2  
                    withCredentials([string(credentialsId: '7e96a4f8-6d39-4f2a-b52c-065442b21bc4', variable: 'SONAR_TOKEN')]) {
                        sh """
                           mvn clean verify sonar:sonar   -Dsonar.projectKey=demo3-maven-service   -Dsonar.projectName='demo3-maven-service'   -Dsonar.host.url=http://127.0.0.1:9000   -Dsonar.token=sqp_6c103a0b79b2a2d873e6770e877bb1cc2ac2f62d
                           // mvn sonar:sonar   \
                           // -Dsonar.projectName=${env.projectName} \
                           // -Dsonar.projectKey=${env.projectName} \
                           // -Dsonar.host.url=http://192.168.10.105:9000 \
                           // -Dsonar.token=${SONAR_TOKEN}                    
                         """
                    }

                }
            }
        }
        stage("PushArtifacts"){
            steps{
                echo "PushArtifacts"
                script  {
                    pkgName = sh returnStdout: true, script: "ls target/ | grep jar\$"
                    pkgName = pkgName.trim()
                    
                    pkgVersion =  GetCommitID()
                    pkgDir = "${JOB_NAME}".split('_')[0]
                    print(pkgName)
                    withCredentials([usernamePassword(credentialsId: 'f26c7693-66d8-41d9-8ad4-bfb20eaa4634', passwordVariable: 'NEXUSPASSWD', usernameVariable: 'NEXUSUSER')]) {
  

                    sh """ 
                        cd target


                        curl -X 'POST' \
                        'http://192.168.10.105:8081/service/rest/v1/components?repository=demo3' \
                        -H 'accept: application/json' \
                        -H 'Content-Type: multipart/form-data' \
                        -H 'NX-ANTI-CSRF-TOKEN: 0.14480951255686547' \
                        -H 'X-Nexus-UI: true' \
                        -F "raw.directory=${pkgDir}/${pkgVersion}/" \
                        -F 'raw.asset1=@${pkgName};type=application/x-java-archive' \
                        -F 'raw.asset1.filename=${env.projectName}.jar' \
                        -u ${NEXUSUSER}:${NEXUSPASSWD}
                        cd ..








                    """
                    }
                }

            }
        }
    }
}

//获取CommitID
def GetCommitID(){
     ID = sh returnStdout: true, script: "git rev-parse HEAD"
     ID = ID - "\n"
     return ID[0..7]
}

// Analyze "demo3-maven-service": sqp_6c103a0b79b2a2d873e6770e877bb1cc2ac2f62d
//sonar err 
//  [ERROR] Failed to execute goal org.sonarsource.scanner.maven:sonar-maven-plugin:4.0.0.4121:sonar (default-cli) on project demo: You're not authorized to analyze this project or the project doesn't exist on SonarQube and you're not authorized to create it. Please contact an administrator. -> [Help 1]

// withCredentials([usernamePassword(credentialsId: 'f26c7693-66d8-41d9-8ad4-bfb20eaa4634', passwordVariable: 'NEXUS_PASSWD', usernameVariable: 'NEXUS_USER')]) {
//     // some block
// }

