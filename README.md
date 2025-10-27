# 🎨 3DCommerce - Backend

[![Versão](https://img.shields.io/badge/Versão-1.0.0-blue)](https://github.com/viniciuspiotto/3DCommerce/releases)
[![Licença](https://img.shields.io/badge/Licença-MIT-green)](LICENSE)

## 💻 Sobre

Este projeto é uma API de um ecommerce para vendas de figuras 3D, proporcionando uma plataforma independente para artistas de modelos 3D para vender a licença de seus modelos.
A API foi desenvolvida em Java com o framework **Spring Boot** utilizando uma arquitetura **monolítica modular** desenhada utilizando **DDD estratégico e tático**.

Os principais **Bounded Contexts** são:
* **Catalog:** Gerenciamento de modelos 3D, categorias e busca.
* **IAM (Identity and Access Management):** Autenticação e gestão de vendedores e clientes.
* **Shopping:** Funcionalidades de carrinho de compras.
* **Order:** Processamento de pedidos.
* **Payment:** Integração com serviços de pagamento.

As funcionalidades foram listadas utilizando um [Product Backlog](https://docs.google.com/spreadsheets/d/110nIU_ETxrNIARdGnJO4Gz_BzmWrDTBoJfLqcpKBtaY) para listar todas as funcionalidades da aplicação.

## 🛠️ Stack

* **Linguagem:** Java 21+
* **Framework Principal:** Spring Boot 3.x
* **Banco de Dados:** PostgreSQL e H2
* **Persistência:** Spring Data JPA e Hibernate
* **Segurança:** Spring Security
* **Documentação:** OpenAPI/Swagger

## Autores
| <img src="https://avatars.githubusercontent.com/u/196210367?v=4" width="100" alt="Vinícius Piotto" /> | <img src="https://avatars.githubusercontent.com/u/137798623?v=4" width="100" alt="Maicon Mian" /> |
|:-----------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------:|
|                                          **Vinícius Piotto**                                          |                                          **Maicon Mian**                                          |
|                                   [Linkedin](https://www.linkedin.com/in/viniciushpiotto/)                                    |                        [LinkedIn](https://www.linkedin.com/in/maiconmian/)                        |