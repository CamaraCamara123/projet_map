package com.position.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.position.IDao.PositionRepository;
import com.position.entity.Position;

@RestController
@RequestMapping("/position")
public class PositionController {
    
    @Autowired
    PositionRepository positionRepository;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Position position){
        positionRepository.save(position);
        String message = "{\"message\": \"Created succesfully\"}";

        return ResponseEntity.ok(message);
    }

     @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<Position>>> showPositions() {
        List<Position> positions =positionRepository.findAll();

        Map<String, List<Position>> response = new HashMap<>();
        response.put("positions", positions);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(required = true) String id){
        positionRepository.deleteById(Integer.parseInt(id));
    }

    @PostMapping("/update")
    public void update(@RequestBody Position position){
        positionRepository.save(position);
    }
}
