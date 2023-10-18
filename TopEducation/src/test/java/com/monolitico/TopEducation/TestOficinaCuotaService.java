package com.monolitico.TopEducation;
import com.monolitico.TopEducation.entities.CuotaEntity;
import com.monolitico.TopEducation.entities.EstudianteEntity;
import com.monolitico.TopEducation.entities.MesCuotaEntity;
import com.monolitico.TopEducation.repositories.CuotaRepository;
import com.monolitico.TopEducation.repositories.EstudianteRepository;
import com.monolitico.TopEducation.repositories.MesCuotaRepository;
import com.monolitico.TopEducation.repositories.ExamenRepository;
import com.monolitico.TopEducation.services.OficinaCuotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestOficinaCuotaService {

    @InjectMocks
    private OficinaCuotaService oficinaCuotaService;

    @Mock
    private CuotaRepository cuotaRepository;
    @Mock
    private EstudianteRepository estudianteRepository;
    @Mock
    private MesCuotaRepository mesCuotaRepository;
    @Mock
    private ExamenRepository examenRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindCuotaById() {
        CuotaEntity cuotaEntity = new CuotaEntity();
        when(cuotaRepository.findById(1L)).thenReturn(Optional.of(cuotaEntity));

        CuotaEntity result = oficinaCuotaService.findCuotaById(1L);
        verify(cuotaRepository).findById(1L);
    }

    @Test
    void testMarcarMatriculaComoPagada() {
        CuotaEntity cuotaEntity = mock(CuotaEntity.class, RETURNS_DEEP_STUBS);
        when(cuotaRepository.findById(1L)).thenReturn(Optional.of(cuotaEntity));

        oficinaCuotaService.marcarMatriculaComoPagada(1L);
        verify(cuotaRepository).save(any(CuotaEntity.class));
    }

    @Test
    void testPagarAlContado() {
        CuotaEntity cuotaEntity = mock(CuotaEntity.class, RETURNS_DEEP_STUBS);
        when(cuotaRepository.findById(1L)).thenReturn(Optional.of(cuotaEntity));

        oficinaCuotaService.pagarAlContado(1L);
        verify(mesCuotaRepository).save(any(MesCuotaEntity.class));
        verify(estudianteRepository).save(any(EstudianteEntity.class));
        verify(cuotaRepository).save(any(CuotaEntity.class));
    }



    @Test
    void testCalcularYAplicarIntereses() {
        CuotaEntity cuotaEntity = mock(CuotaEntity.class, RETURNS_DEEP_STUBS);
        when(cuotaEntity.getFechaInicial()).thenReturn(java.sql.Date.valueOf("2023-05-05"));

        oficinaCuotaService.calcularYAplicarIntereses(cuotaEntity);


    }


    @Test
    void testPagarMesCuota() {
        MesCuotaEntity mesCuotaEntity = new MesCuotaEntity();
        when(cuotaRepository.findById(anyLong())).thenReturn(Optional.of(new CuotaEntity()));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            oficinaCuotaService.pagarMesCuota(1L, true);
        });

        assertEquals("Mes de cuota no encontrado.", exception.getMessage());
    }



}