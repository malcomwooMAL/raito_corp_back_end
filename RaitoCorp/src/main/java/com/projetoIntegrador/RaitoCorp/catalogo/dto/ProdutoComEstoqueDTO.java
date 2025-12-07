package com.projetoIntegrador.RaitoCorp.catalogo.dto;

import com.projetoIntegrador.RaitoCorp.catalogo.model.Produto;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProdutoComEstoqueDTO {
    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean ativo;
    private Boolean emDestaque;
    private Boolean isNovidade;
    private Boolean isPromocao;
    private BigDecimal precoOriginal;
    private String imagemUrl;
    private Integer quantidadeEstoque;
    private List<String> categorias;

    public ProdutoComEstoqueDTO() {}

    public ProdutoComEstoqueDTO(Produto produto, Integer quantidadeEstoque) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.ativo = produto.isAtivo();
        this.emDestaque = produto.isEmDestaque();
        this.isNovidade = produto.isNovidade();
        this.isPromocao = produto.isPromocao();
        this.precoOriginal = produto.getPrecoOriginal();
        this.imagemUrl = produto.getImagemUrl();
        this.quantidadeEstoque = quantidadeEstoque;
        this.categorias = List.of();
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getEmDestaque() {
        return emDestaque;
    }

    public void setEmDestaque(Boolean emDestaque) {
        this.emDestaque = emDestaque;
    }

    public Boolean getIsNovidade() {
        return isNovidade;
    }

    public void setIsNovidade(Boolean isNovidade) {
        this.isNovidade = isNovidade;
    }

    public Boolean getIsPromocao() {
        return isPromocao;
    }

    public void setIsPromocao(Boolean isPromocao) {
        this.isPromocao = isPromocao;
    }

    public BigDecimal getPrecoOriginal() {
        return precoOriginal;
    }

    public void setPrecoOriginal(BigDecimal precoOriginal) {
        this.precoOriginal = precoOriginal;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }
}
