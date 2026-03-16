import java.time.LocalDate; //Data

class ContaPoupanca extends Conta {
    private double taxaRendimentoMensal;
    private int diaAniversario;
    
    public ContaPoupanca(String numeroConta, String titular, double saldoInicial, 
                         double taxaRendimentoMensal, int diaAniversario) 
                         throws ValorInvalido {
        super(numeroConta, titular, saldoInicial);
        if (taxaRendimentoMensal < 0) {
            throw new ValorInvalido("Taxa de rendimento não pode ser negativa");
        }
        if (diaAniversario < 1 || diaAniversario > 31) {
            throw new ValorInvalido("Dia de aniversário deve estar entre 1 e 31");
        }
        this.taxaRendimentoMensal = taxaRendimentoMensal;
        this.diaAniversario = diaAniversario;
    }
    
    @Override
    public void sacar(double valor) throws SaldoInsuficiente, ValorInvalido {
        if (valor <= 0) {
            throw new ValorInvalido("Valor de saque deve ser positivo");
        }
        if (saldo < valor) {
            throw new SaldoInsuficiente("Saldo insuficiente. Disponível: R$ " + String.format("%.2f", saldo));
        }
        saldo -= valor;
        adicionarTransacao("Saque: -R$ " + String.format("%.2f", valor));
    }
    
    public double calcularRendimento() {
        return saldo * (taxaRendimentoMensal / 100);
    }
    
    protected void aplicarRendimento(LocalDate data) {
        int diaEfetivo = Math.min(diaAniversario, data.lengthOfMonth());
        if (data.getDayOfMonth() == diaEfetivo) {
            double rendimento = calcularRendimento();
            saldo += rendimento;
            adicionarTransacao("Rendimento: +R$ " + String.format("%.2f", rendimento));
        }
    }
    
    @Override
    public String getTipoConta() {
        return "Conta Poupança";
    }
}
