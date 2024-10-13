package org.example.todolistproject.service;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.aop.SecurePasswordService;
import org.example.todolistproject.aop.TableType;
import org.example.todolistproject.aop.ValidPassword;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.user.request.UserCreateRequestDto;
import org.example.todolistproject.dto.user.request.UserDeleteAuthenticationRequestDto;
import org.example.todolistproject.dto.user.response.UserInfoResponseDto;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @SecurePasswordService
    public Long add(UserCreateRequestDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return userRepository.save(user).getUserId();
    }

    public UserInfoResponseDto findOne(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(NoResultDataException::new);
        return modelMapper.map(findUser, UserInfoResponseDto.class);
    }

    @ValidPassword(value = TableType.USER)
    public void delete(UserDeleteAuthenticationRequestDto dto) {
        userRepository.deleteById(dto.getUserId());
    }




}
