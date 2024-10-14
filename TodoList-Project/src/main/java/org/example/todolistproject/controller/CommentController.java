package org.example.todolistproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.dto.comment.reponse.CommentInfoResponseDto;
import org.example.todolistproject.dto.comment.request.CommentCreateRequestDto;
import org.example.todolistproject.dto.comment.request.CommentDeleteAuthenticationRequestDto;
import org.example.todolistproject.dto.comment.request.CommentUpdateAuthenticationRequestDto;
import org.example.todolistproject.security.JwtProvider;
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
            @RequestBody CommentCreateRequestDto dto,
            @CookieValue(JwtProvider.AUTHORIZATION_HEADER) String tokenValue)
    {
        Long commentId = commentService.add(dto, scheduleId, tokenValue);
        return new ResponseEntity<>(commentId, HttpStatus.CREATED);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentInfoResponseDto> findComment(@PathVariable Long commentId) {
        CommentInfoResponseDto dto = commentService.findOne(commentId);
        return ResponseEntity.ok(dto);
    }


    @PatchMapping
    public ResponseEntity<Void> updateComment(@RequestBody CommentUpdateAuthenticationRequestDto dto)  {
        commentService.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteComment(@RequestBody CommentDeleteAuthenticationRequestDto dto) {
        commentService.delete(dto);
        return ResponseEntity.noContent().build();
    }
}
