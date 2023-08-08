package com.mediagenix.elibrary.web.controller;


import com.mediagenix.elibrary.dto.BookDTO;
import com.mediagenix.elibrary.service.book.BookService;
import com.mediagenix.elibrary.web.exception.BookNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Object> createBook(@Valid @RequestBody BookDTO bookDTO) {
        try {
            return ResponseEntity.ok(bookService.create(bookDTO));
        } catch (DuplicateKeyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAll() {
        try {
            return ResponseEntity.ok(bookService.getAllBookDTOs());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookService.getBookById(id));
        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* PUT /api/books/{id}: Update an existing book by ID.
     *      - ID exist in book table -> EntityNotFoundException
     *      - Data entry must exist */
    @PutMapping()
    public ResponseEntity<Object> updateBook(@Valid @RequestBody BookDTO bookDTO) {
        if(bookDTO.getId() == null) {
            return ResponseEntity.badRequest().body("id should not be null");
        }
        try {
            ResponseEntity.ok((bookDTO = bookService.update(bookDTO)));
        } catch (BookNotFoundException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (DuplicateKeyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.accepted().body(bookDTO);
    }

    /* DELETE /api/books/{id}: Delete a book by ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        try {
            bookService.deleteById(id);
            String msg = String.format("Book %d deleted successfully", id);
            return ResponseEntity.ok(msg);
        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
