package org.example.todolistproject.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.comment.request.CommentCreateRequestDto;
import org.example.todolistproject.dto.schedule.request.ScheduleCreateRequestDto;
import org.example.todolistproject.dto.user.request.UserCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class SecurePasswordServiceAspect {

    private final PasswordEncoder passwordEncoder;


    @Pointcut("@annotation(org.example.todolistproject.aop.SecurePasswordService)")
    public void passwordHashing(){
    }

    @Around("passwordHashing()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        for (Object param : args) {
            if(param instanceof ScheduleCreateRequestDto dto){
                String password = dto.getPassword();
                String encodedPassword = passwordEncoder.encode(password);
                dto.setPassword(encodedPassword);
                break;
            }
            if(param instanceof UserCreateRequestDto dto){
                String password = dto.getPassword();
                String encodedPassword = passwordEncoder.encode(password);
                dto.setPassword(encodedPassword);
                break;
            }
            if(param instanceof CommentCreateRequestDto dto){
                String password = dto.getPassword();
                String encodedPassword = passwordEncoder.encode(password);
                dto.setPassword(encodedPassword);
                break;
            }
        }
        return joinPoint.proceed(args);
    }
}
