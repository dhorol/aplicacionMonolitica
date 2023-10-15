package com.monolitico.TopEducation.services;

import com.monolitico.TopEducation.entities.EstudianteEntity;
import com.monolitico.TopEducation.entities.ExamenEntity;
import com.monolitico.TopEducation.repositories.EstudianteRepository;
import com.monolitico.TopEducation.repositories.ExamenRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.sql.Date;
import java.util.Iterator;

@Service
public class ExamenService {

    @Autowired
    private ExamenRepository examenRepository;

    @Autowired
    private EstudianteRepository estudianteRepository; // Agregamos la dependencia del repositorio de estudiantes

    public void saveExamenesFromExcel(MultipartFile file) throws Exception {
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (currentRow.getRowNum() == 0) { // Skip header row
                    continue;
                }

                Cell rutCell = currentRow.getCell(0);
                Cell fechaCell = currentRow.getCell(1);
                Cell puntajeCell = currentRow.getCell(2);

                String rut = rutCell.getStringCellValue();

                EstudianteEntity estudiante = estudianteRepository.findByRut(rut); // Buscamos al estudiante por RUT

                if (estudiante == null) {
                    throw new RuntimeException("Estudiante no encontrado.");
                }

                ExamenEntity examen = new ExamenEntity();
                examen.setEstudiante(estudiante); // Establecemos la referencia del estudiante en el examen
                examen.setRutEstudiante(rut);
                examen.setFechaExamen(new Date(fechaCell.getDateCellValue().getTime()));
                examen.setPuntaje(puntajeCell.getNumericCellValue());

                examenRepository.save(examen); // Guardamos el examen con la referencia al estudiante
            }
        }
    }


    public void eliminarTodosLosExamenes() {
        examenRepository.deleteAll();
    }
}
