package com.mediagenix.elibrary.service.book;

import com.mediagenix.elibrary.entities.Book;
import com.mediagenix.elibrary.mapper.BookMapper;
import com.mediagenix.elibrary.repository.book.BookRepository;
import com.mediagenix.elibrary.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    @Override
    public List<Book> getAllBooks() {
        return repository.findAll(Sort.by("title"));
    }

    @Override
    public BookDTO create(BookDTO bookDTO) {
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        return null;
    }

    @Override
    public List<BookDTO> getAllBookDTOs() {
        return null;
    }

    @Override
    public BookDTO update(BookDTO bookDTO) {
        return null;
    }

}
