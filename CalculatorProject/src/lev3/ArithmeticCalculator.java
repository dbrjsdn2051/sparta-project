package lev3;

import lev3.exception.ExceptionHandler;
import lev3.exception.TypeValidation;
import lev3.factory.CalculatorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ArithmeticCalculator {

    private final List<Number> result;
    private final ExceptionHandler exceptionHandler;
    private final TypeValidation typeValidation;


    public ArithmeticCalculator() {
        this.result = new ArrayList<>();
        this.exceptionHandler = new ExceptionHandler();
        this.typeValidation = new TypeValidation();
    }

    public Number calculation(String firstNumber, String secondNumber, String operator) throws Exception {
        exceptionHandler.validation(firstNumber, secondNumber, operator); // 에러 체크
        boolean isDoubleType = typeValidation.type(firstNumber); // 타입 체크
        Number answer;

        if (isDoubleType) answer = performOperationRetAnswer(firstNumber, secondNumber, operator, Double.class);
        else answer = performOperationRetAnswer(firstNumber, secondNumber, operator, Integer.class);

        result.add(answer);
        return answer;
    }

    private <T extends Number> Number performOperationRetAnswer(String firstNumber, String secondNumber, String operator, Class<T> clazz) throws Exception {
        AbstractOperation<T> operation = (AbstractOperation<T>) CalculatorFactory.operationType(operator, clazz);
        return operation.operator(castType(firstNumber, clazz), castType(secondNumber, clazz));
    }

    private <T extends Number> T castType(String number, Class<T> clazz) throws Exception {
        if (clazz == Double.class) return clazz.cast(Double.parseDouble(number));
        if (clazz == Integer.class) return clazz.cast(Integer.parseInt(number));
        throw new Exception("유효하지 않은 타입정보입니다.");
    }


    public void getResult() {
        AtomicInteger idx = new AtomicInteger(0);
        result.forEach(number -> {
            int cnt = idx.incrementAndGet();
            System.out.println(cnt + "번째 연산결과 : " + number);
        });
    }

    /**
     private Integer getInteger(String firstNumber, String secondNumber, String operator) throws Exception {
     AbstractOperation<Integer> integerAbstractOperation = CalculatorIntegerFactory.valueOf(operator).operation(operator);
     Integer answer = integerAbstractOperation.operator(Integer.parseInt(firstNumber), Integer.parseInt(secondNumber));
     result.add(answer);
     return answer;
     }

     private Double getaDouble(String firstNumber, String secondNumber, String operator) throws Exception {
     AbstractOperation<Double> doubleAbstractOperation = CalculatorDoubleFactory.valueOf(operator).operation(operator);
     Double answer = doubleAbstractOperation.operator(Double.parseDouble(firstNumber), Double.parseDouble(secondNumber));
     result.add(answer);
     return answer;
     }
     */


}
