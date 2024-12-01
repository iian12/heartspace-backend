package com.jygoh.heartspace.domain.post.repository;

import com.jygoh.heartspace.domain.post.model.Posts;
import com.jygoh.heartspace.domain.post.model.Type;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Posts, Long> {

    List<Posts> findAll(Sort createdAt);

    List<Posts> findByType(Type type);
}
