package com.monolitico.TopEducation.repositories;

import com.monolitico.TopEducation.entities.ExamenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepository extends CrudRepository<ExamenEntity, Long> {

    List<ExamenEntity> findByEstudianteId(Long estudianteId);

}
