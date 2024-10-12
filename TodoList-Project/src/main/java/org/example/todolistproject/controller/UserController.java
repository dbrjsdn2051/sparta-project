package org.example.todolistproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.dto.user.request.UserCreateRequestDto;
import org.example.todolistproject.dto.user.request.UserDeleteRequestDto;
import org.example.todolistproject.dto.user.response.UserInfoResponseDto;
import org.example.todolistproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<Long> join(@RequestBody UserCreateRequestDto dto) {
        Long userId = userService.add(dto);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponseDto> findUser(@PathVariable Long userId) {
        UserInfoResponseDto dto = userService.findOne(userId);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(@RequestBody UserDeleteRequestDto dto) {
        userService.delete(dto);
        return ResponseEntity.noContent().build();
    }
}
