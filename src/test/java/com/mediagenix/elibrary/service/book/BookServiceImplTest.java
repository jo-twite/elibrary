package com.mediagenix.elibrary.service.book;

import com.mediagenix.elibrary.entities.Book;
import com.mediagenix.elibrary.dto.BookDTO;
import com.mediagenix.elibrary.mapper.BookMapper;
import com.mediagenix.elibrary.repository.book.BookRepository;
import com.mediagenix.elibrary.web.exception.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll(Sort.by("title"))).thenReturn(Arrays.asList(new Book(), new Book()));
        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
    }

    @Test
    void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        BookDTO bookDTO = bookService.getBookById(bookId);
        assertNotNull(bookDTO);
        assertEquals(bookId, bookDTO.getId());
    }

    @Test
    void testGetBookByIdNotFound() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(bookId));
    }

    @Test
    void testCreateBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Sample Title");
        bookDTO.setAuthor("Sample Author");

        when(bookRepository.save(any(Book.class))).thenReturn(new Book());

        BookDTO savedBookDTO = bookService.create(bookDTO);
        assertNotNull(savedBookDTO);
    }

    @Test
    void testCreateBookWithDuplicateISBN() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn("duplicateISBN");
        when(bookRepository.save(any(Book.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DuplicateKeyException.class, () -> bookService.create(bookDTO));
    }

    @Test
    void testDeleteById() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));

        assertDoesNotThrow(() -> bookService.deleteById(bookId));
        verify(bookRepository, times(1)).delete(any(Book.class));
    }

    @Test
    void testDeleteByIdNotFound() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteById(bookId));
    }

    @Test
    void testUpdateBook() {
        Long bookId = 1L;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookId);
        bookDTO.setTitle("Updated Title");
        bookDTO.setAuthor("Updated Author");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));

        BookDTO updatedBookDTO = bookService.update(bookDTO);
        assertNotNull(updatedBookDTO);
        assertEquals(bookDTO.getTitle(), updatedBookDTO.getTitle());
        assertEquals(bookDTO.getAuthor(), updatedBookDTO.getAuthor());
    }

    @Test
    void testUpdateBookNotFound() {
        Long bookId = 1L;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.update(bookDTO));
    }
}
