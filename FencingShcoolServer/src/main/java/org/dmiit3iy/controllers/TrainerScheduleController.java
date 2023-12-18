package org.dmiit3iy.controllers;

import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.TrainerSchedule;
import org.dmiit3iy.service.TrainerScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/trainerschedule")
public class TrainerScheduleController {
    private TrainerScheduleService trainerScheduleService;

    @Autowired
    public void setTrainerScheduleService(TrainerScheduleService trainerScheduleService) {
        this.trainerScheduleService = trainerScheduleService;
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<TrainerSchedule>> add (@PathVariable long id, String dayWeek,
                                                                LocalTime start, LocalTime end){
         this.trainerScheduleService.add(id, dayWeek, start, end);
         //разобраться с этой дичью
         return new ResponseEntity<>(new ResponseResult<>(null,null), HttpStatus.OK);

    }
}
