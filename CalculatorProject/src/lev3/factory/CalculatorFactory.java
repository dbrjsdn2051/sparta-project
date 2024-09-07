package lev3.factory;

import lev3.AbstractOperation;
import lev3.doubleImpl.AddDoubleOperation;
import lev3.doubleImpl.DivDoubleOperation;
import lev3.doubleImpl.MulDoubleOperation;
import lev3.doubleImpl.SubDoubleOperation;
import lev3.integerImpl.AddIntegerOperation;
import lev3.integerImpl.DivIntegerOperation;
import lev3.integerImpl.MulIntegerOperation;
import lev3.integerImpl.SubIntegerOperation;

import java.util.Optional;

public enum CalculatorFactory {

    ADD("+", new AddIntegerOperation(), new AddDoubleOperation()),
    SUB("-", new SubIntegerOperation(), new SubDoubleOperation()),
    MUL("*", new MulIntegerOperation(), new MulDoubleOperation()),
    DIV("/", new DivIntegerOperation(), new DivDoubleOperation());


    private final String operator;
    private final AbstractOperation<Integer> integerAbstractOperation;
    private final AbstractOperation<Double> doubleAbstractOperation;

    CalculatorFactory(String operator, AbstractOperation<Integer> integerAbstractOperation, AbstractOperation<Double> doubleAbstractOperation) {
        this.operator = operator;
        this.integerAbstractOperation = integerAbstractOperation;
        this.doubleAbstractOperation = doubleAbstractOperation;
    }

    public static <T extends Number> AbstractOperation<T> operationType(String operator, Class<T> type) {
        for (CalculatorFactory value : values()) {
            if (value.operator.equals(operator) && type == Integer.class) return (AbstractOperation<T>) value.integerAbstractOperation;
            if (value.operator.equals(operator) && type == Double.class) return (AbstractOperation<T>) value.doubleAbstractOperation;
        }
        return null;
    }


    /**
    public static AbstractOperation<Integer> operation(String operator) throws Exception {
        for (CalculatorIntegerFactory value : values()) {
            if(value.operator.equals(operator)) return value.abstractOperation;
        }
        return null;
    }
    */

}
