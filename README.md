---

# Sistema de Venda de Ingressos de Teatro

Este repositório contém o código fonte e documentação do projeto de um sistema de venda de ingressos para um sistema simulado de um teatro, desenvolvido para o Projeto Integrador do curso de Análise e Desenvolvimento de Sistemas da Faculdade SENAI FATESG.

## Sobre o Projeto

O projeto tem como objetivo desenvolver um sistema computacional que automatize e gerencie a venda de ingressos para o Teatro ABC. O sistema permite aos clientes selecionar peças teatrais, sessões (manhã, tarde, noite) e áreas de assentos (plateia A, plateia B, camarote, frisa, balcão nobre) através de uma interface intuitiva. Além disso, oferece funcionalidades para imprimir comprovantes de ingressos e gerar estatísticas detalhadas de vendas.

## Funcionalidades Principais

- **Compra de Ingressos:** Os clientes podem escolher a peça, sessão e área de assento desejadas, efetuando a compra através do sistema informando o CPF.

- **Impressão de Ingressos:** Após a compra, os clientes têm a opção de imprimir o comprovante de ingresso diretamente na plataforma.

- **Estatísticas de Vendas:** O sistema gera relatórios detalhados sobre o desempenho das vendas, incluindo análises sobre popularidade de peças, ocupação de poltronas, lucratividade por sessão e peça.

## Metodologias
- **Scrum:** O objetivo do Scrum é facilitar o desenvolvimento ágil de software através de ciclos curtos e iterativos (sprints), promovendo transparência, colaboração intensa e adaptação contínua às mudanças, para entregar valor de forma rápida e eficaz aos clientes.

  ---

## Colaboradores:
> - 🚨Atenção todos os colaboradores abaixo participaram efetivamente da parte de Desenvolvimento, Analise e Testador do Sistema
- **Angelo Augusto**: Desenvolvedor
- **Guilherme Xavier**: Auxiliar de projeto 
- **Kalleb Mendes**: Projetista e Documentador
  
  ---

## Software

- **IDE:** IntelliJ, Apache Netbeans, Visual Studio Code
- **Linguagem:** Java 17
- **Interface Gráfica:** Swing
- **Banco de Dados:** Excel
- **Controle de Versão:** Git
- **Repositório:** GitHub
- **Gerenciamento de Projeto:** Discord
- **Reunião:** Presencial e Discord

---
'''mermaid
classDiagram
    class Assento {
        -boolean ocupado
        +ocupar()
        +liberar()
    }

    class Setor {
        -String nome
        -int capacidade
        -double preco
        -Assento[][] assentos
        +calcularOcupacao()
        +calcularLucro()
    }

    class Peca {
        -String titulo
        -String diretor
        -String elenco
        +getDetalhes()
    }

    class Sessao {
        -String horario
        -Setor[] setores
        +getDisponibilidade()
    }

    class Ingresso {
        -String cpf
        -Peca peca
        -Sessao sessao
        -Setor setor
        -Assento assento
    }

    class Teatro {
        -Setor[] setores
        -Sessao[] sessoes
        -Peca[] pecas
        +gerarRelatorio()
        +calcularLucroTotal()
    }

    Assento --> Setor
    Setor --> Sessao
    Sessao --> Peca
    Sessao --> Ingresso
    Teatro --> Sessao
    Teatro --> Peca
'''
