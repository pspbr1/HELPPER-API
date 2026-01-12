/*O repository é a camada de acesso de dados
da aplicação. Sendo responsável por buscar e salvar dados no DB
verificar existencia de registros e encapsular interações com persistencia;*/


package br.com.helpper.helpper_api.REPOSITORY;

import br.com.helpper.helpper_api.ENTITY.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //anotação do Spring para marcar uma classe como um compionente
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //JpaRepository<EntidadePrincipal, ChavePrimaria> (Usuario, Long)
    //herda metodos CRUD básicos do SpringDataJPA

    //definindo métodos
    Optional<Usuario> findByCpf(String cpf);//busca um usuario pelo cpf

    boolean existsByEmail(String email);/*Retorna true se já existir um
                                            usuario com o email informado*/

    boolean existsByCpf(String cpf); /*Retorna true sejá existir
                                        um usuario com o cpf fornecido*/

    Optional<Usuario>findByEmail(String email);

}
