//checkout code from git

//checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: '42c79e35-fc3e-4067-8bbf-a5e3cc0669a8', url: 'http://mygit.com/devops6/devops6-maven-service.git']])
//checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: '42c79e35-fc3e-4067-8bbf-a5e3cc0669a8', url: 'http://mygit.com/devops6/devops6-maven-service.git']])

//参数选择有问题 先直接跳过啦 Oct 02  2024  p124.groovy 是成功的呀  p148.groovy 也是成功的呀
def bakCheckOut() {
    println("CheckOut")
    checkout([$class: 'GitSCM',
       branches: [[name: "${env.branchName}"]],
       extensions: [], 
       gitTool: 'Default', 
       userRemoteConfigs: [[credentialsId: '42c79e35-fc3e-4067-8bbf-a5e3cc0669a8',
       url: 'http://mygit.com/devops6/devops6-maven-service.git']]])
       //url: "${params.srcUrl}"]]])
    sh "ls -l"
}

def CheckOut(){
    println("CheckOut")
    checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: '42c79e35-fc3e-4067-8bbf-a5e3cc0669a8', url: 'http://mygit.com/devops6/devops6-maven-service.git']])
    sh "ls -l"

}



//build maven project 编辑构建 
def Build() {
    println("Build 开始")
    sh "${params.buildShell}"

}