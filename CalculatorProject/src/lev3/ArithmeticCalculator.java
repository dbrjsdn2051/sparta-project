package lev3;

import lev3.exception.ExceptionHandler;
import lev3.exception.TypeValidation;
import lev3.factory.CalculatorDoubleFactory;
import lev3.factory.CalculatorIntegerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ArithmeticCalculator {

    private final List<Number> result = new ArrayList<>();
    private final ExceptionHandler exceptionHandler;
    private final TypeValidation typeValidation;

    public ArithmeticCalculator() {
        this.exceptionHandler = new ExceptionHandler();
        this.typeValidation = new TypeValidation();
    }

    public Number calculation(String firstNumber, String secondNumber, String operator) throws Exception {
        exceptionHandler.validation(firstNumber, secondNumber, operator); // 에러 체크
        boolean isDoubleType = typeValidation.type(firstNumber); // 타입 체크

        if (isDoubleType) return getaDouble(firstNumber, secondNumber, operator); // 구현체 주입
        return getInteger(firstNumber, secondNumber, operator); // 구현체 주입
    }

    private Integer getInteger(String firstNumber, String secondNumber, String operator) throws Exception {
        AbstractOperation<Integer> integerOperation = CalculatorIntegerFactory.operation(operator);
        Integer answer = integerOperation.operator(Integer.parseInt(firstNumber), Integer.parseInt(secondNumber));
        result.add(answer);
        return answer;
    }

    private Double getaDouble(String firstNumber, String secondNumber, String operator) throws Exception {
        AbstractOperation<Double> doubleAbstractOperation = CalculatorDoubleFactory.operation(operator);
        Double answer = doubleAbstractOperation.operator(Double.parseDouble(firstNumber), Double.parseDouble(secondNumber));
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
