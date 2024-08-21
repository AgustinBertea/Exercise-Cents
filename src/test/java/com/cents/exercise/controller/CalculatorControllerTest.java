package com.cents.exercise.controller;

import com.cents.exercise.exception.CalculatorException;
import com.cents.exercise.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorController.class)
public class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculatorService calculatorService;

    @Test
    public void testCalculateOkParams() throws Exception {
        Mockito.when(calculatorService.calculate(anyDouble(), anyDouble())).thenReturn(5.5);

        mockMvc.perform(post("/api/v1/calculate")
                        .param("firstNumber", "-2.5")
                        .param("secondNumber", "3.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("5.5"));

        Mockito.verify(calculatorService).calculate(-2.5, 3.0);
    }

    @Test
    public void testCalculateMissingParams() throws Exception {
        Mockito.when(calculatorService.calculate(anyDouble(), anyDouble())).thenReturn(5.5);

        mockMvc.perform(post("/api/v1/calculate")
                        .param("secondNumber", "3.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"errorMessage\":\"Required request parameter " +
                        "'firstNumber' for method parameter type Double is not present\"}"));
    }

    @Test
    public void testCalculateInternalServerError() throws Exception {
        Mockito.when(calculatorService.calculate(anyDouble(), anyDouble()))
                .thenThrow(new CalculatorException("### ERROR ###"));

        mockMvc.perform(post("/api/v1/calculate")
                        .param("firstNumber", "-2.5")
                        .param("secondNumber", "3.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"errorMessage\":\"### ERROR ###\"}"));
    }

}