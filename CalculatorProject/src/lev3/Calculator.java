package lev3;

import lev3.exception.ExceptionHandler;
import lev3.exception.TypeValidation;
import lev3.factory.CalculatorDoubleFactory;
import lev3.factory.CalculatorIntegerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Calculator {

    private final List<Number> result = new ArrayList<>();

    public Number calculation(String firstNumber, String secondNumber, String operator) throws Exception {

        new ExceptionHandler(firstNumber, secondNumber, operator).validation();
        boolean isDoubleType = new TypeValidation().type(firstNumber);

        if (isDoubleType) {
            AbstractOperation<Double> doubleAbstractOperation = CalculatorDoubleFactory.operation(operator);
            Double answer = doubleAbstractOperation.operator(Double.parseDouble(firstNumber), Double.parseDouble(secondNumber));
            result.add(answer);
            return answer;
        }

        AbstractOperation<Integer> integerOperation = CalculatorIntegerFactory.operation(operator);
        Integer answer = integerOperation.operator(Integer.parseInt(firstNumber), Integer.parseInt(secondNumber));
        result.add(answer);
        return answer;
    }

    public void getResult() {
        AtomicInteger idx = new AtomicInteger(0);
        result.forEach(number -> {
            int cnt = idx.incrementAndGet();
            System.out.println(cnt + "번째 연산결과 :" + number);
        });
    }


}
