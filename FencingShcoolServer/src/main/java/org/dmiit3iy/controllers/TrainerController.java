package org.dmiit3iy.controllers;

import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainer")
public class TrainerController {

    private TrainerService trainerService;

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }


    @PostMapping
    public ResponseEntity<ResponseResult<Trainer>> add(@RequestBody Trainer trainer) {
        try {
            this.trainerService.add(trainer);
            return new ResponseEntity<>(new ResponseResult<>(null, trainer), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<Trainer>>> get() {
        List<Trainer> trainerList = trainerService.get();
        return new ResponseEntity<>(new ResponseResult<>(null, trainerList), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseResult<Trainer>> get(@PathVariable long id) {
        try {
            Trainer trainer = this.trainerService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, trainer), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping
    public ResponseEntity<ResponseResult<Trainer>> put(@RequestBody Trainer trainer) {
        try {
            Trainer updateTrainer = this.trainerService.update(trainer);
            return new ResponseEntity<>(new ResponseResult<>(null, updateTrainer), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseResult<Trainer>> delete(@PathVariable long id) {
        try {
            Trainer trainer = this.trainerService.delete(id);
            return new ResponseEntity<>(new ResponseResult<>(null, trainer), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

}


