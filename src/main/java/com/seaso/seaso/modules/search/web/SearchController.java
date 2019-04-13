package com.seaso.seaso.modules.search.web;

import com.seaso.seaso.common.exception.ApiIllegalArgumentException;
import com.seaso.seaso.modules.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
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
                                    @RequestParam MultipartFile file) {
        String name = file.getOriginalFilename();
        String mimeType = request.getServletContext().getMimeType(name);
        if (!mimeType.startsWith("image/"))
            throw new ApiIllegalArgumentException("Unrecognized image type" + mimeType);

        searchService.searchQuestionByImage(file);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
