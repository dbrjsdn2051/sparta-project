package lev3.factory;

import lev3.AbstractOperation;
import lev3.doubleImpl.AddDoubleOperation;
import lev3.doubleImpl.DivDoubleOperation;
import lev3.doubleImpl.MulDoubleOperation;
import lev3.doubleImpl.SubDoubleOperation;

public enum CalculatorDoubleFactory {
    ADDDOUBLE("+", new AddDoubleOperation()),
    SUBDOUBLE("-", new SubDoubleOperation()),
    MULDOUBLE("*", new MulDoubleOperation()),
    DIVDOUBLE("/", new DivDoubleOperation());


    private final String operator;
    private final AbstractOperation<Double> abstractOperation;

    CalculatorDoubleFactory(String operator, AbstractOperation<Double> abstractOperation) {
        this.operator = operator;
        this.abstractOperation = abstractOperation;
    }

    public static AbstractOperation<Double> operation(String operator) throws Exception {
        for (CalculatorDoubleFactory value : values()) {
            if(value.operator.equals(operator)) return value.abstractOperation;
        }

        throw new Exception("연산자 타입이 아닙니다.");
    }

}
