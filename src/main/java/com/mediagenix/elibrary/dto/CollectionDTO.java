package com.mediagenix.elibrary.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CollectionDTO {

    private Long id;
    private String name;
    private List<BookDTO> books;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}