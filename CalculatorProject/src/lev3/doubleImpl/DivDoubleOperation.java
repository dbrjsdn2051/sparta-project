package lev3.doubleImpl;

import lev3.AbstractOperation;

public class DivDoubleOperation implements AbstractOperation<Double> {
    @Override
    public Double operator(Double firstNumber, Double secondNumber) {
        return firstNumber / secondNumber;
    }
}
