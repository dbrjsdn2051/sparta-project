package org.example.todolistproject.factory;

import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.comment.CommentDto;
import org.example.todolistproject.entity.Comment;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.MissMatchPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentFactoryTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    CommentFactory commentFactory;

    CommentDto.Create createDto;
    CommentDto.Update updateDto;
    CommentDto.Delete deleteDto;

    Comment comment;
    Schedule schedule;

    @BeforeEach
    void init() {
        createDto = new CommentDto.Create("user1", "good", "1234");
        updateDto = new CommentDto.Update();
        updateDto.setCommentId(1L);
        updateDto.setPassword("1234");
        updateDto.setContent("LGTM");

        deleteDto = new CommentDto.Delete();
        deleteDto.setCommentId(1L);
        deleteDto.setPassword("1234");

        User user = User.builder()
                .username("testUser")
                .password("encodedPassword")
                .role(Role.USER)
                .email("user@naver.com")
                .build();


        schedule = Schedule.builder()
                .title("Main Title1")
                .content("TIL")
                .weather("rain")
                .password("encodedPassword")
                .user(user)
                .build();

        comment = new Comment("username", "encodedPassword", "content", schedule);

    }

    @Test
    void creatSuccessTest() {
        // given
        String password = createDto.getPassword();
        String encodedPassword = "securedPassword";
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        // when
        Comment createdComment = commentFactory.createComment(createDto, schedule);

        // then
        assertThat(createdComment.getContent()).isEqualTo(createDto.getContent());
        assertThat(createdComment.getUsername()).isEqualTo(createDto.getUsername());
        assertThat(createdComment.getPassword()).isEqualTo(encodedPassword);

        verify(passwordEncoder, times(1)).encode(any());
    }

    @Test
    void updateSuccessTest() {
        // given

        // when
        commentFactory.updateComment(updateDto, comment);

        // then
        assertThat(comment.getContent()).isEqualTo(updateDto.getContent());
        verify(passwordEncoder, times(1)).validPassword(any(), any());
    }

    @Test
    void updateFailTest(){
        // given

        // when
        doThrow(MissMatchPasswordException.class)
                .when(passwordEncoder)
                .validPassword(updateDto.getPassword(), comment.getPassword());

        // then
        assertThrows(MissMatchPasswordException.class, () ->
                commentFactory.updateComment(updateDto, comment));
    }

    @Test
    void deleteTest(){
        // given

        // when
        commentFactory.deleteComment(deleteDto, comment);
        // then
        verify(passwordEncoder, times(1)).validPassword(any(), any());

    }

    @Test
    void getCommentTest(){

        // when
        CommentDto.Response response = commentFactory.getComment(comment);

        // then
        assertThat(response.getContent()).isEqualTo(comment.getContent());
        assertThat(response.getUsername()).isEqualTo(comment.getUsername());
    }

}