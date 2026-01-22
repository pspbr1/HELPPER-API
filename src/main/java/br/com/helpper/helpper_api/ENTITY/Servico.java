package br.com.helpper.helpper_api.ENTITY;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String orcamento;

    @Column
    private String tipo;

    @Column
    private Date  data;

    @Column
    private String descricao;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "contratante_id")
    private Contratante contratante;

  

    public Servico(){
    }
    
    //AQUI HERDA O ENDEREÇO DO USUARIO

    public String getEnderecoDoContratante() {
        return contratante != null ? contratante.getEndereco() : null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(String orcamento) {
        this.orcamento = orcamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
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

}
