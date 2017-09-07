/*
 * Globalcode - "The Developers Company"
 * 
 * Academia do Java
 * 
 */
package br.com.globalcode.gui;

import java.util.List;

import javax.swing.JComboBox;

import br.com.globalcode.beans.Cliente;
import br.com.globalcode.dao.ClienteDAO;
import br.com.globalcode.dao.ClienteDAOSerialization;
import br.com.globalcode.util.GlobalcodeException;

/**
 * @author Globalcode
 * 
 */
public class ComboClientes extends JComboBox {

    public ComboClientes() throws GlobalcodeException {
        this.reloadFromDatabase();
    }

    public final void reloadFromDatabase() throws GlobalcodeException {
        ClienteDAO db = new ClienteDAOSerialization();
        List<Cliente> clientes = db.getAllClientes();
        this.removeAllItems();
        for (Cliente cliente : clientes) {
            this.addItem(cliente);
        }
    }
}
