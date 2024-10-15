package org.example.todolistproject.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todolistproject.entity.Comment;

public class CommentDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Create {
        private String username;
        private String content;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Update {
        private Long commentId;
        private String content;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Delete {
        private Long commentId;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {
        private String username;
        private String content;

        public Response(Comment comment) {
            this.username = comment.getUsername();
            this.content = comment.getContent();
        }
    }


}
