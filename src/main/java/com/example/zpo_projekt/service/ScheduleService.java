package com.example.zpo_projekt.service;

import com.example.zpo_projekt.model.Schedule;
import com.example.zpo_projekt.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public List<Schedule> getAll() {
       return scheduleRepository.findAll();
    }

    public Schedule getSingleSchedule(String name) {
        return scheduleRepository.findByName(name);
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public Schedule updateSchedule(Schedule schedule, String monday, String tuesday, String wednesday, String thursday,
                                   String friday, String saturday, String sunday) {
        Schedule scheduleEdited = scheduleRepository.findByName(schedule.getName());
        scheduleEdited.setMonday(monday);
        scheduleEdited.setTuesday(tuesday);
        scheduleEdited.setWednesday(wednesday);
        scheduleEdited.setThursday(thursday);
        scheduleEdited.setFriday(friday);
        scheduleEdited.setSaturday(saturday);
        scheduleEdited.setSunday(sunday);
        return scheduleEdited;
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}
