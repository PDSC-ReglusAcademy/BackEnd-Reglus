package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.schedule.Schedule;
import com.reglus.backend.model.entities.schedule.ScheduleRequest;
import com.reglus.backend.model.entities.users.User;
import com.reglus.backend.repositories.ScheduleRepository;
import com.reglus.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "*")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        try {
            User educator = userRepository.findById(scheduleRequest.getEducatorId())
                    .orElseThrow(() -> new RuntimeException("Educador não encontrado"));

            User student = userRepository.findById(scheduleRequest.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));

            Schedule schedule = new Schedule();
            schedule.setTitle(scheduleRequest.getTitle());
            schedule.setDescription(scheduleRequest.getDescription());
            schedule.setDate(scheduleRequest.getDate());
            schedule.setEducator(educator);
            schedule.setStudent(student);
            schedule.setEventType(scheduleRequest.getEventType());
            schedule.setCreatedAt(LocalDateTime.now());
            schedule.setUpdatedAt(LocalDateTime.now());

            Set<User> sharedUsers = new HashSet<>();
            for (Long userId : scheduleRequest.getSharedUserIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                sharedUsers.add(user);
            }
            schedule.setSharedUsers(sharedUsers);

            Schedule savedSchedule = scheduleRepository.save(schedule);
            return new ResponseEntity<>(savedSchedule, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequest scheduleRequest) {
        try {
            Schedule schedule = scheduleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

            schedule.setTitle(scheduleRequest.getTitle());
            schedule.setDescription(scheduleRequest.getDescription());
            schedule.setDate(scheduleRequest.getDate());


            if (scheduleRequest.getEventType() != null) {
                schedule.setEventType(scheduleRequest.getEventType());
            } else {
                return new ResponseEntity<>("O tipo do evento não pode ser nulo", HttpStatus.BAD_REQUEST);
            }

            schedule.setUpdatedAt(LocalDateTime.now());

            Schedule updatedSchedule = scheduleRepository.save(schedule);
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSchedule(@PathVariable Long id) {
        try {
            if (!scheduleRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            scheduleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/day")
    public ResponseEntity<List<Schedule>> getSchedulesByDay(@RequestParam LocalDate date) {
        try {
            List<Schedule> schedules = scheduleRepository.findByDate(date);
            if (schedules.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/week")
    public ResponseEntity<List<Schedule>> getSchedulesByWeek(@RequestParam LocalDate startDate) {
        try {
            LocalDate endDate = startDate.plusDays(6);
            List<Schedule> schedules = scheduleRepository.findByDateBetween(startDate, endDate);
            if (schedules.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/month")
    public ResponseEntity<List<Schedule>> getSchedulesByMonth(@RequestParam int year, @RequestParam int month) {
        try {
            List<Schedule> schedules = scheduleRepository.findByMonth(year, month);
            if (schedules.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/year")
    public ResponseEntity<List<Schedule>> getSchedulesByYear(@RequestParam int year) {
        try {
            List<Schedule> schedules = scheduleRepository.findByYear(year);
            if (schedules.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}/share")
    public ResponseEntity<?> shareScheduleResponse(@PathVariable Long id, @RequestBody Set<Long> userIds) {
        try {
            // Buscar o evento pelo ID
            Schedule schedule = scheduleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

            // Obter os usuários compartilhados
            Set<User> sharedUsers = schedule.getSharedUsers();

            // Adicionar os novos usuários ao conjunto de compartilhamento
            for (Long userId : userIds) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                sharedUsers.add(user);
            }

            // Atualizar o conjunto de usuários compartilhados no evento
            schedule.setSharedUsers(sharedUsers);

            // Salvar o evento atualizado
            Schedule updatedSchedule = scheduleRepository.save(schedule);
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
