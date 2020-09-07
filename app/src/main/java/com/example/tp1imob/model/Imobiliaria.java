package com.example.tp1imob.model;
import java.io.Serializable;

public class Imobiliaria implements Serializable {
    private String email;
    private String nome;
    private String cnpj;
    private String id;
    private String endereco;
    private String telefone;
    private String celular;


    public Imobiliaria(){

    }

    public Imobiliaria(String email, String nome, String cnpj){
        this.cnpj = cnpj;
        this.email = email;
        this. nome = nome;
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
