package com.example.dice_game

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dice_game.ui.theme.DicegameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DicegameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DiceRollerApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var resultDice1 by remember { mutableIntStateOf(1) }
    var resultDice2 by remember { mutableIntStateOf(1) }
    var totalResult by remember { mutableIntStateOf(0) }
    var hits by remember { mutableIntStateOf(0) }
    var attempts by remember { mutableIntStateOf(0) }
    var enableScoreDisplay by remember { mutableStateOf(false) }

    val imageResourceDice1 = getImageResource(resultDice1)
    val imageResourceDice2 = getImageResource(resultDice2)

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Image(
                painter = painterResource(imageResourceDice1),
                contentDescription = resultDice1.toString()
            )

            Image(
                painter = painterResource(imageResourceDice2),
                contentDescription = resultDice2.toString()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            resultDice1 = getRandomDiceValue()
            resultDice2 = getRandomDiceValue()
            totalResult = resultDice1 + resultDice2
            enableScoreDisplay = true
            if (totalResult == 7 || totalResult == 11) hits++
            attempts++
        }) {
            Text(stringResource(R.string.roll))
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (enableScoreDisplay) {
            ScoreComponent(totalResult, hits, attempts)
        }
    }
}

@Composable
private fun Header() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            stringResource(R.string.game_title),
            fontSize = 40.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(stringResource(R.string.roll_instructions))
    }
}

@Composable
private fun ScoreComponent(totalResult: Int, hits: Int, attempts: Int) {
    val iconResource = when (totalResult) {
        7, 11 -> R.drawable.check_circle_24dp_e8eaed_fill0_wght400_grad0_opsz24
        else -> R.drawable.cancel_24dp_e8eaed_fill0_wght400_grad0_opsz24
    }

    val resultText = when (totalResult) {
        7, 11 -> stringResource(R.string.hit_text)
        else -> stringResource(R.string.fail_text)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Row {
            InfoText(stringResource(R.string.score_text, totalResult))

            InfoText(resultText)

            Icon(
                painter = painterResource(iconResource),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            InfoText(
                stringResource(
                    R.string.score_info,
                    hits,
                    attempts,
                    formatWinRatio(hits, attempts)
                )
            )
        }
    }
}

@Composable
fun InfoText(text: String) {
    Text(
        text = text,
        fontSize = 20.sp,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}

@Preview
@Composable
fun DiceRollerApp(modifier: Modifier = Modifier) {
    DiceWithButtonAndImage(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

fun getRandomDiceValue(): Int = (1..6).random()

fun getImageResource(result: Int): Int {
    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    return imageResource
}

@SuppressLint("DefaultLocale")
fun formatWinRatio(hits: Int, attempts: Int): String {
    return String.format("%.2f", (hits.toFloat() / attempts.toFloat()) * 100) + "%"
}
