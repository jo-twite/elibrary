package com.mediagenix.elibrary.mapper;


import com.mediagenix.elibrary.dto.CollectionDTO;
import com.mediagenix.elibrary.entities.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CollectionMapper {


    CollectionMapper INSTANCE = Mappers.getMapper(CollectionMapper.class);

    CollectionDTO map(Collection collection);

    Collection map(CollectionDTO collectionDTO);

    public List<CollectionDTO> map(List<Collection> collections);
}
