package com.seaso.seaso.modules.search.service.impl;

import com.seaso.seaso.modules.question.dao.QuestionRepository;
import com.seaso.seaso.modules.question.entity.Question;
import com.seaso.seaso.modules.search.manager.SearchManager;
import com.seaso.seaso.modules.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchManager searchManager;

    private final QuestionRepository questionRepository;

    @Autowired
    public SearchServiceImpl(SearchManager searchManager, QuestionRepository questionRepository) {
        this.searchManager = searchManager;
        this.questionRepository = questionRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Question> searchQuestionByImage(MultipartFile image) {
        String result = searchManager.getTextFromImage(image);
        System.out.println(result);
        return null;
    }
}
