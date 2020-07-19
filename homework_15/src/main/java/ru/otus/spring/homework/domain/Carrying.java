package ru.otus.spring.homework.domain;

import lombok.*;

@Data
@Builder
public class Carrying {
    private String uuid;
    private Cat cat;
    private String location;
}
