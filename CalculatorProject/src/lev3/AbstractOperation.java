package lev3;

public interface AbstractOperation<T extends Number> {

    T operator(T firstNumber, T secondNumber);
}
