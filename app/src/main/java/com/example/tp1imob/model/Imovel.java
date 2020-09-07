package com.example.tp1imob.model;

import java.io.Serializable;

public class Imovel implements Serializable {
    private String idimobiliaria;
    private String identificacao;
    private String cep;
    private String logradouro;
    private String bairro;
    private String numero;
    private String compAdicional;
    private String complemento;
    private String localidade;
    private String uf;
    private String id;

    public Imovel() {
    }

    public Imovel(String identificacao, String cep, String logradouro, String numero, String compAdicional, String bairro,  String localidade, String uf) {
        this.identificacao = identificacao;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.compAdicional = compAdicional;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getIdimobiliaria() {
        return idimobiliaria;
    }

    public void setIdimobiliaria(String idimobiliaria) {
        this.idimobiliaria = idimobiliaria;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCompAdicional() {
        return compAdicional;
    }

    public void setCompAdicional(String compAdicional) {
        this.compAdicional = compAdicional;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
