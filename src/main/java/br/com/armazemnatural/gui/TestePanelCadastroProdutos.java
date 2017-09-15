/*
 * Globalcode - "The Developers Company"
 * 
 * Academia do Java
 * 
 */
package br.com.armazemnatural.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Globalcode
 * 
 */
public class TestePanelCadastroProdutos extends JFrame {

    public TestePanelCadastroProdutos(String titulo) {
        super(titulo);
        // Indica que quando a janela for fechada, a execuçao da classe
        // sera finalizada, caso contrario, a classe continuara na memoria
        // depois que a janela for fechada
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            this.getContentPane().add(new PanelCadastroProdutos());
            super.setSize(300, 250);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Mensagem de Erro", JOptionPane.OK_OPTION);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestePanelCadastroProdutos gf = new TestePanelCadastroProdutos("Teste Panel Cadastro Produtos");
        gf.setVisible(true);
    }
}
