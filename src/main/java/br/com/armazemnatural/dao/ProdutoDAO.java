/**
 * @author Globalcode
 * Esta e uma interface que especifica metodos para obter e armazenar clientes,
 * nao importando onde estes objetos serao persistidos, ex: arquivo, collections, 
 * banco de dados, etc...
 */
/*
 * Globalcode - "The Developers Company"
 * 
 * Academia do Java
 * 
 */
package br.com.armazemnatural.dao;

import br.com.armazemnatural.beans.Produto;
import br.com.armazemnatural.util.GlobalcodeException;

import java.util.List;

public interface ProdutoDAO {

    /**
     * @param produto
     *            Produto a ser inserido na fonte de dados
     * @throws GlobalcodeException
     */
    public void salvar(Produto produto) throws GlobalcodeException;

    /**
     * @param nome
     *            nome do Produto a ser excluido da fonte de dados
     * @throws GlobalcodeException
     */
    public void excluir(String nome) throws GlobalcodeException;

    /**
     * @return java.util.List Colecao contendo todos os produtos da fonte da dados em questao
     * @throws GlobalcodeException
     */
    public List<Produto> getAllProdutos() throws GlobalcodeException;

    /**
     * @param nome
     *            NOME do produto a ser pesquisado na fonte de dados
     * @return Produto
     * @throws GlobalcodeException
     */
    public Produto getProdutoByNome(String nome) throws GlobalcodeException;
}