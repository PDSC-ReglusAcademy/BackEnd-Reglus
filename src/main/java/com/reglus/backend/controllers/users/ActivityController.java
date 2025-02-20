package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.rooms.Activity;
import com.reglus.backend.model.entities.rooms.Room;
import com.reglus.backend.model.entities.schedule.Schedule;
import com.reglus.backend.model.entities.users.Educator;
import com.reglus.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    //Repositorio da classe entidy Room ainda não implementado
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EducatorRepository educatorRepository;

    // Criar uma nova atividade
    @PostMapping
    public ResponseEntity<?> createActivity(@RequestBody Activity activity) {
        try {
            // Verifica se o Educator foi informado corretamente
            if (activity.getEducator() == null || activity.getEducator().getEducatorId() == null) {
                return new ResponseEntity<>("O Educator deve ter um ID válido", HttpStatus.BAD_REQUEST);
            }

            // Busca o Educator pelo ID no banco de dados
            Optional<Educator> educatorOptional = educatorRepository.findById(activity.getEducator().getEducatorId());
            if (educatorOptional.isEmpty()) {
                return new ResponseEntity<>("Educator não encontrado", HttpStatus.NOT_FOUND);
            }
            activity.setEducator(educatorOptional.get());

            // Verifica se o Schedule já existe antes de associá-lo à Activity
            if (activity.getSchedule() != null && activity.getSchedule().getId() != null) {
                Optional<Schedule> scheduleOptional = scheduleRepository.findById(activity.getSchedule().getId());
                if (scheduleOptional.isEmpty()) {
                    return new ResponseEntity<>("Schedule não encontrado", HttpStatus.NOT_FOUND);
                }
                activity.setSchedule(scheduleOptional.get());
            } else {
                return new ResponseEntity<>("Schedule deve ter um ID válido", HttpStatus.BAD_REQUEST);
            }


            // Salva a Activity
            Activity savedActivity = activityRepository.save(activity);
            return new ResponseEntity<>(savedActivity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Obter todas as atividades
    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityRepository.findAll();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    // Obter uma atividade por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getActivityById(@PathVariable Long id) {
        Optional<Activity> activity = activityRepository.findById(id);
        return activity.map(value -> new ResponseEntity<Object>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<Object>("Atividade não encontrada", HttpStatus.NOT_FOUND));
    }


    // Atualizar uma atividade
    @PutMapping("/{id}")
    public ResponseEntity<?> updateActivity(@PathVariable Long id, @RequestBody Activity updatedActivity) {
        try {
            Activity activity = activityRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Atividade não encontrada"));

            activity.setTitle(updatedActivity.getTitle());
            activity.setMaxPoints(updatedActivity.getMaxPoints());
            activity.setDueDate(updatedActivity.getDueDate());
            activity.setUpdatedAt(LocalDateTime.now());

            Activity savedActivity = activityRepository.save(activity);
            return new ResponseEntity<>(savedActivity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Deletar uma atividade
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteActivity(@PathVariable Long id) {
        try {
            if (!activityRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            activityRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obter atividades por sala (Room)
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Activity>> getActivitiesByRoom(@PathVariable Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        return room.map(value -> new ResponseEntity<>(activityRepository.findByRoom(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obter atividades por educador (Educator)
    @GetMapping("/educator/{educatorId}")
    public ResponseEntity<List<Activity>> getActivitiesByEducator(@PathVariable Long educatorId) {
        Optional<Educator> educator = educatorRepository.findById(educatorId);
        return educator.map(value -> new ResponseEntity<List<Activity>>(activityRepository.findByEducator(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<List<Activity>>(HttpStatus.NOT_FOUND));
    }

    // Obter atividades por cronograma (Schedule)
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<Activity>> getActivitiesBySchedule(@PathVariable Long scheduleId) {
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);
        return schedule.map(value -> new ResponseEntity<>(activityRepository.findBySchedule(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obter atividades com data de vencimento antes de uma data
    @GetMapping("/due-before")
    public ResponseEntity<List<Activity>> getActivitiesDueBefore(@RequestParam LocalDateTime date) {
        List<Activity> activities = activityRepository.findByDueDateBefore(date);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    // Obter atividades com data de vencimento depois de uma data
    @GetMapping("/due-after")
    public ResponseEntity<List<Activity>> getActivitiesDueAfter(@RequestParam LocalDateTime date) {
        List<Activity> activities = activityRepository.findByDueDateAfter(date);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }
}
