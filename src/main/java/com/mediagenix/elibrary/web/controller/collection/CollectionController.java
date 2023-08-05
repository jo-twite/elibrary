package com.mediagenix.elibrary.web.controller.collection;


import com.mediagenix.elibrary.service.collection.CollectionService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class CollectionController {

    CollectionService service;
}
