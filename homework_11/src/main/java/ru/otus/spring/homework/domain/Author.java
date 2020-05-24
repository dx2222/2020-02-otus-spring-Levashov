package ru.otus.spring.homework.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Author")
public class Author {

   @Id
   private String id;

   private String name;

   public static List<Author>  getAuthorList(String[] authors) {
        List<Author> authorList = new ArrayList<>();
        for (String curAuthor : authors) {
            authorList.add(new Author(null,curAuthor));
        }
        return  authorList;
    }
}
