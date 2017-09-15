/*
 * Globalcode - "The Developers Company"
 * 
 * Academia do Java
 * 
 * 
 */
package br.com.armazemnatural.beans;



public class Produto implements java.io.Serializable{
	private Integer codigo;
    private String nome;
    private Double quantidade;
    private Double qtminimo;
    
    public Produto() {}
    
	public Produto(Integer codigo, String nome, Double quantidade, Double qtminimo) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.quantidade = quantidade;
		this.qtminimo = qtminimo;
	}
	
	
	public Produto(String nome, Double quantidade, Double qtminimo) {
		super();
		this.nome = nome;
		this.quantidade = quantidade;
		this.qtminimo = qtminimo;
	}

	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	public Double getQtminimo() {
		return qtminimo;
	}
	public void setQtminimo(Double qtminimo) {
		this.qtminimo = qtminimo;
	}
    
    

}
