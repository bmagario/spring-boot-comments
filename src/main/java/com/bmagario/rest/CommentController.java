package com.bmagario.rest;

import com.bmagario.model.dto.CommentDto;
import com.bmagario.model.dto.NewCommentDto;
import com.bmagario.service.CommentService;
import com.bmagario.service.PostService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/posts/{id}")
public class CommentController {

	private final PostService postService;
	private final CommentService commentService;


	public CommentController(PostService postService, CommentService commentService) {
		this.postService = postService;
		this.commentService = commentService;
	}

	@PostMapping(value = "/comments")
	public ResponseEntity<String> addComment(
		@PathVariable Long id,
		@RequestBody NewCommentDto newCommentDto
	) {
		Long commentId = commentService.addComment(id, newCommentDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(commentId.toString());
	}

	@GetMapping(value = "/comments")
	public List<CommentDto> getCommentsForPost(@PathVariable Long id) {
		List<CommentDto> comments = commentService.getCommentsForPost(id);

		if (comments.isEmpty()) {
			return new ArrayList<>();
		}

		return comments;
	}
}
