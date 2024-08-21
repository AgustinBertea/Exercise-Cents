package com.cents.exercise.service;

import com.cents.exercise.exception.CalculatorException;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double calculate(Double firstNumber, Double secondNumber) {
        try {
            // Find the largest number and pass it as a parameter to the helper method to find the next prime number
            int nextPrime = getNextPrime(Math.max(firstNumber, secondNumber));
            return firstNumber + secondNumber + nextPrime;
        } catch (Exception ex) {
            throw new CalculatorException("An error occurred while attempting to operate on the numbers, reason: "
                    + ex.getMessage());
        }
    }

    private int getNextPrime(Double number) {
        // Round the number, as it might be a decimal
        int rounded = (int) Math.ceil(number);

        // Check that the rounded number is not equal to the initial number, if it is, add 1
        int aux = rounded!=number ? rounded : rounded+1;

        // Check if the number is prime, if it is not, add 1 to the number and continue with the validation
        while(!isPrime(aux)) {
            aux++;
        }

        return aux;
    }

    private boolean isPrime(int number) {
        if (number <= 1)
            return false;
        if (number == 2)
            return true;
        if (number % 2 == 0)
            return false;

        // Iterates over possible odd divisors from 3 to the square root of the number
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0)
                return false;
        }

        return true;
    }
}