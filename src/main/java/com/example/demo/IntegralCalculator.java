package com.example.demo;

import java.util.function.DoubleUnaryOperator;

public class IntegralCalculator implements Runnable{
    private Function function;
    private HelloController helloController;

    public IntegralCalculator(double a, double b, int nSteps, DoubleUnaryOperator f, HelloController helloController){
        function = new Function(a,b,nSteps,f);
        this.helloController = helloController;
    }

    @Override
    public void run() {
        double res = function.calculateTrapezia();
        helloController.callback(res);
    }
}
