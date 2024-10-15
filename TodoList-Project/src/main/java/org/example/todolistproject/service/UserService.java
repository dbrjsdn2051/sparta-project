package org.example.todolistproject.service;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.dto.user.UserDto;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.factory.UserFactory;
import org.example.todolistproject.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public Long add(UserDto.Create userDto) {
        User user = userFactory.createUser(userDto);
        return userRepository.save(user).getUserId();
    }

    public UserDto.Response findOne(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(NoResultDataException::new);
        return userFactory.getUser(findUser);
    }

    public void update(UserDto.Update userDto) {
        User user = userRepository.findById(userDto.getUserId()).orElseThrow(NoResultDataException::new);
        userFactory.updateUser(userDto, user);
    }

    public void delete(UserDto.Delete userDto) {
        User user = userRepository.findById(userDto.getUserId()).orElseThrow(NoResultDataException::new);
        userFactory.deleteUser(userDto, user);
        userRepository.deleteById(user.getUserId());
    }


}
