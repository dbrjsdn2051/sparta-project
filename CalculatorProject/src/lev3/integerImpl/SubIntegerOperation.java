package lev3.integerImpl;

import lev3.AbstractOperation;

public class SubIntegerOperation implements AbstractOperation<Integer> {
    @Override
    public Integer operator(Integer firstNumber, Integer secondNumber) {
        return firstNumber - secondNumber;
    }
}
