package lev3;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArithmeticCalculator arithmeticCalculator = new ArithmeticCalculator();
        while (true) {
            System.out.print("첫번째 수를 입력해주세요 : ");
            String firstNum = sc.next();

            System.out.print("두번째 수를 입력해주세요 : ");
            String secondNum = sc.next();

            System.out.print("연산자를 입력해주세요 : ");
            String operator = sc.next();
            System.out.println();
            try {
                Number answer = arithmeticCalculator.calculation(firstNum, secondNum, operator);
                System.out.println("결과 : " + answer);
                System.out.println();
            } catch (Exception e){
                System.out.println("에러 메시지: " + e.getMessage());
                continue;
            }

            System.out.println("지금까지 연산 결과");
            arithmeticCalculator.getResult();

            System.out.print("더 계산하시겠습니까? (exit 입력 시 종료) : ");
            String exit = sc.next();

            if(exit.equals("exit")) break;

        }


    }
}
