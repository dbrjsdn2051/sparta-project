package org.example.todolistproject.service;

import org.example.todolistproject.dto.comment.CommentDto;
import org.example.todolistproject.entity.Comment;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.MissMatchPasswordException;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.factory.CommentFactory;
import org.example.todolistproject.repository.CommentRepository;
import org.example.todolistproject.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    CommentFactory commentFactory;

    @InjectMocks
    CommentService commentService;

    CommentDto.Create createDto;
    CommentDto.Update updateDto;
    CommentDto.Delete deleteDto;

    Comment comment;
    Schedule schedule;

    User user;


    @BeforeEach
    void init() {
        createDto = new CommentDto.Create("user", "content", "1234");
        updateDto = new CommentDto.Update();
        updateDto.setCommentId(1L);
        updateDto.setContent("TIL");
        updateDto.setPassword("1234");

        deleteDto = new CommentDto.Delete();
        deleteDto.setCommentId(1L);
        deleteDto.setPassword("1234");

        user = User.builder()
                .username("testUser")
                .password("encodedPassword")
                .role(Role.USER)
                .email("user@naver.com")
                .build();

        schedule = Schedule.builder()
                .title("TEST1")
                .content("GOOD")
                .weather("rain")
                .password("1234")
                .user(user)
                .build();

        comment = Comment.builder()
                .username("USER")
                .content("GOOD")
                .schedule(schedule)
                .password("1111")
                .build();
    }

    @Test
    void addSuccessTest() {
        // given
        when(commentRepository.save(comment)).thenReturn(comment);
        when(scheduleRepository.findById(schedule.getScheduleId())).thenReturn(Optional.ofNullable(schedule));
        when(commentFactory.createComment(createDto, schedule)).thenReturn(comment);

        // when
        Long commentId = commentService.add(createDto, schedule.getScheduleId(), user);

        // then
        assertThat(commentId).isEqualTo(comment.getCommentId());
        verify(commentFactory, times(1)).createComment(any(), any());
        verify(commentRepository, times(1)).save(any());
        verify(scheduleRepository, times(1)).findById(any());
    }

    @Test
    void updateSuccessTest() {
        // given
        when(commentRepository.findById(1L)).thenReturn(Optional.ofNullable(comment));
        doNothing().when(commentFactory).updateComment(updateDto, comment);

        // when
        commentService.update(updateDto);

        // then
        verify(commentRepository, times(1)).findById(any());
        verify(commentFactory, times(1)).updateComment(any(), any());
    }

    @Test
    void updateFail_NotFoundComment_Test() {
        // given
        doThrow(NoResultDataException.class)
                .when(commentRepository)
                .findById(1L);

        // when & then
        assertThrows(NoResultDataException.class, () -> commentService.update(updateDto));
        verify(commentRepository, times(1)).findById(any());
    }

    @Test
    void updateFail_MissMatchPassword_Test() {
        // given
        when(commentRepository.findById(1L)).thenReturn(Optional.ofNullable(comment));
        doThrow(MissMatchPasswordException.class)
                .when(commentFactory)
                .updateComment(updateDto, comment);

        // when & then
        assertThrows(MissMatchPasswordException.class, () -> commentService.update(updateDto));
        verify(commentRepository, times(1)).findById(1L);
        verify(commentFactory, times(1)).updateComment(any(), any());
    }

    @Test
    void deleteSuccessTest() {
        // given
        when(commentRepository.findById(1L)).thenReturn(Optional.ofNullable(comment));
        doNothing().when(commentFactory)
                .deleteComment(deleteDto, comment);
        doNothing().when(commentRepository)
                .deleteById(1L);
        // when
        commentService.delete(deleteDto);

        // then
        verify(commentRepository, times(1)).findById(any());
        verify(commentFactory, times(1)).deleteComment(any(), any());
        verify(commentRepository, times(1)).deleteById(any());
    }

    @Test
    void deleteFail_NotFound_Test(){
        // given
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NoResultDataException.class, () -> commentService.delete(deleteDto));
        verify(commentRepository, times(1)).findById(any());
    }

    @Test
    void deleteFail_MissMatchPassword_Test(){
        // given
        when(commentRepository.findById(1L)).thenReturn(Optional.ofNullable(comment));
        doThrow(MissMatchPasswordException.class)
                .when(commentFactory)
                .deleteComment(deleteDto, comment);

        // when & then
        assertThrows(MissMatchPasswordException.class, () -> commentService.delete(deleteDto));
        verify(commentRepository, times(1)).findById(any());
        verify(commentFactory).deleteComment(any(), any());
    }


    @Test
    void findOneTest(){
        // given
        when(commentRepository.findById(1L)).thenReturn(Optional.ofNullable(comment));
        when(commentFactory.getComment(comment)).thenReturn(new CommentDto.Response(comment));

        // when
        CommentDto.Response response = commentService.findOne(1L);

        // then
        assertThat(response.getUsername()).isEqualTo(comment.getUsername());
        assertThat(response.getContent()).isEqualTo(comment.getContent());

        verify(commentRepository, times(1)).findById(any());
        verify(commentFactory, times(1)).getComment(any());
    }
}