package com.mediagenix.elibrary.service.collection;

import com.mediagenix.elibrary.dto.BookDTO;
import com.mediagenix.elibrary.dto.CollectionDTO;
import com.mediagenix.elibrary.entities.Book;
import com.mediagenix.elibrary.entities.Collection;
import com.mediagenix.elibrary.mapper.BookMapper;
import com.mediagenix.elibrary.repository.collection.CollectionRepository;
import com.mediagenix.elibrary.service.book.BookService;
import com.mediagenix.elibrary.web.exception.BookNotFoundException;
import com.mediagenix.elibrary.web.exception.CollectionNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CollectionServiceImplTest {

    @Mock
    private CollectionRepository collectionRepository;

    @Mock
    private BookService bookService;


    private CollectionServiceImpl collectionService;
    BookMapper bookMapper = BookMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        collectionService = new CollectionServiceImpl(collectionRepository, bookService);
    }

    @Test
    void testCreateCollection() {
        CollectionDTO collectionDTO = new CollectionDTO();
        collectionDTO.setName("Test Collection");

        Collection collection = new Collection();
        collection.setName(collectionDTO.getName());

        when(collectionRepository.save(any(Collection.class))).thenReturn(collection);

        CollectionDTO savedCollectionDTO = collectionService.createCollection(collectionDTO);
        assertNotNull(savedCollectionDTO);
        assertEquals(collectionDTO.getName(), savedCollectionDTO.getName());
    }

    @Test
    void testGetAllCollections() {
        when(collectionRepository.findAll(Sort.by("name"))).thenReturn(Arrays.asList(new Collection(), new Collection()));
        List<CollectionDTO> collectionDTOs = collectionService.getAllCollections();
        assertEquals(2, collectionDTOs.size());
    }

    @Test
    void testGetCollectionById() {
        Long collectionId = 1L;
        Collection collection = new Collection();
        collection.setId(collectionId);
        when(collectionRepository.findById(collectionId)).thenReturn(Optional.of(collection));

        CollectionDTO collectionDTO = collectionService.getCollectionById(collectionId);
        assertNotNull(collectionDTO);
        assertEquals(collectionId, collectionDTO.getId());
    }

    @Test
    void testUpdateCollection() {
        Long collectionId = any(Long.class);
        CollectionDTO collectionDTO = new CollectionDTO();
        collectionDTO.setName("Updated Collection");

        Collection collection = new Collection();
        collection.setId(collectionId);

        when(collectionRepository.findById(collectionId)).thenReturn(Optional.of(collection));

        CollectionDTO updatedCollectionDTO = collectionService.updateCollection(collectionId, collectionDTO);
        assertNotNull(updatedCollectionDTO);
        assertEquals(collectionDTO.getName(), updatedCollectionDTO.getName());
    }

    @Test
    void testDeleteById() {
        Long collectionId = 1L;
        doNothing().when(collectionRepository).deleteById(collectionId);

        assertDoesNotThrow(() -> collectionService.deleteById(collectionId));
        verify(collectionRepository, times(1)).deleteById(collectionId);
    }

    @Test
    void testAddBookToEmptyCollection() {
        Long bookId = 1L;
        Long collectionId = 2L;

        Book book = new Book();
        book.setId(bookId);

        Collection collection = new Collection();
        collection.setId(collectionId);

        when(bookService.findById(bookId)).thenReturn(book);
        when(collectionRepository.findById(collectionId)).thenReturn(Optional.of(collection));

        CollectionDTO updatedCollectionDTO = collectionService.addBook(bookId, collectionId);
        assertTrue(updatedCollectionDTO.getBooks().contains(book));
        assertEquals(1, updatedCollectionDTO.getBooks().size());
    }

    @Test
    void testAddBookToExistingCollection() {
        Long bookId = 1L;
        Long collectionId = 2L;

        Book book = new Book();
        book.setId(bookId);

        Collection collection = new Collection();
        collection.setId(collectionId);
        collection.setBooks(Collections.singleton(book));

        when(bookService.findById(bookId)).thenReturn(book);
        when(collectionRepository.findById(collectionId)).thenReturn(Optional.of(collection));

        CollectionDTO updatedCollectionDTO = collectionService.addBook(bookId, collectionId);
        assertNotNull(updatedCollectionDTO);
        assertEquals(2, updatedCollectionDTO.getBooks().size());
    }

    @Test
    void testAddBookThrowsException() {
        Long bookId = 1L;
        Long collectionId = 2L;

        Book book = new Book();
        book.setId(bookId);

        Collection collection = new Collection();
        collection.setId(collectionId);
        collection.setBooks(Collections.singleton(book));

        when(bookService.findById(bookId)).thenReturn(book);
        when(collectionRepository.findById(collectionId)).thenReturn(Optional.of(collection));

        when(collectionRepository.findById(collectionId).get().getBooks()).thenReturn(Set.of(book));

        assertThrows(RuntimeException.class, () -> collectionService.addBook(bookId, collectionId));
    }

    @Test
    void testRemoveBook() {
        Long bookId = 1L;
        Long collectionId = 2L;

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookId);

        Book bookToRemove = new Book();
        bookToRemove.setId(bookId);

        Collection collection = new Collection();
        collection.setId(collectionId);
        collection.setBooks(Collections.singleton(bookToRemove));

        when(bookService.findById(bookId)).thenReturn(bookToRemove);
        when(collectionRepository.findById(collectionId)).thenReturn(Optional.of(collection));

        int initialSize = collection.getBooks().size();

        CollectionDTO updatedCollectionDTO = collectionService.removeBook(bookId, collectionId);
        assertNotNull(updatedCollectionDTO);

        int newSize = updatedCollectionDTO.getBooks().size();

        assertEquals(initialSize - 1, newSize);
    }

    @Test
    void testFindBookAndCollection() {
        Long bookId = 1L;
        Long collectionId = 2L;

        when(bookService.findById(bookId)).thenReturn(new Book());
        when(collectionRepository.findById(collectionId)).thenReturn(Optional.of(new Collection()));

        Object[] bookAndCollection = collectionService.findBookAndCollection(bookId, collectionId);
        assertNotNull(bookAndCollection);
        assertEquals(2, bookAndCollection.length);
    }

    @Test
    void testFindBookAndCollectionBookNotFound() {
        Long bookId = 1L;
        Long collectionId = 2L;

        when(bookService.findById(bookId)).thenReturn(null);
        when(collectionRepository.findById(collectionId)).thenReturn(Optional.of(new Collection()));

        assertThrows(BookNotFoundException.class, () -> collectionService.findBookAndCollection(bookId, collectionId));
    }

    @Test
    void testFindBookAndCollectionCollectionNotFound() {
        Long bookId = 1L;
        Long collectionId = 2L;

        when(bookService.findById(bookId)).thenReturn(new Book());
        when(collectionRepository.findById(collectionId)).thenReturn(Optional.empty());

        assertThrows(CollectionNotFoundException.class, () -> collectionService.findBookAndCollection(bookId, collectionId));
    }

    @Test
    void testFindBookAndCollectionBothNotFound() {
        Long bookId = 1L;
        Long collectionId = 2L;

        when(bookService.findById(bookId)).thenReturn(null);
        when(collectionRepository.findById(collectionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> collectionService.findBookAndCollection(bookId, collectionId));
    }

    @Test
    void testAnyNull() {
        int result1 = collectionService.anyNull(null, null);
        int result2 = collectionService.anyNull(new Book(), null);
        int result3 = collectionService.anyNull(null, new Collection());
        int result4 = collectionService.anyNull(new Book(), new Collection());

        assertEquals(3, result1);
        assertEquals(2, result2);
        assertEquals(1, result3);
        assertEquals(0, result4);
    }
}
