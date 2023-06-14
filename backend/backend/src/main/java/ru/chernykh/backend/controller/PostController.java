package ru.chernykh.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import ru.chernykh.backend.dto.PostDto;
import ru.chernykh.backend.dto.form.CategoriesResponse;
import ru.chernykh.backend.dto.form.CreatePostRequest;
import ru.chernykh.backend.dto.form.PostResponse;
import ru.chernykh.backend.dto.form.UpdatePostRequest;
import ru.chernykh.backend.model.Category;
import ru.chernykh.backend.service.PostService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/v1/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {this.postService = postService;}

    @PostMapping
    public ResponseEntity<PostDto> savePost(@RequestBody @Valid CreatePostRequest createPostRequest) {
        System.out.println(createPostRequest);
        PostDto save = this.postService.save(createPostRequest);
        return ResponseEntity.ok(save);
    }

    @PatchMapping("/update")
    public ResponseEntity<PostDto> updatePost(@RequestBody @Valid UpdatePostRequest update) {
        return ResponseEntity.ok(this.postService.update(update));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id){
        this.postService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getPosts(@RequestParam Optional<String> category) {
        Category c = null;
        if (category.isPresent() && !category.get().isBlank()) {
            c = Category.findByName(category.get());
        }
        List<PostDto> all = postService.findAll(c).stream().sorted((a, b) -> b.getCreatedTime().compareTo(a.getCreatedTime())).collect(Collectors.toList());
        System.out.println(all);
        return ResponseEntity.ok(new PostResponse(all));
    }

    @GetMapping("/contains")
    public ResponseEntity<PostResponse> getPostsContains(@RequestParam Optional<String> value) {
        if (value.isEmpty() || value.get().isBlank()) {
            return getPosts(Optional.empty());
        }
        PostResponse postResponse = new PostResponse(postService.findAllContains(value.get()).stream().sorted((a, b) -> b.getCreatedTime().compareTo(a.getCreatedTime())).collect(Collectors.toList()));
        System.out.println(postResponse);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoriesResponse> getCategories() {
        return ResponseEntity.ok(new CategoriesResponse(postService.findAllCategories()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findPostById(@PathVariable("id") String id) {
        System.out.println(id);
        PostDto post = postService.findPostById(Long.parseLong(id));
        System.out.println(post);
        return ResponseEntity.ok((post));
    }
}
