package com.roman.cookbook

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.roman.cookbook.model.Recipe
import kotlinx.android.synthetic.main.fragment_recipe.*
import java.util.*

class RecipeListFragment: Fragment() {

    interface Callbacks {
        fun onRecipeSelected(recipeId: UUID)
    }

    private var callbacks: Callbacks? = null
    private lateinit var recipeRecyclerView: RecyclerView
    private var adapter: RecipeAdapter? = RecipeAdapter(emptyList())

    private val recipeListViewModel: RecipeListViewModel by lazy {
        ViewModelProviders.of(this).get(RecipeListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.fragment_recipe_list, menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)

        recipeRecyclerView = view.findViewById(R.id.recipe_recycler_view) as RecyclerView
        recipeRecyclerView.layoutManager = GridLayoutManager(context, 2)
        recipeRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeListViewModel.recipesLiveData.observe(
            viewLifecycleOwner,
            Observer { recipes ->
                recipes?.let {
                    updateUI(recipes)
                }
            }
        )
    }

    private fun updateUI(recipes: List<Recipe>) {
        adapter = RecipeAdapter(recipes)
        recipeRecyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): RecipeListFragment {
            return RecipeListFragment()
        }
    }

    private inner class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var recipe: Recipe

        val nameTextView = itemView.findViewById(R.id.recipe_name_text_view) as TextView
        val imageView = itemView.findViewById(R.id.recipe_image_view) as ImageView

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            nameTextView.text = this.recipe.name
            Glide.with(context!!).load(this.recipe.picture).into(imageView)
        }

        override fun onClick(v: View?) {
            callbacks?.onRecipeSelected(recipe.recipeId)
        }
    }

    private inner class RecipeAdapter(var recipes: List<Recipe>) : RecyclerView.Adapter<RecipeViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
            val view = layoutInflater.inflate(R.layout.lits_item_recipe, parent, false)
            return RecipeViewHolder(view)
        }

        override fun getItemCount(): Int {
            return recipes.size
        }

        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
            val recipe = recipes[position]
            holder.bind(recipe)
        }
    }
}