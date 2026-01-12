/*O repository é a camada de acesso de dados
da aplicação. Sendo responsável por buscar e salvar dados no DB
verificar existencia de registros e encapsular interações com persistencia;*/


package br.com.helpper.helpper_api.REPOSITORY;

import br.com.helpper.helpper_api.ENTITY.Prestador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestadorRepository extends JpaRepository<Prestador, Long> {

    //Busca prestadores por cidade
    List<Prestador> fndByCidade(String cidade);

    //busca prestadores por avaliação
    List<Prestador> FindByAvaliacao(Double avaliacaoMinima);

    //busca prestadores por cidade E avaliacao
    List<Prestador> FindByAvaliacaoCidade(
            String cidade,
            Double avaliacaoMinima
    );

    //busca prestadores que oferecem um servico especifco
    List<Prestador> findByServico(@Param("servico") String servico);
}
