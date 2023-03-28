package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.DoubleUnaryOperator;

import static org.junit.jupiter.api.Assertions.*;
class FunctionTest {
    private Function function;

    @BeforeEach
    public void setUp(){
        double a = 1.0;
        double b = 4.0;
        int nSteps = 10000000;
        DoubleUnaryOperator f = (t -> (t+1)/Math.sqrt(2*t));
        function = new Function(a,b,nSteps,f);
    }

    @Test
    public void calculateTrapeziaTest() {
        double expected = 4.71404;
        assertEquals(expected, function.calculateTrapezia(), 1e-5);
    }
}