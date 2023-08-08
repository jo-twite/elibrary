package com.mediagenix.elibrary.web.controller;

import com.mediagenix.elibrary.dto.CollectionDTO;
import com.mediagenix.elibrary.service.collection.CollectionService;
import com.mediagenix.elibrary.web.exception.BookNotFoundException;
import com.mediagenix.elibrary.web.exception.CollectionNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;


import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/collections")
public class CollectionController {

    private CollectionService collectionService;

    public CollectionController(CollectionService service) {
        this.collectionService = service;
    }

    @GetMapping
    public ResponseEntity<List<CollectionDTO>> getAllCollections() {
        return ok(collectionService.getAllCollections());
    }

    @PostMapping
    public ResponseEntity<Object> createCollection(@Valid @RequestBody CollectionDTO collectionDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok(collectionService.createCollection(collectionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCollectionById(@PathVariable Long id) {
        try {
            CollectionDTO collectionDTO = collectionService.getCollectionById(id);
            return ResponseEntity.ok(collectionDTO);
        } catch (CollectionNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    /**
     * TODO PUT /api/collections/{id}: Update an existing collection by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCollection(@PathVariable Long id, @Valid @RequestBody CollectionDTO collectionDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        CollectionDTO updatedCollectionDTO = collectionService.updateCollection(id, collectionDTO);
        if (updatedCollectionDTO == null) {
            return ResponseEntity.notFound().build(); // Collection with the provided id was not found
        }
        return ResponseEntity.ok(updatedCollectionDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCollectionById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("Invalid collection ID. Please provide a valid positive number.");
        }
        try {
            collectionService.deleteById(id);
            String successMessage = String.format("Collection %d deleted successfully", id);
            return ResponseEntity.ok().body(successMessage);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    /** TODO
     * * if book exists in Collection
     */
    @PostMapping("/{collectionId}/addBook/{bookId}")
    public ResponseEntity<?> addToCollection(@PathVariable Long bookId, @PathVariable Long collectionId) {
        try {
            return ResponseEntity.ok().body(collectionService.addBook(bookId, collectionId));
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (BookNotFoundException b) {
            return ResponseEntity.badRequest().body(b.getMessage());
        } catch (CollectionNotFoundException c) {
            return ResponseEntity.badRequest().body(c.getMessage());
        }
    }

    @PostMapping("/{collectionId}/removeBook/{bookId}")
    public ResponseEntity<?> removeFromCollection(@PathVariable Long bookId, @PathVariable Long collectionId) {
        try {
            CollectionDTO collectionDTO = collectionService.removeBook(bookId, collectionId);
            return ResponseEntity.ok(collectionDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (BookNotFoundException b) {
            return ResponseEntity.badRequest().body(b.getMessage());
        } catch (CollectionNotFoundException c) {
            return ResponseEntity.badRequest().body(c.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
