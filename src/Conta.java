import java.io.Serializable; //Interface para salvar/carregar arquivos
import java.time.LocalDate; //Data
import java.time.format.DateTimeFormatter; //Formatação de data
import java.util.ArrayList; //Lista dinâmica
import java.util.List; //Interface de lista

abstract class Conta implements Serializable {
    protected String numeroConta;
    protected String titular;
    protected double saldo;
    protected LocalDate dataAbertura;
    protected List<String> historicoTransacoes;
    
    public Conta(String numeroConta, String titular, double saldoInicial) throws ValorInvalido {
        if (saldoInicial < 0) {
            throw new ValorInvalido("Saldo inicial não pode ser negativo");
        }
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = saldoInicial;
        this.dataAbertura = LocalDate.now();
        this.historicoTransacoes = new ArrayList<>();
        adicionarTransacao("Conta criada - Saldo inicial: R$ " + String.format("%.2f", saldoInicial));
    }
    
    public void depositar(double valor) throws ValorInvalido {
        if (valor <= 0) {
            throw new ValorInvalido("Valor de depósito deve ser positivo");
        }
        saldo += valor;
        adicionarTransacao("Depósito: +R$ " + String.format("%.2f", valor));
    }
    
    public void depositar(double valor, String origem) throws ValorInvalido {
        if (valor <= 0) {
            throw new ValorInvalido("Valor de depósito deve ser positivo");
        }
        saldo += valor;
        adicionarTransacao("Depósito: +R$ " + String.format("%.2f", valor) + " - Origem: " + origem);
    }
    
    public abstract void sacar(double valor) throws SaldoInsuficiente, ValorInvalido;
    
    public double consultarSaldo() {
        return saldo;
    }
    
    public String gerarExtrato() {
        StringBuilder extrato = new StringBuilder();
        extrato.append("=== EXTRATO DA CONTA ===\n");
        extrato.append("Número: ").append(numeroConta).append("\n");
        extrato.append("Titular: ").append(titular).append("\n");
        extrato.append("Tipo: ").append(getTipoConta()).append("\n");
        extrato.append("Saldo: R$ ").append(String.format("%.2f", saldo)).append("\n");
        extrato.append("Data de Abertura: ").append(dataAbertura.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        extrato.append("\n=== HISTÓRICO ===\n");
        for (String transacao : historicoTransacoes) {
            extrato.append(transacao).append("\n");
        }
        return extrato.toString();
    }
    
    protected void adicionarTransacao(String transacao) {
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        historicoTransacoes.add("[" + data + "] " + transacao);
    }
    
    public abstract String getTipoConta();
    
    public String getNumeroConta() { return numeroConta; }
    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }
    public LocalDate getDataAbertura() { return dataAbertura; }
}
