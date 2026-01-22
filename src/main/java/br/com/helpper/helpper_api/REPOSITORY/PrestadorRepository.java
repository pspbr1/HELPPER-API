/*O repository é a camada de acesso de dados
da aplicação. Sendo responsável por buscar e salvar dados no DB
verificar existencia de registros e encapsular interações com persistencia;*/


package br.com.helpper.helpper_api.REPOSITORY;

import br.com.helpper.helpper_api.ENTITY.Prestador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrestadorRepository extends JpaRepository<Prestador, Long> {

    //Busca prestadores por cidade
    List<Prestador> findByCidade(String cidade);

    // Busca prestadores por média de avaliação
    List<Prestador> findByMediaAvaliacaoGreaterThanEqual(BigDecimal mediaMinima);

    // Busca prestadores por cidade E média de avaliação
    List<Prestador> findByCidadeAndMediaAvaliacaoGreaterThanEqual(
            String cidade,
            BigDecimal mediaMinima
    );

    // Busca prestadores por serviço (se for relacionamento simples)
    List<Prestador> findByCidadeAndServicosContaining(String cidade, String servico);


    Optional<Prestador> findById(Long Id);

    //busca prestadores que oferecem um servico especifco
    List<Prestador> findByServicos(String servicos);
}
