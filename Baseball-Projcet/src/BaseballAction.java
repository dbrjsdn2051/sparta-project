import exception.ExceptionHandler;
import exception.InputLevelValidExceptionHandlerImpl;
import exception.StartNumberValidExceptionHandlerImpl;
import start.BaseballScore;
import start.GameStart;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class BaseballAction {

    private static final int SET_LENGTH_LIMIT = 0;
    private static final int START_GAME = 1;
    private static final int GET_LIST = 2;
    private static final int EXIT_GAME = 3;


    private ExceptionHandler exceptionHandler;
    private final Scanner sc;
    private int level;
    private final List<Integer> list;

    public BaseballAction() {
        this.sc = new Scanner(System.in);
        this.list = new ArrayList<>();
        this.level = 3; // 생성 시점 default
    }

    public void action(int gameSet) throws Exception { /** 게임 디스플레이 동작 **/
        exceptionHandler = new StartNumberValidExceptionHandlerImpl();
        exceptionHandler.valid(String.valueOf(gameSet)); /** 에러 인터셉터 **/

        if (gameSet == SET_LENGTH_LIMIT) {
            setLengthLimit();
        }else if (gameSet == START_GAME) {
            list.add(startGame());
        }else if (gameSet == GET_LIST) {
            getList();
        }else if (gameSet == EXIT_GAME) {
            System.out.println("게임을 종료합니다.");
        }
    }

    private void setLengthLimit() throws Exception {  /** 난이도 조정 **/
        exceptionHandler = new InputLevelValidExceptionHandlerImpl();
        System.out.println("자릿수를 입력해주세요");
        String input = sc.nextLine();
        exceptionHandler.valid(input); /** 에러 인터셉터 **/
        level = Integer.parseInt(input);

        System.out.println("자릿수 설정이 완료되었습니다.");
        System.out.println();
    }

    private int startGame() { /** 게임 시작 **/
        return new GameStart(level).start();
    }

    private void getList() { /** 게임 기록 **/
        System.out.println("< 게임 기록 보기 >");
        if (list.isEmpty()) System.out.println("아직 게임기록이 없어요!!");
        IntStream.range(0, list.size()).forEach(i -> System.out.println((i + 1) + "번째 게임 : 시도 횟수 - " + list.get(i)));
        System.out.println();
    }
}
