package com.example.teashop.reusable_interface.text_fields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily
import kotlin.math.absoluteValue

class MaskVisualTransformation(private val mask: String): VisualTransformation {
    private val specialSymbolsIndices = mask.indices.filter { mask[it] != '#' }

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0
        text.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++
        }
        return TransformedText(AnnotatedString(out), offsetTranslator())
    }

    private fun offsetTranslator() = object: OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val offsetValue = offset.absoluteValue
            if (offsetValue == 0) return 0
            var numberOfHashtags = 0
            val masked = mask.takeWhile {
                if (it == '#') numberOfHashtags++
                numberOfHashtags < offsetValue
            }
            return masked.length + 1
        }

        override fun transformedToOriginal(offset: Int): Int {
            return mask.take(offset.absoluteValue).count { it == '#' }
        }
    }
}

@Composable
fun MakeMaskedTextField(
    header: String,
    onValueChange: (String) -> Unit = {},
    bottomPadding: Int = 10,
    inputValue: String = "",
    mask: String,
    contextLength: Int = 255
){
    val visualTransform = MaskVisualTransformation(mask)
    var value by remember{
        mutableStateOf(inputValue)
    }
    Column(
        modifier = Modifier.padding(start = 10.dp)
    ) {
        Text(
            text = header,
            fontFamily = montserratFamily,
            fontSize = 13.sp,
            fontWeight = FontWeight.W400,
            color = Black10
        )
        TextField(
            value = value,
            onValueChange = {
                val cleanedInput = it.filter { char -> char.isDigit() }.take(contextLength)
                value = cleanedInput
                onValueChange(value)
            },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = White10,
                unfocusedIndicatorColor = White10,
                focusedContainerColor = Grey20,
                unfocusedContainerColor = Grey20,
                disabledContainerColor = Black10,
                disabledTextColor = Black10,
                focusedTextColor = Black10,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .padding(end = 10.dp, bottom = bottomPadding.dp)
                .fillMaxWidth(),
            singleLine = true,
            visualTransformation = visualTransform
        )
    }
    onValueChange(value)
}