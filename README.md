# API para o Bootcamp Java - Spring Boot

Projeto desenvolvido como desafio final do bootcamp java com spring boot da Squadra, com base no banco de dados passado, a proposta foi desevolver uma API para cadastrar, atualizar, remover e listar dados das entidades UF, Município, Bairro e Pessoa, com suas devidas regras, na qual implementei as seguintes rotas com seus métodos:


#### Rota UF
```
POST /uf -> Cadastrar UF
PUT /uf -> Atualizar UF
GET /uf -> Listar Ufs com parâmetros
DELETE /uf -> Deletar UF 
```

#### Rota Município
```
POST /municipio -> Cadastrar Município
PUT /municipio -> Atualizar Município
GET /municipio -> Listar Município com parâmetros
DELETE /municipio -> Deletar Município
```

#### Rota Bairro
```
POST /bairro -> Cadastrar Bairro
PUT /bairro -> Atualizar Bairro
GET /bairro -> Listar Bairro com parâmetros
DELETE /bairro -> Deletar Bairro
```

#### Rota Pessoa
```
POST /pessoa -> Cadastrar Pessoa
PUT /pessoa -> Atualizar Pessoa
GET /pessoa -> Listar Pessoa com parâmetros
DELETE /pessoa -> Deletar Pessoa
```

## Tecnologias utilizadas

###### JAVA 17
###### Spring Boot WEB
###### Spring JPA DATA
###### banco de dados Oracle 21c XE
###### Bean Validation
###### Lombok


## Testar rotas

Caminho padrão -> http://localhost:8080/ ou http://ip-personalizado:8080/


Existe a possibilidade de testar as rotas com algum programa como Postman ou Insomnia.

É possível também testar pelo Swagger UI
Caminho padrão ->  http://localhost:8080/swagger-ui.html ou http://ip-personalizado:8080/swagger-ui.html