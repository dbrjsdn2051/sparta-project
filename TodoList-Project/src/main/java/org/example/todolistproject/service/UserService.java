package org.example.todolistproject.service;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.user.UserDto;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.CustomException;
import org.example.todolistproject.exception.ErrorCode;
import org.example.todolistproject.factory.UserFactory;
import org.example.todolistproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final PasswordEncoder passwordEncoder;

    public Long add(UserDto.Create userDto) {
        User user = userFactory.createUser(userDto);
        return userRepository.save(user).getUserId();
    }

    public UserDto.Response findOne(Long userId) {
        User findUser = get(userId);

        return userFactory.getUser(findUser);
    }

    @Transactional
    public void update(UserDto.Update userDto) {
        User user = get(userDto.getUserId());

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.MISS_MATCHER_PASSWORD);
        }

        user.change(userDto.getUsername(), userDto.getEmail());
    }

    public void delete(UserDto.Delete userDto) {
        User user = get(userDto.getUserId());

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.MISS_MATCHER_PASSWORD);
        }

        userRepository.deleteById(user.getUserId());
    }

    public User get(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

}
