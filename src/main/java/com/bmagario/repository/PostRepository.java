package com.bmagario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmagario.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
