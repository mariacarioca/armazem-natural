/*
 * Globalcode - "The Developers Company"
 * 
 * Academia do Java
 * 
 */
package br.com.globalcode.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import br.com.globalcode.dao.ClienteDAO;
import br.com.globalcode.dao.ClienteDAOSerialization;
import br.com.globalcode.beans.Cliente;
import br.com.globalcode.util.GlobalcodeException;

/**
 * 
 * Esta classe representa um Panel de cadastro de clientes, para ser visualizada deve ser adicionada a um JFrame ou
 * outro container que possa ser visualizado.
 * 
 * @author Globalcode
 * 
 */
public class PanelCadastroClientes extends JPanel {

    private JLabel lNome;
    private JTextField tfNome;
    private JLabel lTelefone;
    private JTextField tfTelefone;
    private JLabel lCPF;
    private JTextField tfCPF;
    private JLabel lID;
    private JTextField tfID;
    private JPanel pCadastroCliente;
    private JPanel pBotoesCadastro;
    private JButton bExcluir;
    private JButton bSalvar;
    private JButton bNovo;
    // 1 - Crie um atributo private do tipo ComboClientes
    ComboClientes cbClientes;

    /**
     * Este construtor monta um Panel para inserçao de dados de Cadastro de Clientes,
     */
    public PanelCadastroClientes() throws GlobalcodeException {
        this.setLayout(new BorderLayout());
        // Montagem do panel para insercao ou edicao de um cliente
        pCadastroCliente = montaPanelCadastroCliente();
        // Montagem do panel com botoes para salvar ou excluir clientes
        pBotoesCadastro = montaPanelBotoesCadastro();
        // 2 - Instancie o atributo ComboClientes
        cbClientes = new ComboClientes();
        /***************************************************************************************************************
         * Adicao dos paineis pCadastroCliente e pBotoesCadastro no panel principal
         **************************************************************************************************************/
        this.add(pCadastroCliente, BorderLayout.CENTER);
        this.add(pBotoesCadastro, BorderLayout.SOUTH);
        // 3. Adicione o atributo ComboClientes ao painel na posicao NORTE
        this.add(cbClientes, BorderLayout.NORTH);
        // 8. Associe a inner class CarregarClienteHandler ao atributo ComboClientes
        cbClientes.addActionListener(new CarregarClienteHandler());
    }

    /**
     * Metodo utilizado internamente para montagem do JPanel com o botao Salvar, por isto ele foi declarado como
     * private.
     * 
     * @return JPanel com o botao Salvar, para salvar um cliente
     */
    private JPanel montaPanelBotoesCadastro() {
        JPanel pBotoesCadastro = new JPanel();
        bSalvar = new JButton("Salvar");
        bSalvar.setMnemonic(KeyEvent.VK_S);
        bSalvar.addMouseListener(new SalvarClienteHandler());
        bNovo = new JButton("Novo");
        bNovo.setMnemonic(KeyEvent.VK_N);
        bNovo.addMouseListener(new NovoClienteHandler());
        bExcluir = new JButton("Excluir");
        bExcluir.setMnemonic(KeyEvent.VK_E);
        bExcluir.addActionListener(new ExcluirClienteHandler());
        pBotoesCadastro.add(bSalvar);
        pBotoesCadastro.add(bExcluir);
        pBotoesCadastro.add(bNovo);
        return pBotoesCadastro;
    }

    /**
     * Metodo utilizado internamente para montagem do JPanel para cadastro ou edicao de um cliente, por isto ele foi
     * declarado como private.
     * 
     * @return JPanel para cadastro ou ediçao de um cliente
     * 
     */
    private JPanel montaPanelCadastroCliente() {
        JPanel pCadastroCliente = new JPanel();
        GridLayout layout = new GridLayout(8, 1);
        pCadastroCliente.setLayout(layout);
        lNome = new JLabel("Nome:");
        tfNome = new JTextField();
        lTelefone = new JLabel("Telefone:");
        tfTelefone = new JTextField();
        lCPF = new JLabel("CPF:");
        tfCPF = new JTextField();
        lID = new JLabel("ID:");
        tfID = new JTextField();
        tfID.setEditable(false);
        pCadastroCliente.add(lNome);
        pCadastroCliente.add(tfNome);
        pCadastroCliente.add(lTelefone);
        pCadastroCliente.add(tfTelefone);
        pCadastroCliente.add(lCPF);
        pCadastroCliente.add(tfCPF);
        pCadastroCliente.add(lID);
        pCadastroCliente.add(tfID);
        return pCadastroCliente;
    }

    /**
     * Metodo utilizado internamente para fazer a limpeza dos JTextFields dados do cliente.
     * 
     */
    private void clearClienteFromPanel() {
        System.out.println("Limpando o painel de cadastro de clientes");
        this.tfCPF.setText("");
        this.tfNome.setText("");
        this.tfTelefone.setText("");
        this.tfID.setText("");
    }

    /**
     * Metodo utilizado internamente para fazer a leitura dos dados do cliente dos JTextFields referentes aos dados do
     * cliente, cria um objeto da classe Cliente e retorna o Cliente.
     * 
     * @return Cliente com os dados obtidos dos JTextFields apresentados
     * 
     */
    private Cliente loadClienteFromPanel() throws GlobalcodeException {
        System.out.println("Carregando o cliente em ediçao para um objeto da classe Cliente");
        String nome = tfNome.getText();
        String cpf = tfCPF.getText();
        String telefone = tfTelefone.getText();
        String strId = tfID.getText();
        int id = 0;
        if ((strId != null) && (strId.length() > 0)) {
            id = Integer.parseInt(strId);
        }
        Cliente clienteAtual = new Cliente(nome, telefone, cpf, id);
        return clienteAtual;
    }

    /**
     * Metodo utilizado internamente para fazer a escrita dos dados do cliente dentro dos JTextFields referentes aos
     * dados do cliente, cria um objeto da classe Cliente e retorna o Cliente.
     * 
     * @param Objeto
     *            cliente com os dados advindos de algum elemento externo
     * 
     */
    private void loadClienteToPanel(Cliente c) {
        if (c != null) {
            System.out.println("Carregando cliente selecionado para ediçao");
            this.tfCPF.setText(c.getCpf());
            this.tfID.setText(Integer.toString(c.getId()));
            this.tfNome.setText(c.getNome());
            this.tfTelefone.setText(c.getTelefone());
        }
    }
    

    class SalvarClienteHandler extends MouseAdapter {

        Cliente cliente = null;

        public void mouseClicked(MouseEvent arg0) {
            Cliente cliente = null;
            try {
                cliente = loadClienteFromPanel();
                // 4. Adicionar uma chamada ao ClienteDAOSerialization
                ClienteDAO dao = new ClienteDAOSerialization();
                dao.salvar(cliente);
                cbClientes.reloadFromDatabase();
                // Executar a gravacao do cliente chamando o metodo salvar do ClientesDB;
            } catch (GlobalcodeException e) {
                e.printStackTrace();
            }
            System.out.println(cliente);
        }
    }

    // 5. Criar uma classe interna NovoClienteHandler conforme definido na apostila
    class NovoClienteHandler extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            clearClienteFromPanel();
        }
    }

    // 7. Criar uma classe interna CarregarClienteHandler conforme definido na apostila
    class CarregarClienteHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Cliente c = (Cliente) cbClientes.getSelectedItem();
            loadClienteToPanel(c);
        }
    }
    
    class ExcluirClienteHandler implements ActionListener {
    
        public void actionPerformed(ActionEvent e) {
            Cliente c = (Cliente) cbClientes.getSelectedItem();
            if (c != null) {
                ClienteDAO dao = new ClienteDAOSerialization();
                try {
                    dao.excluir(c.getCpf());
                    clearClienteFromPanel();
                    cbClientes.reloadFromDatabase();
                } catch (GlobalcodeException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
    
    }
}