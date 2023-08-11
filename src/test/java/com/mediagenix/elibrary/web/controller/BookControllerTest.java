package com.mediagenix.elibrary.web.controller;

import com.mediagenix.elibrary.dto.BookDTO;
import com.mediagenix.elibrary.service.book.BookService;
import com.mediagenix.elibrary.web.exception.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setup() {
        when(bookService.create(any())).thenReturn(new BookDTO());
        when(bookService.getBookById(anyLong())).thenReturn(new BookDTO());
        when(bookService.update(any())).thenReturn(new BookDTO());
    }

    @Test
    public void testCreateBook() {
        BookDTO bookDTO = new BookDTO();
        ResponseEntity<Object> response = bookController.createBook(bookDTO);
        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    public void testCreateBookDuplicateKeyException() {
        BookDTO bookDTO = new BookDTO();
        when(bookService.create(any())).thenThrow(DuplicateKeyException.class);
        ResponseEntity<Object> response = bookController.createBook(bookDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetAll() {
        ResponseEntity<List<BookDTO>> response = bookController.getAll();
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull().isEmpty();
    }

    @Test
    public void testGetAllRuntimeException() {
        when(bookService.getAllBookDTOs()).thenThrow(RuntimeException.class);
        ResponseEntity<List<BookDTO>> response = bookController.getAll();
        assertThat(response.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testGetById() {
        ResponseEntity<Object> response = bookController.getById(1L);
        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    public void testGetByIdBookNotFoundException() {
        when(bookService.getBookById(anyLong())).thenThrow(BookNotFoundException.class);
        ResponseEntity<Object> response = bookController.getById(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        ResponseEntity<Object> response = bookController.updateBook(bookDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    public void testUpdateBookBookNotFoundException() {
        BookDTO bookDTO = new BookDTO();
        when(bookService.update(any())).thenThrow(BookNotFoundException.class);
        ResponseEntity<Object> response = bookController.updateBook(bookDTO);
        assertThat(response.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testUpdateBookDuplicateKeyException() {
        BookDTO bookDTO = new BookDTO();
        when(bookService.update(any())).thenThrow(DuplicateKeyException.class);
        ResponseEntity<Object> response = bookController.updateBook(bookDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testDeleteById() {
        ResponseEntity<Object> response = bookController.deleteById(1L);
        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    public void testDeleteByIdBookNotFoundException() {
        doThrow(BookNotFoundException.class).when(bookService).deleteById(anyLong());
        ResponseEntity<Object> response = bookController.deleteById(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
