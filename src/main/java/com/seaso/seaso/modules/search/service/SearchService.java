package com.seaso.seaso.modules.search.service;

import com.seaso.seaso.modules.question.entity.Question;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SearchService {

    List<Question> searchQuestionByImage(MultipartFile image);
}
