package com.example.zpo_projekt.controller;


import com.example.zpo_projekt.model.Post;
import com.example.zpo_projekt.model.Schedule;
import com.example.zpo_projekt.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ScheduleController {

    @Autowired
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/api/schedules")
    public List<Schedule> getAll(){
        return  scheduleService.getAll();
    }

    @GetMapping("/api/schedules/{name}")
    public Schedule getSingleSchedule(@PathVariable String name){
        return scheduleService.getSingleSchedule(name);
    }

    @PostMapping("/api/schedules")
    public Schedule createSchedule(@RequestBody Schedule schedule){
        return scheduleService.createSchedule(schedule);
    }

    @RequestMapping(value = "/api/schedules", produces = "application/json", method=RequestMethod.PUT)
    public Schedule updateSchedule(@RequestBody Schedule schedule, @RequestParam("monday") String monday,
                                   @RequestParam("tuesday") String tuesday, @RequestParam("wednesday") String wednesday,
                                   @RequestParam("thursday") String thursday, @RequestParam("friday") String friday,
                                   @RequestParam("saturday") String saturday,@RequestParam("sunday") String sunday){
        return scheduleService.updateSchedule(schedule,monday,tuesday,wednesday,thursday,friday,saturday,sunday);
    }

    @DeleteMapping("/api/schedules/{id}")
    public void deletePost(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
    }
}
