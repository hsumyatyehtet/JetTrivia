package com.hmyh.jettrivia.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.hmyh.jettrivia.component.Question

@Composable
fun TriviaHome(viewModel: QuestionViewModel = hiltViewModel(),modifier: Modifier){
    Question(viewModel,modifier)
}