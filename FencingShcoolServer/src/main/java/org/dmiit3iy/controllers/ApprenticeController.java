package org.dmiit3iy.controllers;

import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.service.ApprenticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apprentice")
public class ApprenticeController {
    private ApprenticeService apprenticeService;

    @Autowired
    public void setApprenticeService(ApprenticeService apprenticeService) {
        this.apprenticeService = apprenticeService;
    }


    @PostMapping
    public ResponseEntity<ResponseResult<Apprentice>> add(@RequestBody Apprentice apprentice) {
        try {
            this.apprenticeService.add(apprentice);
            return new ResponseEntity<>(new ResponseResult<>(null, apprentice), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<Apprentice>>> get() {
        List<Apprentice> apprenticeList = apprenticeService.get();
        return new ResponseEntity<>(new ResponseResult<>(null, apprenticeList), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseResult<Apprentice>> get(@PathVariable long id) {
        try {
            Apprentice apprentice = this.apprenticeService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, apprentice), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping
    public ResponseEntity<ResponseResult<Apprentice>> put(@RequestBody Apprentice apprentice) {
        try {
            Apprentice updateApprentice = this.apprenticeService.update(apprentice);
            return new ResponseEntity<>(new ResponseResult<>(null, updateApprentice), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseResult<Apprentice>> delete(@PathVariable long id) {
        try {
            Apprentice apprentice = this.apprenticeService.delete(id);
            return new ResponseEntity<>(new ResponseResult<>(null, apprentice), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

}
