package com.mediagenix.elibrary.dto;

import com.mediagenix.elibrary.entities.Book;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class BookDTO {

    private Long id;
    private String title;
    private String isbn;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if(o == null || ! (o instanceof BookDTO) && ! (o instanceof Book)) {
            return false;
        }
        return ((BookDTO) o).getId() == this.id;
    }
}
