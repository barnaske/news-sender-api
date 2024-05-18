DB Docker Run Command:
docker run --name news-sender-db -p 5432:5432 -e POSTGRES_DB=news-sender-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123456 postgres:latest