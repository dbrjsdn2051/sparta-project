package lev1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NonClassCaculator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Integer> total = new ArrayList<>();
        int result = 0;

        while (true) {
            System.out.print("첫번째 숫자를 입력해주세요: ");
            int firstNum = Integer.parseInt(sc.nextLine());

            System.out.print("두번째 숫자를 입력해주세요: ");
            int secondNum = Integer.parseInt(sc.nextLine());

            System.out.print("연산자를 입력해주세요 (+, -, *, /): ");
            String operator = sc.nextLine();

            if (operator.equals("+")) {
                result = firstNum + secondNum;
            } else if (operator.equals("-")) {
                result = firstNum - secondNum;
            } else if (operator.equals("*")) {
                result = firstNum * secondNum;
            } else if (operator.equals("/")) {
                if (secondNum != 0) {
                    result = firstNum / secondNum;
                } else {
                    System.out.println("0으로 나눌 수 없습니다.");
                    continue;
                }
            } else {
                System.out.println("유효한 연산자가 아닙니다.");
                continue;
            }

            System.out.println("결과: " + result);
            total.add(result);

            System.out.println("지금까지 계산 결과:");
            for (Integer i : total) {
                System.out.println(i);
            }

            System.out.print("더 계산하시겠습니까? (exit 입력 시 종료): ");
            String exit = sc.nextLine();
            if (exit.equalsIgnoreCase("exit")) break;
        }

    }
}