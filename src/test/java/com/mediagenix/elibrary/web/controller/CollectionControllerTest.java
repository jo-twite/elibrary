package com.mediagenix.elibrary.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediagenix.elibrary.dto.CollectionDTO;
import com.mediagenix.elibrary.service.collection.CollectionService;
import com.mediagenix.elibrary.web.exception.BookNotFoundException;
import com.mediagenix.elibrary.web.exception.CollectionNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CollectionController.class)
public class CollectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollectionService collectionService;

    @Autowired
    private ObjectMapper objectMapper;

    private String addBookRequest = "/collections/%d/addBook/%d";
    private String removeBookRequest = "/collections/%d/removeBook/%d";
    private String bookNotfoundMsg = "Book with id=%d not found";
    private String collectionNotfoundMsg = "Collection with id=%d not found";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCollections() throws Exception {
        when(collectionService.getAllCollections()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/collections"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateCollectionValidInput() throws Exception {
        CollectionDTO inputDTO = new CollectionDTO();
        inputDTO.setName("Test Collection");

        when(collectionService.createCollection(any(CollectionDTO.class))).thenReturn(inputDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/collections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Collection"));
    }

    @Test
    public void testGetCollectionByIdCollectionNotFound() throws Exception {
        Long anyLong = any(Long.class);
        when(collectionService.getCollectionById(anyLong)).thenThrow(new CollectionNotFoundException(anyLong));

        mockMvc.perform(MockMvcRequestBuilders.get("/collections/" + anyLong))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddToCollection_Success() throws Exception {
        Long anyBookId = any(Long.class);
        Long anyCollectionId = any(Long.class);
        when(collectionService.addBook(anyCollectionId, anyBookId)).thenReturn(new CollectionDTO());

        mockMvc.perform(MockMvcRequestBuilders.post(String.format(addBookRequest, anyCollectionId, anyBookId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testAddToCollection_EntityNotFound() throws Exception {
        when(collectionService.addBook(any(Long.class), any(Long.class))).thenThrow(new EntityNotFoundException("Entity not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/collections/1/addBook/2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Entity not found"));
    }

    @Test
    public void testAddToCollectionThrowBookNotFound() throws Exception {
        Long anyBookId = any(Long.class);
        Long anyCollectionId = any(Long.class);
        when(collectionService.addBook(anyBookId, anyCollectionId)).thenThrow(new BookNotFoundException(anyBookId));


        mockMvc.perform(MockMvcRequestBuilders.post(String.format(addBookRequest, anyBookId, anyCollectionId)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Book with id=" + anyBookId + " not found"));
    }

    @Test
    public void testAddToCollectionThrowCollectionNotFound() throws Exception {
        Long anyLong = any(Long.class);
        when(collectionService.addBook(any(Long.class), anyLong)).thenThrow(new CollectionNotFoundException(anyLong));

        String testURI = String.format("/collections/%d/addBook/%d", anyLong, anyLong);
        mockMvc.perform(MockMvcRequestBuilders.post(testURI))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Collection with id=" + anyLong + " not found"));
    }

    @Test
    public void testRemoveFromCollectionSuccess() throws Exception {
        when(collectionService.removeBook(any(Long.class), any(Long.class))).thenReturn(new CollectionDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/collections/1/removeBook/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testRemoveFromCollectionEntityNotFound() throws Exception {
        when(collectionService.removeBook(any(Long.class), any(Long.class))).thenThrow(new EntityNotFoundException("Entity not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/collections/1/removeBook/2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Entity not found"));
    }

    @Test
    public void testRemoveFromCollectionBookNotFound() throws Exception {
        Long anyLong = any(Long.class);
        when(collectionService.removeBook(any(Long.class), anyLong)).thenThrow(new BookNotFoundException(anyLong));
        String testURI = String.format("/collections/1/removeBook/%d", anyLong);
        String msg = String.format("Book with id=%d not found", anyLong);

        mockMvc.perform(MockMvcRequestBuilders.post(testURI))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(msg));
    }

    @Test
    public void testRemoveFromCollectionCollectionNotFound() throws Exception {
        Long anyLong = any(Long.class);
        when(collectionService.removeBook(anyLong, any(Long.class))).thenThrow(new CollectionNotFoundException(anyLong));

        String testURI = String.format("/collections/%d/removeBook/2", anyLong);
        String msg = String.format("Collection with id=%d not found", anyLong);

        mockMvc.perform(MockMvcRequestBuilders.post(testURI))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(msg));
    }

    @Test
    public void testRemoveFromCollectionInternalServerError() throws Exception {
        when(collectionService.removeBook(any(Long.class), any(Long.class))).thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(MockMvcRequestBuilders.post("/collections/1/removeBook/2"))
                .andExpect(status().isInternalServerError());
    }

}
