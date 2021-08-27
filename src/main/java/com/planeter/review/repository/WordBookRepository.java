package com.planeter.review.repository;

import com.planeter.review.model.entity.WordBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordBookRepository extends JpaRepository<WordBook,String> {
}
