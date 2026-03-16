import javax.swing.*; //Interface gráfica
import javax.swing.table.DefaultTableModel; //Modelo de dados
import java.awt.*; //Layouts e componentes gráficos
import java.io.File; //Arquivos/diretórios
import java.util.List; //Interface de listas

public class SistemaBancario extends JFrame {
    private GerenciadorContas gerenciador;
    private JTable tabelaContas;
    private DefaultTableModel modeloTabela;
    private JTextField campoBusca;
    
    public SistemaBancario() {
        gerenciador = new GerenciadorContas();
        inicializarInterface();
    }
    
    private void inicializarInterface() {
        setTitle("Sistema de Gerenciamento de Contas Bancárias");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //Painel superior - busca
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelSuperior.add(new JLabel("Buscar por titular:"));
        campoBusca = new JTextField(20);
        painelSuperior.add(campoBusca);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarContas());
        painelSuperior.add(btnBuscar);
        
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> {
            campoBusca.setText("");
            atualizarTabela();
        });
        painelSuperior.add(btnLimpar);
        
        //Tabela
        String[] colunas = {"Número", "Titular", "Tipo", "Saldo", "Data Abertura"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaContas = new JTable(modeloTabela);
        JScrollPane scrollTabela = new JScrollPane(tabelaContas);
        
        //Painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(3, 3, 5, 5));
        
        JButton btnCC = new JButton("Nova Conta Corrente");
        btnCC.addActionListener(e -> adicionarContaCorrente());
        
        JButton btnCP = new JButton("Nova Conta Poupança");
        btnCP.addActionListener(e -> adicionarContaPoupanca());
        
        JButton btnCS = new JButton("Nova Conta Salário");
        btnCS.addActionListener(e -> adicionarContaSalario());
        
        JButton btnDepositar = new JButton("Depositar");
        btnDepositar.addActionListener(e -> realizarDeposito());
        
        JButton btnSacar = new JButton("Sacar");
        btnSacar.addActionListener(e -> realizarSaque());
        
        JButton btnTransferir = new JButton("Transferir");
        btnTransferir.addActionListener(e -> realizarTransferencia());
        
        JButton btnExtrato = new JButton("Ver Extrato");
        btnExtrato.addActionListener(e -> visualizarExtrato());
        
        JButton btnRemover = new JButton("Remover Conta");
        btnRemover.addActionListener(e -> removerConta());
        
        JButton btnSalvar = new JButton("Salvar Dados");
        btnSalvar.addActionListener(e -> salvarDados());
        
        JButton btnCarregar = new JButton("Carregar Dados");
        btnCarregar.addActionListener(e -> carregarDados());
        
        painelBotoes.add(btnCC);
        painelBotoes.add(btnCP);
        painelBotoes.add(btnCS);
        painelBotoes.add(btnDepositar);
        painelBotoes.add(btnSacar);
        painelBotoes.add(btnTransferir);
        painelBotoes.add(btnExtrato);
        painelBotoes.add(btnRemover);
        painelBotoes.add(new JLabel());
        
        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.add(painelBotoes, BorderLayout.CENTER);
        
        JPanel painelArquivos = new JPanel(new FlowLayout());
        painelArquivos.add(btnSalvar);
        painelArquivos.add(btnCarregar);
        painelInferior.add(painelArquivos, BorderLayout.SOUTH);
        
        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
        painelPrincipal.add(scrollTabela, BorderLayout.CENTER);
        painelPrincipal.add(painelInferior, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private void adicionarContaCorrente() {
        JTextField numConta = new JTextField();
        JTextField titular = new JTextField();
        JTextField saldoInicial = new JTextField();
        JTextField limiteCheque = new JTextField();
        JTextField taxaManutencao = new JTextField();
        
        Object[] campos = {
            "Número da Conta:", numConta,
            "Titular:", titular,
            "Saldo Inicial:", saldoInicial,
            "Limite Cheque Especial:", limiteCheque,
            "Taxa de Manutenção:", taxaManutencao
        };
        
        int resultado = JOptionPane.showConfirmDialog(this, campos, "Nova Conta Corrente", JOptionPane.OK_CANCEL_OPTION);
        
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                ContaCorrente conta = new ContaCorrente(
                    numConta.getText(),
                    titular.getText(),
                    Double.parseDouble(saldoInicial.getText()),
                    Double.parseDouble(limiteCheque.getText()),
                    Double.parseDouble(taxaManutencao.getText())
                );
                gerenciador.adicionarConta(conta);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void adicionarContaPoupanca() {
        JTextField numConta = new JTextField();
        JTextField titular = new JTextField();
        JTextField saldoInicial = new JTextField();
        JTextField taxaRendimento = new JTextField();
        JTextField diaAniversario = new JTextField();
        
        Object[] campos = {
            "Número da Conta:", numConta,
            "Titular:", titular,
            "Saldo Inicial:", saldoInicial,
            "Taxa de Rendimento (%):", taxaRendimento,
            "Dia de Aniversário:", diaAniversario
        };
        
        int resultado = JOptionPane.showConfirmDialog(this, campos, "Nova Conta Poupança", JOptionPane.OK_CANCEL_OPTION);
        
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                ContaPoupanca conta = new ContaPoupanca(
                    numConta.getText(),
                    titular.getText(),
                    Double.parseDouble(saldoInicial.getText()),
                    Double.parseDouble(taxaRendimento.getText()),
                    Integer.parseInt(diaAniversario.getText())
                );
                gerenciador.adicionarConta(conta);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void adicionarContaSalario() {
        JTextField numConta = new JTextField();
        JTextField titular = new JTextField();
        JTextField saldoInicial = new JTextField();
        JTextField empresa = new JTextField();
        JTextField limiteSaques = new JTextField();
        
        Object[] campos = {
            "Número da Conta:", numConta,
            "Titular:", titular,
            "Saldo Inicial:", saldoInicial,
            "Empresa:", empresa,
            "Limite de Saques:", limiteSaques
        };
        
        int resultado = JOptionPane.showConfirmDialog(this, campos, "Nova Conta Salário", JOptionPane.OK_CANCEL_OPTION);
        
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                ContaSalario conta = new ContaSalario(
                    numConta.getText(),
                    titular.getText(),
                    Double.parseDouble(saldoInicial.getText()),
                    empresa.getText(),
                    Integer.parseInt(limiteSaques.getText())
                );
                gerenciador.adicionarConta(conta);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void realizarDeposito() {
        int linha = tabelaContas.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma conta", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String numeroConta = (String) modeloTabela.getValueAt(linha, 0);
        Conta conta = gerenciador.buscarConta(numeroConta);
        
        String valorStr = JOptionPane.showInputDialog(this, "Valor do depósito:");
        if (valorStr != null && !valorStr.isEmpty()) {
            try {
                double valor = Double.parseDouble(valorStr);
                conta.depositar(valor);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Depósito realizado!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void realizarSaque() {
        int linha = tabelaContas.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma conta", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String numeroConta = (String) modeloTabela.getValueAt(linha, 0);
        Conta conta = gerenciador.buscarConta(numeroConta);
        
        String valorStr = JOptionPane.showInputDialog(this, "Valor do saque:");
        if (valorStr != null && !valorStr.isEmpty()) {
            try {
                double valor = Double.parseDouble(valorStr);
                conta.sacar(valor);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Saque realizado!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void realizarTransferencia() {
        String numeroOrigem = JOptionPane.showInputDialog(this, "Número da conta de origem:");
        if (numeroOrigem == null || numeroOrigem.isEmpty()) return;
        
        String numeroDestino = JOptionPane.showInputDialog(this, "Número da conta de destino:");
        if (numeroDestino == null || numeroDestino.isEmpty()) return;
        
        String valorStr = JOptionPane.showInputDialog(this, "Valor:");
        if (valorStr != null && !valorStr.isEmpty()) {
            try {
                double valor = Double.parseDouble(valorStr);
                gerenciador.transferir(numeroOrigem, numeroDestino, valor);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Transferência realizada!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void visualizarExtrato() {
        int linha = tabelaContas.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma conta", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String numeroConta = (String) modeloTabela.getValueAt(linha, 0);
        Conta conta = gerenciador.buscarConta(numeroConta);
        
        JTextArea area = new JTextArea(conta.gerarExtrato());
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scroll, "Extrato", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void removerConta() {
        int linha = tabelaContas.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma conta", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String numeroConta = (String) modeloTabela.getValueAt(linha, 0);
        
        int confirmacao = JOptionPane.showConfirmDialog(this, 
            "Remover conta " + numeroConta + "?", 
            "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                gerenciador.removerConta(numeroConta);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Conta removida!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void buscarContas() {
        String titular = campoBusca.getText().trim();
        if (titular.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um nome", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<Conta> resultados = gerenciador.buscarContasPorTitular(titular);
        
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma conta encontrada", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        modeloTabela.setRowCount(0);
        for (Conta conta : resultados) {
            Object[] linha = {
                conta.getNumeroConta(),
                conta.getTitular(),
                conta.getTipoConta(),
                String.format("R$ %.2f", conta.getSaldo()),
                conta.getDataAbertura().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void salvarDados() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("contas.dat"));
        
        int resultado = fileChooser.showSaveDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                String arquivo = fileChooser.getSelectedFile().getAbsolutePath();
                gerenciador.salvarDados(arquivo);
                JOptionPane.showMessageDialog(this, "Dados salvos!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void carregarDados() {
        JFileChooser fileChooser = new JFileChooser();
        
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                String arquivo = fileChooser.getSelectedFile().getAbsolutePath();
                gerenciador.carregarDados(arquivo);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Dados carregados!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Conta conta : gerenciador.getContas()) {
            Object[] linha = {
                conta.getNumeroConta(),
                conta.getTitular(),
                conta.getTipoConta(),
                String.format("R$ %.2f", conta.getSaldo()),
                conta.getDataAbertura().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            };
            modeloTabela.addRow(linha);
        }
    }
}