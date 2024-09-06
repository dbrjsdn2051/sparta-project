package lev3.exception;

import java.util.regex.Pattern;

public class ExceptionHandler {

    private final String REGEX = "[0-9]+";
    private static final String OPERATION_REG = "[+\\-*/]";

    public void validation(String firstNum, String secondNum, String operator) throws Exception {
        boolean firstType = new TypeValidation().type(firstNum);
        boolean secondType = new TypeValidation().type(secondNum);

        if (!Pattern.matches(OPERATION_REG, operator)) throw new Exception("연산자 타입이 맞지 않습니다.");
        if ((firstType && !secondType) || (!firstType && secondType)) throw new Exception("숫자 타입이 서로 맞지 않습니다.");
        if (operator.equals("/") && secondType && Double.parseDouble(secondNum) == 0.0)
            throw new Exception("0으로 나눌 수 없습니다.");
        if (operator.equals("/") && !secondType && Integer.parseInt(secondNum) == 0)
            throw new Exception("0으로 나눌 수 없습니다.");
        if (!Pattern.matches(REGEX, firstNum) || !Pattern.matches(REGEX, secondNum))
            throw new Exception("제대로된 숫자를 입력해주세요.");
    }

}
