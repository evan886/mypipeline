import jenkins

server = jenkins.Jenkins("http://127.0.0.1:8080/", username="evan", password="evan2240881")
#server = jenkins.Jenkins('http://localhost:8080', username='myuser', password='mypassword')
user = server.get_whoami()
version = server.get_version()
print('Hello %s from Jenkins %s' % (user['fullName'], version))
