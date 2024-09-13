package start;

import exception.ExceptionHandler;
import exception.InputNumberValidExceptionHandlerImpl;

import java.util.*;
import java.util.stream.Collectors;

public class GameStart {

    private final int gameLevel;
    private final Scanner sc;
    private final RandomNumber randomNumber;
    private final ExceptionHandler exceptionHandler;
    private final NumberValid numberValid;

    public GameStart(int gameLevel) {
        this.gameLevel = gameLevel;
        this.sc = new Scanner(System.in);
        this.randomNumber = new RandomNumber(this.gameLevel);
        this.exceptionHandler = new InputNumberValidExceptionHandlerImpl(this.gameLevel);
        this.numberValid = new NumberValid();
    }

    public int start() {
        int cnt = 0;

        while (true) {

            System.out.println("숫자를 입력해주세요.");
            String input = sc.nextLine();

            try {
                exceptionHandler.valid(input); /** 에러 인터셉터 **/
            } catch (Exception e) {
                System.out.println("에러메시지 : " + e.getMessage());
                continue;
            }

            HashMap<String, Integer> calculateStrikeBallOut = numberValid.countStrikeBallOut(inputNumberList(input), randomNumber.randomNumberList());

            if (calculateStrikeBallOut.get("Strike") == gameLevel) {
                System.out.println("정답입니다. ");
                return cnt;
            }

            System.out.println("스트라이크 : " + calculateStrikeBallOut.get("Strike") + " 볼 : " + calculateStrikeBallOut.get("Ball") + " 아웃 : " + calculateStrikeBallOut.get("Out"));
            cnt += 1;
        }
    }

    public List<Integer> inputNumberList(String input) {
        return input.chars().mapToObj(Character::getNumericValue)
                .collect(Collectors.toList());
    }

}
