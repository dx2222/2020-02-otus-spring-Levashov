package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.config.GroomerSettings;
import ru.otus.spring.homework.domain.*;

@Service
public class GroomingService {

    private final GroomerSettings groomer;

    @Autowired
    public GroomingService(GroomerSettings groomer) {
        this.groomer      = groomer;
    }

    public Cat grooming(Cat cat) {

        if (groomer.getLocation().compareTo(cat.getLocation()) == 0 ) {
            cat.setCoatLength(cat.getCoatLength() - groomer.getMaxDecCoatLength());

            if (cat.getCoatLength() < groomer.getMinCoatLength()) {
                cat.setCoatLength(groomer.getMinCoatLength());
            }
            System.out.println("Кот " + cat.getName() + " подстрижен");
        } else {
            System.out.println("Кот " + cat.getName() + " не подстрижен");
        }
        return  cat;
    }
}
