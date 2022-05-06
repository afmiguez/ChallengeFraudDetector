
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
* [Utilização do Projeto](#utilização-projeto)
* [Tecnologias utilizadas](#tecnologias-utilizadas)
* [Pré-requisitos](#pre-requisitos)


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
git clone 
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




## Tecnologias utilizadas

## Pré-requisitos
