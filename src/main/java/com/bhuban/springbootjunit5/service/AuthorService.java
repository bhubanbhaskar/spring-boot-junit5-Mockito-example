package com.bhuban.springbootjunit5.service;

import com.bhuban.springbootjunit5.dto.AuthorDto;
import com.bhuban.springbootjunit5.exception.DuplicatedEntityException;
import com.bhuban.springbootjunit5.exception.EntityNotFoundException;
import com.bhuban.springbootjunit5.model.Author;
import com.bhuban.springbootjunit5.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Long create(AuthorDto authorDto) {

        if (authorDto.getId() != null) {
            authorRepository.findById(authorDto.getId()).ifPresent(author -> {
                throw new DuplicatedEntityException(" Entity Author with id " + author.getId() + "already exists");
            });

        }
        Author author = modelMapper.map(authorDto, Author.class);
        return authorRepository.save(author).getId();
    }

    @Transactional
    public AuthorDto delete(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(EntityNotFoundException::new);
        authorRepository.delete(author);
        return modelMapper.map(author, AuthorDto.class);
    }

}
