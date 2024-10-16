package org.example.todolistproject.factory;

import org.example.todolistproject.aop.CheckAuthority;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.user.UserDto;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.MissMatchPasswordException;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFactoryTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserFactory userFactory;


    UserDto.Create createDto;
    UserDto.Update updateDto;
    UserDto.Delete deleteDto;
    User user;


    @BeforeEach
    void init() {
        createDto = new UserDto.Create("CreateUser1", "user1@naver.com", "1234", Role.USER);
        updateDto = new UserDto.Update();
        updateDto.setUserId(1L);
        updateDto.setUsername("updateUser1");
        updateDto.setEmail("user1@naver.com");
        updateDto.setPassword("1234");

        deleteDto = new UserDto.Delete();
        deleteDto.setUserId(1L);
        deleteDto.setPassword("1234");

        user = User.builder().username("testUser").password("encodedPassword").role(Role.USER).email("user@naver.com").build();
    }

    @Test
    void createTest() {
        //given
        String password = createDto.getPassword();
        String encodedPassword = "Password1234";
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        //when
        User createdUser = userFactory.createUser(createDto);

        //then
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getUsername()).isEqualTo(createDto.getUsername());
        assertThat(createdUser.getPassword()).isEqualTo(encodedPassword);
        assertThat(createdUser.getEmail()).isEqualTo(createDto.getEmail());

        verify(passwordEncoder, times(1)).encode(password);
    }

    @Test
    void updateSuccessTest() {
        //given

        //when
        userFactory.updateUser(updateDto, user);

        //Then
        assertThat(user.getUsername()).isEqualTo(updateDto.getUsername());
        assertThat(user.getEmail()).isEqualTo(updateDto.getEmail());

        verify(passwordEncoder, times(1)).validPassword(any(), any());
    }

    @Test
    void updateFailTest() {
        // given
        doThrow(new MissMatchPasswordException())
                .when(passwordEncoder)
                .validPassword(updateDto.getPassword(), user.getPassword());

        assertThrows(MissMatchPasswordException.class, () -> userFactory.updateUser(updateDto, user));
    }

    @Test
    void deleteSuccessTest(){
        userFactory.deleteUser(deleteDto, user);

        verify(passwordEncoder).validPassword(deleteDto.getPassword(), user.getPassword());
    }

    @Test
    void deleteFailTest(){
        //given
        doThrow(MissMatchPasswordException.class)
                .when(passwordEncoder).validPassword(deleteDto.getPassword(), user.getPassword());
        // when & then
        assertThrows(MissMatchPasswordException.class, () -> userFactory.deleteUser(deleteDto, user));
    }
}