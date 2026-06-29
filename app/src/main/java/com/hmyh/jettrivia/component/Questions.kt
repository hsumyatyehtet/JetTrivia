package com.hmyh.jettrivia.component

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.hmyh.jettrivia.screens.QuestionViewModel

@Composable
fun Question(viewModel: QuestionViewModel){
    val questions = viewModel.data.value.data?.toMutableList()

    if (viewModel.data.value.loading == true){

        CircularProgressIndicator()

        Log.d("Loading", "Question....loading...")
    }
    else{
        Log.d("Loading", "Question loading stop")

        questions?.forEach { questionItem ->
            Log.d("Result","Questions ${questionItem.question}")
        }
    }
}