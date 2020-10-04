GRADLE=./gradlew
DOCKER_IMAGE=end-digits-fetcher

build:
	${GRADLE} build

docker-build: build
	mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)
	docker build -t ${DOCKER_IMAGE} .

clean:
	${GRADLE} clean

run: build
	java -jar build/libs/end-digits-fetcher-0.0.1-SNAPSHOT.jar

docker-run: docker-build
	docker run --name end-digits-fetcher -e "SPRING_PROFILES_ACTIVE=live" -p 8080:8080 ${DOCKER_IMAGE}

docker-run-dev: docker-build
	docker run --name end-digits-fetcher-dev -e "SPRING_PROFILES_ACTIVE=dev" -p 8080:8080 ${DOCKER_IMAGE}

.PHONEY: build, run, clean, docker-build, docker-run, docker-run-dev