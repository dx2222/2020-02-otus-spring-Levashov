package ru.otus.spring.homework.restcontroller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.homework.dto.BookDto;
import ru.otus.spring.homework.dto.CommentDto;
import ru.otus.spring.homework.repository.BookRepository;
import ru.otus.spring.homework.repository.CommentRepository;
import java.util.stream.Collectors;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RestControllerConfig {

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(BookRepository bookRepository, CommentRepository commentRepository) {
        return route()
                .GET("/book", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(bookRepository.findAll()
                                .map(BookDto::toDto).collect(Collectors.toList()), BookDto.class)
                )
                .GET("/book/selectby", queryParam("cbFindType","name"),
                        request -> ok().contentType(APPLICATION_JSON).body(bookRepository.findByNameLike(request.queryParam("FindText").orElse(""))
                                .map(BookDto::toDto).collect(Collectors.toList()), BookDto.class)
                )
                .GET("/book/selectby", queryParam("cbFindType","author"),
                        request -> ok().contentType(APPLICATION_JSON).body(bookRepository.findByAuthorNameLike(request.queryParam("FindText").orElse(""))
                                .map(BookDto::toDto).collect(Collectors.toList()), BookDto.class)
                )
                .GET("/book/selectby", queryParam("cbFindType","genre"),
                        request -> ok().contentType(APPLICATION_JSON).body(bookRepository.findByGenreNameLike(request.queryParam("FindText").orElse(""))
                                .map(BookDto::toDto).collect(Collectors.toList()), BookDto.class)
                )
                .GET("/book/selectby", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(bookRepository.findAll()
                                .map(BookDto::toDto).collect(Collectors.toList()), BookDto.class)
                )

                .GET("/book/{id}", accept(APPLICATION_JSON),
                        request -> bookRepository.findById(request.pathVariable("id"))
                                .map(BookDto::toDto)
                                .flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromObject(book)))
                )
                .POST("/book", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(BookDto.class)
                                .map(BookDto::toDomain)
                                .flatMap(book -> ok().body(bookRepository.save(book).map(BookDto::toDto), BookDto.class))
                )
                .PUT("/book", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(BookDto.class)
                                .map(BookDto::toDomain)
                                .flatMap(book -> ok().body(bookRepository.save(book).map(BookDto::toDto), BookDto.class))
                )
                .DELETE("/book/{id}", accept(APPLICATION_JSON),
                        request -> bookRepository.deleteById(request.pathVariable("id"))
                                .flatMap(book -> ok().contentType(APPLICATION_JSON).<Void>build())
                )


                .GET("/comment/{bookID}", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(commentRepository.findByBookId(request.pathVariable("bookID"))
                                .map(CommentDto::toDto).collect(Collectors.toList()), CommentDto.class)
                )
                .POST("/comment", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(CommentDto.class)
                                .map(CommentDto::toDomain)
                                .flatMap(comment -> ok().body(commentRepository.save(comment).map(CommentDto::toDto), CommentDto.class))
                )
                .DELETE("/comment/{id}", accept(APPLICATION_JSON),
                        request -> commentRepository.deleteById(request.pathVariable("id"))
                                .flatMap(comment -> ok().contentType(APPLICATION_JSON).<Void>build())
                )

                .build();
    }
}
