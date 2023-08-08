package com.mediagenix.elibrary.dto;

import com.mediagenix.elibrary.entities.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Data
public class BookDTO {

    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @NotNull(message = "Title cannot be null")
    private String title;

    @NotBlank(message = "ISBN cannot be blank")
    @NotNull(message = "ISBN cannot be null")
    private String isbn;

    @NotBlank(message = "Author cannot be blank")
    @NotNull(message = "Author cannot be null")
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book that)) return false;
        if (!(o instanceof BookDTO that2)) return false;
        return this.id == that.getId() && this.isbn.equals(that.getIsbn())
                || this.id == that2.id && this.isbn.equals(that2.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
