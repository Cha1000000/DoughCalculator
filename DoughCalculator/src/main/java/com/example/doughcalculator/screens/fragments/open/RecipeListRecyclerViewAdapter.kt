package com.example.doughcalculator.screens.fragments.open

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doughcalculator.databinding.FragmentRecipeItemBinding
import com.example.doughcalculator.screens.fragments.open.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class RecipeListRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<RecipeListRecyclerViewAdapter.RecipeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {

        return RecipeHolder(
            FragmentRecipeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        val item = values[position]
        holder.itemTitle.id = item.id.toInt()
        holder.itemTitle.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class RecipeHolder(binding: FragmentRecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val itemTitle: TextView = binding.tvRecipeTitle
        val starButton: ImageButton = binding.btStar
        val deleteButton: ImageButton = binding.btDelete

        override fun toString(): String {
            return super.toString() + " '" + itemTitle.text + "'"
        }
    }

}