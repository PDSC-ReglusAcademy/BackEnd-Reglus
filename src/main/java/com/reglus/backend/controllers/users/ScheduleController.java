package com.reglus.backend.controllers.users;

import com.reglus.backend.model.entities.schedule.Schedule;
import com.reglus.backend.model.entities.users.User;
import com.reglus.backend.repositories.ScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleRepository scheduleRepository;

    public ScheduleController(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping("/day")
    public List<Schedule> getSchedulesByDay(@RequestParam LocalDate date) {
        return scheduleRepository.findByDate(date);
    }

    @GetMapping("/week")
    public List<Schedule> getSchedulesByWeek(@RequestParam LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(6); // Define o fim da semana (7 dias após a data inicial)
        return scheduleRepository.findByWeek(startDate, endDate);
    }

    @GetMapping("/month")
    public List<Schedule> getSchedulesByMonth(@RequestParam int year, @RequestParam int month) {
        return scheduleRepository.findByMonth(year, month);
    }

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setUpdatedAt(LocalDateTime.now());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return ResponseEntity.ok(savedSchedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule updatedSchedule) {
        return scheduleRepository.findById(id)
                .map(schedule -> {
                    schedule.setDescription(updatedSchedule.getDescription());
                    schedule.setUpdatedAt(LocalDateTime.now());
                    schedule.setStudent(updatedSchedule.getStudent());
                    schedule.setEducator(updatedSchedule.getEducator());

                    if (updatedSchedule.getSharedUsers() != null) {
                        schedule.setSharedUsers(updatedSchedule.getSharedUsers());
                    }

                    return ResponseEntity.ok(scheduleRepository.save(schedule));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/share")
    public ResponseEntity<Schedule> shareSchedule(@PathVariable Long id, @RequestBody Set<User> users) {
        return scheduleRepository.findById(id)
                .map(schedule -> {
                    schedule.addSharedUsers(users);
                    Schedule updatedSchedule = scheduleRepository.save(schedule);
                    return ResponseEntity.ok(updatedSchedule);
                }).orElse(ResponseEntity.notFound().build());
    }
}