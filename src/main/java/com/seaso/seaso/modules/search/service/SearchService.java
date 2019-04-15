package com.seaso.seaso.modules.search.service;

import com.seaso.seaso.modules.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface SearchService {

    Page<Question> searchQuestionByImage(MultipartFile image, Pageable pageable);
}
