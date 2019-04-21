package com.seaso.seaso.modules.question.utils;

import com.seaso.seaso.modules.question.entity.Question;
import com.seaso.seaso.modules.sys.utils.UserPreference;

import java.util.Map;

public class QuestionUtils {
    public static LikeStatus mapPreferenceMapsToLikeStatus(Long answerId, Map<Long, UserPreference> likeMapper) {
        UserPreference userPreference = likeMapper.get(answerId);
        if (userPreference != null) {
            return userPreference.getPreference() ? LikeStatus.LIKE : LikeStatus.DISLIKE;
        }
        return LikeStatus.NONE;
    }

    public static Question nullQuestion() {
        return new Question(null, null, null, null);
    }
}
