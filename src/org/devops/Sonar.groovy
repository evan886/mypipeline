//Sonar.groovy
def SonarScannerByPlugin() {
    withSonarQubeEnv("SonarQube") {
        withCredentials([usernamePassword(credentialsId: '2523cbac-a621-4815-b7f8-e68e03e14cba',
                usernameVariable: 'USERNAME',
                passwordVariable: 'SSPASSWORD')]) {

        withSonarQubeEnv{"SonarQube"} 
        sh """
           echo 'evan sonar'
           /home/evan/data/apps/sonar-scanner/bin/sonar-scanner \
           -Dsonar.login=admin \
           -Dsonar.password=evan2240881 \
           -Dsonar.host.url=http://192.168.10.105:9000 \
           -Dsonar.branch.name=main

                    
        """
                 }
    }
}




def Sonar36() {
    withCredentials([([usernamePassword(credentialsId: '2523cbac-a621-4815-b7f8-e68e03e14cba',
                usernameVariable: 'USERNAME',
                passwordVariable: 'PASSWORD')]) {
    withSonarQubeEnv{"SonarQube"} 
        sh "/home/evan/data/apps/sonar-scanner/bin/sonar-scanner  \
           -Dsonar.login=admin \
           -Dsonar.password=evan2240881"
                }
}
























def bakSonarScannerByPlugin() {
    withSonarQubeEnv("SonarQube") {
        withCredentials([usernamePassword(credentialsId: '2523cbac-a621-4815-b7f8-e68e03e14cba',
                usernameVariable: 'SONAR_USER',
                passwordVariable: 'SONAR_PASSWD')]) {
        withSonarQubeEnv{"SonarQube"}  
        sh """
           echo 'evan sonar'
           /home/evan/data/apps/sonar-scanner/bin/sonar-scanner  \
           -Dsonar.login=admin \
           -Dsonar.password=evan2240881 \
           -Dsonar.host.url=http://192.168.10.105:9000 \
           -Dsonar.branch.name=main

                    
        """
                 }
    }
}