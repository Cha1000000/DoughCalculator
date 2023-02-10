package com.example.doughcalculator.screens.fragments.open

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doughcalculator.R
import com.example.doughcalculator.screens.fragments.open.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class RecipeItemFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipes_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    COLUMN_COUNT <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, Companion.COLUMN_COUNT)
                }
                adapter = RecipeListRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }
        return view
    }

    companion object {

        @JvmStatic
        fun getInstance() =
            RecipeItemFragment()

        const val COLUMN_COUNT = 3
    }
}