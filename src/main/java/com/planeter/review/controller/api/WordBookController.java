package com.planeter.review.controller.api;

import com.planeter.review.model.entity.WordBook;
import com.planeter.review.repository.WordBookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 词书
 */
@RestController
public class WordBookController {
    @Resource
    WordBookRepository wordBookRepository;

    @GetMapping("/wordBook/getList")
    public List<WordBook> getWordBookList() {
        return wordBookRepository.findAll();
    }
}
