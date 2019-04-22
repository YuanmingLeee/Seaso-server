package com.seaso.seaso.modules.search.web;

import com.seaso.seaso.common.exception.ApiIllegalArgumentException;
import com.seaso.seaso.modules.question.entity.Question;
import com.seaso.seaso.modules.search.service.SearchService;
import com.seaso.seaso.modules.sys.utils.JsonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> search(HttpServletRequest request,
                                    @RequestParam MultipartFile image,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        String name = image.getOriginalFilename();
        String mimeType = request.getServletContext().getMimeType(name);
        if (!mimeType.startsWith("image/"))
            throw new ApiIllegalArgumentException("Unrecognized image type" + mimeType);

        Pageable pageable = PageRequest.of(page, size);

        Page<Question> questions = searchService.searchQuestionByImage(image, pageable);

        return new ResponseEntity<>(new JsonResponseBody<>(200, questions), HttpStatus.OK);
    }

    @RequestMapping(value = "/text/", method = RequestMethod.POST)
    public ResponseEntity<?> search(@RequestParam String text,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Question> questions = searchService.searchQuestionByText(text, pageable);

        return new ResponseEntity<>(new JsonResponseBody<>(200, questions), HttpStatus.OK);
    }
}
