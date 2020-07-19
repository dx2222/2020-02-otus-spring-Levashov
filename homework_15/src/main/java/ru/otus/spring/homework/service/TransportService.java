package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.config.LocationSettings;
import ru.otus.spring.homework.domain.Carrying;

@Service
public class TransportService{

    private final LocationSettings location;

    @Autowired
    public TransportService(LocationSettings location) {
        this.location      = location;
    }

    public Carrying transporting(Carrying carrying) {

        if (carrying.getLocation().compareTo(location.getLOCATION_HOME()) == 0) {
            carrying.setLocation(location.getLOCATION_GROOMING_STUDIO());
        } else {
            carrying.setLocation(location.getLOCATION_HOME());
        }
        System.out.println("Переноска доставлена в : "+carrying.getLocation());
        return  carrying;
    }
}
