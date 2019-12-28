package example.controller;

import example.dto.AuthorDto;
import example.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;

@Api(value="Author Resource Endpoint")
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AuthorResource {

    private final AuthorService authorService;

    @Inject
    public AuthorResource(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ApiOperation(value = "Creates a new author")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(path = "/authors", consumes = {"application/json"})
    public ResponseEntity<Void> create(@Valid @RequestBody AuthorDto authorData) {

        log.info("POST /api/v1/authors :"+authorData);
        Long adId = authorService.create(authorData);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(adId).toUri();

        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Removes the requested author")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping(path = "/authors/{id}", produces = {"application/json"})
    public ResponseEntity<Void> delete(@PathVariable("id") Long authorId) {

        log.info("DELETE /api/v1/authors/"+authorId);
        authorService.delete(authorId);
        return ResponseEntity.noContent().build();
    }
}
