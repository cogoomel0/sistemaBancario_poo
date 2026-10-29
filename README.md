# Sistema Bancário
Projeto acadêmico desenvolvido em Java para a disciplina de Programação Orientada a Objetos (POO). O sistema simula operações básicas de um banco, foca no gerenciamento de diferentes tipos de contas bancárias, no tratamento de erros e persistência de dados (arquivos).

## Funcionalidades
- Gerenciamento de contas: criação e manipulação de diferentes tipos de contas;
- Operações básicas: saque, depósito e transferência entre contas;
- Tratamento de exceções customizadas: controle rigoroso de regras, que impede operações inválidas (ex: saldo insuficiente, valor negativo, entre outros).
### Regras específicas:
- Conta corrente: possui um limite de cheque especial;
- Conta poupança: possui limite de quantidade de saques;
- Conta salário: tem restrições específicas para depósito e transferência.

## Estrutura do Projeto
O projeto faz uso de herança e polimorfismo, possui uma classe base Conta e suas respectivas especializações.
### Classes principais
- `App.java`: classe principal com método main, responsável por inicializar o sistema;
- `SistemaBancário.java`: Interface/menu do usuário e integração das operações;
- `GerenciadorConta.java`: classe responsável pela lógica de armazenamento e busca das contas, como também persistência em arquivo.

### Modelos de Conta
- `Conta.java`: classe abstrata/superclasse com os atributos comuns (agência, número, saldo);
- `ContaCorrente.java`: herda de Conta.java e implementa regras de cheque especial;
- `ContaPoupanca.java`: herda de Conta.java e implementa limite de saques;
- `ContaSalario.java`: herda de Conta.java com regras limitadas de movimentação.

### Exceções Customizadas
- `SaldoInsuficiente.java`: Lançada ao tentar sacar mais do que o saldo (+ limite) disponível;
- `LimiteChequeEspecial.java`: Lançada quando a operação ultrapassa o limite de crédito;
- `LimiteSaques.java`: Lançada quando o limite de saques mensais de uma poupança é excedido;
- `ValorInvalido.java`: Lançada em tentativas de depósitos/saques com valores negativos ou zero;
- `ContaInvalida.java`: Lançada ao tentar acessar uma conta que não existe;
- `ErroArquivo.java`: Lançada em caso de falhas ao ler ou gravar dados no disco.

## Tecnologias utilizadas
- **Linguagem:** Java (JDK 8)
- **Paradigma:** POO
