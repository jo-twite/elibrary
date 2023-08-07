package com.mediagenix.elibrary.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @NotNull(message = "Title cannot be null")
    private String title;

    @NotBlank(message = "Isbn cannot be blank")
    @NotNull(message = "Isbn cannot be null")
    private String isbn;

    @NotBlank(message = "Author cannot be blank")
    @NotNull(message = "Author cannot be null")
    private String author;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "BOOK_COLLECTION",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "COLLECTION_ID")
    )
    Set<Collection> collections;

}
