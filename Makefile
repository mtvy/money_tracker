
build:
	mvn package -DskipTests
	docker network create mainnet

up:
	docker-compose up --build -d

down:
	docker-compose down