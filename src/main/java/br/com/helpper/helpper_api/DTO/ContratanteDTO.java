/*
 * ContratanteDTO.java
 * DTO para retornar dados completos do Contratante
 * Herda campos de usuário e adiciona informações específicas
 * Usado em respostas de API que retornam perfil do contratante
 */
package br.com.helpper.helpper_api.DTO;

import br.com.helpper.helpper_api.ENTITY.Contratante;

public class ContratanteDTO {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private String endereco;
    private String fotoPerfilUrl;

    public ContratanteDTO() {
    }

    public ContratanteDTO(Contratante contratante) {
        this.id = contratante.getId();
        this.nome = contratante.getNome();
        this.email = contratante.getEmail();
        this.cpf = contratante.getCpf();
        this.telefone = String.valueOf(contratante.getTelefone());
        this.endereco = contratante.getEndereco();
        this.fotoPerfilUrl = contratante.getFotoPerfilUrl();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }
}