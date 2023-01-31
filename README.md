# Carbon - Apontamentos

## Configuração e execução do projeto

Projeto desenvolvido em JAVA com framework Spring Boot
A compilação é automatizado através do Maven
O Banco de Dados MySQL
Autenticação e autorização com Spring Security e tokens JWT (JSON Web Token)
Testes unitários e de integração com JUnit e Mockito
Caching com EhCache

## Banco de Dados

Todos arquivos de banco de dados estão armazenados na pasta sql:

* 1-MER.mwb: MER do MYSQL para visualização, modelagem e sincronia. 
* 1-Structure.sql: arquivo de estrutura do banco de dados.
* 2-BasicInserts.sql: arquivo com os inserts básicos para o funcionamento do Portal.

Para configuração do banco de dados com os dados iniciais da aplicação, ou seja, os dados que permitem o login e acesso às telas administrativas é necessário os seguintes passos:
* 1- Criar um schema no banco de dados com o nome do banco de dados do cliente/projeto.
* 2- Executar os scripts para configuração do banco. Primeiro o script 1-Structure.sql e depois 2.BasicInters.sql.
* 3- Após a correta execução dos scritps, acessar o portal com o usuário de acesso/login da aplicação.  User: root / Pass: heineken

Para configuração das credenciais de acesso ao banco de dados, edite o arquivo `config/application.yml` e configure as credenciais de acesso do seu banco de dados local.

## Acesso do Portal

User: root
Pass: heineken


## Testes

O projeto conta com testes unitários, onde validamos as principais classes, Services e DAOs

