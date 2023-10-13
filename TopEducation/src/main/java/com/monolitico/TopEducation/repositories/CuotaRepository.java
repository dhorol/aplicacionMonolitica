package com.monolitico.TopEducation.repositories;

import com.monolitico.TopEducation.entities.CuotaEntity;
import com.monolitico.TopEducation.entities.EstudianteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CuotaRepository extends CrudRepository<CuotaEntity, Long> {

}
