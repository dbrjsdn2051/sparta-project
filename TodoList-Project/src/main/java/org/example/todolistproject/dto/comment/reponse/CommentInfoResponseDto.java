package org.example.todolistproject.dto.comment.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentInfoResponseDto {

    private String username;
    private String content;
}
