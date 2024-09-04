package lev2;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private int firstNum;
    private int secondNum;
    private String operator;

    private List<Integer> result;

    public Calculator() {
        this.result = new ArrayList<>();
    }

    public void setFirstNum(int firstNum) {
        this.firstNum = firstNum;
    }

    public void setSecondNum(int secondNum) {
        this.secondNum = secondNum;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int operation() throws Exception {
        int answer = 0;
        if (this.operator.equals("+")) {
            answer = firstNum + secondNum;
            this.result.add(answer);
        } else if (this.operator.equals("-")) {
            answer = firstNum - secondNum;
            this.result.add(answer);
        } else if (this.operator.equals("*")) {
            answer = firstNum * secondNum;
            this.result.add(answer);
        } else {
            if (secondNum == 0) {
                throw new Exception("0으로 나눌 수 없습니다.");
            }
            answer = firstNum / secondNum;
            this.result.add(answer);
        }

        return answer;
    }

    public List<Integer> getResult() {
        return result;
    }

    public List<Integer> removeResult() {
        result.remove(0);
        return result;
    }


}
