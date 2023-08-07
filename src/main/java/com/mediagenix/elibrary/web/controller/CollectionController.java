package com.mediagenix.elibrary.web.controller;


import com.mediagenix.elibrary.dto.CollectionDTO;
import com.mediagenix.elibrary.service.collection.CollectionService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/collections")
@AllArgsConstructor
@NoArgsConstructor
public class CollectionController {

    CollectionService collectionService;

    /** GET /api/collections: Get a list of all collections (alphabetically sorted by name). */
    @GetMapping
    public ResponseEntity<List<CollectionDTO>> getAllCollections() {
        return ResponseEntity.ok(collectionService.getAll());
    }


    /** GET /api/collections/{id}: Get details of a specific collection by ID */
    @GetMapping("/{id}")
    public ResponseEntity<CollectionDTO> getCollection(Long id) {

        return ResponseEntity.ok(collectionService.getById(id));
    }


    /** POST /api/collections: Create a new collection */
    @PostMapping
    public ResponseEntity<CollectionDTO> createCollection(CollectionDTO collectionDTO) {
        return null;
    }


    /** PUT /api/collections/{id}: Update an existing collection by ID
     */
    @PutMapping("{id}")
    public ResponseEntity<CollectionDTO> updateCollection(@PathVariable Long id, CollectionDTO collectionDTO) {
        return null;
    }


    /** DELETE /api/collections/{id}: Delete a collection by ID
     */
    @DeleteMapping("{id}")
    public ResponseEntity<CollectionDTO> deleteCollectionById(Long id) {
        return null;
    }
}
