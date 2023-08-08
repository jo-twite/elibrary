package com.mediagenix.elibrary.service.collection;

import com.mediagenix.elibrary.dto.CollectionDTO;
import com.mediagenix.elibrary.entities.Collection;

import java.util.List;

public interface CollectionService {
    List<CollectionDTO> getAllCollections();

    Collection findById(Long id);

    CollectionDTO getCollectionById(Long id);

    CollectionDTO updateCollection(Long id, CollectionDTO collectionDTO);

    CollectionDTO createCollection(CollectionDTO collectionDTO);

    Void deleteById(Long id);

    CollectionDTO addBook(Long bookId, Long CollectionId);

    CollectionDTO removeBook(Long bookId, Long collectionId);
}
