package start;

import exception.ExceptionHandler;
import exception.InputNumberValidExceptionHandlerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameStart {

    private final int gameLevel;
    private Scanner sc;
    private RandomNumber randomNumber;
    private ExceptionHandler exceptionHandler;

    public GameStart(int gameLevel) {
        this.gameLevel = gameLevel;
        this.sc = new Scanner(System.in);
        this.randomNumber = new RandomNumber(this.gameLevel);
        this.exceptionHandler = new InputNumberValidExceptionHandlerImpl(gameLevel);
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
            }
            int[] result = NumberValid.numberCheck(parserNumber(input), randomNumber.numberList());
            if (result[0] == 3) {
                System.out.println("정답입니다. ");
                return cnt;
            } else {
                System.out.println("스트라이크 : " + result[0] + " 볼 : " + result[1] + " 아웃 : " + result[2]);
                cnt += 1;
            }
        }
    }

    public List<Integer> parserNumber(String input) {
        ArrayList<Integer> list = new ArrayList<>();
        for (char c : input.toCharArray()) {
            list.add(Integer.parseInt(String.valueOf(c)));
        }
        return list;
    }

    public int getGameLevel() {
        return gameLevel;
    }
}
