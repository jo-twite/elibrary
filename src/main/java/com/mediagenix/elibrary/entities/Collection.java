package com.mediagenix.elibrary.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "collections")
    private Set<Book> books;

}
