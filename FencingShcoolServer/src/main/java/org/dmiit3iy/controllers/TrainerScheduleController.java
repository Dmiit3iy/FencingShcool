package org.dmiit3iy.controllers;

import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.TrainerSchedule;
import org.dmiit3iy.service.TrainerScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/schedule")
public class TrainerScheduleController {
    private TrainerScheduleService trainerScheduleService;

    @Autowired
    public void setTrainerScheduleService(TrainerScheduleService trainerScheduleService) {
        this.trainerScheduleService = trainerScheduleService;
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<TrainerSchedule>> add(@PathVariable long id, @RequestParam String dayWeek,
                                                               @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime start,
                                                               @RequestParam @DateTimeFormat (pattern = "HH:mm") LocalTime end) {
        try {
            TrainerSchedule trainerSchedule = this.trainerScheduleService.add(id, dayWeek, start, end);
            return new ResponseEntity<>(new ResponseResult<>(null, trainerSchedule), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<TrainerSchedule>> get(@PathVariable long id) {
        try {
            TrainerSchedule trainerSchedule = this.trainerScheduleService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, trainerSchedule), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}/update")
    public ResponseEntity<ResponseResult<TrainerSchedule>> put(@PathVariable long id, @RequestParam String dayWeek,
                                                               @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime start,
                                                               @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime end) {
        try {
            TrainerSchedule trainerSchedule = this.trainerScheduleService.add(id, dayWeek, start, end);
            return new ResponseEntity<>(new ResponseResult<>(null, trainerSchedule), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

//    @PutMapping
//    public ResponseEntity<ResponseResult<TrainerSchedule>> put(@RequestBody TrainerSchedule trainerSchedule) {
//        try {
//            TrainerSchedule updateTrainerSchedule = this.trainerScheduleService.update(trainerSchedule);
//            return new ResponseEntity<>(new ResponseResult<>(null, updateTrainerSchedule), HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
//        }
//    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<TrainerSchedule>> delete(@PathVariable long id, @RequestParam String day) {
        try {
            TrainerSchedule trainerSchedule = this.trainerScheduleService.delete(id, day);
            return new ResponseEntity<>(new ResponseResult<>(null, trainerSchedule), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }

    }

}
