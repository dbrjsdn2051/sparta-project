package start;

import java.util.HashMap;
import java.util.List;

import static start.BaseballScore.*;

public class NumberValid {

    private final HashMap<BaseballScore, Integer> map;

    public NumberValid() {
        map = new HashMap<>();
    }

    public HashMap<BaseballScore, Integer> countStrikeBallOut(List<Integer> numberList, List<Integer> randomNumber) {
        mapToInit();
        for (int i = 0; i < randomNumber.size(); i++) {
            if (randomNumber.contains(numberList.get(i))) {
                if (randomNumber.get(i).equals(numberList.get(i))) {
                    map.put(STRIKE, map.getOrDefault(STRIKE, 0) + 1);
                } else {
                    map.put(BALL, map.getOrDefault(BALL, 0) + 1);
                }
            } else {
                map.put(OUT, map.getOrDefault(OUT, 0) + 1);
            }
        }
        return map;
    }


    private void mapToInit() { /** map 초기화 값 세팅 **/
        map.put(STRIKE, 0);
        map.put(BALL, 0);
        map.put(OUT, 0);
    }

}
