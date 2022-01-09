package com.example.dinnerdeciderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Ingredient
import java.util.*

class NewMealActivity : AppCompatActivity() {

    private lateinit var addIngredientBtn: Button
    private lateinit var ingredientName: EditText
    private lateinit var ingredientQuantity: EditText

    private lateinit var ingredientListRV: RecyclerView
    private lateinit var ingredientListAdapter: AddIngredientAdapter

    private var modelToBeUpdated: Stack<Ingredient> = Stack()

    private val OnIngredientClickListener = object : OnIngredientClickListener {
        override fun onDelete(model: Ingredient) {
            ingredientListAdapter.removeIngredient(model)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_meal)

        //Initialise the recycler view
        ingredientListRV = findViewById<RecyclerView>(R.id.rv_ingredients)
        ingredientListRV.layoutManager = LinearLayoutManager(this)

        ingredientName = findViewById(R.id.tv_IngrName)
        ingredientQuantity = findViewById(R.id.tv_IngrQuantity)

        addIngredientBtn = findViewById(R.id.btn_addIngr)
        addIngredientBtn.setOnClickListener {

        }

    }

}