package com.mediagenix.elibrary.dto;


import com.mediagenix.elibrary.entities.Collection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CollectionDTO {

    private Long id;
    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name cannot be null")
    private String name;
    private Set<BookDTO> books;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Collection that)) return false;
        if (!(o instanceof CollectionDTO that2)) return false;
        return this.id == that.getId() || this.id == that2.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}