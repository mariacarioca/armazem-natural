/*
 * Globalcode - "The Developers Company"
 * 
 * Academia do Java
 * 
 * Esta classe oferece alguns metodos para interacao com usuario via tela console.
 * 
 * 
 */
package br.com.armazemnatural.teste;

import br.com.armazemnatural.beans.Cliente;
import br.com.armazemnatural.dao.*;
import br.com.armazemnatural.util.GlobalcodeException;
import br.com.armazemnatural.util.Teclado;

import java.io.IOException;
import java.util.List;

public class TesteClienteDAO {

    public static ClienteDAO dao = new ClienteDAOSerialization();

    /**
     * Este metodo e reponsavel pela montagem do menu de opcoes do usuario: INSERIR | REMOVER | LISTAR | FINALIZAR
     */
    public static void montaMenu() {
        System.out.println("-------------------------");
        System.out.println("INSERIR NOVO CLIENTE : 1");
        System.out.println("REMOVER CLIENTE      : 2");
        System.out.println("LISTAR CLIENTES      : 3");
        System.out.println("FINALIZAR            : 0");
        System.out.println("-------------------------");
        System.out.println("ESCOLHA A OPERACAO: ");
    }

    /**
     * Este metodo e responsavel por retornar a opcao de menu do usuario
     */
    public static int leOperacao() throws IOException, NumberFormatException {
        // Utiliza o metodo estatico da classe Teclado para ler a opcao digitada pelo usuario
        String op = Teclado.le();
        // Transforma a opcao, que e uma String em int
        int operacao = Integer.parseInt(op);
        return operacao;
    }

    /**
     * Este metodo e reponsavel por ler um cliente digitado pelo usuario, fazendo todas as "perguntas / interacoes"
     * necessarias para obter dos dados digitados.
     */
    public static Cliente leCliente() {
        // Leitura dos dados do Cliente do teclado
        Cliente cliente = null;
        try {
            System.out.println("Nome do cliente: ");
            // Leitura do nome
            String nome = Teclado.le();
            System.out.println("Telefone do cliente: ");
            // Leitura do telefone
            String telefone = Teclado.le();
            System.out.println("CPF do cliente: ");
            // Leitura do CPF
            String cpf = Teclado.le();
            int id = 0;
            cliente = new Cliente(nome, telefone, cpf, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // retorna o Cliente lido
        return cliente;
    }

    /**
     * Este metodo e reponsavel por ler o ID de um cliente digitado pelo usuario, fazendo todas as "perguntas /
     * interacoes" necessarias para obter os dados
     */
    public static String leCPFCliente() {
        // Leitura do CPF do Cliente a ser removido
        String cpf = "";
        try {
            // O cpf deve obrigatoriamente ser uma String, por isto criamos um while
            // enquanto o usuario nao digitar um inteiro
            while (cpf.equals("")) {
                System.out.println("CPF do cliente : ");
                String strId = Teclado.le();
                // Se o cpf e valido, interrompemos o while
                if (strId != null && strId.length() > 0) {
                    cpf = strId;
                } // end if
            } // end while
        } catch (Exception e) {
            e.printStackTrace();
        }
        // retorna o Cliente lido
        return cpf;
    }

    public static void listarClientes(List<Cliente> clientes) {
        if (clientes != null) {
            for (Cliente cliente: clientes) {
                System.out.println(cliente);
            }
        }
    }

    /**
     * Este metodo executa uma tarefa de acordo com a operacao passada como parametro.
     */
    public static void executarTarefa(int operacao) throws GlobalcodeException {
        switch (operacao) {
        case 1:
            System.out.println("Inserindo cliente");
            Cliente cliente = leCliente();
            dao.salvar(cliente);
            break;
        case 2:
            String clienteID = leCPFCliente();
            dao.excluir(clienteID);
            break;
        case 3:
            System.out.println("Listando clientes");
            List<Cliente> clientes = dao.getAllClientes();
            listarClientes(clientes);
            break;
        }
    }

    public static void main(String[] args) throws GlobalcodeException {
        int TERMINAR = 0;
        int operacao = 1;
        do {
            try {
                montaMenu();
                operacao = leOperacao();
                if (operacao == TERMINAR) {
                    break;
                } else {
                    executarTarefa(operacao);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("Operacao invalida");
            } catch (Exception e ) {
                System.out.println(e.getMessage());
            }
        } while (operacao != TERMINAR);
    }
}
