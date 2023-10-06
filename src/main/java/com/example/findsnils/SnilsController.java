package com.example.findsnils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/findsnils")
public class SnilsController {

    @Autowired
    private SnilsRepository snilsRepository;

    @PostMapping
    public ResponseEntity<Map<String, String>> findSnils(@RequestParam("snils") String snils) {
        Optional<SnilsEntity> snilsEntity = snilsRepository.findBySnils(snils);
        Map<String, String> response = new HashMap<>();

        if (snilsEntity.isPresent()) {
            response.put("status", "success");
            response.put("message", "СНИЛС найден в базе данных.");
        } else {
            response.put("status", "error");
            response.put("message", "СНИЛС не найден в базе данных.");
        }

        return ResponseEntity.ok(response);
    }

}
