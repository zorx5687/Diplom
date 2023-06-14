package ru.chernykh.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String address;
    private String description;
    private Float price;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String url;
    @CreationTimestamp
    private LocalDateTime createdTime;
    @ManyToOne
    @JoinColumn(name = "", nullable = false)
    private User author;
}
