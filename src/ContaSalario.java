class ContaSalario extends Conta {
    private String empresaVinculada;
    private int limiteSaquesGratuitos;
    private int saquesRealizados;
    
    public ContaSalario(String numeroConta, String titular, double saldoInicial, 
                        String empresaVinculada, int limiteSaquesGratuitos) 
                        throws ValorInvalido {
        super(numeroConta, titular, saldoInicial);
        this.empresaVinculada = empresaVinculada;
        this.limiteSaquesGratuitos = limiteSaquesGratuitos;
        this.saquesRealizados = 0;
    }
    
    @Override
    public void sacar(double valor) throws SaldoInsuficiente, ValorInvalido {
        if (valor <= 0) {
            throw new ValorInvalido("Valor de saque deve ser positivo");
        }
        if (!verificarSaquesGratuitos()) {
            return;
        }
        if (this.saldo < valor) {
            throw new SaldoInsuficiente("Saldo insuficiente");
        }
        this.saldo -= valor;
        saquesRealizados++;
    }
    
    public boolean verificarSaquesGratuitos() {
        return saquesRealizados < limiteSaquesGratuitos;
    }
    
    public void receberSalario(double valor) throws ValorInvalido {
        depositar(valor);
        saquesRealizados = 0;
    }
    
    @Override
    public String getTipoConta() {
        return "Conta Salário";
    }
    
    @Override
    public void depositar(double valor, String origem) throws ValorInvalido {
        if (valor <= 0) {
            throw new ValorInvalido("Valor de depósito deve ser positivo");
        }
        
        //Depósitos apenas de empresas vinculadas ou transferências internas
        if (!origem.equals(empresaVinculada) && !origem.equals("Transferência")) {
            throw new ValorInvalido("Conta salário só aceita depósitos da empresa vinculada: " + empresaVinculada);
        }
        
        saldo += valor;
        adicionarTransacao("Depósito: +R$ " + String.format("%.2f", valor) + " - Origem: " + origem);
    }

    public String getEmpresaVinculada() {
        return empresaVinculada;
    }
}