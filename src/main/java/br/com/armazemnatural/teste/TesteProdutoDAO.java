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

import br.com.armazemnatural.beans.Produto;
import br.com.armazemnatural.dao.*;
import br.com.armazemnatural.util.GlobalcodeException;
import br.com.armazemnatural.util.Teclado;

import java.io.IOException;
import java.util.List;

public class TesteProdutoDAO {

    public static ProdutoDAO dao = new ProdutoDAOSerialization();

    /**
     * Este metodo e reponsavel pela montagem do menu de opcoes do usuario: INSERIR | REMOVER | LISTAR | FINALIZAR
     */
    public static void montaMenu() {
        System.out.println("-------------------------");
        System.out.println("INSERIR NOVO PRODUTO : 1");
        System.out.println("REMOVER PRODUTO      : 2");
        System.out.println("LISTAR PRODUTOS      : 3");
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
     * Este metodo e reponsavel por ler um produto digitado pelo usuario, fazendo todas as "perguntas / interacoes"
     * necessarias para obter dos dados digitados.
     */
    public static Produto leProduto() {
        // Leitura dos dados do Produto do teclado
        Produto produto = null;
        try {
            System.out.println("Nome do produto: ");
            String nome = Teclado.le();
            System.out.println("Codigo do produto: ");
            Integer codigo = Integer.parseInt(Teclado.le());
            System.out.println("Quantidade do produto: ");
            Double quantidade = Double.parseDouble(Teclado.le());
            System.out.println("Quantidade minima do produto: ");
            Double minimo = Double.parseDouble(Teclado.le());
            int id = 0;
            produto = new Produto(codigo, nome, quantidade, minimo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // retorna o Produto lido
        return produto;
    }

    /**
     * Este metodo e reponsavel por ler o codigo de um produto digitado pelo usuario, fazendo todas as "perguntas /
     * interacoes" necessarias para obter os dados
     */
    public static String lenomeproduto() {
        // Leitura do nome do produto a ser removido
        String nome = "";
        try {
            // O nome deve obrigatoriamente ser uma String, por isto criamos um while
            // enquanto o usuario nao digitar um inteiro
            while (nome.equals("")) {
                System.out.println("Nome do produto : ");
                String strCodigo = Teclado.le();
                // Se o nome e valido, interrompemos o while
                if (strCodigo != null && strCodigo.length() > 0) {
                    nome = strCodigo;
                } // end if
            } // end while
        } catch (Exception e) {
            e.printStackTrace();
        }
        // retorna o Produto lido
        return nome;
    }

    public static void listarProdutos(List<Produto> produtos) {
        if (produtos != null) {
            for (Produto produto: produtos) {
                System.out.println(produto);
            }
        }
    }

    /**
     * Este metodo executa uma tarefa de acordo com a operacao passada como parametro.
     */
    public static void executarTarefa(int operacao) throws GlobalcodeException {
        switch (operacao) {
        case 1:
            System.out.println("Inserindo produto");
            Produto produto = leProduto();
            dao.salvar(produto);
            break;
        case 2:
            String nome = lenomeproduto();
            dao.excluir(nome);
            break;
        case 3:
            System.out.println("Listando produtos");
            List<Produto> produtos = dao.getAllProdutos();
            listarProdutos(produtos);
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
