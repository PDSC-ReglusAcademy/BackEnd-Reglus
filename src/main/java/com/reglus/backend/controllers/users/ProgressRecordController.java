package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.rooms.ProgressRecord;
import com.reglus.backend.model.entities.users.Educator;
import com.reglus.backend.model.entities.users.Student;
import com.reglus.backend.repositories.ProgressRecordRepository;
import com.reglus.backend.repositories.StudentRepository;
import com.reglus.backend.repositories.EducatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/educators/{educatorId}/students/{studentId}/progress-records")
public class ProgressRecordController {

    @Autowired
    private ProgressRecordRepository progressRecordRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EducatorRepository educatorRepository;

    // Obter todos os registros de progresso de um estudante (criados pelo educador)
    @GetMapping
    public ResponseEntity<List<ProgressRecord>> getProgressRecords(
            @PathVariable Long educatorId, @PathVariable Long studentId) {

        Optional<Educator> educatorOptional = educatorRepository.findById(educatorId);
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (educatorOptional.isPresent() && studentOptional.isPresent()) {
            List<ProgressRecord> progressRecords = progressRecordRepository.findByEducatorEducatorIdAndStudentStudentId(
                    educatorOptional.get().getEducatorId(), studentOptional.get().getStudentId());
            return new ResponseEntity<>(progressRecords, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Criar um novo registro de progresso (apenas para o educador)
    @PostMapping
    public ResponseEntity<ProgressRecord> createProgressRecord(
            @PathVariable Long educatorId, @PathVariable Long studentId, @RequestBody ProgressRecord progressRecord) {

        Optional<Educator> educatorOptional = educatorRepository.findById(educatorId);
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (educatorOptional.isPresent() && studentOptional.isPresent()) {
            progressRecord.setStudent(studentOptional.get());
            progressRecord.setEducator(educatorOptional.get());
            progressRecord.setUpdatedAt(LocalDateTime.now());

            ProgressRecord savedProgressRecord = progressRecordRepository.save(progressRecord);
            return new ResponseEntity<>(savedProgressRecord, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Atualizar um registro de progresso (apenas para o educador)
    @PutMapping("/{progressId}")
    public ResponseEntity<ProgressRecord> updateProgressRecord(
            @PathVariable Long educatorId, @PathVariable Long studentId, @PathVariable Long progressId,
            @RequestBody ProgressRecord progressRecordDetails) {

        Optional<ProgressRecord> progressRecordOptional = progressRecordRepository.findById(progressId);
        if (progressRecordOptional.isPresent()) {
            ProgressRecord progressRecord = progressRecordOptional.get();

            // Verifica se o registro de progresso pertence ao educador e ao estudante
            if (!progressRecord.getEducator().getEducatorId().equals(educatorId) ||
                    !progressRecord.getStudent().getStudentId().equals(studentId)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Atualiza os campos
            progressRecord.setObservations(progressRecordDetails.getObservations());
            progressRecord.setGoals(progressRecordDetails.getGoals());
            progressRecord.setActionPlans(progressRecordDetails.getActionPlans());
            progressRecord.setUpdatedAt(LocalDateTime.now());

            ProgressRecord updatedProgressRecord = progressRecordRepository.save(progressRecord);
            return new ResponseEntity<>(updatedProgressRecord, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Excluir um registro de progresso (apenas para o educador)
    @DeleteMapping("/{progressId}")
    public ResponseEntity<HttpStatus> deleteProgressRecord(
            @PathVariable Long educatorId, @PathVariable Long studentId, @PathVariable Long progressId) {

        Optional<ProgressRecord> progressRecordOptional = progressRecordRepository.findById(progressId);
        if (progressRecordOptional.isPresent()) {
            ProgressRecord progressRecord = progressRecordOptional.get();

            // Verifica se o registro de progresso pertence ao educador e ao estudante
            if (!progressRecord.getEducator().getEducatorId().equals(educatorId) ||
                    !progressRecord.getStudent().getStudentId().equals(studentId)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            progressRecordRepository.delete(progressRecord);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}