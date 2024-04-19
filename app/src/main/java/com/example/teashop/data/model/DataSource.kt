package com.example.teashop.data.model

import com.example.teashop.R
import com.example.teashop.data.model.order.Order
import com.example.teashop.data.model.order.OrderStatus
import com.example.teashop.data.model.product.ProductFull
import com.example.teashop.data.model.review.Review

class DataSource {
    fun loadProducts():List<ProductFull>{
        return listOf(
            ProductFull(R.string.teaExample,"453",4.2,5,273,292,3,"Черный цейлонский чай, отличается насыщенным, но легким, слегка сладковатым вкусом и тонким приятным ароматом.", loadFeedback(),R.drawable.image2),
            ProductFull(R.string.teaExample,"532",4.6,6,150,320,11,"Опустите пакетики в чайник – так часто делают в Англии – или разложите их по чашкам. В любом случае, будете пить отличный высокогорный чай, который порадует ярким настоем, крепким вкусом и выраженным тонизирующим действием.", imageResourceId = R.drawable.image3),
            ProductFull(R.string.teaExample,"123",3.7,9,312,350,21,"Чай Curtis Isabella Grape (Изабелла Грейп) черный, 20пак*1,8г.Черный листовой байховый чай с лепестками цветов, кусочками и ароматом винограда в пирамидках. Высший сорт.", imageResourceId = R.drawable.image4),
            ProductFull(R.string.teaExample,"5436",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            ProductFull(R.string.teaExample,"5231r",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            ProductFull(R.string.teaExample,"432",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            ProductFull(R.string.teaExample,"7543",4.2,5,273,292,3,"Черный цейлонский чай, отличается насыщенным, но легким, слегка сладковатым вкусом и тонким приятным ароматом.", loadFeedback(),R.drawable.image2),
            ProductFull(R.string.teaExample,"856",4.6,6,150,320,11,"Опустите пакетики в чайник – так часто делают в Англии – или разложите их по чашкам. В любом случае, будете пить отличный высокогорный чай, который порадует ярким настоем, крепким вкусом и выраженным тонизирующим действием.", imageResourceId = R.drawable.image3),
            ProductFull(R.string.teaExample,"4235",3.7,9,312,350,21,"Чай Curtis Isabella Grape (Изабелла Грейп) черный, 20пак*1,8г.Черный листовой байховый чай с лепестками цветов, кусочками и ароматом винограда в пирамидках. Высший сорт.", imageResourceId = R.drawable.image4),
            ProductFull(R.string.teaExample,"5326",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            ProductFull(R.string.teaExample,"21412",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            ProductFull(R.string.teaExample,"6436",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            ProductFull(R.string.teaExample,"41532",4.2,5,273,292,3,"Черный цейлонский чай, отличается насыщенным, но легким, слегка сладковатым вкусом и тонким приятным ароматом.", loadFeedback(),R.drawable.image2),
            ProductFull(R.string.teaExample,"5325",4.6,6,150,320,11,"Опустите пакетики в чайник – так часто делают в Англии – или разложите их по чашкам. В любом случае, будете пить отличный высокогорный чай, который порадует ярким настоем, крепким вкусом и выраженным тонизирующим действием.", imageResourceId = R.drawable.image3),
            ProductFull(R.string.teaExample,"12421",3.7,9,312,350,21,"Чай Curtis Isabella Grape (Изабелла Грейп) черный, 20пак*1,8г.Черный листовой байховый чай с лепестками цветов, кусочками и ароматом винограда в пирамидках. Высший сорт.", imageResourceId = R.drawable.image4),
            ProductFull(R.string.teaExample,"43134",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            ProductFull(R.string.teaExample,"12463",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            ProductFull(R.string.teaExample,"7644",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            ProductFull(R.string.teaExample,"4218976",4.2,5,273,292,3,"Черный цейлонский чай, отличается насыщенным, но легким, слегка сладковатым вкусом и тонким приятным ароматом.", loadFeedback(),R.drawable.image2),
            ProductFull(R.string.teaExample,"7456452",4.6,6,150,320,11,"Опустите пакетики в чайник – так часто делают в Англии – или разложите их по чашкам. В любом случае, будете пить отличный высокогорный чай, который порадует ярким настоем, крепким вкусом и выраженным тонизирующим действием.", imageResourceId = R.drawable.image3),
            ProductFull(R.string.teaExample,"5864874",3.7,9,312,350,21,"Чай Curtis Isabella Grape (Изабелла Грейп) черный, 20пак*1,8г.Черный листовой байховый чай с лепестками цветов, кусочками и ароматом винограда в пирамидках. Высший сорт.", imageResourceId = R.drawable.image4),
            ProductFull(R.string.teaExample,"35325235",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            ProductFull(R.string.teaExample,"5326436",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            ProductFull(R.string.teaExample,"4236435",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
        )
    }

    fun loadFeedback(): List<Review>{
        return listOf(
            Review("Кирилл", 4, "Хороший чай", R.drawable.feedback_example),
            Review("Кирилл", 3, "Чай так себе", R.drawable.feedback_example),
            Review("Кирилл", 4, "Качественный, но не особо понравился", R.drawable.feedback_example),
            Review("Кирилл", 5, "Отличный чай", R.drawable.feedback_example),
            Review("Кирилл", 5, "Чай супер", R.drawable.feedback_example),
        )
    }

    fun loadOrders(): List<Order>{
        return listOf(
            Order(id = 1234567890, bonusesAccrued = 15, bonusesSpent = 5, productList = loadProducts(), status = OrderStatus.CONFIRMED, totalCost = 5000.0, trackNumber = "124151514124"),
            Order(id = 1234567890, bonusesAccrued = 15, bonusesSpent = 5, productList = loadProducts(), status = OrderStatus.CONFIRMED, totalCost = 5000.0, trackNumber = "124151514124"),
            Order(id = 1234567890, bonusesAccrued = 15, bonusesSpent = 5, productList = loadProducts(), status = OrderStatus.CONFIRMED, totalCost = 5000.0, trackNumber = "124151514124"),
            Order(id = 1234567890, bonusesAccrued = 15, bonusesSpent = 5, productList = loadProducts(), status = OrderStatus.CONFIRMED, totalCost = 5000.0, trackNumber = "124151514124"),
            Order(id = 1234567890, bonusesAccrued = 15, bonusesSpent = 5, productList = loadProducts(), status = OrderStatus.CONFIRMED, totalCost = 5000.0, trackNumber = "124151514124"),
        )
    }
}