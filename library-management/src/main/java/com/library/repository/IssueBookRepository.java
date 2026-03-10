package com.library.repository;

import com.library.entity.IssueBook;
import com.library.enums.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueBookRepository extends JpaRepository<IssueBook, Long> {
    List<IssueBook> findByUserUserId(Long userId);
    List<IssueBook> findByStatus(IssueStatus status);
    List<IssueBook> findByBookBookId(Long bookId);
}
