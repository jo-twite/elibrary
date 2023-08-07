package com.mediagenix.elibrary.service.book;


import com.mediagenix.elibrary.entities.Book;
import com.mediagenix.elibrary.dto.BookDTO;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    BookDTO create(BookDTO bookDTO);

    Long deleteById(Long id);

    List<BookDTO> getAllBookDTOs();

    BookDTO update(BookDTO bookDTO);
}
