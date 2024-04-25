package com.example.teashop.logic

fun reviewDeclension(count: Int): (String){
    var countClone = count
    while(countClone>100){
        countClone-=100
    }
    val result: String = if(countClone<10){
        when(countClone%10){
            1 -> " отзыв"
            2,3,4 -> " отзыва"
            else -> " отзывов"
        }
    }else if(countClone in 10..20){
        " отзывов"
    }else{
        when(countClone%10){
            1 -> " отзыв"
            2,3,4 -> " отзыва"
            else -> " отзывов"
        }
    }
    return result
}

fun bonusDeclension(count: Int): (String){
    var countClone = count
    while(countClone>100){
        countClone-=100
    }
    val result: String = if(countClone<10){
        when(countClone%10){
            1 -> " бонус"
            2,3,4 -> " бонуса"
            else -> " бонусов"
        }
    }else if(countClone in 10..20){
        " бонусов"
    }else{
        when(countClone%10){
            1 -> " бонус"
            2,3,4 -> " бонуса"
            else -> " бонусов"
        }
    }
    return result
}