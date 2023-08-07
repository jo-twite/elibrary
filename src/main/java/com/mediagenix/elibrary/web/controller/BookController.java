package com.mediagenix.elibrary.web.controller;


import com.mediagenix.elibrary.dto.BookDTO;
import com.mediagenix.elibrary.service.book.BookService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
@AllArgsConstructor
@NoArgsConstructor
public class BookController {

    private BookService service;

    /**
     * CRUD Operations for Books:
     *
     * GET /api/books/{id}: Get details of a specific book by ID.
     * POST /api/books: Create a new book.
     *      - title, isbn and author not null -> NullPointerException
     *      - isbn doesn't exist and valid -> SQLException(UniqueConstraint)
     *
     * PUT /api/books/{id}: Update an existing book by ID.
     *      - ID exist in book table -> EntityNotFoundException
     *      - Data entry must exist
     * DELETE /api/books/{id}: Delete a book by ID.
     */


    /* POST /api/books: Create a new book.
     *      - title, isbn and author not null -> NullPointerException
     *      - isbn doesn't exist and valid -> SQLException(UniqueConstraint)
     */
    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
            return ResponseEntity.ok(service.create(bookDTO));
    }

    /* GET /api/books: Get a list of all books (alphabetically sorted by title). */
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAll() {
        return ResponseEntity.ok(service.getAllBookDTOs());
    }


    /* GET /api/books/{id}: Get details of a specific book by ID.*/
        @GetMapping("/{id}")
        public ResponseEntity<BookDTO> getById(Long id) {
        return null;
    }


    /* PUT /api/books/{id}: Update an existing book by ID.
     *      - ID exist in book table -> EntityNotFoundException
     *      - Data entry must exist */
    @PutMapping("{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, BookDTO bookDTO) {
        return ResponseEntity.ok(service.update(bookDTO));
    }

    /* DELETE /api/books/{id}: Delete a book by ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteById(Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }
}
