package org.example.todolistproject.service;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.user.request.UserCreateRequestDto;
import org.example.todolistproject.dto.user.request.UserDeleteRequestDto;
import org.example.todolistproject.dto.user.response.UserInfoResponseDto;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.MissMatchPasswordException;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public Long add(UserCreateRequestDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);


        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
        return user.getUserId();
    }

    public UserInfoResponseDto findOne(Long userId) {
        User findUser = get(userId);
        return modelMapper.map(findUser, UserInfoResponseDto.class);
    }

    public void delete(UserDeleteRequestDto dto) {
        User user = get(dto.getUserId());
        validPassword(dto.getPassword(), user.getPassword());
        userRepository.deleteById(dto.getUserId());
    }

    private User get(Long userId) {
        return userRepository.findById(userId).orElseThrow(NoResultDataException::new);
    }

    private void validPassword(String password, String encodedPassword) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new MissMatchPasswordException();
        }
    }

}
