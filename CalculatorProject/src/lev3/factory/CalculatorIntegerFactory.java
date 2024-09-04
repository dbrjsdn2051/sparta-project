package lev3.factory;

import lev3.AbstractOperation;
import lev3.integerImpl.AddIntegerOperation;
import lev3.integerImpl.DivIntegerOperation;
import lev3.integerImpl.MulIntegerOperation;
import lev3.integerImpl.SubIntegerOperation;

public enum CalculatorIntegerFactory {

    ADDINTEGER("+", new AddIntegerOperation()),
    SUBINTEGER("-", new SubIntegerOperation()),
    MULINTEGER("*", new MulIntegerOperation()),
    DIVINTEGER("/", new DivIntegerOperation());


    private final String operator;
    private final AbstractOperation<Integer> abstractOperation;

    CalculatorIntegerFactory(String operator, AbstractOperation<Integer> abstractOperation) {
        this.operator = operator;
        this.abstractOperation = abstractOperation;
    }

    public static AbstractOperation<Integer> operation(String operator) throws Exception {
        for (CalculatorIntegerFactory value : values()) {
            if(value.operator.equals(operator)) return value.abstractOperation;
        }
        return null; 
    }


}
