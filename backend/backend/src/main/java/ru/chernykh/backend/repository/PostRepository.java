package ru.chernykh.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernykh.backend.model.Category;
import ru.chernykh.backend.model.Post;
import ru.chernykh.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long aLong);
    List<Post> findAllByCategory(Category category);

    List<Post> findAllByAuthor(User author);
}
