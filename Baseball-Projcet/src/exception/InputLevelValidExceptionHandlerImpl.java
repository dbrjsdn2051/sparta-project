package exception;

import java.util.regex.Pattern;

public class InputLevelValidExceptionHandlerImpl implements ExceptionHandler {

    private static final String ONLY_THREE_TO_FIVE = "^[345]+$";

    @Override
    public void valid(String inputNumber) {
        if (!Pattern.matches(ONLY_THREE_TO_FIVE, inputNumber)) throw new UncheckedRuntimeException("3, 4, 5 숫자만 입력가능합니다.");
    }
}
