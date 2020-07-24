package ru.otus.spring.homework.domain;

import lombok.*;

@Data
@Builder
public class Cat {
    private String uuid;
    private String name;
    private Double coatLength;
    private String location;
}
