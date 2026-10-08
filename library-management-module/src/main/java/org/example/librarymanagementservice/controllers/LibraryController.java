package org.example.librarymanagementservice.controllers;

import org.example.librarymanagementservice.dto.AuthorDTO;
import org.example.librarymanagementservice.dto.BookDTO;
import org.example.librarymanagementservice.dto.BookPatchDTO;
import org.example.librarymanagementservice.dto.CreateBookDTO;
import org.example.librarymanagementservice.services.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/apis/library")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService){
        this.libraryService = libraryService;
    }

    @GetMapping(path = "/get-all-books-and-authors")
    public ResponseEntity<List<BookDTO>> getAllBooksAndAuthors(){
        List<BookDTO> bookDTO =  libraryService.getAllBooksAndAuthors();
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/create-author")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO){
        return new ResponseEntity<>(libraryService.createAuthor(authorDTO), HttpStatus.CREATED);
    }

    @PostMapping(path = "/create-book")
    public ResponseEntity<BookDTO> createAuthor(@RequestBody CreateBookDTO bookDTO){
        return new ResponseEntity<>(libraryService.createBook(bookDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/get-author-by-id/{authorId}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable(name = "authorId") Long id){
        return new ResponseEntity<>(libraryService.getAuthorById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/get-book-by-id/{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable(name = "bookId") Long id){
        return new ResponseEntity<>(libraryService.getBookById(id), HttpStatus.OK);
    }

    @PatchMapping(path = "/update-book-by-id/{bookId}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable(name = "bookId") Long id,
                                                  @RequestBody BookPatchDTO bookPathDTO){
        return new ResponseEntity<>(libraryService.updateBookById(id, bookPathDTO), HttpStatus.OK);
    }

    @PatchMapping(path = "/update-author-by-id/{authorId}")
    public ResponseEntity<AuthorDTO> updateAuthorById(@PathVariable(name = "authorId") Long id,
                                                  @RequestBody Map<String, Object> updates){
        return new ResponseEntity<>(libraryService.updateAuthorById(id, updates), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete-book-by-id/{bookId}")
    public ResponseEntity<Object> deleteBookById(@PathVariable(name = "bookId") Long id){
        libraryService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete-author-by-id/{authorId}")
    public ResponseEntity<Object> deleteAuthorById(@PathVariable(name = "authorId") Long id){
        libraryService.deleteAuthorById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/get-book-by-title/{title}")
    public ResponseEntity<BookDTO> getBookByTitle(@PathVariable(name="title") String title){
        BookDTO bookDTO = libraryService.findBookByTitle(title);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/get-author-by-name/{name}")
    public ResponseEntity<AuthorDTO> getAuthorByName(@PathVariable(name="name") String name){
        AuthorDTO authorDTO = libraryService.findAuthorByName(name);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/get-books-after-date/{date}")
    public ResponseEntity<List<BookDTO>> getBooksAfterDate(@PathVariable LocalDate date){
        List<BookDTO> bookDTOS = libraryService.findBooksAfterDate(date);
        return ResponseEntity.ok(bookDTOS);
    }

    @GetMapping(path = "/get-books-by-author/{authorName}")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable(name = "authorName") String  name){
        List<BookDTO> bookDTOS = libraryService.findBooksByAuthor(name);
        return ResponseEntity.ok(bookDTOS);
    }
}
