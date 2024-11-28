# Mini Autorizador de Cartões

Este projeto é uma API simples de autorização de cartões, onde é possível cadastrar cartões e consultar o saldo. Desenvolvido com **Spring Boot**, o projeto segue boas práticas de desenvolvimento de software, utilizando conceitos como **TDD**, **SOLID**, **Clean Code**, e outras boas práticas.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para criação de aplicações Java, com foco em produtividade e fácil configuração.
- **Spring Data JPA**: Para acesso ao banco de dados de maneira simples e eficiente, utilizando a abstração de repositórios.
- **H2 Database**: Banco de dados em memória para desenvolvimento e testes.
- **Lombok**: Para reduzir a verbosidade do código, gerando automaticamente getters, setters, construtores e outros métodos comuns.
- **JUnit & Mockito**: Ferramentas para realizar testes automatizados e mocks, garantindo maior segurança no código.

## Padrões e Boas Práticas

### 1. **Test-Driven Development (TDD)**

O desenvolvimento orientado a testes (TDD) foi adotado para garantir que o código esteja sempre coberto por testes automatizados. As fases do TDD são seguidas:

- **Red**: Escrever um teste que falha (porque a funcionalidade ainda não foi implementada).
- **Green**: Implementar a funcionalidade de forma que o teste passe.
- **Refactor**: Refatorar o código sem alterar seu comportamento, mantendo os testes passando.

Com isso, a base do código é testada desde o início, o que facilita a manutenção e garante que mudanças no código não quebrem funcionalidades existentes.

### 2. **SOLID**

Os princípios **SOLID** são seguidos para garantir que o sistema seja modular, de fácil manutenção e extensível:

- **S - Single Responsibility Principle (SRP)**: Cada classe ou módulo tem uma única responsabilidade. Por exemplo, a classe `CardService` é responsável apenas pela lógica de negócios relacionada ao cartão.

- **O - Open/Closed Principle (OCP)**: O código deve estar aberto para extensão, mas fechado para modificação. Novas funcionalidades podem ser adicionadas sem modificar o código existente.

- **L - Liskov Substitution Principle (LSP)**: Objetos de uma classe derivada devem poder substituir objetos da classe base sem afetar a funcionalidade do sistema.

- **I - Interface Segregation Principle (ISP)**: As interfaces devem ser específicas e não forçar os implementadores a dependerem de métodos que não utilizam.

- **D - Dependency Inversion Principle (DIP)**: As classes de alto nível não devem depender de classes de baixo nível, mas de abstrações. Da mesma forma, as abstrações não devem depender de detalhes, mas sim os detalhes das abstrações.

### 3. **Boas Práticas do Projeto**

- **Injeção de Dependências**: O projeto faz uso de injeção de dependências com o Spring, o que facilita a testabilidade e a manutenção do código.

- **Segregação de Responsabilidades**: Cada camada do projeto tem uma responsabilidade bem definida, seguindo o padrão MVC (Model-View-Controller):
    - **Controller**: Responsável por lidar com as requisições HTTP e chamar os serviços apropriados.
    - **Service**: Contém a lógica de negócios.
    - **Repository**: Responsável pela interação com o banco de dados.

- **Documentação das APIs**: O projeto segue boas práticas de documentação das APIs, permitindo que outros desenvolvedores possam consumir a API facilmente. Exemplos incluem nomes de endpoints claros e descrições de parâmetros e respostas.

- **Mensagens de Erro Personalizadas**: As mensagens de erro são amigáveis e informativas, ajudando os desenvolvedores e usuários a entenderem o que deu errado em uma requisição.

- **Banco de Dados em Memória para Desenvolvimento**: O uso do banco de dados H2 permite que o sistema seja rapidamente testado sem a necessidade de configurações complexas de banco de dados. O sistema pode ser facilmente configurado para usar um banco de dados persistente em um ambiente de produção.

- **Uso de DTOs**: Os objetos de transferência de dados (`DTOs`) são usados para garantir que os dados trafeguem entre as camadas do sistema de maneira clara e concisa.

- **Validação de Entrada**: O uso de validações com o `@Valid` e anotações como `@NotBlank` nas entradas da API assegura que os dados recebidos são corretos, evitando erros em tempo de execução.

- **Tratamento de Erros**: As exceções são tratadas de forma centralizada, usando `@ControllerAdvice`, o que mantém o código limpo e facilita o gerenciamento de falhas.

### 4. **Lidando com Concorrência**

### Decisão de Implementação

A decisão foi adotar **Pessimistic Locking** (bloqueio pessimista) nas operações de leitura e escrita dos cartões, garantindo que ao acessar o número do cartão, ele seja bloqueado para outras transações até que a operação seja concluída.

A implementação foi realizada utilizando a anotação `@Lock` do Spring Data JPA, com o modo de bloqueio `LockModeType.PESSIMISTIC_WRITE`. Isso assegura que, ao consultar um cartão para modificá-lo (como ao atualizar o saldo), o registro seja bloqueado para outras transações de escrita, evitando inconsistências.

## **Como Executar o Projeto**

### **Pré-requisitos**
- Maven
- JDK17
- Make
- Docker

1. Clone o repositório:

    ```bash
    git clone https://github.com/vitorugoc/Teste-VR-Beneficios
    ```

2. Navegue até o diretório do projeto:

    ```bash
    cd Teste-VR-Beneficios
    ```

3. Execute o comando build do Makefile

    ```bash
    make build
    ```
4. Caso deseje parar o serviço use o comando stop do Makefile

    ```bash
    make stop
    ```
5. Caso deseje iniciar novamente o serviço use o comando run do Makefile

    ```bash
    make run
    ```

6. Acesse a API:

   A aplicação estará disponível em `http://localhost:8080`.

7. Caso não deseje utilizar o Makefile esses são os passos para iniciar a aplicação:

    ```bash
    mvn clean package
    cd docker
    docker compose up --build -d
    ```

