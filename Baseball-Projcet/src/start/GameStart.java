package start;

import exception.ExceptionHandler;
import exception.InputNumberValidExceptionHandlerImpl;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class GameStart {

    private final int gameLevel;
    private final Scanner sc;
    private final RandomNumber randomNumber;
    private final ExceptionHandler exceptionHandler;

    public GameStart(int gameLevel) {
        this.gameLevel = gameLevel;
        this.sc = new Scanner(System.in);
        this.randomNumber = new RandomNumber(this.gameLevel);
        this.exceptionHandler = new InputNumberValidExceptionHandlerImpl(this.gameLevel);
    }

    public int start() {
        int cnt = 0;
        while (true) {
            System.out.println("숫자를 입력해주세요.");
            String input = sc.nextLine();
            try {
                exceptionHandler.valid(input);
            } catch (Exception e) {
                System.out.println("에러메시지 : " + e.getMessage());
                continue;
            }
            int[] result = NumberValid.numberCheck(inputNumberList(input), randomNumber.numberList());
            if (result[0] == gameLevel) {
                System.out.println("정답입니다. ");
                return cnt;
            } else {
                System.out.println("스트라이크 : " + result[0] + " 볼 : " + result[1] + " 아웃 : " + result[2]);
                cnt += 1;
            }
        }
    }

    public List<Integer> inputNumberList(String input) {
        return input.chars().mapToObj(Character::getNumericValue)
                .collect(Collectors.toList());
    }

}
