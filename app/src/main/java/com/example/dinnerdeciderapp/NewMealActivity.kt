package com.example.dinnerdeciderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Ingredient

class NewMealActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_meal)


        val ingredientList = mutableListOf<Ingredient>()

        val addIngredientButton = findViewById<Button>(R.id.btn_addIngr)
        val txtIngrQuantity = findViewById<TextView>(R.id.tv_IngrQuantity)
        val txtIngrName = findViewById<TextView>(R.id.tv_IngrName)
        val rvIngrList = findViewById<RecyclerView>(R.id.rv_ingredients)

        rvIngrList.layoutManager = LinearLayoutManager(this)
        val itemAdapter = AddIngredientAdapter(this, ingredientList)
        rvIngrList.adapter = itemAdapter

        addIngredientButton.setOnClickListener{
            //when {
                //txtIngrQuantity.text.isNotBlank() && txtIngrName.text.isNotBlank() -> {
                    val newIngredient = Ingredient(txtIngrName.text.toString(), txtIngrQuantity.text.toString())
                    ingredientList.add(newIngredient)


                    itemAdapter.notifyItemChanged(ingredientList.size - 1)
                //}
            //}
        }

    }
}