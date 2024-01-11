package com.example.cryptocrazycompose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cryptocrazycompose.model.CryptoListItem
import com.example.cryptocrazycompose.viewmodel.CryptoListViewModel


@Composable
fun CryptoListScreen(
    navController: NavController,
    viewModel : CryptoListViewModel = hiltViewModel()
) {
    
    Surface(
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(text = "Crypto Crazy", modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
                textAlign = TextAlign.Center,
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(10.dp))
            SearchBar(
                hint = "Search ...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                viewModel.searchCryptoList(it)
            }
            Spacer(modifier = Modifier.height(10.dp))
            CryptoList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    modifier : Modifier = Modifier,
    hint : String = "",
    onSearch : (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    Box(modifier = modifier){
        BasicTextField(value = text, onValueChange = {
            text = it
            onSearch(it)
        },  maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                }
        )

        if(isHintDisplayed){
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}

@Composable
fun CryptoList(navController: NavController,
                viewModel: CryptoListViewModel = hiltViewModel()
) {
        val cryptoList by remember {viewModel.cryptoList}
        val errorMessage by remember {viewModel.errorMessage}
        val isLoading by remember {viewModel.isLoading}

        CryptoListView(navController = navController, cryptos = cryptoList)

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if(isLoading){
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            if(errorMessage.isNotEmpty()){
                RetryView(error = errorMessage){
                    viewModel.loadCryptos()
                }
            }
        }
}

@Composable
fun CryptoListView(navController: NavController, cryptos : List<CryptoListItem>) {
    LazyColumn(contentPadding = PaddingValues(5.dp)){
        items(cryptos) {crypto ->
            CryptoRow(navController = navController, crypto = crypto)
        }
    }
    
}
@Composable
fun CryptoRow(navController: NavController, crypto : CryptoListItem) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.tertiary)
        .clickable {
            navController.navigate("crypto_detail_screen/${crypto.asset_id}/${crypto.name}/${crypto.price_usd}")
        }) {
        Text(text = crypto.asset_id,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
            )
        
        Text(text = crypto.price_usd.toString(),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(2.dp),
            color = MaterialTheme.colorScheme.secondary)
    }
}

@Composable
fun RetryView(
    error : String,
    onRetry : () -> Unit
) {
    Column() {
        Text(text = error,color = Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            onRetry
        }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Retry")
        }
    }

}