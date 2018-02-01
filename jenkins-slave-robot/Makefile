build:
	docker build -t "jr00n/jenkins-slave-robotframework" -f Dockerfile .

.PHONY:test
test:
	docker build -t "jr00n/jenkins-slave-robotframework-candidate" -f Dockerfile .
	sh test/run
