package com.example.demo;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;

public class Function {
    private double a;
    private double b;
    private int nSteps;
    private double step;
    private DoubleUnaryOperator f;

    public Function(double a, double b, int nSteps, DoubleUnaryOperator f) {
        this.a = a;
        this.b = b;
        this.nSteps = nSteps;
        this.f = f;
        step = (b-a)/nSteps;
    }

    public double calculateTrapezia(){
        double v1 = f.applyAsDouble(a);
        double v2 = f.applyAsDouble(b);
        return ((v1+v2)/2*step+IntStream.range(1, nSteps).mapToDouble(it -> a+it*step).map(f).map(y -> y*step).sum());
    }

}
