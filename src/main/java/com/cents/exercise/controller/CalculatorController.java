package com.cents.exercise.controller;

import com.cents.exercise.service.CalculatorService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CalculatorController {

    @Autowired
    CalculatorService calculatorService;

    @PostMapping("/calculate")
    public ResponseEntity<Double> calculate(@RequestParam @NotNull Double firstNumber,
                                            @RequestParam @NotNull Double secondNumber) {
        return new ResponseEntity<>(calculatorService.calculate(firstNumber, secondNumber), HttpStatus.OK);
    }

}