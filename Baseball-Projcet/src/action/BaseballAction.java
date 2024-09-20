package action;

import action.GameAction;
import exception.ExceptionHandler;
import exception.InputLevelValidExceptionHandlerImpl;
import exception.StartNumberValidExceptionHandlerImpl;
import exception.template.BaseballActionExceptionTemplate;
import exception.template.BaseballActionExceptionTemplateImpl;
import start.BaseballScore;
import start.GameStart;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class BaseballAction {

    private final BaseballActionExceptionTemplate exceptionHandler;
    private final Scanner sc;
    private int level;
    private final List<Integer> list;

    public BaseballAction() {
        this.sc = new Scanner(System.in);
        this.list = new ArrayList<>();
        this.level = 3; // 생성 시점 default
        this.exceptionHandler = new BaseballActionExceptionTemplateImpl();
    }

    public void action(int gameSet) { /** 게임 디스플레이 동작 **/

        exceptionHandler.actionException(String.valueOf(gameSet)); /** 에러 인터셉터 **/

        GameAction gameAction = GameAction.valueOf(gameSet);

        if (gameAction == GameAction.LEVEL) {
            setLengthLimit();
        } else if (gameAction == GameAction.START) {
            list.add(startGame());
        } else if (gameAction == GameAction.LIST) {
            getList();
        } else if (gameAction == GameAction.EXIT) {
            System.out.println("게임을 종료합니다.");
        }
    }

    private void setLengthLimit() {  /** 난이도 조정 **/

        System.out.println("자릿수를 입력해주세요");

        String input = sc.nextLine();
        exceptionHandler.levelException(input); /** 에러 인터셉터 **/
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
