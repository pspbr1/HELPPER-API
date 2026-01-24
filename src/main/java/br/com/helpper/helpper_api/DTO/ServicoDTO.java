/*
 * ServicoDTO.java
 * DTO para retornar dados de um serviço/solicitação
 * Inclui informações básicas do contratante sem expor entidade completa
 * Usado em listagens e detalhes de serviços
 */
package br.com.helpper.helpper_api.DTO;

import br.com.helpper.helpper_api.ENTITY.Servico;
import br.com.helpper.helpper_api.ENTITY.StatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ServicoDTO {

    private Long id;
    private BigDecimal orcamento;
    private String tipo;
    private LocalDateTime data;
    private String descricao;
    private StatusEnum status;

    // Informações do contratante (apenas dados necessários)
    private Long contratanteId;
    private String contratanteNome;
    private String enderecoDoContratante;

    public ServicoDTO() {
    }

    public ServicoDTO(Servico servico) {
        this.id = servico.getId();
        this.orcamento = servico.getOrcamento();
        this.tipo = servico.getTipo();
        this.data = servico.getData();
        this.descricao = servico.getDescricao();
        this.status = servico.getStatus();

        // Preenche dados do contratante se existir
        if (servico.getContratante() != null) {
            this.contratanteId = servico.getContratante().getId();
            this.contratanteNome = servico.getContratante().getNome();
            this.enderecoDoContratante = servico.getContratante().getEndereco();
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(BigDecimal orcamento) {
        this.orcamento = orcamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Long getContratanteId() {
        return contratanteId;
    }

    public void setContratanteId(Long contratanteId) {
        this.contratanteId = contratanteId;
    }

    public String getContratanteNome() {
        return contratanteNome;
    }

    public void setContratanteNome(String contratanteNome) {
        this.contratanteNome = contratanteNome;
    }

    public String getEnderecoDoContratante() {
        return enderecoDoContratante;
    }

    public void setEnderecoDoContratante(String enderecoDoContratante) {
        this.enderecoDoContratante = enderecoDoContratante;
    }
}