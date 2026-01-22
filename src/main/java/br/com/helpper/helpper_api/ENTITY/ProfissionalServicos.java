package br.com.helpper.helpper_api.ENTITY;

import jakarta.persistence.*;

@Entity
@Table(name = "profissional_servico")
public class ProfissionalServicos {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profissional_user_id", referencedColumnName = "id")
    private Prestador profissional;

    @ManyToOne
    @JoinColumn(name = "servico_id", referencedColumnName = "id")
    private Servico servico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prestador getProfissional() {
        return profissional;
    }

    public void setProfissional(Prestador profissional) {
        this.profissional = profissional;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }
}
