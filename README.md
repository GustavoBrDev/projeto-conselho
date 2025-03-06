# Conselho do Estudante

O conselho do Estudante é uma aplicação Web responsiva para todos os dispositivos, com foco principal para a versão **Desktop** e em secundário para **celulares**. Esse projeto foi solicitado pela equipe pedagógica da instituição com ínicio do desenvolvimento em Fevereiro de 2025 e conclusão estima em Abril do mesmo ano.

Este projeto foi dividido em dois repositórios especificos, um para o **frontend (feito em Next e React)** e outro para o **backend (feito com Spring Boot)**. Este repositório é dedicado ao desenvolvimento backend, você pode acessar o outro repositório [clicando aqui]().

## ❗ Funcionalidades do Projeto

> * O projeto deve suportar 5 tipos de usuários: **alunos, representantes, professores, equipe pedagógica (técnico e orientador), supervisor e um administrador**;
> * O projeto deve permitir que os **alunos** sejam capazes de **visualizar o histórico de feedbacks, alterar configurações básicas, utilizar o chat integrado e visualizar seu perfil**;
> * O projeto deve permitir que os **representantes** sejam capazes de **visualizar o histórico de feedbacks, alterar configurações básicas, utilizar o chat integrado, visualizar seu perfil e cadastrar os pré-conselhos da turma**;
> * O projeto deve permitir que os **professores** sejam capazes de **visualizar o histórico de feedbacks (por turma / disciplina), alterar configurações básicas, visualizar seu perfil, utilizar o chat integrado, cadastrar um pré-conselho sobre os alunos e visualizar o mesmo**;
> * O projeto deve permitir que os **técnicos pedagógicos** sejam capazes de **alterar configurações básicas, visualizar seu perfil, gerenciar conselhos de classe, visualizar dashboards, utilizar o chat integrado, gerenciar usuários (supervisores, professores, alunos e representantes), gerenciar turmas (importação com CSV), gerenciar cursos, gerenciar disciplinas e gerenciar professores PCP**;
> * O projeto deve permitir que os **orientadores pedagógicos** sejam capazes de **alterar configurações básicas, visualizar seu perfil, gerenciar conselhos de classe, visualizar dashboards, utilizar o chat integrado, gerenciar usuários (supervisores, professores, alunos e representantes), gerenciar turmas (importação com CSV), gerenciar cursos, gerenciar disciplinas, gerenciar professores PCP e gerencar a equipe pedagógica**;
> * O projeto deve constar constar com um perfil de **administrador** que será responsável por gerenciar os usuários, principalmente os **orientadores pedagógicos**.

## 💻 Sobre o Backend

O **backend** será construido utilizando a linguagem **Java** e **Spring Boot** constando com algumas depedências:

* Spring Boot Dev Tools
* Swagger
* Mysql Conector
* Spring Data JPA
* Spring Email
* Spring Web

## 👩‍💻 Diagrama de Classes

Foi elaborado um diagrama de classes para a documentação do projeto. Esse diagrama pode ser acessível [clicando aqui]()

## 💽 Banco de Dados

Para o projeto será utilizado dois bancos de dados especificos:

> 🐬 **Mysql**: Para representar os **usuários** e outros dados estruturados.

> 🍃 **MongoDB**: Para armazenar os logs das operações realizadas no sistema.

