# -*- coding: utf-8 -*-
import jenkins
import sys

server = jenkins.Jenkins("http://127.0.0.1:8080/", username="evan", password="evan2240881")

#server.build_job("pt20")
server.build_job("devops6-maven-service_CD")


# myre=[]
# with open("myjobslist","r") as f:
#     myre = [l.strip() for l in f]

# #print(myre)

# for i in myre:
#    server.build_job(i)

#f.close
#server.build_job('empty')

#先把文件读取并转为list 

# cat myjobslist 
# intra_pipe-product-server
# intra_pipe_evaluating-server
