package com.mediagenix.elibrary.service.collection;

import com.mediagenix.elibrary.repository.collection.CollectionRepository;
import com.mediagenix.elibrary.dto.CollectionDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    CollectionRepository repository;


    @Override
    public List<CollectionDTO> getAll() {
        return null;
    }

    @Override
    public CollectionDTO getById(Long id) {
        return null;
    }


}
