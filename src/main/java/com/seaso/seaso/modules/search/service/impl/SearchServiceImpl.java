package com.seaso.seaso.modules.search.service.impl;

import com.google.common.collect.Lists;
import com.seaso.seaso.modules.question.dao.QuestionRepository;
import com.seaso.seaso.modules.question.entity.Question;
import com.seaso.seaso.modules.search.manager.SearchManager;
import com.seaso.seaso.modules.search.service.SearchService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
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
    public Page<Question> searchQuestionByImage(MultipartFile image, Pageable pageable) {
        String result = searchManager.getTextFromImage(image);

        return searchQuestionByText(result, pageable);
    }

    @Override
    public Page<Question> searchQuestionByText(String text, Pageable pageable) {
        QueryBuilder queryBuilder = buildQueryBuilders(text);
        // TODO: add rescorer
//        QueryRescorerBuilder rescorerBuilder = new QueryRescorerBuilder(queryBuilders[0])
//                .windowSize(pageable.getPageSize() * 5)
//                .setScoreMode(QueryRescoreMode.Multiply);

        SortBuilder sortBuilder = new ScoreSortBuilder();
        HighlightBuilder highlightBuilder = buildHightlightBuilder();
        List<HighlightBuilder.Field> fields = highlightBuilder.fields();

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .withSort(sortBuilder)
                .withHighlightBuilder(highlightBuilder)
                .withHighlightFields(fields.toArray(new HighlightBuilder.Field[0]))
                .build();

//        SearchRequest searchRequest = new SearchRequest();
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
//                .query(queryBuilder)
//                .addRescorer(rescorerBuilder)
//                .highlighter(highlightBuilder)
//                .sort(sortBuilder);
//        searchRequest.routing("test/question").source(searchSourceBuilder);
        try {
            return questionRepository.search(searchQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PageImpl<>(Lists.newArrayList());
    }

    private QueryBuilder buildQueryBuilders(String result) {

        // build 0-* matcher
        MatchQueryBuilder queryBuilder0_0 = QueryBuilders.matchQuery("title", result).minimumShouldMatch("30%").boost(1.2F);
        MatchQueryBuilder queryBuilder0_1 = QueryBuilders.matchQuery("content", result).minimumShouldMatch("30%");
        MatchQueryBuilder queryBuilderEng0_0 = queryBuilder0_0.analyzer("english");
        MatchQueryBuilder queryBuilderEng_0_1 = queryBuilder0_1.analyzer("english");

        // build 2- match query
        QueryBuilder matchQueryBuilder2 = queryBuilderEqualMerge(queryBuilder0_0, queryBuilder0_0);
        QueryBuilder matchQueryBuilderEng2 = queryBuilderEqualMerge(queryBuilderEng0_0, queryBuilderEng_0_1);

        // build 3- query
        QueryBuilder queryBuilder3_2 = queryBuilderEqualMerge(matchQueryBuilder2, matchQueryBuilderEng2);

        // build top
        return queryBuilder3_2;
    }

    private QueryBuilder queryBuilderEqualMerge(QueryBuilder queryBuilder, QueryBuilder queryBuilder2) {
        return QueryBuilders.boolQuery().should(queryBuilder).should(queryBuilder2);
    }

    private HighlightBuilder buildHightlightBuilder() {
        String preTag = "<span class=\"highlight\">";
        String postTag = "</span>";
        HighlightBuilder highlightBuilder = new HighlightBuilder();

        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title")
                .preTags(preTag)
                .postTags(postTag);

        HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("content")
                .preTags(preTag)
                .postTags(postTag);

        highlightBuilder.field(highlightTitle).field(highlightContent);

        return highlightBuilder;
    }
}
