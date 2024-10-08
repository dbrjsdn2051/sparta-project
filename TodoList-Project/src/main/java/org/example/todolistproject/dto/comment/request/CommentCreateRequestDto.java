package org.example.todolistproject.dto.comment.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentCreateRequestDto {

    private String username;
    private String content;
    private String password;
}
