package com.mediagenix.elibrary.controller;

import com.mediagenix.elibrary.dto.BookDTO;
import com.mediagenix.elibrary.service.book.BookService;
import com.mediagenix.elibrary.web.controller.BookController;
import com.mediagenix.elibrary.web.exception.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@SpringJUnitConfig
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @BeforeEach
    public void setup() {
        // Mock behavior of bookService methods as needed
    }

    @Test
    public void testCreateBook() throws Exception {
        BookDTO bookDTO = new BookDTO();
        Mockito.when(bookService.create(Mockito.any(BookDTO.class))).thenReturn(bookDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Book\",\"isbn\":\"123456789\",\"author\":\"Test Author\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Mockito.when(bookService.getAllBookDTOs()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetBookById() throws Exception {
        Long bookId = 1L;
        Mockito.when(bookService.getBookById(bookId)).thenReturn(new BookDTO());
        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", bookId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetBookByIdNotFound() throws Exception {
        Long bookId = 201L;
        Mockito.when(bookService.getBookById(bookId)).thenThrow(new BookNotFoundException(bookId));
        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", bookId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
