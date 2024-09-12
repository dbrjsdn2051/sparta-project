package start;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomNumber {

    private final int count;
    private final Set<Integer> numbers = new HashSet<>();

    public RandomNumber(int count) {
        this.count = count;
    }

    public List<Integer> numberList() {
        Random random = new Random();

        while (numbers.size() != count) {
            int num = random.nextInt(9) + 1;
            numbers.add(num);
        }

        return numbers.stream().toList();
    }
}
