/*
 * Globalcode - "The Developers Company"
 * 
 * Academia do Java
 * 
 */
package br.com.armazemnatural.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

import br.com.armazemnatural.beans.Produto;
import br.com.armazemnatural.beans.Produto;
import br.com.armazemnatural.dao.ProdutoDAO;
import br.com.armazemnatural.dao.ProdutoDAOSerialization;
import br.com.armazemnatural.util.GlobalcodeException;

/**
 * 
 * Esta classe representa um Panel de cadastro de produtos, para ser visualizada deve ser adicionada a um JFrame ou
 * outro container que possa ser visualizado.
 * 
 * @author Globalcode
 * 
 */
public class PanelCadastroProdutos extends JPanel {

    private JLabel lNome;
    private JTextField tfNome;
    private JLabel lCodigo;
    private JTextField tfCodigo;
    private JLabel lQuantidade;
    private JTextField tfQuantidade;
    private JLabel lMinimo;
    private JTextField tfMinimo;
    private JPanel pCadastroProduto;
    private JPanel pBotoesCadastro;
    private JButton bExcluir;
    private JButton bSalvar;
    private JButton bNovo;
    // 1 - Crie um atributo private do tipo ComboProdutos
    ComboProdutos cbProdutos;

    /**
     * Este construtor monta um Panel para inserçao de dados de Cadastro de Clientes,
     */
    public PanelCadastroProdutos() throws GlobalcodeException {
        this.setLayout(new BorderLayout());
        // Montagem do panel para insercao ou edicao de um produto
        pCadastroProduto = montaPanelCadastroProduto();
        // Montagem do panel com botoes para salvar ou excluir produtos
        pBotoesCadastro = montaPanelBotoesCadastro();
        // 2 - Instancie o atributo ComboProdutos
        cbProdutos = new ComboProdutos();
        /***************************************************************************************************************
         * Adicao dos paineis pCadastroProduto e pBotoesCadastro no panel principal
         **************************************************************************************************************/
        this.add(pCadastroProduto, BorderLayout.CENTER);
        this.add(pBotoesCadastro, BorderLayout.SOUTH);
        // 3. Adicione o atributo ComboProduto ao painel na posicao NORTE
        this.add(cbProdutos, BorderLayout.NORTH);
        // 8. Associe a inner class CarregarProduroHandler ao atributo ComboProdutos
        cbProdutos.addActionListener(new CarregarProdutoHandler());
    }

    /**
     * Metodo utilizado internamente para montagem do JPanel com o botao Salvar, por isto ele foi declarado como
     * private.
     * 
     * @return JPanel com o botao Salvar, para salvar um produto
     */
    private JPanel montaPanelBotoesCadastro() {
        JPanel pBotoesCadastro = new JPanel();
        bSalvar = new JButton("Salvar");
        bSalvar.setMnemonic(KeyEvent.VK_S);
        bSalvar.addMouseListener(new SalvarProdutoHandler());
        bNovo = new JButton("Novo");
        bNovo.setMnemonic(KeyEvent.VK_N);
        bNovo.addMouseListener(new NovoProdutoHandler());
        bExcluir = new JButton("Excluir");
        bExcluir.setMnemonic(KeyEvent.VK_E);
        bExcluir.addActionListener(new ExcluirProdutoHandler());
        pBotoesCadastro.add(bSalvar);
        pBotoesCadastro.add(bExcluir);
        pBotoesCadastro.add(bNovo);
        return pBotoesCadastro;
    }

    /**
     * Metodo utilizado internamente para montagem do JPanel para cadastro ou edicao de um produto, por isto ele foi
     * declarado como private.
     * 
     * @return JPanel para cadastro ou ediçao de um produto
     * 
     */
    private JPanel montaPanelCadastroProduto() {
        JPanel pCadastroProduto = new JPanel();
        GridLayout layout = new GridLayout(8, 1);
        pCadastroProduto.setLayout(layout);
        lNome = new JLabel("Nome:");
        tfNome = new JTextField();
        lCodigo = new JLabel("Codigo:");
        tfCodigo = new JTextField();
        lQuantidade = new JLabel("Quantidade:");
        tfQuantidade = new JTextField();
        lMinimo = new JLabel("Minimo:");
        tfMinimo = new JTextField();
        tfNome.setEditable(false);
        pCadastroProduto.add(lNome);
        pCadastroProduto.add(tfNome);
        pCadastroProduto.add(lCodigo);
        pCadastroProduto.add(tfCodigo);
        pCadastroProduto.add(lQuantidade);
        pCadastroProduto.add(tfQuantidade);
        pCadastroProduto.add(lMinimo);
        pCadastroProduto.add(tfMinimo);

        return pCadastroProduto;
    }

    /**
     * Metodo utilizado internamente para fazer a limpeza dos JTextFields dados do produto.
     * 
     */
    private void clearProdutoFromPanel() {
        System.out.println("Limpando o painel de cadastro de produtos");
        this.tfCodigo.setText("");
        this.tfNome.setText("");
        this.tfQuantidade.setText("");
        this.tfMinimo.setText("");
        
    }

    /**
     * Metodo utilizado internamente para fazer a leitura dos dados do produto dos JTextFields referentes aos dados do
     * produto, cria um objeto da classe Produto e retorna o Produto.
     * 
     * @return Produto com os dados obtidos dos JTextFields apresentados
     * 
     */
    private Produto loadProdutoFromPanel() throws GlobalcodeException {
        System.out.println("Carregando o produto em ediçao para um objeto da classe Produto");
        String nome = tfNome.getText();
        Integer codigo = Integer.parseInt(tfCodigo.getText());
        Double quantidade = Double.parseDouble(tfQuantidade.getText());
        Double minimo = Double.parseDouble(tfMinimo.getText());

        Produto produtoAtual = new Produto(codigo, nome, quantidade, minimo);
        return produtoAtual;
    }

    /**
     * Metodo utilizado internamente para fazer a escrita dos dados do cliente dentro dos JTextFields referentes aos
     * dados do cliente, cria um objeto da classe Cliente e retorna o Cliente.
     * 
     * @param Objeto
     *            cliente com os dados advindos de algum elemento externo
     * 
     */
    private void loadProdutoToPanel(Produto c) {
        if (c != null) {
            System.out.println("Carregando produto selecionado para ediçao");
            this.tfNome.setText(c.getNome());
            this.tfCodigo.setText(Integer.toString(c.getCodigo()));

        }
    }
    

    class SalvarProdutoHandler extends MouseAdapter {

        Produto produto = null;

        public void mouseClicked(MouseEvent arg0) {
            Produto produto = null;
            try {
                produto = loadProdutoFromPanel();
                // 4. Adicionar uma chamada ao ProdutoDAOSerialization
                ProdutoDAO dao = new ProdutoDAOSerialization();
                dao.salvar(produto);
                cbProdutos.reloadFromDatabase();
                // Executar a gravacao do produto chamando o metodo salvar do ProdutoDB;
            } catch (GlobalcodeException e) {
                e.printStackTrace();
            }
            System.out.println(produto);
        }
    }

    // 5. Criar uma classe interna NovoProdutoHandler conforme definido na apostila
    class NovoProdutoHandler extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            clearProdutoFromPanel();
        }
    }

    // 7. Criar uma classe interna CarregarClienteHandler conforme definido na apostila
    class CarregarProdutoHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Produto c = (Produto) cbProdutos.getSelectedItem();
            loadProdutoToPanel(c);
        }
    }
    
    class ExcluirProdutoHandler implements ActionListener {
    
        public void actionPerformed(ActionEvent e) {
            Produto c = (Produto) cbProdutos.getSelectedItem();
            if (c != null) {
                ProdutoDAO dao = new ProdutoDAOSerialization();
                try {
                    dao.excluir(c.getNome());
                    clearProdutoFromPanel();
                    cbProdutos.reloadFromDatabase();
                } catch (GlobalcodeException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
    
    }
}