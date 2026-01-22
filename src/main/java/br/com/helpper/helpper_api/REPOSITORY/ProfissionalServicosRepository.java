package br.com.helpper.helpper_api.REPOSITORY;

import br.com.helpper.helpper_api.ENTITY.ProfissionalServicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalServicosRepository extends JpaRepository<ProfissionalServicos,Long> {


}
