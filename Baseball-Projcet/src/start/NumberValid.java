package start;

import java.util.HashMap;
import java.util.List;

public class NumberValid {

    private HashMap<String, Integer> map;

    public NumberValid() {
        map = new HashMap<>();
    }

    public HashMap<String, Integer> countStrikeBallOut(List<Integer> numberList, List<Integer> randomNumber) {
        mapToInit();
        for (int i = 0; i < randomNumber.size(); i++) {
            if (randomNumber.contains(numberList.get(i))) {
                if (randomNumber.get(i).equals(numberList.get(i))) {
                    map.put("Strike", map.getOrDefault("Strike", 0) + 1);
                } else {
                    map.put("Ball", map.getOrDefault("Ball", 0) + 1);
                }
            } else {
                map.put("Out", map.getOrDefault("Out", 0) + 1);
            }
        }
        return map;
    }


    private void mapToInit() { /** map 초기화 값 세팅 **/
        map.put("Strike", 0);
        map.put("Ball", 0);
        map.put("Out", 0);
    }

}
