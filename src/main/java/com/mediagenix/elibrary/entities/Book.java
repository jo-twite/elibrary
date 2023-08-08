package com.mediagenix.elibrary.entities;


import com.mediagenix.elibrary.dto.BookDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(unique=true)
    private String isbn;

    private String author;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book that)) return false;
        if (!(o instanceof BookDTO that2)) return false;
        return this.id == that.id || this.id == that2.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
