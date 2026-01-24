package br.com.helpper.helpper_api.ENTITY;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CONTRATANTE")
public class Contratante extends Usuario {

    @Column(length = 20)
    private String telefone;

    @Column(length = 255)
    private String endereco;

    public Contratante() {
        super();
    }

    // Getters e Setters
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String getCpf() {
        return super.getCpf();
    }

    @Override
    public void setCpf(String cpf) {
        super.setCpf(cpf);
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}