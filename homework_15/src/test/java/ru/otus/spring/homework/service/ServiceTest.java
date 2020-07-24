package ru.otus.spring.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.*;
import ru.otus.spring.homework.CatHaircutApplication;
import ru.otus.spring.homework.domain.*;
import java.util.UUID;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис для работы")
@SpringBootTest
@ContextConfiguration(classes = CatHaircutApplication.class)
@ExtendWith(SpringExtension.class)
class ServiceTest {


    private static final String DEAULT_CAT_NAME = "1111";
    private static final String DEFAULT_LOCATION = "Дом";

    private static final Double DEFAULT_COATLENGTH = 10.0;
    private static final Double DEFAULT_COATLENGTH2 = 5.0;
    private static final String DEFAULT_UUID    = UUID.randomUUID().toString();


    @Autowired
    private CarryingService carryingService;

    @Autowired
    private GroomingService groomingService;

    @Autowired
    private TransportService transportService;


    @Test
    @DisplayName("стрижка кота")
    void insert()  {

        Cat cat = Cat.builder().name(DEAULT_CAT_NAME).location(DEFAULT_LOCATION).coatLength(DEFAULT_COATLENGTH).uuid(DEFAULT_UUID).build();

        Carrying carrying = carryingService.toCarrying(cat);

        assertEquals(cat.getUuid(), carrying.getCat().getUuid());

        Carrying carrying2 = transportService.transporting(carrying);

        assertEquals(carrying.getCat().getUuid(), carrying2.getCat().getUuid());
        assertNotEquals(carrying.getLocation(), cat.getLocation());

        Cat cat2 = carryingService.getCat(carrying2);

        assertEquals(cat.getUuid(), cat2.getUuid());

        Cat cat3 = groomingService.grooming(cat2);

        assertEquals(cat3.getCoatLength(), DEFAULT_COATLENGTH2);
    }

}