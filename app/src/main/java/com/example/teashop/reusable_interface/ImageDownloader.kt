package com.example.teashop.reusable_interface

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.R
import com.example.teashop.screen.screen.feedback_screen.MakeImage
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun ImageDownloader(imageList: SnapshotStateList<Uri>){
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageList.add(uri!!)
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = White10),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 5.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Фотографии",
                fontFamily = montserratFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.W400
            )
            Text(
                text = "До 3 штук",
                fontFamily = montserratFamily,
                fontSize = 10.sp,
                fontWeight = FontWeight.W400,
                color = Grey10
            )
        }
        Row(
            modifier = Modifier
                .padding(bottom = 5.dp, end = 10.dp)
                .fillMaxWidth(),
        ) {
            if (imageList.size < 3) {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Grey20),
                    shape = RoundedCornerShape(15.dp),
                    onClick = {
                        launcher.launch("image/*")
                    },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(100.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.add_image_icon),
                        tint = Green10,
                        modifier = Modifier.size(30.dp),
                        contentDescription = null
                    )
                }
            }
            if (imageList.isNotEmpty()) {
                for (i in 0..<imageList.size) {
                    MakeImage(i, imageList)
                }
            }
        }
    }
}