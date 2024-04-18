package com.example.teashop.reusable_interface.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.R
import com.example.teashop.data.SearchSwitch
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun MakeSearchCard(searchCardHide: (SearchSwitch)->Unit = {}){
    var searchRequest by remember{ mutableStateOf("") }
    Card(modifier = Modifier
        .fillMaxWidth(),
        shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp)
    ){
        TextField(
            modifier = Modifier
                .background(Green10)
                .padding(10.dp)
                .fillMaxWidth(),
            value = searchRequest,
            placeholder = {
                Text(
                text = stringResource(R.string.searchDefault),
                fontSize = 15.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W100,
                color = Grey10
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {searchCardHide(SearchSwitch.FILTERS)}),
            leadingIcon = { Icon(painter = painterResource(R.drawable.searchicon), null) },
            onValueChange = {searchRequest = it},
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Green10,
                unfocusedIndicatorColor = Green10,
                focusedContainerColor = White10,
                unfocusedContainerColor = White10,
                disabledContainerColor = White10,
                disabledTextColor = Black10,
                focusedTextColor = Black10,
            ),
            shape = RoundedCornerShape(15.dp),
            singleLine = true
        )
    }
}