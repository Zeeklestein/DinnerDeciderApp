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
    private lateinit var mIngredientListAdapter: AddIngredientAdapter

    //private var modelToBeUpdated: Stack<Ingredient> = Stack()

    private val mOnIngredientClickListener = object : OnIngredientClickListener {

        override fun onDelete(model: Ingredient) {
            mIngredientListAdapter.removeIngredient(model)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_meal)

        //Initialise the recycler view
        ingredientListRV = findViewById(R.id.rv_ingredients)
        ingredientListRV.layoutManager = LinearLayoutManager(this)
        //ingredientListRV.setHasFixedSize(true)

        mIngredientListAdapter = AddIngredientAdapter(this, mOnIngredientClickListener)
        ingredientListRV.adapter = mIngredientListAdapter

        ingredientName = findViewById(R.id.editText_IngrName)
        ingredientQuantity = findViewById(R.id.editText_IngrQuantity)

        addIngredientBtn = findViewById(R.id.btn_addIngr)
        addIngredientBtn.setOnClickListener {

            val name = ingredientName.text.toString()
            val quantity = ingredientQuantity.text.toString()

            if (name.isNotBlank() && quantity.isNotBlank()) {

                //Get id automatically using method in adapter
                val id = mIngredientListAdapter.getNextItemId()
                //Create the ingredient model
                val model = Ingredient(id, name, quantity)

                //add model to the adapter
                mIngredientListAdapter.addIngredient(model)

                //Reset input
                ingredientName.setText("")
                ingredientQuantity.setText("")
            } //TODO("Add Snackbar or toast for blank input")

        }

    }

}