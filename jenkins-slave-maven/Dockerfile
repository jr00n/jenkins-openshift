FROM openshift/jenkins-slave-maven-centos7

MAINTAINER Jeroen Wolff <j.wolff@belastingdienst.nl>

# $HOME is set by openshift/jenkins-slave-base-centos7 to /home/jenkins

ENV MAVEN_HOME=/opt/rh/rh-maven33/root/usr/share/maven

COPY ./contrib/settings.xml $MAVEN_HOME/conf/

USER 1001
