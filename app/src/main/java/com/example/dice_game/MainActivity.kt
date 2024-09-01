package com.example.dice_game

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
    var scoreText by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }

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
            scoreText = "Result: $totalResult"
            resultText = if (totalResult == 7 || totalResult == 11) "You won!" else "You Lose!"
        }) {
            Text(stringResource(R.string.roll))
        }

        Spacer(modifier = Modifier.height(16.dp))

        ScoreComponent(scoreText, resultText)
    }
}

@Composable
private fun ScoreComponent(scoreText: String, resultText: String) {
    Text(scoreText)

    Row {
        Text(text = resultText)
    }
}

@Composable
private fun Header() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "7 or 11 Game",
            fontSize = 40.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = "Roll the dices and hit a total of 7 or 11 points to win!"
        )
    }
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
