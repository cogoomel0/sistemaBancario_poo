import java.io.*; //Operações de entrada/saída
import java.util.ArrayList; //Lista dinâmica
import java.util.List; //Interface de listas

class GerenciadorContas {
    private List<Conta> contas;
    
    public GerenciadorContas() {
        contas = new ArrayList<>();
    }
    
    public void adicionarConta(Conta conta) throws ContaInvalida {
        if (buscarConta(conta.getNumeroConta()) != null) {
            throw new ContaInvalida("Já existe uma conta com este número");
        }
        contas.add(conta);
    }
    
    public void removerConta(String numeroConta) throws ContaInvalida {
        Conta conta = buscarConta(numeroConta);
        if (conta == null) {
            throw new ContaInvalida("Conta não encontrada");
        }
        contas.remove(conta);
    }
    
    public Conta buscarConta(String numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumeroConta().equals(numeroConta)) {
                return conta;
            }
        }
        return null;
    }
    
    public List<Conta> buscarContasPorTitular(String titular) {
        List<Conta> resultado = new ArrayList<>();
        for (Conta conta : contas) {
            if (conta.getTitular().toLowerCase().contains(titular.toLowerCase())) {
                resultado.add(conta);
            }
        }
        return resultado;
    }
    
    public List<Conta> getContas() {
        return new ArrayList<>(contas);
    }
    
    public void transferir(String numeroOrigem, String numeroDestino, double valor) 
            throws ContaInvalida, SaldoInsuficiente, ValorInvalido {
        
        Conta origem = buscarConta(numeroOrigem);
        Conta destino = buscarConta(numeroDestino);
        
        if (origem == null) {
            throw new ContaInvalida("Conta de origem não encontrada");
        }
        if (destino == null) {
            throw new ContaInvalida("Conta de destino não encontrada");
        }
        
        origem.sacar(valor);
        destino.depositar(valor);
    }
    
    public void salvarDados(String arquivo) throws ErroArquivo {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(contas);
        } catch (IOException e) {
            throw new ErroArquivo("Erro ao salvar dados", e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public void carregarDados(String arquivo) throws ErroArquivo {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            contas = (List<Conta>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ErroArquivo("Erro ao carregar dados", e);
        }
    }
}