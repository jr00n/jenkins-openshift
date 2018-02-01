build:
	docker build -t "jr00n/jenkins-slave-maven3-ibmjdk8" -f Dockerfile .

.PHONY:test
test:
	docker build -t "jr00n/jenkins-slave-maven3-ibmjdk8-candidate" -f Dockerfile .
	sh test/run
