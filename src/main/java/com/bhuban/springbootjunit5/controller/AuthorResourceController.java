package com.bhuban.springbootjunit5.controller;

import com.bhuban.springbootjunit5.dto.AuthorDto;
import com.bhuban.springbootjunit5.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AuthorResourceController {

    private final AuthorService authorService;
@Autowired
    public AuthorResourceController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(summary = "Creates a new author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the author"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping(path = "/authors", consumes = {"application/json"})
    public ResponseEntity<Void> createAuthor(@Valid @RequestBody AuthorDto authorDto) {
        log.info(" POST /api/vi/authors : " + authorDto);
        long authorId = authorService.create(authorDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(authorId).toUri();
        return ResponseEntity.created(uri).build();

    }

    @Operation(summary = "Removes a requested author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping(path = "/authors/{id}", produces = {"application/json"})
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long authorId) {
        log.info("DELETE /api/v1/authors/" + authorId);
        authorService.delete(authorId);
        return ResponseEntity.noContent().build();

    }
}
