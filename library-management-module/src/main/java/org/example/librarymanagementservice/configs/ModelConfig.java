package org.example.librarymanagementservice.configs;

import org.example.librarymanagementservice.dto.BookDTO;
import org.example.librarymanagementservice.entities.Book;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

    @Bean
    public ModelMapper getModelMapper(){

        ModelMapper modelMapper = new ModelMapper();

        // Explicitly map Book.author -> BookDTO.authorDTO
        modelMapper.typeMap(Book.class, BookDTO.class).addMappings(mapper -> {
            mapper.map(Book::getAuthor, BookDTO::setAuthorDTO);
        });
        return modelMapper;
    }

}
