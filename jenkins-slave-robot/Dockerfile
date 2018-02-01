FROM openshift/jenkins-slave-base-centos7

MAINTAINER Jeroen Wolff <j.wolff@belastingdienst.nl>

#Copy the needed packages because we don't have Internet access.
COPY rpm/. /tmp/rpm/
COPY pip/. /tmp/pip/

# install Python and needed tools
RUN yum -y --nogpgcheck localinstall \
    /tmp/rpm/python-devel-2.7.5-58.el7.x86_64.rpm \
    /tmp/rpm/python2-pip-8.1.2-5.el7.noarch.rpm \
    /tmp/rpm/python-setuptools-0.9.8-7.el7.noarch.rpm \
    && rm -rf /tmp/rpm

# install robotframework
RUN pip install --no-cache \
    /tmp/pip/pip-9.0.1-py2.py3-none-any.whl \
    /tmp/pip/decorator-4.1.2-py2.py3-none-any.whl \
    /tmp/pip/robotframework-3.0.2.tar.gz \
    /tmp/pip/robotframework-selenium2library-1.8.0.tar.gz \
    /tmp/pip/robotframework-extendedselenium2library-0.9.1.tar.gz \
    /tmp/pip/selenium-2.53.6-py2.py3-none-any.whl \
    /tmp/pip/robotframework-ftplibrary-1.4.zip \
    /tmp/pip/xvfbwrapper-0.2.9.tar.gz \
    && rm -rf /tmp/pip

USER 1001
