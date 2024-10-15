package org.example.todolistproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.todolistproject.dto.user.UserDto;
import org.example.todolistproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> join(@RequestBody UserDto.Create dto) {
        Long userId = userService.add(dto);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto.Response> findUser(@PathVariable Long userId) {
        UserDto.Response user = userService.findOne(userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserDto.Update dto) {
        userService.update(dto);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody UserDto.Delete dto) {
        userService.delete(dto);
        return ResponseEntity.noContent().build();
    }


}
