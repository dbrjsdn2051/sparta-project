package start;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberValid {

    public static int[] numberCheck(List<Integer> numberList, List<Integer> randomNumber) {
        int[] result = new int[3];
        for (int i = 0; i < randomNumber.size(); i++) {
            if (randomNumber.contains(numberList.get(i))) {
                if (randomNumber.get(i).equals(numberList.get(i))) result[0] += 1;
                else result[1] += 1;
            } else result[2] += 1;
        }
        return result;
    }
}
