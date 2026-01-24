package br.com.helpper.helpper_api.ENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 2)
    private BigDecimal orcamento;

    @Column(nullable = false, length = 100)
    private String tipo;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contratante_id", nullable = false)
    private Contratante contratante;

    public Servico() {
        this.status = StatusEnum.CRIADO; // Status padrão ao criar
        this.data = LocalDateTime.now();
    }

    // Método auxiliar para obter endereço do contratante
    public String getEnderecoDoContratante() {
        return contratante != null ? contratante.getEndereco() : null;
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
        this.orcamento = orcamento; // ✅ Corrigido - antes faltava "this."
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

    public Contratante getContratante() {
        return contratante;
    }

    public void setContratante(Contratante contratante) {
        this.contratante = contratante;
    }
}