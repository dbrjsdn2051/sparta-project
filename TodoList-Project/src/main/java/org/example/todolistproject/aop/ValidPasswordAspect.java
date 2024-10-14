package org.example.todolistproject.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.AuthenticationRequestDto;
import org.example.todolistproject.entity.Comment;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.MissMatchPasswordException;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.repository.CommentRepository;
import org.example.todolistproject.repository.ScheduleRepository;
import org.example.todolistproject.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidPasswordAspect {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Pointcut("@annotation(org.example.todolistproject.aop.ValidPassword)")
    public void validPasswordAnnotation() {
    }



    @Before("validPasswordAnnotation()")
    public void beforeAdvice(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        ValidPassword validPassword = method.getAnnotation(ValidPassword.class);
        TableType type = validPassword.value();

        Object[] args = joinPoint.getArgs();
        AuthenticationRequestDto dto = (AuthenticationRequestDto) args[0];


        if (type == TableType.USER) {
            Long requestId = dto.getRequestId();
            User findUser = userRepository.findById(requestId).orElseThrow(NoResultDataException::new);
            passwordEncoder.validPassword(dto.getRequestPassword(), findUser.getPassword());
        } else if (type == TableType.SCHEDULE) {
            Long requestId = dto.getRequestId();
            Schedule findSchedule = scheduleRepository.findById(requestId).orElseThrow(NoResultDataException::new);
            passwordEncoder.validPassword(dto.getRequestPassword(), findSchedule.getPassword());
        } else if (type == TableType.COMMENT) {
            Long requestId = dto.getRequestId();
            Comment findComment = commentRepository.findById(requestId).orElseThrow(NoResultDataException::new);
            passwordEncoder.validPassword(dto.getRequestPassword(), findComment.getPassword());
        }

    }

}
