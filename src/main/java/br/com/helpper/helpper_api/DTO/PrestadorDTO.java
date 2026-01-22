package br.com.helpper.helpper_api.DTO;

import br.com.helpper.helpper_api.ENTITY.Prestador;

import java.math.BigDecimal;
import java.util.List;

public class PrestadorDTO {
    private BigDecimal mediaAvaliacao;
    private Integer totalAvaliacoes;
    private String cidade;
    private List<String> servicos;
    private String bioProfissional;

    public PrestadorDTO() {

    }

    public PrestadorDTO(Prestador prestador) {
        this.mediaAvaliacao = prestador.getMediaAvaliacao();
        this.totalAvaliacoes = prestador.getTotalAvaliacoes();
        this.cidade = prestador.getCidade();
        this.servicos = prestador.getServicos();
        this.bioProfissional = prestador.getBioProfissional();
    }

}
