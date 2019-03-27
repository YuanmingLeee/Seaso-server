package com.seaso.seaso.modules.question.utils;

import java.util.Date;
import java.util.Map;

public class QuestionUtils {
    public static LikeStatus mapPreferenceMapsToLikeStatus(String answerId, Map<String, Date> likeMapper,
                                                           Map<String, Date> dislikeMapper) {
        if (likeMapper.containsKey(answerId))
            return LikeStatus.LIKE;
        if (dislikeMapper.containsKey(answerId))
            return LikeStatus.DISLIKE;
        return LikeStatus.NONE;
    }
}
