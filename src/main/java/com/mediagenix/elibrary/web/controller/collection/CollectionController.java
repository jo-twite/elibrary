package com.mediagenix.elibrary.web.controller.collection;


import com.mediagenix.elibrary.model.dto.BookDTO;
import com.mediagenix.elibrary.model.dto.CollectionDTO;
import com.mediagenix.elibrary.service.collection.CollectionService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class CollectionController {

    CollectionService service;

    public ResponseEntity<CollectionDTO> create(CollectionDTO collectionDTO) {
        return null;
    }

    public ResponseEntity<CollectionDTO> getById(Long id) {
        return null;
    }

    public ResponseEntity<CollectionDTO> setById(CollectionDTO collectionDTO) {
        return null;
    }

    public ResponseEntity<CollectionDTO> deleteById(Long id) {
        return null;
    }
}
