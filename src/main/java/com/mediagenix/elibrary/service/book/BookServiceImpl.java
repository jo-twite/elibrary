package com.mediagenix.elibrary.service.book;

import com.mediagenix.elibrary.repository.book.BookRepository;

public class BookServiceImpl implements BookService{

    private BookRepository repository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.repository = bookRepository;
    }
}
