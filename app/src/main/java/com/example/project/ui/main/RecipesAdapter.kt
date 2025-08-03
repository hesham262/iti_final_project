package com.example.project.ui.main
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project.R
import com.example.project.data.model.Meal

class RecipeAdapter(
    private var items: List<Meal>,
    private val onItemClick: (Meal) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMeal: ImageView = itemView.findViewById(R.id.imgMeal)
        val tvMealName: TextView = itemView.findViewById(R.id.tvMealName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meal, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val meal = items[position]
        holder.tvMealName.text = meal.strMeal
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.imgMeal)

        holder.itemView.setOnClickListener {
            onItemClick(meal)
        }
    }



    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Meal>) {
        items = newItems
        notifyDataSetChanged()
    }
}
