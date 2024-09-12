package exception;

import java.util.regex.Pattern;

public class StartNumberValidExceptionHandlerImpl implements ExceptionHandler {


    private static final String ONLY_ONE_TO_THREE = "^[0123]+$";

    @Override
    public void valid(String inputNumber) throws Exception {
        if (!Pattern.matches(ONLY_ONE_TO_THREE, inputNumber)) throw new Exception("0, 1, 2, 3 숫자만 입력가능합니다.");
    }
}
