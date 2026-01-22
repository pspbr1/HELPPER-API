package br.com.helpper.helpper_api.ENTITY;

import jakarta.persistence.*;


import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CONTRATANTE")
public class Contratante extends Usuario {

    private String cpf;

    private int telefone;

    private String endereco;

    public Contratante() {
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getTelefone() {
        return telefone;
    }
    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
}

