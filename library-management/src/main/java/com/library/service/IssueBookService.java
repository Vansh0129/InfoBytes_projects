package com.library.service;

import com.library.entity.IssueBook;

import java.util.List;

public interface IssueBookService {
    IssueBook issueBook(Long bookId, String username);
    IssueBook returnBook(Long issueId, String username);
    List<IssueBook> getMyIssuedBooks(String username);
    List<IssueBook> getAllIssuedBooks();
    List<IssueBook> getOverdueBooks();
    void checkAndMarkOverdue();
}
