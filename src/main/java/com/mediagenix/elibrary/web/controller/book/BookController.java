package com.mediagenix.elibrary.web.controller.book;


import com.mediagenix.elibrary.model.dto.BookDTO;
import com.mediagenix.elibrary.service.book.BookService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class BookController {

    private BookService service;

    public ResponseEntity<BookDTO> create(BookDTO bookDTO) {
        return null;
    }

    public ResponseEntity<BookDTO> getById(Long id) {
        return null;
    }

    public ResponseEntity<BookDTO> setById(BookDTO bookDTO) {
        return null;
    }

    public ResponseEntity<BookDTO> deleteById(Long id) {
        return null;
    }

}
