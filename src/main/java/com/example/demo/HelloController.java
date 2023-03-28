package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.function.DoubleUnaryOperator;

public class HelloController {
    @FXML
    private Label labelTime;
    @FXML
    private TextField nThreadsField;
    @FXML
    private Label labelFunction;
    @FXML
    private Label labelIntegralSum;
    @FXML
    private TextField aField;
    @FXML
    private TextField bField;
    @FXML
    private TextField stepField;

    private double finalResult = 0;
    private int nOperationsLeft = 0;

    public void initialize() {
        labelFunction.setText(" 1 + t / sqrt(2t)");
    }

    public void onCalculateClicked() {
        finalResult = 0;
        nOperationsLeft = 0;
        double a = Double.parseDouble(aField.getText());
        double b = Double.parseDouble(bField.getText());
        int nSteps = Integer.parseInt(stepField.getText());
        int nThreads = Integer.parseInt(nThreadsField.getText());
        DoubleUnaryOperator f = (t -> (1 + t) / Math.sqrt(2 * t));

        long resTime = getTimeForCalculating(a,b,nSteps,nThreads,f);

        labelIntegralSum.setText(String.valueOf(finalResult));
        labelTime.setText(String.valueOf(resTime));
    }

    private long getTimeForCalculating(double a, double b, int nSteps, int nThreads, DoubleUnaryOperator f){
        long time1 = System.currentTimeMillis();
        calculatingByThreads(a, b, nSteps, nThreads, f);
        long time2 = System.currentTimeMillis();
        return time2 - time1;
    }

    private void calculatingByThreads(double a, double b, int nSteps, int nThreads, DoubleUnaryOperator f) {
        for (int i = 0; i < nThreads; i++) {
            double det = (b - a) / nThreads;
            new Thread(new IntegralCalculator(a + det * i, a + det * (i + 1), nSteps / nThreads, f, this)).start();
        }
        try {
            synchronized (this) {
                while (nOperationsLeft < nThreads) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void callback(double res) {
        finalResult += res;
        nOperationsLeft++;
        notify();
    }
}