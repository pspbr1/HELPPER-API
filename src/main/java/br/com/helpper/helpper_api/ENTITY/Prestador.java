package br.com.helpper.helpper_api.ENTITY;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PRESTADOR")
public class Prestador extends Usuario {

    private Boolean verificado = false;
    private BigDecimal mediaAvaliacao;
    private Integer totalAvaliacoes;
    private String cidade;

    @ElementCollection
    private List<String> servicos = new ArrayList<>();

    private String bioProfissional;

    public Prestador() {

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

    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
