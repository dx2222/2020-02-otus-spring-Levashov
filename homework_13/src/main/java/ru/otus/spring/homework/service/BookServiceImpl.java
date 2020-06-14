package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.dto.Role;
import ru.otus.spring.homework.repository.*;
import ru.otus.spring.homework.domain.*;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepositoryJpa bookRepository;

    private final MutableAclService mutableAclService;

    @Autowired
    public BookServiceImpl(BookRepositoryJpa bookRepository, MutableAclService mutableAclService) {
        this.bookRepository      = bookRepository;
        this.mutableAclService   = mutableAclService;
    }

    @PreAuthorize("not hasRole("+(char) 39+ Role.ROLE_ADMIN+(char) 39+ ")")
    public Book insertBook(String bookName, String authortxt, String genretxt) {
        int cntattr = 0;
        Book book = new Book();
        Book newBook = null;

        if (!bookName.trim().equals("")) {
            book.setName(bookName);
            cntattr++;
        }
        if (!authortxt.trim().equals("")) {
            List<Author> authorsList = Author.getAuthorList(authortxt.split(","));
            book.setAuthor(authorsList);
            cntattr++;
        }
        if (!genretxt.trim().equals("")) {
            List<Genre> genresList = Genre.getGenreList(genretxt.split(","));
            book.setGenre(genresList);
            cntattr++;
        }

        if (cntattr == 3) {
           newBook = bookRepository.save(book);

            final Sid owner = new PrincipalSid( SecurityContextHolder.getContext().getAuthentication());

            MutableAcl acl = mutableAclService.createAcl(new ObjectIdentityImpl(newBook.getClass(), newBook.getId()));
            acl.setOwner(owner);
            acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, owner, true);
            mutableAclService.updateAcl(acl);

        }
        return newBook;
    }

    @PreAuthorize("hasRole("+(char) 39+ Role.ROLE_ADMIN+(char) 39+ ") or hasPermission(#id, 'ru.otus.spring.homework.domain.Book', 'WRITE')")
    public void updateBook(Long id, String bookName, String authortxt, String genretxt) {
        boolean updateed = false;
        Book book = bookRepository.findById(id).orElse(null);

        if (book != null) {
            if (!bookName.trim().equals("")) {
                book.setName(bookName);
                updateed = true;
            }
            if (!authortxt.trim().equals("")) {
                List<Author> authorsList = Author.getAuthorList(authortxt.split(","));
                book.setAuthor(authorsList);
                updateed = true;
            }

            if (!genretxt.trim().equals("")) {
                List<Genre> genresList = Genre.getGenreList(genretxt.split(","));
                book.setGenre(genresList);
                updateed = true;
            }

            if (updateed) {
              bookRepository.save(book);
            }
        }
    }


    public void deleteBook(Long id) {
        bookRepository.findById(id).ifPresent(book -> bookRepository.deleteById(book.getId()));
    }

    public Book selectBookByID(Long id){
      return  bookRepository.findById(id).orElse(null);
    }

    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }


    public List<Book> findAllBookByName(String name) {
        return bookRepository.findByNameLike('%'+name+"%");
    }

    public List<Book> findAllBookByAuthor(String name) {
        return bookRepository.findAllByAuthor('%'+name+"%");
    }

    public List<Book> findAllBookByGenre(String name) {
        return bookRepository.findAllByGenre("%"+name+"%");
    }


}
