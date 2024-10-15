package org.example.todolistproject.controller;

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
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{scheduleId}")
    public ResponseEntity<Long> join(
            @PathVariable Long scheduleId,
            @RequestBody CommentDto.Create dto,
            @LoginUser User user)
    {
        Long commentId = commentService.add(dto, scheduleId, user);
        return new ResponseEntity<>(commentId, HttpStatus.CREATED);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto.Response> findComment(@PathVariable Long commentId) {
        CommentDto.Response findComment = commentService.findOne(commentId);
        return ResponseEntity.ok(findComment);
    }


    @PatchMapping
    public ResponseEntity<Void> updateComment(@RequestBody CommentDto.Update dto) {
        commentService.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteComment(@RequestBody CommentDto.Delete dto) {
        commentService.delete(dto);
        return ResponseEntity.noContent().build();
    }
}
