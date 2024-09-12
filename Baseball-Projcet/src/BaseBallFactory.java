import exception.ExceptionHandler;
import exception.InputLevelValidExceptionHandlerImpl;
import exception.StartNumberValidExceptionHandlerImpl;
import start.GameStart;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BaseBallFactory {

    private ExceptionHandler exceptionHandler;
    private final Scanner sc;
    private int level;
    private final List<Integer> list;

    public BaseBallFactory() {
        this.sc = new Scanner(System.in);
        this.list = new ArrayList<>();
        this.level = 3; // 생성 시점 default
    }

    public void action(int gameSet) throws Exception {
        exceptionHandler = new StartNumberValidExceptionHandlerImpl();
        exceptionHandler.valid(String.valueOf(gameSet));
        if (gameSet == 0) setDigitLimit();
        if (gameSet == 1) list.add(startGame());
        if (gameSet == 2) getList();
        if (gameSet == 3) System.out.println("게임을 종료합니다.");
    }

    private void setDigitLimit() throws Exception {
        exceptionHandler = new InputLevelValidExceptionHandlerImpl();
        System.out.println("자릿수를 입력해주세요");

        String input = sc.nextLine();
        exceptionHandler.valid(input);
        level = Integer.parseInt(input);

        System.out.println("자릿수 설정이 완료되었습니다.");
        System.out.println();

    }

    private int startGame() {
        GameStart gameStart = new GameStart(level);
        return gameStart.start();
    }


    private void getList() {
        System.out.println("< 게임 기록 보기 >");

        if (list.isEmpty()) {
            System.out.println("아직 게임기록이 없어요!!");
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "번째 게임 : 시도 횟수 - " + list.get(i));
        }
        System.out.println();
    }
}
