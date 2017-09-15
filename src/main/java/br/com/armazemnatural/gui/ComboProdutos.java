/*
 * Globalcode - "The Developers Company"
 * 
 * Academia do Java
 * 
 */
package br.com.armazemnatural.gui;

import java.util.List;

import javax.swing.JComboBox;

import br.com.armazemnatural.beans.Produto;
import br.com.armazemnatural.dao.ProdutoDAO;
import br.com.armazemnatural.dao.ProdutoDAOSerialization;
import br.com.armazemnatural.util.GlobalcodeException;

/**
 * @author Globalcode
 * 
 */
public class ComboProdutos extends JComboBox {

    public ComboProdutos() throws GlobalcodeException {
        this.reloadFromDatabase();
    }

    public final void reloadFromDatabase() throws GlobalcodeException {
        ProdutoDAO db = new ProdutoDAOSerialization();
        List<Produto> produtos = db.getAllProdutos();
        this.removeAllItems();
        for (Produto produto : produtos) {
            this.addItem(produto);
        }
    }
}
