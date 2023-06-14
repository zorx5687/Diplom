package ru.chernykh.backend.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import ru.chernykh.backend.dto.PostDto;
import ru.chernykh.backend.dto.form.CreatePostRequest;
import ru.chernykh.backend.dto.form.UpdatePostRequest;
import ru.chernykh.backend.mapping.PostMapping;
import ru.chernykh.backend.model.Category;
import ru.chernykh.backend.model.Post;
import ru.chernykh.backend.model.User;
import ru.chernykh.backend.repository.PostRepository;
import ru.chernykh.backend.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMapping postMapping;
    private final UserService userService;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, PostMapping postMapping, UserService userService,
                       UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postMapping = postMapping;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public PostDto save(CreatePostRequest createPostRequest) {
        System.out.println(createPostRequest);
        User userById = userService.findUserById(createPostRequest.authorId());
        if (userById == null) {
            throw new IllegalArgumentException("Автор не найден");
        }
        Post postToPost = new Post();
        postToPost.setCategory(Category.findByName(createPostRequest.category()));
        postToPost.setAuthor(userById);
        postToPost.setPrice(Float.valueOf(createPostRequest.price().substring(2)));
        postToPost.setAddress(createPostRequest.address());
        postToPost.setDescription(createPostRequest.description());
        postToPost.setTitle(createPostRequest.title());
        postToPost.setUrl(createPostRequest.photoUrl());
        userById.addPost(postToPost);
        Post save = postRepository.save(postToPost);
        userRepository.save(userById);
        return postMapping.postToPostDto(save);
    }

    public List<PostDto> findAll(Category c) {
        List<Post> posts;
        if (c == null) {
            posts = postRepository.findAll();
        } else {
            posts = postRepository.findAllByCategory(c);
        }
        return posts.stream().map(postMapping::postToPostDto).collect(Collectors.toList());
    }

    public List<String> findAllCategories() {
        return Arrays.stream(Category.values()).map(Category::getValue).collect(Collectors.toList());
    }

    public PostDto findPostById(long id) {
        return postRepository.findById(id).map(postMapping::postToPostDto).orElseThrow();
    }

    public List<PostDto> findPostsByUser(Long id) {
        User userById = userService.findUserById(id);
        return postRepository.findAllByAuthor(userById).stream().map(postMapping::postToPostDto).collect(Collectors.toList());
    }

    public List<PostDto> findAllContains(String contains) {
        return postRepository.findAll().stream().filter(post -> {
            String string = post.getTitle() + post.getDescription() + post.getCategory().getValue();
            string = string.replaceAll("\\s", "").toLowerCase();
            return string.contains(contains.replaceAll("\\s", "").toLowerCase());
        }).map(postMapping::postToPostDto).collect(Collectors.toList());
    }

    public PostDto update(@Valid UpdatePostRequest update) {
        Post post = postRepository.findById(update.postId()).orElseThrow();
        if (post.getAuthor().getId() != update.authorId()) {
            throw new IllegalArgumentException("Автор не имеет доступа");
        }
        post.setUrl(update.photoUrl());
        post.setPrice(Float.valueOf(update.price().substring(2)));
        post.setAddress(update.address());
        post.setDescription(update.description());
        post.setTitle(update.title());
        post.setCategory(Category.findByName(update.category()));
        return postMapping.postToPostDto(postRepository.save(post));
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
