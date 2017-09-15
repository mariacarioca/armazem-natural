/*
 * Globalcode - "The Developers Company"
 *
 * Academia do Java
 *
 *
 */
package br.com.armazemnatural.dao;

import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import br.com.armazemnatural.beans.Produto;
import br.com.armazemnatural.util.GlobalcodeException;
import br.com.armazemnatural.visitor.ListaArquivosVisitor;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

public class ProdutoDAOSerialization implements ProdutoDAO {

	public void salvar(Produto produto) throws GlobalcodeException {

		String nomeArquivo = "Produto" + produto.getNome() + ".ser";

		if (produto.getCodigo() == 0) {
			// definicao de id por sorteio
			produto.setCodigo(1 + (int) (999999 * Math.random()));
		}

		try (FileOutputStream fos = new FileOutputStream(nomeArquivo);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(produto);
		} catch (FileNotFoundException e) {
			throw new GlobalcodeException(e, "Arquivo Inexistente!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void excluir(String codigo) throws GlobalcodeException {
		Path p = Paths.get("Produto" + codigo + ".ser");
		if (Files.exists(p)) {
			try {
				Files.delete(p);
			} catch (IOException ex) {
				String msg = "Impossivel remover o arquivo " + p.getFileName()
						+ " - deve estar sendo utilizado por outro processo";
				throw new GlobalcodeException(msg);
			}
		} else {
			throw new GlobalcodeException("Produto não encontrado com codigo = " + codigo);
		}
	}

	public List<Produto> getAllProdutos() throws GlobalcodeException {

		// este objeto representa o diretorio de trabalho atual
		Path diretorioAtual = Paths.get(".");
		System.out.println("diretorio atual:" + diretorioAtual.toAbsolutePath());

		List<Produto> todosProdutos = new ArrayList<>();

		// 1.Inicialize o padrao de acordo com o pedido na apostila
		String padrao = "glob:**/Produto*.ser";

		// 2.Crie um PathMatcher com o padrao criado
		PathMatcher matcher = FileSystems.getDefault().getPathMatcher(padrao);

		// 3.Crie um ListaArquivosVisitor com o PathMatcher criado
		ListaArquivosVisitor visitor = new ListaArquivosVisitor(matcher);

		try {
			// navega recursivamente pelo diretorio atual processando os eventos com visitor
			Files.walkFileTree(diretorioAtual, visitor);

			// 4.Leia a lista de arquivos gerada pelo visitor
			List<Path> arquivosFiltrados = visitor.getArquivos();

			// montagem da lista de clientes recuperados do sistema de arquivos
			for (Path arquivo : arquivosFiltrados) {
				System.out.println(arquivo);
				Produto umProduto = getProduto(arquivo);
				todosProdutos.add(umProduto);
			}
		} catch (IOException e) {
			throw new GlobalcodeException("Erro na leitura dos clientes");
		}

		return todosProdutos;

	}

	private Produto getProduto(Path arquivo) throws GlobalcodeException {
		String nomeArquivo = arquivo.toAbsolutePath().toString();
		return lerProduto(nomeArquivo);
	}

	public Produto getProdutoByNome(String codigo) throws GlobalcodeException {
		String nomeArquivo = "Produto" + codigo + ".ser";
		return lerProduto(nomeArquivo);
	}

	private Produto lerProduto(String nomeArquivo) throws GlobalcodeException {
		Produto c = null;
		try (FileInputStream fis = new FileInputStream(nomeArquivo);
				ObjectInputStream ois = new ObjectInputStream(fis)) {

			c = (Produto) ois.readObject();
		} catch (IOException e) {
			throw new GlobalcodeException(e, "Erro ao executar operacao de leitura");
		} catch (ClassNotFoundException e) {
			throw new GlobalcodeException(e, "Erro ao localizar a classe Cliente.class");
		}
		return c;
	}

}
