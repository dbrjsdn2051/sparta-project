package org.example.todolistproject.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.MonthDay;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

    private String date;
    private String weather;
}
