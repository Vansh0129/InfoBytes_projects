package com.library.entity;

import com.library.enums.BookStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "author_name")
    private String authorName;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status", nullable = false)
    @Builder.Default
    private BookStatus bookStatus = BookStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ElementCollection
    @CollectionTable(name = "book_reviews", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "review")
    @Builder.Default
    private List<String> reviewList = new ArrayList<>();
}
