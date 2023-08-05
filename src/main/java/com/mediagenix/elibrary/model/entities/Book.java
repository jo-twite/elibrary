package com.mediagenix.elibrary.model.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(unique = true)
    private String isbn;

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
