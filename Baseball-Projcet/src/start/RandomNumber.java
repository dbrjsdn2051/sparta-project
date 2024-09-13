package start;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomNumber {

    private final int count;
    private final Set<Integer> numberSet;
    private final Random random;

    public RandomNumber(int count) {
        this.random = new Random();
        this.count = count;
        this.numberSet = new HashSet<>();
    }

    public List<Integer> randomNumberList() {
        while (numberSet.size() != count){
            numberSet.add(random.nextInt(9) + 1);
        }
        return numberSet.stream().toList();
    }
}
