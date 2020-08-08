package ru.otus.spring.homework.healthIndicator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.repository.BookRepositoryJpa;

@Component
public class ExistsBookHI implements HealthIndicator {

    private final BookRepositoryJpa bookRepository;

    @Autowired
    public ExistsBookHI(BookRepositoryJpa bookRepository) {
        this.bookRepository   = bookRepository;
    }

    @Override
    public Health health() {
        long cnt = bookRepository.count();
        if (cnt == 0) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Кочисество книг: 0 !!!")
                    .build();
        } else {
            return Health
                    .up()
                    .withDetail("message", "Кочисество книг: "+ Long.valueOf(cnt).toString())
                    .build();
        }
    }
}
