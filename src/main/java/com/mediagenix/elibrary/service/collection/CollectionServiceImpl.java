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
import java.util.List;
import java.util.Set;


/**
 * CONVENTIONS
 * Return only DTO's to controller
 * getters named with find return Entities, only used by services
 * getters named with get return DTOs, mostly used by controllers
 */

@Service
public class CollectionServiceImpl implements CollectionService {

    private CollectionRepository collectionRepository;
    private CollectionMapper mapper;
    private BookMapper bookMapper;
    private BookService bookService;

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

        Set<BookDTO> bookDTOs = collectionDTO.getBooks();
        Set<Book> books = bookMapper.asBookSet(bookDTOs);
        collection.setBooks(books);

        return mapper.map(collection);
    }



    @Override
    public Void deleteById(Long id) {
        collectionRepository.deleteById(id);
        return Void.TYPE.cast(id);
    }

    @Transactional
    @Override
    public CollectionDTO addBook(Long bookId, Long collectionId) {
        Object[] bookAndCollection = findBookAndCollection(bookId, collectionId);
        Book book = (Book) bookAndCollection[0];
        Collection collection = (Collection) bookAndCollection[1];
        Boolean added = collection.getBooks().add(book);
        if(added) {
            collection.setCreatedAt(LocalDateTime.now());
            for(Book b: collection.getBooks()) {
                System.out.println(book.getId());
            }
        }
        else {
            System.out.println("Book not added");
        }
        return mapper.map(collectionRepository.save(collection));
    }

    @Transactional
    @Override
    public CollectionDTO removeBook(Long bookId, Long collectionId) {
        Object[] bookAndCollection = findBookAndCollection(bookId, collectionId);
        Book book = (Book) bookAndCollection[0];
        Collection collection = (Collection) bookAndCollection[1];
        Boolean removed = collection.getBooks().remove(book);
        collection.setCreatedAt(LocalDateTime.now());
        return mapper.map(collectionRepository.save(collection));
    }

    /* @HELPERS */

    private Object[] findBookAndCollection(Long bookId, Long collectionId) {
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

    /**
     * @HELPER
     * @return: Not null ->0
     * Book null -> 1
     * Collection null -> 2
     * both null -> 3
     */
    private int anyNull(Book book, Collection collection) {
        int error = 0;
        if (book == null) {
            error = 1;
        }
        if (collection == null) {
            error += 2;
        }
        return error;
    }

}
