package com.alexlyxy.alexretrofitlessontwo.presentation.common.viewsmvc

import android.app.Activity

class ScreensNavigator (private val activity: Activity){

    fun navigateBack() {
        activity.onBackPressed()
    }

//    fun toQuestionDetails(questionId: String) {
//        QuestionDetailsActivity.start(activity, questionId)
//    }
}
