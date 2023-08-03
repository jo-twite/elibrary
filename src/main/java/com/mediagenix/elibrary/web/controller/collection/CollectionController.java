package com.mediagenix.elibrary.web.controller.collection;


import com.mediagenix.elibrary.service.collection.CollectionService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CollectionController {

    CollectionService service;

    public CollectionController(CollectionService collectionService) {
        this.service = collectionService;
    }
}
