package com.mediagenix.elibrary.web.controller.book;


import com.mediagenix.elibrary.service.book.BookService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private BookService service;

    public BookController(BookService bookService) {
        this.service = service;
    }
}
