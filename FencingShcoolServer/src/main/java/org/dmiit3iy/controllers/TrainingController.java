package org.dmiit3iy.controllers;

import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.Training;
import org.dmiit3iy.model.User;
import org.dmiit3iy.service.TrainingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(path = "/training")
public class TrainingController {
    private TrainingsService trainingsService;

    @Autowired
    public void setTrainingsService(TrainingsService trainingsService) {
        this.trainingsService = trainingsService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<Training>> add(@RequestParam long idTrainer, @RequestParam long idApprentice,
                                                        @RequestParam int numberGym,
                                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                        @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime startTime){
        try{
       Training training= this.trainingsService.add(idTrainer,idApprentice,numberGym,date,startTime);
            return new ResponseEntity<>(new ResponseResult<>(null, training), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Training>> get(@PathVariable long id){
        try {
            Training training = this.trainingsService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, training), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(path = "/trainer/{id}")
    public ResponseEntity<ResponseResult<List<Training>>> getByTrainer(@PathVariable long id){
        try {
            List<Training> trainingList = this.trainingsService.getByTrainerId(id);
            return new ResponseEntity<>(new ResponseResult<>(null, trainingList), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/apprentice/{id}")
    public ResponseEntity<ResponseResult<List<Training>>> getByApprentice(@PathVariable long id){
        try {
            List<Training> trainingList = this.trainingsService.getByApprenticeId(id);
            return new ResponseEntity<>(new ResponseResult<>(null, trainingList), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<LocalTime>>> getLocalTime(@RequestParam long idTrainer, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                                        @RequestParam int numberGym){
        try {
            List<LocalTime> localTimeList = this.trainingsService.getTime(idTrainer,date,numberGym);
            return new ResponseEntity<>(new ResponseResult<>(null, localTimeList), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/freetime")
    public ResponseEntity<ResponseResult<Boolean>> getAnyFreeTime(@RequestParam long idTrainer, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        try {
            Boolean freeTime = this.trainingsService.getAnyFreeTime(idTrainer,date);
            return new ResponseEntity<>(new ResponseResult<>(null, freeTime), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Training>> delete(@PathVariable long id){
        try {
            Training training = this.trainingsService.delete(id);
            return new ResponseEntity<>(new ResponseResult<>(null, training), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

}
