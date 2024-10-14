package org.example.todolistproject.dto.comment.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todolistproject.dto.AuthenticationRequestDto;

@Getter
@Setter
@NoArgsConstructor
public class CommentDeleteAuthenticationRequestDto implements AuthenticationRequestDto {
    private Long commentId;
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
