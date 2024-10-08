package org.example.todolistproject.dto.page;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageResponseDto {

    private String title;
    private String content;
    private Long comment;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String username;

}
