package org.example.todolistproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.todolistproject.config.resolver.LoginUser;
import org.example.todolistproject.dto.comment.CommentDto;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{scheduleId}/comment")
    public ResponseEntity<Long> createComment(
            @PathVariable Long scheduleId,
            @RequestBody @Valid CommentDto.Create dto,
            @LoginUser User user) {
        Long commentId = commentService.add(dto, scheduleId, user);
        return new ResponseEntity<>(commentId, HttpStatus.CREATED);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentDto.Response> findComment(@PathVariable Long commentId) {
        CommentDto.Response findComment = commentService.findOne(commentId);
        return ResponseEntity.ok(findComment);
    }

    @PatchMapping("/comment")
    public ResponseEntity<Void> updateComment(@RequestBody @Valid CommentDto.Update dto) {
        commentService.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/comment")
    public ResponseEntity<Void> deleteComment(@RequestBody @Valid CommentDto.Delete dto) {
        commentService.delete(dto);
        return ResponseEntity.noContent().build();
    }
}
