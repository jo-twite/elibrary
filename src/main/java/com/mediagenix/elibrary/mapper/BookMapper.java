package com.mediagenix.elibrary.mapper;

import com.mediagenix.elibrary.dto.BookDTO;
import com.mediagenix.elibrary.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDTO map(Book book);

    Book map(BookDTO bookDTO);
    Set<BookDTO> asDTOSet(Set<BookDTO> books);

    Set<Book> asBookSet(Set<BookDTO> bookDTOs);
}
