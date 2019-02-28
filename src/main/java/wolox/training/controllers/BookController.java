package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController extends AbstractCrudController<Book, Long> {

    @Autowired
    private BookRepository bookRepository;

    protected BookRepository getRepository() {
        return bookRepository;
    }
}
