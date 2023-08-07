package com.mediagenix.elibrary.service.collection;

import com.mediagenix.elibrary.dto.CollectionDTO;

import java.util.List;

public interface CollectionService {
    List<CollectionDTO> getAll();

    CollectionDTO getById(Long id);
}
