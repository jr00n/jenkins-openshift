FROM openshift/jenkins-slave-base-centos7

MAINTAINER Jeroen Wolff <jeroen.wolff@gmail.com>

ENV MAVEN_VERSION=3.3.9

# Install IBM JDK
COPY ./contrib/ibm-java-sdk-8.0-4.1-x86_64-archive.bin /tmp/ibm-java.bin

RUN echo "INSTALLER_UI=silent" > /tmp/response.properties \
    && echo "USER_INSTALL_DIR=/opt/ibm/java" >> /tmp/response.properties \
    && echo "LICENSE_ACCEPTED=TRUE" >> /tmp/response.properties \
    && mkdir -p /opt/ibm \
    && chmod +x /tmp/ibm-java.bin \
    && /tmp/ibm-java.bin -i silent -f /tmp/response.properties \
    && rm -f /tmp/response.properties \
    && rm -f /tmp/index.yml \
    && rm -f /tmp/ibm-java.bin


ENV JAVA_HOME=/opt/ibm/java/jre \
    PATH=/opt/ibm/java/bin:$PATH \
    IBM_JDK_80=/opt/ibm/ja


# Install Maven
COPY ./contrib/apache-maven-$MAVEN_VERSION-bin.tar.gz /tmp/maven-bin.tar.gz
RUN tar -zxf /tmp/maven-bin.tar.gz -C /usr/local && \
    ln -sf /usr/local/apache-maven-$MAVEN_VERSION/bin/mvn /usr/local/bin/mvn && \
    mkdir -p $HOME/.m2

COPY ./contrib/settings.xml $HOME/.m2/

RUN chown -R 1001:0 $HOME && \
    chmod -R g+rw $HOME

USER 1001
