FROM centos:centos7
RUN curl -sL https://rpm.nodesource.com/setup | bash -
RUN yum install -y npm nodejs git
RUN npm install -g grunt-cli
