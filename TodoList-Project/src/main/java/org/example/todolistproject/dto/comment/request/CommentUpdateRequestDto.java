package org.example.todolistproject.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentUpdateRequestDto {

    private Long commentId;
    private String updateContent;
    private String password;

}
