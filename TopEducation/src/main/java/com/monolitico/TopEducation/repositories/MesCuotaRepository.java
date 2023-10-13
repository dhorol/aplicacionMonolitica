package com.monolitico.TopEducation.repositories;


import com.monolitico.TopEducation.entities.MesCuotaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesCuotaRepository extends CrudRepository<MesCuotaEntity, Long> {
}
