#until we're missing docker-compose we can use this file
docker stop frontend
docker stop backend
docker rm $(docker ps -aq)
docker run -d -p 8081:80 --name frontend bonnie-frontend
docker run -d -p 8082:8082 --name backend bonnie-backend