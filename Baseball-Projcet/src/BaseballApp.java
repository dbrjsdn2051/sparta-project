import action.BaseballAction;

import java.util.Scanner;

public class BaseballApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BaseballAction baseballAction = new BaseballAction();

        String input = "";
        System.out.println("환영합니다! 원하시는 번호를 입력해주세요");
        while (!input.equals("3")) {
            System.out.println("0. 자리수 설정 1. 게임 시작하기  2. 게임 기록 보기  3. 종료하기");
            input = sc.nextLine();
            baseballAction.action(Integer.parseInt(input));
        }
    }


}
