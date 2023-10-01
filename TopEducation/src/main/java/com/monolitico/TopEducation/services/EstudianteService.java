package com.monolitico.TopEducation.services;

import com.monolitico.TopEducation.repositories.EstudianteRepository;
import com.monolitico.TopEducation.entities.EstudianteEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstudianteService {
    @Autowired
    EstudianteRepository estudianteRepository;

}
