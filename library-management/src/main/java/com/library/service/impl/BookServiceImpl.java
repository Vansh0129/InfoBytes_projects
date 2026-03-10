package com.library.service.impl;

import com.library.dto.request.BookRequest;
import com.library.dto.response.BookResponse;
import com.library.entity.Author;
import com.library.entity.Book;
import com.library.enums.BookStatus;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    //  HELPERS

    private Author resolveAuthorByUsername(String username) {
        return authorRepository.findByGmail(username)
                .orElseThrow(() -> new AccessDeniedException(
                        "No author profile found for user: " + username + ". Only registered authors can manage books."));
    }

    private BookResponse mapToResponse(Book book) {
        return BookResponse.builder()
                .bookId(book.getBookId())
                .bookName(book.getBookName())
                .description(book.getDescription())
                .publishedDate(book.getPublishedDate())
                .authorName(book.getAuthor() != null ? book.getAuthor().getAuthorName() : book.getAuthorName())
                .bookStatus(book.getBookStatus())
                .reviewList(book.getReviewList())
                .build();
    }

    //  CRUD

    @Override
    @Transactional
    public BookResponse createBook(BookRequest request, String authorUsername) {
        Author author = resolveAuthorByUsername(authorUsername);

        Book book = Book.builder()
                .bookName(request.getBookName())
                .description(request.getDescription())
                .publishedDate(request.getPublishedDate())
                .authorName(author.getAuthorName())
                .author(author)
                .bookStatus(BookStatus.AVAILABLE)
                .build();

        Book saved = bookRepository.save(book);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public BookResponse updateBook(Long bookId, BookRequest request, String authorUsername) {
        Author author = resolveAuthorByUsername(authorUsername);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

        if (!book.getAuthor().getAuthorId().equals(author.getAuthorId())) {
            throw new AccessDeniedException("You can only update your own books.");
        }

        book.setBookName(request.getBookName());
        book.setDescription(request.getDescription());
        book.setPublishedDate(request.getPublishedDate());

        return mapToResponse(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId, String authorUsername) {
        Author author = resolveAuthorByUsername(authorUsername);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

        if (!book.getAuthor().getAuthorId().equals(author.getAuthorId())) {
            throw new AccessDeniedException("You can only delete your own books.");
        }

        bookRepository.delete(book);
    }

    @Override
    public BookResponse getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));
        return mapToResponse(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByStatus(BookStatus status) {
        return bookRepository.findByBookStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> searchBooksByName(String name) {
        return bookRepository.findByBookNameContainingIgnoreCase(name).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByAuthor(Long authorId) {
        authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with ID: " + authorId));
        return bookRepository.findByAuthorAuthorId(authorId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookResponse addReview(Long bookId, String review, String username) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));
        book.getReviewList().add(review);
        return mapToResponse(bookRepository.save(book));
    }
}
