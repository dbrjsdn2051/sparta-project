package exception.template;

import exception.ExceptionHandler;
import exception.InputLevelValidExceptionHandlerImpl;
import exception.StartNumberValidExceptionHandlerImpl;
import exception.UncheckedRuntimeException;

import java.util.logging.Logger;

public class BaseballActionExceptionTemplateImpl implements BaseballActionExceptionTemplate {

    ExceptionHandler exceptionHandler;

    @Override
    public void actionException(String gameSet) {
        exceptionHandler = new StartNumberValidExceptionHandlerImpl();
        try {
            exceptionHandler.valid(gameSet);
        } catch (UncheckedRuntimeException e) {
            System.out.println("에러 메시지 : " + e.getMessage());
        }
    }

    @Override
    public void levelException(String input) {
        exceptionHandler = new InputLevelValidExceptionHandlerImpl();
        try {
            exceptionHandler.valid(input);
        } catch (UncheckedRuntimeException e) {
            System.out.println("에러 메시지 : " + e.getMessage());
        }
    }
}
