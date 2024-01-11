package com.example.cryptocrazycompose.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.cryptocrazycompose.model.CryptoIconListItem
import com.example.cryptocrazycompose.util.Resource
import com.example.cryptocrazycompose.viewmodel.CryptoDetailViewModel

@Composable
fun CryptoDetailScreen(
    navController: NavController,
    id : String,
    name : String,
    price : String,
    viewModel : CryptoDetailViewModel = hiltViewModel()
) {
    // Step 1 -> Wrong
    /*
    val scope = rememberCoroutineScope()

    var cryptoItem by remember { mutableStateOf<Resource<CryptoIconListItem>>(Resource.Loading())
        }
        scope.launch{
        cryptoItem = viewModel.getCryptoIcon(id)
            println(cryptoItem)
        }
     */

    // Step 2 -> Better
    /*
    var cryptoItem by remember { mutableStateOf<Resource<CryptoIconListItem>>(Resource.Loading()) }

    LaunchedEffect(key1 = Unit){
        cryptoItem = viewModel.getCryptoIcon(id)
        println(cryptoItem)
    }
     */

    val cryptoItem by produceState<Resource<CryptoIconListItem>>(initialValue = Resource.Loading()){
        value = viewModel.getCryptoIcon(id)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.tertiary),
        contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            when(cryptoItem){
                is Resource.Success -> {
                    val selectedCrypto = cryptoItem.data!!
                    
                    Text(text = name,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center)
                    
                    Image(
                        painter = rememberImagePainter(data = selectedCrypto.url),
                        contentDescription = name,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(200.dp, 200.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )

                    Text(text = price,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center)

                }
                is Resource.Error -> {
                    Text(text = cryptoItem.message!!)
                }
                is Resource.Loading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }

}