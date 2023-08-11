package com.mediagenix.elibrary.service.book;

import com.mediagenix.elibrary.entities.Book;
import com.mediagenix.elibrary.mapper.BookMapper;
import com.mediagenix.elibrary.repository.book.BookRepository;
import com.mediagenix.elibrary.dto.BookDTO;
import com.mediagenix.elibrary.web.exception.BookNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * CONVENTIONS
 * Return only DTO's to controller
 * getters named with find return Entities, only used by services
 * getters named with get return DTOs, mostly used by controllers
 */

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository repository) {
     this.bookRepository = repository;
     this.bookMapper = BookMapper.INSTANCE;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll(Sort.by("title"));
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = findById(id);
        if(book == null) {
            throw new BookNotFoundException(id);
        }
        return bookMapper.map(book);
    }

    @Override
    public BookDTO create(BookDTO bookDTO) {
        bookDTO.setCreatedAt(LocalDateTime.now());
        bookDTO.setUpdatedAt(bookDTO.getCreatedAt());
        Book book = bookMapper.map(bookDTO);
        try {
            Book savedBook = bookRepository.save(book);
            return bookMapper.map(savedBook);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException("ISBN already exists. Please provide a unique ISBN.");
        }
    }

    @Override
    public Void deleteById(Long id) {
        Book book = findById(id);
        if(book == null) {
            throw new BookNotFoundException(id);
        }
        bookRepository.delete(book);
        return null;
    }

    @Override
    public List<BookDTO> getAllBookDTOs() {
        List<Book> books = getAllBooks();
        return books.stream()
                .map(bookMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDTO update(BookDTO bookDTO) {
        Book bookToUpdate = bookRepository.findById(bookDTO.getId())
                .orElseThrow(() -> new BookNotFoundException(bookDTO.getId()));

        bookToUpdate.setTitle(bookDTO.getTitle());
        bookToUpdate.setAuthor(bookDTO.getAuthor());
        bookToUpdate.setUpdatedAt(LocalDateTime.now());
        return bookMapper.map(bookToUpdate);
    }
}