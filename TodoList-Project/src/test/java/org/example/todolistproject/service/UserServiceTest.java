package org.example.todolistproject.service;

import org.example.todolistproject.dto.user.UserDto;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.factory.UserFactory;
import org.example.todolistproject.repository.UserRepository;
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
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserFactory userFactory;

    @InjectMocks
    UserService userService;

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

        user = User.builder()
                .username("testUser")
                .password("encodedPassword")
                .role(Role.USER)
                .email("user@naver.com")
                .build();
    }

    @Test
    void addUserTest() {
        // given
        when(userFactory.createUser(createDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        // when
        Long userId = userService.add(createDto);

        // then
        assertThat(userId).isEqualTo(user.getUserId());
        verify(userFactory, times(1)).createUser(any());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void findOneTest() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(userFactory.getUser(user)).thenReturn(new UserDto.Response(user));

        // when
        UserDto.Response findUser = userService.findOne(1L);

        // then
        assertThat(findUser).isNotNull();
        assertThat(findUser.getUserId()).isEqualTo(user.getUserId());
        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(findUser.getUsername()).isEqualTo(user.getUsername());

        verify(userRepository, times(1)).findById(any());
        verify(userFactory, times(1)).getUser(any());
    }

    @Test
    void updateUserTest() {

        // given
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        doNothing().when(userFactory).updateUser(updateDto, user);

        // when
        userService.update(updateDto);

        // then
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    void update_NotFoundUserFailTest() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NoResultDataException.class, () -> userService.update(updateDto));
    }

    @Test
    void update_MissMatchPasswordFailTest() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        doThrow(MissMatchPasswordException.class)
                .when(userFactory)
                .updateUser(updateDto, user);

        // when & then
        assertThrows(MissMatchPasswordException.class, () -> userService.update(updateDto));
    }

    @Test
    void deleteSuccessTest() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        doNothing().when(userFactory).deleteUser(deleteDto, user);
        doNothing().when(userRepository).deleteById(user.getUserId());

        // when
        userService.delete(deleteDto);

        // then
        verify(userRepository, times(1)).findById(any());
        verify(userFactory, times(1)).deleteUser(any(), any());
        verify(userRepository, times(1)).deleteById(any());
    }

    @Test
    void deleteFailNotFoundUserTest(){
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NoResultDataException.class, () -> userService.delete(deleteDto));
    }

    @Test
    void deleteFailMissMatchPasswordTest(){
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        doThrow(MissMatchPasswordException.class)
                .when(userFactory)
                .deleteUser(deleteDto, user);

        // when & then
        assertThrows(MissMatchPasswordException.class, () -> userService.delete(deleteDto));
    }


}