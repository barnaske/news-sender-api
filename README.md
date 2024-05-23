## Setup da News Sender API

O projeto foi codificado utilizando Java 17, com o Intellij IDEA para inicialização do projeto, um container Docker para banco de dados PostgreSQL e manuseio da API via Postman.

Para configuração do container, é necessário possuir o serviço do Docker instalado na máquina, e o comando para subir o container já configurado no application.yml conta abaixo:

`docker run --name news-sender-db -p 5432:5432 -e POSTGRES_DB=news-sender-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123456 postgres:latest`

Ressalto que o banco de dados está configurado como create-drop, portanto a cada reinicialização da aplicação, ele será resetado.

Dentro do diretório `src/main/resources`, encontra-se o arquivo `import.sql`, que contém uma carga inicial de dados, sendo pré requisitos para rodagem do projeto em de antemão, porém também podem ser cadastrados clientes e notícias conforme necessidade.

Também é possível encontrar em `src/main/resources` a collection do Postman utilizada (`NewsSenderApiCollection.postman_collection.json`) para manipulara a API, tendo todos os bodies dos endpoist já mapeados.