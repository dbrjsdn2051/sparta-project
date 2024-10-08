package org.example.todolistproject.repository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.example.todolistproject.dto.page.PageResponseDto;
import org.example.todolistproject.entity.Comment;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

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


    @BeforeEach
    public void init() {
        User user1 = new User("user1", "asdf@naver.com", "1234", Role.USER);
        User user2 = new User("user2", "qwer@naver.com", "1234", Role.USER);
        User user3 = new User("user3", "zxcv@naver.com", "1234", Role.USER);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        Schedule schedule1 = new Schedule("title1", "1234", "study", null);
        Schedule schedule2 = new Schedule("title2", "1234", "study", null);
        Schedule schedule3 = new Schedule("title3", "1234", "study", null);
        schedule1.addUser(user1);
        schedule2.addUser(user2);
        schedule3.addUser(user3);

        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);
        scheduleRepository.save(schedule3);

        Comment comment = new Comment("user1", "1234", "LGTM");
        comment.addSchedule(schedule1);
        commentRepository.save(comment);

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
        System.out.println("========================================");
        String username2 = findSchedule2.getUser().getUsername();
        assertThat(findSchedule2.getTitle()).isEqualTo("title2");
        assertThat(username2).isEqualTo("user2");
    }

    @Test
    public void updateTesT(){
        Schedule findSchedule = scheduleRepository.findById(1L).orElseThrow();
        findSchedule.setContent("TIL");

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
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<PageResponseDto> findAll = scheduleRepository.findAllWithComment(pageRequest);
        for (PageResponseDto pageResponseDto : findAll) {
            System.out.println("pageResponseDto = " + pageResponseDto.toString());
        }
    }


}