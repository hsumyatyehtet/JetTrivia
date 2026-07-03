package com.hmyh.jettrivia.component

import android.R.attr.onClick
import android.util.Log
import android.view.View
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmyh.jettrivia.model.QuestionItem
import com.hmyh.jettrivia.screens.QuestionViewModel
import com.hmyh.jettrivia.util.AppColors

@Composable
fun Question(viewModel: QuestionViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()

    if (viewModel.data.value.loading == true) {

        CircularProgressIndicator()

    } else {


        if (questions != null) {

            QuestionDisplay(question = questions[0], onNextClick = {
                Log.d("Question", "Question: $it")
            })
        }
    }
}

//@Preview
@Composable
fun QuestionDisplay(
    question: QuestionItem,
    // questionIndex: MutableState<Int>,
    // viewModel: QuestionViewModel,
    onNextClick: (Int) -> Unit = {}
) {


    // Track the choices state
    val choicesState = remember {
        question.choices.toMutableList()
    }

    // Track the selected answer state
    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }

    // Track the correct answer state
    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }

    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }


    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = AppColors.mDarkPurple
    ) {
        Column(
            modifier = Modifier.padding(top = 24.dp, bottom = 12.dp, start = 12.dp, end = 12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            QuestionTracker(
            )
            DrawDottedLine(
                pathEffect = pathEffect,
                Modifier.padding(vertical = 12.dp)
            )

            Column() {
                Text(
                    text = question.question,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f),
                    fontSize = 16.sp,
                    color = AppColors.mOffWhite,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )

                // Choices
                choicesState.forEachIndexed { index, answerText ->
                    Row(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .height(44.dp)
                            .border(
                                width = 4.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        AppColors.mOffDarkPurple,
                                        AppColors.mOffDarkPurple
                                    )
                                ),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clip(
                                RoundedCornerShape(
                                    topStartPercent = 50,
                                    topEndPercent = 50,
                                    bottomEndPercent = 50,
                                    bottomStartPercent = 50
                                )
                            )
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            //Is my index the same as the selected index?
                            selected = (answerState.value == index),
                            onClick = {
                                updateAnswer(index)
                            },
                            modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults
                                .colors(
                                    selectedColor =
                                        if (correctAnswerState.value == true &&
                                            index == answerState.value
                                        ) {
                                            Color.Green.copy(alpha = 0.2f)
                                        } else {
                                            Color.Red.copy(alpha = 0.2f)
                                        }
                                )
                        )

                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    color = if (correctAnswerState.value == true &&
                                        index == answerState.value
                                    ) {
                                        Color.Green
                                    } else if (correctAnswerState.value == false &&
                                        index == answerState.value
                                    ) {
                                        Color.Red
                                    } else {
                                        AppColors.mOffWhite
                                    },
                                    fontSize = 16.sp
                                )
                            ){
                                append(answerText)
                            }
                        }

                        Text(text = annotatedString, modifier = Modifier.padding(6.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun DrawDottedLine(pathEffect: PathEffect, modifier: Modifier) {

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            color = AppColors.mLightGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )

    }

}


@Composable
fun QuestionTracker(counter: Int = 10, outOff: Int = 100) {

    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                style = SpanStyle(
                    color = AppColors.mLightGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            ) {
                append("Question $counter/")
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp
                    )
                ) {
                    append("$outOff")
                }
            }
        }
    })

}