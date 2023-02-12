package com.example.doughcalculator.screens.fragments.open

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doughcalculator.data.BaseRecipeModel
import com.example.doughcalculator.databinding.FragmentRecipeItemBinding

/**
 * [RecyclerView.Adapter] that can display a [BaseRecipeModel].
 */
class RecipeAdapter(
    private val recipeList: ArrayList<BaseRecipeModel>
) : RecyclerView.Adapter<RecipeAdapter.RecipeHolder>() {

    var onItemClick : ((BaseRecipeModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder = RecipeHolder(
        FragmentRecipeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        val item = recipeList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClick?.invoke(item) }
    }

    override fun getItemCount(): Int = recipeList.size

    fun addItem(item: BaseRecipeModel) {
        recipeList.add(item)
        notifyDataSetChanged()
    }

    inner class RecipeHolder(binding: FragmentRecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var itemTitle: TextView = binding.tvRecipeTitle
        private val starButton: ImageButton = binding.btStar

        fun bind(recipe: BaseRecipeModel) {
            itemTitle.text = recipe.title
        }

        override fun toString(): String {
            return super.toString() + " '" + itemTitle.text + "'"
        }
    }

}