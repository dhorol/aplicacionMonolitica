package com.monolitico.TopEducation;



import com.monolitico.TopEducation.entities.EstudianteEntity;
import com.monolitico.TopEducation.repositories.EstudianteRepository;
import com.monolitico.TopEducation.repositories.CuotaRepository;
import com.monolitico.TopEducation.repositories.MatriculaRepository;

import com.monolitico.TopEducation.services.EstudianteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class TestEstudianteService {

    @InjectMocks
    private EstudianteService estudianteService;

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private CuotaRepository cuotaRepository;

    @Mock
    private MatriculaRepository matriculaRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveEstudent() {
        String rut = "12345678-9";
        String apellidos = "Apellidos";
        String nombres = "Nombres";
        Date fechaDeNacimiento = new Date(System.currentTimeMillis());
        String tipoColegio = "Publico";
        String nombreColegio = "Escuela A";
        Long annioEgreso = 2022L;

        estudianteService.saveEstudent(rut, apellidos, nombres, fechaDeNacimiento, tipoColegio, nombreColegio, annioEgreso);

        // Validar que los métodos de los repositorios se llaman al menos una vez
        verify(cuotaRepository, times(1)).save(any());
        verify(matriculaRepository, times(1)).save(any());
        verify(estudianteRepository, times(1)).save(any());
    }

    @Test
    public void testGetAllEstudiantes() {
        EstudianteEntity estudiante1 = new EstudianteEntity();
        EstudianteEntity estudiante2 = new EstudianteEntity();
        when(estudianteRepository.findAll()).thenReturn(Arrays.asList(estudiante1, estudiante2));

        List<EstudianteEntity> estudiantes = (List<EstudianteEntity>) estudianteService.getAllEstudiantes();

        assert(estudiantes.size() == 2);
    }

    @Test
    public void testCuotasConRetraso() {
        // Aquí puedes definir un estudianteEntity mock con una lista de MesCuotaEntity con ciertos valores
        // para probar diferentes casos del método cuotasConRetraso.
        // Por ejemplo, puedes probar un caso donde no hay retrasos, uno donde hay 1 retraso y otro donde hay múltiples retrasos.
    }

    // Puedes agregar más pruebas para otros métodos de la misma manera
}
