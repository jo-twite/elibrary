package com.mediagenix.elibrary.service.book;

import com.mediagenix.elibrary.repository.book.BookRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository repository;
}
