//Push artifact 
def PushNexusArtifact(repoId, targetDir, pkgPath, sourcePkgName,targetPkgName {
    withCredentials([usernamePassword(credentialsId: 'f26c7693-66d8-41d9-8ad4-bfb20eaa4634', usernameVariable: 'USERNAME', passwordVariable: 'PASSWD')]) {
        sh """
            curl -X 'POST' \
            'http://192.168.10.105:8081/service/rest/v1/components?repository=maven-devops6-release'  \
            -H 'accept: application/json' \
            -H 'Content-Type: multipart/form-data' \
            -F "raw.directory=${targetDir}" \
            -F "raw.asset1=@${pkgPath}/${sourcePkgName};type=application/java-archive" \
            -F "raw.asset1.filename=${targetPkgName}" \
            -u ${USERNAME}:${PASSWD}

         """
    }
}