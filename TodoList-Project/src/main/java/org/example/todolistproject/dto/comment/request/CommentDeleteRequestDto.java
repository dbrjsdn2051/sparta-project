package org.example.todolistproject.dto.comment.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDeleteRequestDto {
    private Long commentId;
    private String password;

}
