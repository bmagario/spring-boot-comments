package com.bmagario.repository;

import com.bmagario.model.Comment;
import com.bmagario.model.Post;
import com.bmagario.model.dto.CommentDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPostOrderByCreationDateDesc(Post post);
}
