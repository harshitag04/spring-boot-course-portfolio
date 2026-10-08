package org.example.librarymanagementservice.services;

import org.example.librarymanagementservice.dto.AuthorDTO;
import org.example.librarymanagementservice.dto.BookDTO;
import org.example.librarymanagementservice.dto.BookPatchDTO;
import org.example.librarymanagementservice.dto.CreateBookDTO;
import org.example.librarymanagementservice.entities.Author;
import org.example.librarymanagementservice.entities.Book;
import org.example.librarymanagementservice.exceptions.ResourceNotFoundException;
import org.example.librarymanagementservice.repositories.AuthorRepository;
import org.example.librarymanagementservice.repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public LibraryService(AuthorRepository authorRepository, BookRepository bookRepository, ModelMapper modelMapper){
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public List<BookDTO> getAllBooksAndAuthors() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map((book) -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO){
        Author authorEntity = modelMapper.map(authorDTO, Author.class);
        Author savedAuthor =  authorRepository.save(authorEntity);
        return modelMapper.map(savedAuthor, AuthorDTO.class);
    }

    public BookDTO createBook(CreateBookDTO bookDTO){
        Long authorId = bookDTO.getAuthorId();
        Author authorEntity = authorRepository.findById(authorId).orElseGet(() -> new ResourceNotFoundException("Author with id " + authorId + " does not exist"));

        Book newBookEntity = modelMapper.map(bookDTO, Book.class);
        Book savedBook = bookRepository.save(newBookEntity);
        return modelMapper.map(savedBook, BookDTO.class);
    }

    public BookDTO getBookById(Long bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book with provided Id does not exist"));
        return modelMapper.map(book, BookDTO.class);
    }

    public AuthorDTO getAuthorById(Long authorId){
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author with provided Id does not exist"));
        return modelMapper.map(author, AuthorDTO.class);
    }

    public AuthorDTO updateAuthorById(Long authorId, Map<String, Object> updates){
        Author existingAuthor =  authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author with provided Id does not exist"));
        updates.forEach((field, value) ->{
            Field fieldToBeUpdated = ReflectionUtils.findField(Author.class, field);
            if(fieldToBeUpdated != null){
                ReflectionUtils.makeAccessible(fieldToBeUpdated);
                ReflectionUtils.setField(fieldToBeUpdated, existingAuthor, value);
            }
        });
        Author updatedAuthor = authorRepository.save(existingAuthor);
        return modelMapper.map(updatedAuthor, AuthorDTO.class);
    }

    public BookDTO updateBookById(Long bookId, BookPatchDTO bookPatchDTO){
        Book existingBook =  bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book with provided Id does not exist"));

        if(bookPatchDTO.getTitle() != null){
            existingBook.setTitle(bookPatchDTO.getTitle());
        }

        if (bookPatchDTO.getPrice() != null) {
            existingBook.setPrice(bookPatchDTO.getPrice());
        }

        if(bookPatchDTO.getPublishDate() != null){
            existingBook.setPublishDate(bookPatchDTO.getPublishDate());
        }

        if(bookPatchDTO.getAuthorId() != null){
            Long id = bookPatchDTO.getAuthorId();
            Author newAuthor = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author with provided Id does not exist"));
            existingBook.setAuthor(newAuthor);
        }
        /*bookPatchDTO.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findField(Book.class, field);
            if(fieldToBeUpdated != null){
                ReflectionUtils.makeAccessible(fieldToBeUpdated);
                //Object convertedValue = objectMapper.convertValue(value, fieldToBeUpdated.getType());
                ReflectionUtils.setField(fieldToBeUpdated, existingBook, value);
            }
        });*/
        Book updatedBook = bookRepository.save(existingBook);
        return modelMapper.map(updatedBook, BookDTO.class);
    }

    public void deleteAuthorById(Long authorId) {
        authorRepository.deleteById(authorId);
    }

    public void deleteBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public BookDTO findBookByTitle(String title){
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Book with title " + title + " was not found"));

        return modelMapper.map(book, BookDTO.class);
    }

    public AuthorDTO findAuthorByName(String name){
        Author author = authorRepository.findAuthorByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Author with name " + name + " was not found"));
        return modelMapper.map(author, AuthorDTO.class);
    }

    public List<BookDTO> findBooksAfterDate(LocalDate date){
        List<Book> books = bookRepository.findBooksByPublishDateAfter(date);
        return books
                .stream()
                .map((book) -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    public List<BookDTO> findBooksByAuthor(String author){
        List<Book> books = bookRepository.findByAuthorName(author);
        return books
                .stream()
                .map((book) -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

}
