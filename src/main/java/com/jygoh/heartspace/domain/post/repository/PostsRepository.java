package com.jygoh.heartspace.domain.post.repository;

import com.jygoh.heartspace.domain.post.model.Category;
import com.jygoh.heartspace.domain.post.model.Posts;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface PostsRepository extends CrudRepository<Posts, Long> {

    List<Posts> findByCategory(Category category);
}
