package com.hmyh.jettrivia

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.hmyh.jettrivia.screens.QuestionViewModel
import com.hmyh.jettrivia.ui.theme.JetTriviaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetTriviaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    TriviaHome(
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }
}

@Composable
fun TriviaHome(viewModel: QuestionViewModel = hiltViewModel(),modifier: Modifier){
    Question(viewModel)
}

@Composable
fun Question(viewModel: QuestionViewModel){
    val questions = viewModel.data.value.data?.toMutableList()

    if (viewModel.data.value.loading == true){
        Log.d("Loading", "Question....loading...")
    }
    else{
        Log.d("Loading", "Question loading stop")

        questions?.forEach { questionItem ->
            Log.d("Result","Questions ${questionItem.question}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetTriviaTheme {

    }
}