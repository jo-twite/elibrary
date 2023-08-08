package com.mediagenix.elibrary.mapper;


import com.mediagenix.elibrary.dto.BookDTO;
import com.mediagenix.elibrary.dto.CollectionDTO;
import com.mediagenix.elibrary.entities.Book;
import com.mediagenix.elibrary.entities.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface CollectionMapper {


    CollectionMapper INSTANCE = Mappers.getMapper(CollectionMapper.class);

    CollectionDTO map(Collection collection);

    Collection map(CollectionDTO collectionDTO);

    List<CollectionDTO> asDTOList(List<Collection> collections);

    BookDTO map(Book book);

    Book map(BookDTO bookDTO);

    Set<BookDTO> asBookDTOSet(Set<Book> bookSet);

    Set<Book> asBookSet(Set<BookDTO> bookDTOSet);
}
