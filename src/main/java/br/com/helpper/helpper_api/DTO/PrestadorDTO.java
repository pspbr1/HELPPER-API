/*
 * PrestadorDTO.java
 * DTO para retornar dados completos do Prestador de Serviços
 * Inclui informações profissionais, avaliações e serviços oferecidos
 * Usado em listagens de prestadores e perfil profissional
 */
package br.com.helpper.helpper_api.DTO;

import br.com.helpper.helpper_api.ENTITY.Prestador;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PrestadorDTO {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private Boolean verificado;
    private BigDecimal mediaAvaliacao;
    private Integer totalAvaliacoes;
    private String cidade;
    private List<String> servicos = new ArrayList<>();
    private String bioProfissional;
    private String fotoPerfilUrl;

    public PrestadorDTO() {
    }

    public PrestadorDTO(Prestador prestador) {
        this.id = prestador.getId();
        this.nome = prestador.getNome();
        this.email = prestador.getEmail();
        this.cpf = prestador.getCpf();
        this.verificado = prestador.getVerificado();
        this.mediaAvaliacao = prestador.getMediaAvaliacao();
        this.totalAvaliacoes = prestador.getTotalAvaliacoes();
        this.cidade = prestador.getCidade();
        this.servicos = prestador.getServicos() != null
                ? new ArrayList<>(prestador.getServicos())
                : new ArrayList<>();
        this.bioProfissional = prestador.getBioProfissional();
        this.fotoPerfilUrl = prestador.getFotoPerfilUrl();
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

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

    public BigDecimal getMediaAvaliacao() {
        return mediaAvaliacao;
    }

    public void setMediaAvaliacao(BigDecimal mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }

    public Integer getTotalAvaliacoes() {
        return totalAvaliacoes;
    }

    public void setTotalAvaliacoes(Integer totalAvaliacoes) {
        this.totalAvaliacoes = totalAvaliacoes;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public List<String> getServicos() {
        return servicos;
    }

    public void setServicos(List<String> servicos) {
        this.servicos = servicos;
    }

    public String getBioProfissional() {
        return bioProfissional;
    }

    public void setBioProfissional(String bioProfissional) {
        this.bioProfissional = bioProfissional;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }
}