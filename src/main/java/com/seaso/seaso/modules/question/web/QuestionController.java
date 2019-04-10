package com.seaso.seaso.modules.question.web;

import com.seaso.seaso.modules.question.dao.QuestionRepository;
import com.seaso.seaso.modules.question.entity.Question;
import com.seaso.seaso.modules.question.service.QuestionService;
import com.seaso.seaso.modules.sys.utils.JsonResponseBody;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public JsonResponseBody<List<Question>> findAllQuestions(@RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                                             @RequestParam(value = "sort_by", defaultValue = "questionId") String itemName) {
        List<Question> questions = questionService.findAllQuestions(page, size, Sort.by(itemName).descending());
        return new JsonResponseBody<>(HttpStatus.OK, questions);
    }

    @RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
    public JsonResponseBody<Question> findQuestionById(@PathVariable Long questionId) {
        Question question = questionService.findQuestionById(questionId);
        return new JsonResponseBody<>(HttpStatus.OK, question);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public JsonResponseBody<String> postQuestion(@ModelAttribute Question question) {
        questionService.createQuestion(question);
        return new JsonResponseBody<>();
    }

    private Integer PAGESIZE=10;


    @GetMapping("getSearchResultList")
    public List<Question> getList(Integer pageNumber,String query){
        if(pageNumber==null) pageNumber = 0;
        //es搜索默认第一页页码是0
        SearchQuery searchQuery=getEntitySearchQuery(pageNumber,PAGESIZE,query);
        Page<Question> questionsPage = QuestionRepository.search(searchQuery);
        return questionsPage.getContent();
    }


    private SearchQuery getEntitySearchQuery(int pageNumber, int pageSize, String searchContent) {
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(QueryBuilders.matchPhraseQuery("content", searchContent),
                        ScoreFunctionBuilders.weightFactorFunction(100))
                //设置权重分 求和模式
                .scoreMode("sum")
                //设置权重分最低分
                .setMinScore(10);

        // 设置分页
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }

}



