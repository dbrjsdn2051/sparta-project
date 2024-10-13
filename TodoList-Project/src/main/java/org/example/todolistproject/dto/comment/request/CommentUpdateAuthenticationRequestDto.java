package org.example.todolistproject.dto.comment.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todolistproject.dto.RequestDto;

@Getter
@Setter
@NoArgsConstructor
public class CommentUpdateRequestDto implements RequestDto {

    private Long commentId;
    private String content;
    private String password;

    @Override
    public Long getRequestId() {
        return commentId;
    }

    @Override
    public String getRequestPassword() {
        return password;
    }
}