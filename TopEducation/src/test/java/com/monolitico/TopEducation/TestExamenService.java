package com.monolitico.TopEducation;
import com.monolitico.TopEducation.entities.EstudianteEntity;
import com.monolitico.TopEducation.entities.ExamenEntity;
import com.monolitico.TopEducation.repositories.EstudianteRepository;
import com.monolitico.TopEducation.repositories.ExamenRepository;
import com.monolitico.TopEducation.services.ExamenService;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestExamenService {

    @Mock
    private ExamenRepository examenRepository;

    @Mock
    private EstudianteRepository estudianteRepository;

    @InjectMocks
    private ExamenService examenService;

    private MockMultipartFile file;

    @BeforeEach
    public void setUp() throws IOException {
        // Preparar un archivo Excel de prueba
        XSSFWorkbook workbook = new XSSFWorkbook();
        var sheet = workbook.createSheet();
        var headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("RUT");
        headerRow.createCell(1).setCellValue("Fecha");
        headerRow.createCell(2).setCellValue("Puntaje");

        var dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue("12345678-9");

        // Aquí manejar correctamente los tipos de celda para fecha y número
        dataRow.createCell(1).setCellValue(new java.util.Date()); // Fecha actual como ejemplo
        dataRow.createCell(2, CellType.NUMERIC).setCellValue(100.5); // Un puntaje numérico de ejemplo

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        byte[] bytes = bos.toByteArray();

        file = new MockMultipartFile("file", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", bytes);
    }

    @Test
    public void testSaveExamenesFromExcel() throws Exception {
        EstudianteEntity mockEstudiante = new EstudianteEntity();
        mockEstudiante.setRut("12345678-9");

        when(estudianteRepository.findByRut(any(String.class))).thenReturn(mockEstudiante);
        when(examenRepository.save(any(ExamenEntity.class))).thenReturn(new ExamenEntity());

        examenService.saveExamenesFromExcel(file);

        verify(estudianteRepository, times(1)).findByRut(any(String.class));
        verify(examenRepository, times(1)).save(any(ExamenEntity.class));
    }

    @Test
    public void testEliminarTodosLosExamenes() {
        examenService.eliminarTodosLosExamenes();
        verify(examenRepository, times(1)).deleteAll();
    }
}