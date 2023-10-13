package com.monolitico.TopEducation.repositories;

import com.monolitico.TopEducation.entities.MatriculaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MatriculaRepository extends CrudRepository<MatriculaEntity, Long> {
}
