package com.mediagenix.elibrary.web.controller.book;


import com.mediagenix.elibrary.service.book.BookService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class BookController {

    private BookService service;
}
