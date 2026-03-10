package com.library.repository;

import com.library.entity.Book;
import com.library.enums.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByBookStatus(BookStatus status);
    List<Book> findByAuthorAuthorId(Long authorId);
    List<Book> findByBookNameContainingIgnoreCase(String bookName);
}
