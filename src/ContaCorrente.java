class ContaCorrente extends Conta {
    private double limiteChequeEspecial;
    private double taxaManutencaoMensal;
    
    public ContaCorrente(String numeroConta, String titular, double saldoInicial, 
                         double limiteChequeEspecial, double taxaManutencaoMensal) 
                         throws ValorInvalido {
        super(numeroConta, titular, saldoInicial);
        this.limiteChequeEspecial = limiteChequeEspecial;
        this.taxaManutencaoMensal = taxaManutencaoMensal;
    }
    
    @Override
    public void sacar(double valor) throws SaldoInsuficiente, ValorInvalido {
        if (valor <= 0) {
            throw new ValorInvalido("Valor de saque deve ser positivo");
        }
        
        double saldoDisponivel = saldo + limiteChequeEspecial;
        if (valor > saldoDisponivel) {
            throw new SaldoInsuficiente("Saldo insuficiente. Disponível: R$ " + String.format("%.2f", saldoDisponivel));
        }
        
        saldo -= valor;
        adicionarTransacao("Saque: -R$ " + String.format("%.2f", valor));
    }
    
    public void cobrarTaxaManutencao() {
        saldo -= taxaManutencaoMensal;
        adicionarTransacao("Taxa de manutenção: -R$ " + String.format("%.2f", taxaManutencaoMensal));
    }
    
    @Override
    public String getTipoConta() {
        return "Conta Corrente";
    }
}