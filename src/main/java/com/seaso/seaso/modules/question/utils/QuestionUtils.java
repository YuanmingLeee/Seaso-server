package com.seaso.seaso.modules.question.utils;

import java.util.Date;
import java.util.Map;

public class QuestionUtils {
    public static LikeStatus mapPreferenceMapsToLikeStatus(Long answerId, Map<Long, Date> likeMapper,
                                                           Map<Long, Date> dislikeMapper) {
        if (likeMapper.containsKey(answerId))
            return LikeStatus.LIKE;
        if (dislikeMapper.containsKey(answerId))
            return LikeStatus.DISLIKE;
        return LikeStatus.NONE;
    }
}
