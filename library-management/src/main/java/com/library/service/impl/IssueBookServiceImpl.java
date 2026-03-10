package com.library.service.impl;

import com.library.entity.Book;
import com.library.entity.IssueBook;
import com.library.entity.UserAccess;
import com.library.entity.UserEntity;
import com.library.enums.BookStatus;
import com.library.enums.IssueStatus;
import com.library.exception.BookNotAvailableException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.IssueBookRepository;
import com.library.repository.UserAccessRepository;
import com.library.service.IssueBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueBookServiceImpl implements IssueBookService {

    private final IssueBookRepository issueBookRepository;
    private final BookRepository bookRepository;
    private final UserAccessRepository userAccessRepository;

    private static final double FINE_PER_DAY = 5.0;
    private static final int ALLOWED_DAYS = 14;

    private UserEntity resolveUser(String username) {
        UserAccess access = userAccessRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        UserEntity user = access.getUser();
        if (user == null) throw new ResourceNotFoundException("User profile not linked for: " + username);
        return user;
    }

    @Override
    @Transactional
    public IssueBook issueBook(Long bookId, String username) {
        UserEntity user = resolveUser(username);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

        if (book.getBookStatus() != BookStatus.AVAILABLE) {
            throw new BookNotAvailableException("Book '" + book.getBookName() + "' is currently not available.");
        }

        book.setBookStatus(BookStatus.ISSUED);
        bookRepository.save(book);

        IssueBook issueBook = IssueBook.builder()
                .book(book)
                .bookName(book.getBookName())
                .issuedDate(LocalDate.now())
                .returnDate(LocalDate.now().plusDays(ALLOWED_DAYS))
                .status(IssueStatus.ISSUED)
                .user(user)
                .build();

        return issueBookRepository.save(issueBook);
    }

    @Override
    @Transactional
    public IssueBook returnBook(Long issueId, String username) {
        UserEntity user = resolveUser(username);

        IssueBook issueBook = issueBookRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue record not found with ID: " + issueId));

        if (!issueBook.getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("You can only return books issued to you.");
        }

        LocalDate today = LocalDate.now();
        issueBook.setStatus(IssueStatus.RETURNED);

        // Calculate fine if overdue
        if (today.isAfter(issueBook.getReturnDate())) {
            long daysLate = issueBook.getReturnDate().until(today).getDays();
            double fine = daysLate * FINE_PER_DAY;
            user.setFineAmount(user.getFineAmount() + fine);
        }

        // Free the book
        Book book = issueBook.getBook();
        book.setBookStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);

        return issueBookRepository.save(issueBook);
    }

    @Override
    public List<IssueBook> getMyIssuedBooks(String username) {
        UserEntity user = resolveUser(username);
        return issueBookRepository.findByUserUserId(user.getUserId());
    }

    @Override
    public List<IssueBook> getAllIssuedBooks() {
        return issueBookRepository.findAll();
    }

    @Override
    public List<IssueBook> getOverdueBooks() {
        return issueBookRepository.findByStatus(IssueStatus.OVERDUE);
    }

    @Override
    @Transactional
    public void checkAndMarkOverdue() {
        List<IssueBook> issuedBooks = issueBookRepository.findByStatus(IssueStatus.ISSUED);
        LocalDate today = LocalDate.now();

        issuedBooks.stream()
                .filter(issue -> today.isAfter(issue.getReturnDate()))
                .forEach(issue -> {
                    issue.setStatus(IssueStatus.OVERDUE);
                    issueBookRepository.save(issue);
                });
    }
}
