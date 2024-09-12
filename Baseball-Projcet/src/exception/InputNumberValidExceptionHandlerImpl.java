package exception;

import exception.ExceptionHandler;
import start.GameStart;

import java.util.HashSet;
import java.util.regex.Pattern;

public class InputNumberValidExceptionHandlerImpl implements ExceptionHandler {

    private final static String ONLY_NUMBER_REG = "\"^\\\\d+$\"";
    private final int gameLevel;

    public InputNumberValidExceptionHandlerImpl(int gameLevel) {
        this.gameLevel = gameLevel;
    }

    public void valid(String inputNumber) throws Exception {
        if (inputNumber.length() != gameLevel) throw new Exception("제대로 된 자리수를 입력해주세요");
        if (Pattern.matches(ONLY_NUMBER_REG, inputNumber)) throw new Exception("숫자만 입력해주세요.");
        if (inputNumber.contains("0")) throw new Exception("0은 입력할 수 없습니다.");
        if (sameNumberValid(inputNumber)) throw new Exception("중복된 숫자가 입력되었습니다.");
    }

    private boolean sameNumberValid(String input) {
        HashSet<Character> set = new HashSet<>();
        for (char c : input.toCharArray()) {
            if (set.contains(c)) {
                return true;
            }
            set.add(c);
        }
        return false;
    }

}
