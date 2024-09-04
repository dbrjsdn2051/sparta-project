package lev2;

import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculator calculator = new Calculator();

        while (true){
            System.out.print("첫번째 숫자를 입력해주세요 : ");
            int firstNumber = sc.nextInt();
            calculator.setFirstNum(firstNumber);
            System.out.print("두번째 숫자를 입력해주세요 : ");
            int secondNumber = sc.nextInt();
            calculator.setSecondNum(secondNumber);
            System.out.print("연산자를 입력해주세요 : ");
            String operator = sc.next();
            System.out.println();
            try{
                calculator.setOperator(operator);
                int result = calculator.operation();
                System.out.println("결과는 = " + result);
            } catch (Exception e){
                System.out.println("잘못된 연산입니다 = " + e.getMessage());
                continue;
            }

            System.out.println("지금까지 연산결과");
            List<Integer> result = calculator.getResult();
            for (Integer i : result) {
                System.out.println(i);
            }

            System.out.print("더 계산하시겠습니까? (exit 입력 시 종료): ");
            String exit = sc.next();
            System.out.println();
            if (exit.equalsIgnoreCase("exit")) {
                System.out.println("프로그램 종료");
                break;
            }
        }
    }
}
