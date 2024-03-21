package com.example.teashop.data
import com.example.teashop.R

class DataSource {
    fun loadProducts():List<Product>{
        return listOf<Product>(
            Product(R.string.teaExample,"1234",4.6,5,400,800,6,"abcde", loadFeedback(),R.drawable.teaexample),
            Product(R.string.teaExample,"1234",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            Product(R.string.teaExample,"1234",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            Product(R.string.teaExample,"1234",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            Product(R.string.teaExample,"1234",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample),
            Product(R.string.teaExample,"1234",4.6,5,400,800,6,"abcde", imageResourceId = R.drawable.teaexample)
        )
    }
    fun loadCategories():List<Category> {
        return listOf<Category>(
            Category("Название категории", R.drawable.category_example),
            Category("Название категории", R.drawable.category_example),
            Category("Название категории", R.drawable.category_example),
            Category("Название категории", R.drawable.category_example)
        )
    }
    private fun loadFeedback(): List<Feedback>{
        return listOf<Feedback>(
            Feedback("Керил", 4, "Отличный чай пил с дедом он умер поэтому ставлю четыре", R.drawable.feedback_example)
        )
    }
}