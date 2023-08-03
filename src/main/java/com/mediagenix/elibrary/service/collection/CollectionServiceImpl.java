package com.mediagenix.elibrary.service.collection;

import com.mediagenix.elibrary.repository.collection.CollectionRepository;

public class CollectionServiceImpl implements CollectionService {

    CollectionRepository repository;

    public CollectionServiceImpl(CollectionRepository collectionRepository) {
        this.repository = collectionRepository;
    }
}
