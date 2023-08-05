package com.mediagenix.elibrary.repository.book;

import com.mediagenix.elibrary.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
