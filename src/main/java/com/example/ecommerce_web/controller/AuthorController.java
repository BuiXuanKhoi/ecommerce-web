package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.mapper.AuthorMapper;
import com.example.ecommerce_web.model.dto.respond.AuthorRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    AuthorService authorService;
    AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper){
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping
    public AuthorRespondDTO addAuthor(@RequestBody Author author){
        Author savedAuthor = this.authorService.add(author);
        return authorMapper.toDTO(savedAuthor);
    }

    @GetMapping
    public List<AuthorRespondDTO> getListAuthor(){
        return  this.authorService.getListAuthor()
                                  .stream()
                                  .map(authorMapper::toDTO)
                                  .collect(Collectors.toList());
    }
}