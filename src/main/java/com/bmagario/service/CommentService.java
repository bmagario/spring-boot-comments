package com.bmagario.service;

import com.bmagario.model.Comment;
import com.bmagario.model.Post;
import com.bmagario.repository.CommentRepository;
import com.bmagario.repository.PostRepository;
import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.bmagario.model.dto.CommentDto;
import com.bmagario.model.dto.NewCommentDto;

@Service
public class CommentService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	public CommentService(PostRepository postRepository, CommentRepository commentRepository) {
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
	}

	/**
	 * Returns a list of all comments for a blog post with passed id.
	 *
	 * @param postId id of the post
	 * @return list of comments sorted by creation date descending - most recent first
	 */
	public List<CommentDto> getCommentsForPost(Long postId) {
		Optional<Post> post = postRepository.findById(postId);
		if (post.isEmpty()) {
			throw new IllegalArgumentException("Post not found with Id: " + postId);
		}

		List<Comment> comments = commentRepository.findByPostOrderByCreationDateDesc(post.get());
		return  comments.stream().map(this::mapCommentToCommentDto)
			.collect(Collectors.toList());
	}

	/**
	 * Creates a new comment
	 *
	 * @param postId id of the post
	 * @param newCommentDto data of new comment
	 * @return id of the created comment
	 *
	 * @throws IllegalArgumentException if postId is null or there is no blog post for passed postId
	 */
	public Long addComment(Long postId, NewCommentDto newCommentDto) {
		Optional<Post> post = postRepository.findById(postId);
		if (post.isEmpty()) {
			throw new IllegalArgumentException("Post not found with Id: " + postId);
		}

		Comment comment = new Comment();
		comment.setAuthor(newCommentDto.author());
		comment.setComment(newCommentDto.content());
		comment.setCreationDate(LocalDateTime.now());
		comment.setPost(post.get());

		Comment newSavedComment = commentRepository.save(comment);
		return newSavedComment.getId();
	}

	private CommentDto mapCommentToCommentDto(Comment comment) {
		return new CommentDto(
			comment.getId(),
			comment.getComment(),
			comment.getAuthor(),
			comment.getCreationDate()
		);
	}


}
