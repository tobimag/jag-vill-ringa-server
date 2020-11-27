GRADLE=./gradlew
DOCKER_IMAGE=end-digits-fetcher
APPLICATION_CONTAINER_NAME=end-digits-fetcher
POSTGRES_CONTAINER_NAME=end-digits-postgres-db
NETWORK=my_network

build:
	${GRADLE} build

docker-build: build
	mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)
	docker build -t ${DOCKER_IMAGE} .

clean:
	${GRADLE} clean

docker-clean:
	docker stop $$(docker ps -a -q) > /dev/null 2>&1 || true
	docker rm $$(docker ps -a -q) > /dev/null 2>&1 || true
	docker network rm ${NETWORK}

docker-create-network:
	docker network create -d bridge ${NETWORK}

start-postgres:
	docker run --name ${POSTGRES_CONTAINER_NAME} \
			   --network ${NETWORK} \
			   -e POSTGRES_PASSWORD=mysecretpassword \
			   -e POSTGRES_DB=end-digits-db \
			   -p 5432:5432 \
			   -d \
			   postgres:alpine
	bash -c 'echo "Waiting for Postgres to complete start-up..."; sleep 10;'

run: build
	java -jar build/libs/end-digits-fetcher-0.0.1-SNAPSHOT.jar

docker-run: docker-clean docker-build docker-create-network start-postgres
	docker run --network ${NETWORK} --name ${APPLICATION_CONTAINER_NAME} -e "SPRING_PROFILES_ACTIVE=live" -p 8080:8080 -d ${DOCKER_IMAGE}

docker-run-dev: docker-clean docker-build docker-create-network start-postgres
	docker run --network ${NETWORK} --name ${APPLICATION_CONTAINER_NAME}-dev -e "SPRING_PROFILES_ACTIVE=dev" -p 8080:8080 -d ${DOCKER_IMAGE}

.PHONEY: build, run, clean, docker-build, docker-run, docker-run-dev, start-postgres