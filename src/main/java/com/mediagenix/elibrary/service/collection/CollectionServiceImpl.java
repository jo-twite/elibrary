package com.mediagenix.elibrary.service.collection;

import com.mediagenix.elibrary.dto.BookDTO;
import com.mediagenix.elibrary.entities.Book;
import com.mediagenix.elibrary.entities.Collection;
import com.mediagenix.elibrary.mapper.BookMapper;
import com.mediagenix.elibrary.mapper.CollectionMapper;
import com.mediagenix.elibrary.repository.collection.CollectionRepository;
import com.mediagenix.elibrary.dto.CollectionDTO;
import com.mediagenix.elibrary.service.book.BookService;
import com.mediagenix.elibrary.web.exception.BookNotFoundException;
import com.mediagenix.elibrary.web.exception.CollectionNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * CONVENTIONS
 * Return only DTO's to controller
 * getters named with find return Entities, only used by services
 * getters named with get return DTOs, mostly used by controllers
 */

@Service
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionMapper mapper;
    private final BookMapper bookMapper;
    private final BookService bookService;

    public CollectionServiceImpl(CollectionRepository collectionRepository, BookService bookService) {
        this.collectionRepository = collectionRepository;
        mapper = CollectionMapper.INSTANCE;
        bookMapper = BookMapper.INSTANCE;
        this.bookService = bookService;
    }

    @Override
    public CollectionDTO createCollection(CollectionDTO collectionDTO) {
        Collection collection = mapper.map(collectionDTO);
        collection.setCreatedAt(LocalDateTime.now());
        collection.setUpdatedAt(collection.getCreatedAt());
        Collection createdCollection = collectionRepository.save(collection);
        return mapper.map(createdCollection);
    }

    @Override
    public List<CollectionDTO> getAllCollections() {
        List<Collection> collections = collectionRepository.findAll(Sort.by("name"));
        return mapper.asDTOList(collections);
    }


    @Override
    public Collection findById(Long id) {
        return collectionRepository.findById(id).orElse(null);
    }


    @Override
    public CollectionDTO getCollectionById(Long id) {
        Collection collection = findById(id);
        return mapper.map(collection);
    }



    @Transactional
    @Override
    public CollectionDTO updateCollection(Long id, CollectionDTO collectionDTO) {
        Collection collection = findById(id);
        collection.setUpdatedAt(LocalDateTime.now());
        collection.setName(collectionDTO.getName());
        setBooks(collection, collectionDTO.getBooks());

        return mapper.map(collection);
    }



    @Override
    public Void deleteById(Long id) {
        collectionRepository.deleteById(id);
        return null;
    }

    @Transactional
    @Override
    public CollectionDTO addBook(Long bookId, Long collectionId) {
        Object[] bookAndCollection = findBookAndCollection(bookId, collectionId);
        Book book = (Book) bookAndCollection[0];
        Collection collection = (Collection) bookAndCollection[1];
        Boolean added = null;
        if (collection.getBooks() == null) {
            collection.setBooks(Set.of(book));
            added = true;
        } if(!(added = collection.getBooks().add(book))) {
            throw new RuntimeException("Book not added");
        }
        collection.setCreatedAt(LocalDateTime.now());
        return mapper.map(collectionRepository.save(collection));
    }

    @Transactional
    @Override
    public CollectionDTO removeBook(Long bookId, Long collectionId) {
        Object[] bookAndCollection = findBookAndCollection(bookId, collectionId);
        Book book = (Book) bookAndCollection[0];
        Collection collection = (Collection) bookAndCollection[1];

        Set<Book> updatedBooks = new HashSet<>(collection.getBooks());
        updatedBooks.remove(book);

        collection.setBooks(updatedBooks);
        collection.setUpdatedAt(LocalDateTime.now());

        Collection updatedCollection = collectionRepository.save(collection);
        return mapper.map(updatedCollection);
    }


    /* @HELPERS */

    Object[] findBookAndCollection(Long bookId, Long collectionId) {
        Book book = bookService.findById(bookId);
        Collection collection = findById(collectionId);

        switch (anyNull(book, collection)) {
            case 3 -> {
                String msg = String.format("Both book %d and collection %d not found.\n", bookId, collectionId);
                throw new EntityNotFoundException(msg);
            }
            case 2 -> throw new CollectionNotFoundException(collectionId);
            case 1 -> throw new BookNotFoundException(bookId);
        }
        return new Object[] {book, collection};
    }

    int anyNull(Book book, Collection collection) {
        int error = 0;
        if (book == null) {
            error = 1;
        }
        if (collection == null) {
            error += 2;
        }
        return error;
    }

    private Set<Book> setBooks(Collection collection, Set<BookDTO> bookDTOS) {
        Set<Book> updatedBooks = bookDTOS.stream().map(
                (dto -> bookService.findById(dto.getId()))).collect(Collectors.toSet());
        if (updatedBooks.size() != bookDTOS.size()) {
            throw new EntityNotFoundException("Please provide only books that exist.");
        }
        return updatedBooks;
    }

}
