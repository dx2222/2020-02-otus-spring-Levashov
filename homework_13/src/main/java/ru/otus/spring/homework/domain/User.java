package ru.otus.spring.homework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLES")
    private String roles;

    public List<String>  getRolesList() {
        return  new ArrayList<String>(Arrays.asList(roles.split(",")));
    }

}
