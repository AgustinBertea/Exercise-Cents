package com.cents.exercise.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    public void testCalculateWithPositiveNumbers() {
        double result = calculatorService.calculate(7.5, 3.0);
        assertEquals(21.5, result);
    }

    @Test
    public void testCalculateWithNegativeAndPositiveNumber() {
        double result = calculatorService.calculate(-2.5, 3.0);
        assertEquals(5.5, result);
    }

    @Test
    public void testCalculateWithSameNumbers() {
        double result = calculatorService.calculate(4.5, 4.5);
        assertEquals(14.0, result);
    }
}