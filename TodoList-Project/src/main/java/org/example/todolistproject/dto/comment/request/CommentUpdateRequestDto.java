package org.example.todolistproject.dto.comment.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentUpdateRequestDto {

    private Long commentId;
    private String content;
    private String password;
}
