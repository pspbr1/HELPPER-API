/*O repository é a camada de acesso de dados
da aplicação. Sendo responsável por buscar e salvar dados no DB
verificar existencia de registros e encapsular interações com persistencia;*/


package br.com.helpper.helpper_api.REPOSITORY;

import br.com.helpper.helpper_api.ENTITY.Contratante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratanteRepository extends JpaRepository<Contratante, Long> {

    //Busca contratantes por endereco (filtro por regiao)
    List<Contratante> findByEndereco(String endereco);

    //Busca enderecos por uma palavra especifica
    List<Contratante> findByEnderecoContaining(String termo);
}
