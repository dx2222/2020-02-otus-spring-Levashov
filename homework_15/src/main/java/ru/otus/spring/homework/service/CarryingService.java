package ru.otus.spring.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework.domain.Carrying;
import ru.otus.spring.homework.domain.Cat;

import java.util.UUID;

@Service
public class CarryingService {

    public Carrying toCarrying(Cat cat) {
        Carrying carrying = Carrying.builder().cat(cat).location(cat.getLocation()).uuid(UUID.randomUUID().toString()).build();
        System.out.println("Кот " + cat.getName() + " помещен в переноску "+carrying.getUuid());
        return  carrying;
    }

    public Cat getCat(Carrying carrying) {
        carrying.getCat().setLocation(carrying.getLocation());
        System.out.println("Кот " + carrying.getCat().getName() + " извлечен из переноски "+carrying.getUuid());
        return carrying.getCat()  ;
    }
}
