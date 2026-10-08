package org.example.librarymanagementservice.repositories;

import org.example.librarymanagementservice.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

    List<Book> findBooksByPublishDateAfter(LocalDate date);

    List<Book> findByAuthorName(String authorName);
}
