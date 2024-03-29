package com.mediagenix.elibrary.repository.collection;

import com.mediagenix.elibrary.entities.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

}
