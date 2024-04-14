package com.example.teashop.data
import com.example.teashop.R

class DataSource {
    fun loadProducts():List<Product>{
        return listOf(
            Product(R.string.teaExample,"1234",4.2,5,273,292,3,"Черный цейлонский чай, отличается насыщенным, но легким, слегка сладковатым вкусом и тонким приятным ароматом.", loadFeedback(),R.drawable.image2),
            Product(R.string.teaExample,"1234",4.6,6,150,320,11,"Опустите пакетики в чайник – так часто делают в Англии – или разложите их по чашкам. В любом случае, будете пить отличный высокогорный чай, который порадует ярким настоем, крепким вкусом и выраженным тонизирующим действием.", imageResourceId = R.drawable.image3),
            Product(R.string.teaExample,"1234",3.7,9,312,350,21,"Чай Curtis Isabella Grape (Изабелла Грейп) черный, 20пак*1,8г.Черный листовой байховый чай с лепестками цветов, кусочками и ароматом винограда в пирамидках. Высший сорт.", imageResourceId = R.drawable.image4),
            Product(R.string.teaExample,"1234",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            Product(R.string.teaExample,"1234",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            Product(R.string.teaExample,"1234",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample)
        )
    }
    fun loadCategories():List<Category> {
        return listOf(
            Category("Название категории", R.drawable.category_example),
            Category("Название категории", R.drawable.category_example),
            Category("Название категории", R.drawable.category_example),
            Category("Название категории", R.drawable.category_example)
        )
    }
    private fun loadFeedback(): List<Feedback>{
        return listOf(
            Feedback("Кирилл", 4, "Хороший чай", R.drawable.feedback_example),
            Feedback("Кирилл", 3, "Чай так себе", R.drawable.feedback_example),
            Feedback("Кирилл", 4, "Качественный, но не особо понравился", R.drawable.feedback_example),
            Feedback("Кирилл", 5, "Отличный чай", R.drawable.feedback_example),
            Feedback("Кирилл", 5, "Чай супер", R.drawable.feedback_example),
        )
    }
    fun loadWeights(): List<Boolean>{
        return listOf(false, false, false, false)
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