package com.mediagenix.elibrary.service.collection;

import com.mediagenix.elibrary.repository.collection.CollectionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    CollectionRepository repository;
}
