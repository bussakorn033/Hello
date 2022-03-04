package com.example.android.testhello

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android.testhello.ui.theme.TestHelloTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestHelloTheme {
                MyApp()
            }
        }
    }
}

/* ------------- MyApp ------------- */
@Composable
fun MyApp() {
    val (shouldShowOnboarding, setShouldShowOnboarding) = rememberSaveable { mutableStateOf(true) }
    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { setShouldShowOnboarding(false) })
    } else {
        Greetings()
    }
}
/* ------------- MyApp ------------- */

/* ------------- OnboardingScreen ------------- */
@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = {
                    onContinueClicked()
                }
            ) {
                Text("Continue")
            }
        }
    }
}
/* ------------- OnboardingScreen ------------- */

/* ------------- private Greetings ------------- */
//@Composable
//private fun Greetings(names: List<String> = listOf("World", "Compose")) {
//    Column(modifier = Modifier.padding(vertical = 4.dp)) {
//        for (name in names) {
//            Greeting(name = name)
//        }
//    }
//}
@Composable
private fun Greetings(names: List<String> = List(10) { "$it" } ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            var idx = name.toInt()+1

            Greeting(name = idx.toString())
        }
    }
}
/* ------------- private Greetings ------------- */

/* ------------- private Greeting ------------- */
@Composable
private fun Greeting(name: String) {
    val expanded = remember { mutableStateOf(false) }
//    val extraPadding = if (expanded.value) 48.dp else 0.dp
    val extraPadding by animateDpAsState(
        if (expanded.value) 24.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val context = LocalContext.current
    val ShowLess = stringResource(R.string.ShowLess)
    val ShowMore = stringResource(R.string.ShowMore)
    val duration = Toast.LENGTH_SHORT
    val padding = 20.dp
    val density = LocalDensity.current
    Surface(
        color = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .drawWithContent {
                val paddingPx = with(density) { padding.toPx() }
                clipRect(
                    left = -paddingPx,
                    top = 0f,
                    right = size.width + paddingPx,
                    bottom = size.height + paddingPx
                ) {
                    this@drawWithContent.drawContent()
                }
            }
//            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
    )
    {
        Column(modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = extraPadding.coerceAtLeast(0.dp))) {
        Row(/*modifier = Modifier.padding(24.dp)*/) {
                Column(
                    modifier = Modifier
                        .weight(1f)
//                .padding(bottom = extraPadding)
//                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                ) {
                    Text(text = "Hello, ")
                    Text(
                        text = name,
//                    style = MaterialTheme.typography.h4
                        style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
//            OutlinedButton(
////                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
            IconButton(
                modifier = Modifier.padding(0.dp),
                onClick = {
                    expanded.value = !expanded.value
                    Toast.makeText(
                        context,
                        if (expanded.value) ShowLess else ShowMore,
                        duration
                    ).show()
                }
            ) {
                Icon(
                    if (expanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded.value) {
                        stringResource(R.string.ShowLess)
                    } else {
                        stringResource(R.string.ShowMore)
                    },
//                    modifier = Modifier.size(ButtonDefaults.IconSize)
                    modifier = Modifier
                        .size(25.dp)
                        .padding(start = 0.dp)
                )
            }
            }
                Text(text = if (expanded.value) stringResource(R.string.TextExam) else "")
        }
    }
}
/* ------------- private Greeting ------------- */

/* ------------- Preview ------------- */
@Preview(
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun OnboardingPreview() {
    TestHelloTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
//    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun DefaultPreview() {
    TestHelloTheme {
        Greetings()
    }
}

/* ------------- Preview ------------- */