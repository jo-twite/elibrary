package com.mediagenix.elibrary.entities;


import com.mediagenix.elibrary.dto.CollectionDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "BOOK_COLLECTION",
            joinColumns = @JoinColumn(name = "COLLECTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID")
    )
    private Set<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Collection that)) return false;
        if (!(o instanceof CollectionDTO that2)) return false;
        return this.id == that.id || this.id == that2.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
