FROM centos:centos6
RUN curl -sL https://rpm.nodesource.com/setup | bash -
RUN yum install -y npm nodejs
RUN npm install -g grunt-cli
