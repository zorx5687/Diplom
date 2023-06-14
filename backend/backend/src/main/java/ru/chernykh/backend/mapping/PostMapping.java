package ru.chernykh.backend.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.chernykh.backend.dto.PostDto;
import ru.chernykh.backend.dto.form.CreatePostRequest;
import ru.chernykh.backend.model.Category;
import ru.chernykh.backend.model.Post;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapping {
    @Mapping(target = "category", qualifiedByName = "category")
    PostDto postToPostDto(Post post);

    @Named("category")
    default String category(Category category) {
        return category.getValue();
    }

    Post createPostToPost(CreatePostRequest createPostRequest);
}
