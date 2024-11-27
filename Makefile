APP_NAME = springboot-app
DOCKER_DIR = docker
TARGET_DIR = target
JAR_FILE = $(TARGET_DIR)/*.jar

.PHONY: build package run clean

package:
	mvn clean package

build: package
	docker compose -f $(DOCKER_DIR)/docker-compose.yml up --build -d

run:
	docker compose -f $(DOCKER_DIR)/docker-compose.yml up -d

stop:
	docker compose -f $(DOCKER_DIR)/docker-compose.yml down

clean: stop
	mvn clean
	rm -rf $(TARGET_DIR)
