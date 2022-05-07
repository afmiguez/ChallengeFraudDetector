
[//]: # (Título e Imagem de capa;)
[//]: # (Badges;)
[//]: # (Índice;)
[//]: # (Descrição do Projeto;)
[//]: # (Status do Projeto;)
[//]: # (Funcionalidades e Demonstração da Aplicação;)
[//]: # (Acesso ao Projeto;)
[//]: # (Tecnologias utilizadas;)
[//]: # (Pessoas Contribuidoras;)
[//]: # (Pessoas Desenvolvedoras do Projeto;)
[//]: # (Licença.)

# Fraud Detector (Alura Challenge 2022)

<p align="center">
 <img src="http://img.shields.io/static/v1?label=STATUS&message=WOP&color=GREEN&style=for-the-badge" />
</p>

## Índice

* [Descrição do Projeto](#descrição-do-projeto)
* [Funcionalidades](#funcionalidades-e-demonstração-da-aplicação)
* [Utilização do Projeto](#utilização-do-projeto)
* [Tecnologias utilizadas](#tecnologias-utilizadas)
* [Pré-requisitos](#pré-requisitos)
* [Exemplo](#exemplo)
* [Recursos para deployment](#recursos-para-deployment)


## Descrição do Projeto

Projeto proposto pelo 3º Desafio Alura, que consiste na elaboração de uma API e uma interface web para suporte à análise de transações financeiras suspeitas  

## Funcionalidades

O projeto suporta as seguintes funcionalidades

* Importação de transações por arquivos .csv e .xml
* Detalhes de transações importadas
* Análise de transações suspeitas

Estão incluídos no projeto a API e a interface web, no diretório client.

## Utilização do Projeto

### API
Para clonar este repositório:

```
git clone https://github.com/afmiguez/ChallengeFraudDetector.git
```

Para criar o binário executável da API deve utilizar o comando abaixo:

```
./gradlew shadowJar
```

O binário será criado na pasta build/libs e poderá ser executado com o comando:

```
java -jar ChallengeFraudDetector-0.0.1-all.jar
```

A porta utilizada por padrão é a TCP 8080, mas pode ser mudado, por exemplo para 8000, com o comando abaixo:

```
java -jar -DPORT=8000 ChallengeFraudDetector-0.0.1-all.jar
```

### Interface web

Para executar a interface web é preciso ir para o diretório client e instalar as dependências do projeto com o comando abaixo:

```
npm i
```

Em seguida à instalação, pode executar o projeto em modo de desenvolvimento com o comando abaixo:

```
npm start
```

Para gerar a versão para a produção pode executar o comando

```
npm run build
```

no diretório client 

OU

```
./gradlew assembleFrontend
```

no diretório raiz do projeto (https://github.com/siouan/frontend-gradle-plugin) .

## Tecnologias utilizadas

* API
  * Framework Web: [Ktor](https://ktor.io/)
  * Injeção de dependência: [Koin](https://insert-koin.io/)
  * ORM: [Exposed](https://github.com/JetBrains/Exposed)

* Interface Web
  * [React](https://pt-br.reactjs.org/)
  * [React-Bootstrap](https://react-bootstrap.github.io/)
  * [Axios](https://axios-http.com/docs/intro)
  * [React Router](https://reactrouter.com/docs/en/v6/getting-started/overview)

## Pré-requisitos
* [npm](https://www.npmjs.com/)
* [node](https://nodejs.org/en/)
* [jdk11](https://openjdk.java.net/projects/jdk/11/)

## Exemplo
Uma versão de exemplo pode ser encontrada [em](http://fraud.afmiguez.me)  

## Recursos para deployment

O deployment foi feito com Jenkins, cujo Jenkinsfile se encontra na raiz do projeto.
O diretório deploy contem alguns scripts que podem ser utilizados para transformar os módulos do projeto em serviços linux