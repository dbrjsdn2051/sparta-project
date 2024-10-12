package org.example.todolistproject.repository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.example.todolistproject.dto.comment.request.CommentCreateRequestDto;
import org.example.todolistproject.dto.page.PageResponseDto;
import org.example.todolistproject.dto.schedule.request.ScheduleCreateRequestDto;
import org.example.todolistproject.entity.Comment;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ScheduleRepositoryTest {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EntityManager em;

    @Autowired
    ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        em.createNativeQuery("alter table user auto_increment 1").executeUpdate();
        em.createNativeQuery("alter table schedule auto_increment 1").executeUpdate();
        em.createNativeQuery("alter table comment auto_increment 1").executeUpdate();

        User user1 = new User("user1", "asdf@naver.com", "1234", Role.USER);
        User user2 = new User("user2", "qwer@naver.com", "1234", Role.USER);
        User user3 = new User("user3", "zxcv@naver.com", "1234", Role.USER);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        ScheduleCreateRequestDto scheduleDto1 =
                new ScheduleCreateRequestDto("title1", "hello world", "1234", null);
        ScheduleCreateRequestDto scheduleDto2 =
                new ScheduleCreateRequestDto("title2", "hello world", "1234", null);
        ScheduleCreateRequestDto scheduleDto3 =
                new ScheduleCreateRequestDto("title3", "hello world", "1234", null);

        Schedule schedule1 = modelMapper.map(scheduleDto1, Schedule.class);
        Schedule schedule2 = modelMapper.map(scheduleDto2, Schedule.class);
        Schedule schedule3 = modelMapper.map(scheduleDto3, Schedule.class);
        schedule1.addUser(user1);
        schedule2.addUser(user2);
        schedule3.addUser(user3);

        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);
        scheduleRepository.save(schedule3);

        CommentCreateRequestDto commentDto1
                = new CommentCreateRequestDto("user1", "LGTM", "1234");
        CommentCreateRequestDto commentDto2
                = new CommentCreateRequestDto("user2", "LGTM", "1234");
        CommentCreateRequestDto commentDto3
                = new CommentCreateRequestDto("user3", "LGTM", "1234");

        Comment comment1 = modelMapper.map(commentDto1, Comment.class);
        Comment comment2 = modelMapper.map(commentDto1, Comment.class);
        Comment comment3 = modelMapper.map(commentDto1, Comment.class);

        comment1.addSchedule(schedule1);
        comment2.addSchedule(schedule1);
        comment3.addSchedule(schedule2);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        em.flush();
        em.clear();
        System.out.println("---------------------------------------------------");
    }

    @Test
    public void addTesT() {
        Schedule findSchedule = scheduleRepository.findById(1L).orElseThrow();
        String username = findSchedule.getUser().getUsername();
        assertThat(findSchedule.getTitle()).isEqualTo("title1");
        assertThat(username).isEqualTo("user1");

        Schedule findSchedule2 = scheduleRepository.findById(2L).orElseThrow();
        System.out.println("========================================"); // 쿼리 확인
        String username2 = findSchedule2.getUser().getUsername();
        assertThat(findSchedule2.getTitle()).isEqualTo("title2");
        assertThat(username2).isEqualTo("user2");
    }

    @Test
    public void updateTesT(){
        Schedule findSchedule = scheduleRepository.findById(1L).orElseThrow();
        findSchedule.changeContent("TIL");

        em.flush();
        em.clear();

        Schedule updateSchedule = scheduleRepository.findById(1L).orElseThrow();
        assertThat(updateSchedule.getContent()).isEqualTo("TIL");
    }

    @Test
    public void deleteTest(){
        scheduleRepository.deleteById(1L);
        assertThrows(RuntimeException.class, () -> scheduleRepository.findById(1L).orElseThrow());
    }

    @Test
    public void findAllTest(){
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<PageResponseDto> findAll = scheduleRepository.findAllWithComment(pageRequest);
        assertThat(findAll.getSize()).isEqualTo(3);
    }


}