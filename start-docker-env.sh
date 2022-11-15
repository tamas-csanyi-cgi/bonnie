#until we're missing docker-compose we can use this file
docker run -d -p 8081:80 bonnie-frontend
docker run -d -p 8082:8082 bonnie-backend