package com.eneskayiklik.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eneskayiklik.myapplication.component.stepList
import com.eneskayiklik.myapplication.ui.theme.MyApplicationTheme
import com.eneskayiklik.myapplication.component.introductionText
import com.eneskayiklik.myapplication.ui.theme.Purple
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent(
    viewModel: ConnectWalletViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val walletAddress = viewModel.userWallet.collectAsState().value


    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(LightGray.copy(alpha = .1F)),
            contentPadding = PaddingValues(vertical = 15.dp, horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)) {
            introductionText()
            stepList(isWalletConnected = false) {
                viewModel.connectWallet(context)
            }
        }

        Text(text = walletAddress,
            style = MaterialTheme.typography.h6.copy(fontSize = 10.sp, color = Purple),
            textAlign = TextAlign.Center, modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 5.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        MainContent()
    }
}