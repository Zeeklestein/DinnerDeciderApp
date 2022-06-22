package com.example.dinnerdeciderapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerdeciderapp.model.Meal

class AdapterMealPlanner(
    private var mealList: ArrayList<Meal> = ArrayList()
    ): RecyclerView.Adapter<AdapterMealPlanner.ViewHolder>() {

    private lateinit var holder: ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder{

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_meal_planner, parent, false) as View
        holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){

        //Assign a heading for each day
        when(holder.layoutPosition){
            0 -> holder.tvWeekday.text = Day.MONDAY.toString()
            1 -> holder.tvWeekday.text = Day.TUESDAY.toString()
            2 -> holder.tvWeekday.text = Day.WEDNESDAY.toString()
            3 -> holder.tvWeekday.text = Day.THURSDAY.toString()
            4 -> holder.tvWeekday.text = Day.FRIDAY.toString()
            5 -> holder.tvWeekday.text = Day.SATURDAY.toString()
            6 -> holder.tvWeekday.text = Day.SUNDAY.toString()
        }

        if (mealList.isEmpty())
            holder.tvMealName.text = "No Data"
        else{
            val listItem = mealList[position]
            holder.tvMealName.text = listItem.mealName
            holder.bind(listItem)
        }
    }

    override  fun getItemCount(): Int{
       /*Returns 7 so that one fragment for each day of the week is shown. Allows the use of
       * defining tags etc. before randomising meals.*/
        return 7
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        //TODO: update to include meal image
        val tvMealName: TextView = view.findViewById(R.id.tv_mpfMealName)
        val tvWeekday: TextView = view.findViewById(R.id.tv_mpfWeekday)

        fun bind(meal: Meal){

            // Allow each meal to be clicked on to view meal information
            this.view.setOnClickListener {
                val context = holder.view.context
                val intent = Intent(context, ActivityViewMeal::class.java).apply {
                    putExtra("SelectedMeal", meal)
                }
                context.startActivity(intent)
            }
        }
    }

    fun setMealList(list: ArrayList<Meal>){
        mealList = list
        //TODO: Look into finding an alternative to the following problem
        notifyDataSetChanged()
    }

    enum class Day{
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}