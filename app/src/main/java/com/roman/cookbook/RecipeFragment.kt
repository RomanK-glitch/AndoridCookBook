package com.roman.cookbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.roman.cookbook.model.Ingredient
import com.roman.cookbook.model.PreparationStep
import com.roman.cookbook.model.RecipeIngredientsSteps
import kotlinx.android.synthetic.main.fragment_recipe.*
import java.util.*

private const val ARG_RECIPE_ID = "recipe_id"

class RecipeFragment: Fragment() {
    private lateinit var recipe: RecipeIngredientsSteps
    private lateinit var ingredientsRecyclerView: RecyclerView
    private lateinit var preparationRecyclerView: RecyclerView
    private lateinit var nameTextView: TextView
    private lateinit var additionalInfoTextView: TextView
    private lateinit var recipeImageView: ImageView
    private val recipeDetailViewModel: RecipeDetailViewModel by lazy {
        ViewModelProviders.of(this).get(RecipeDetailViewModel::class.java)
    }

    private var ingredientAdapter: IngredientAdapter? = null
    private var preparationAdapter: PreparationAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipe = RecipeIngredientsSteps()
        val recipeId : UUID = arguments?.getSerializable(ARG_RECIPE_ID) as UUID
        recipeDetailViewModel.loadRecipe(recipeId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recipe, container, false)

        ingredientsRecyclerView = view.findViewById(R.id.ingredients_recycler_view) as RecyclerView
        ingredientsRecyclerView.addItemDecoration(DividerItemDecoration(ingredientsRecyclerView.context, DividerItemDecoration.VERTICAL))
        ingredientsRecyclerView.layoutManager = LinearLayoutManager(context)

        preparationRecyclerView = view.findViewById(R.id.preparation_recycler_view) as RecyclerView
        preparationRecyclerView.layoutManager = LinearLayoutManager(context)

        nameTextView = view.findViewById(R.id.recipe_name_text_view) as TextView
        additionalInfoTextView = view.findViewById(R.id.additional_info_text_view) as TextView
        recipeImageView = view.findViewById(R.id.recipe_image_view) as ImageView

        updateUI()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeDetailViewModel.recipeLiveData.observe(
            viewLifecycleOwner,
            Observer { recipe ->
                recipe?.let {
                    this.recipe = recipe
                    updateUI()
                }
            }
        )
    }

    private fun updateUI() {
        val ingredients = recipe.ingredientsList
        ingredientAdapter = IngredientAdapter(ingredients)
        ingredientsRecyclerView.adapter = ingredientAdapter
        val preparationSteps = recipe.prepStepsList
        preparationAdapter = PreparationAdapter(preparationSteps)
        preparationRecyclerView.adapter = preparationAdapter
        nameTextView.text = recipe.recipe.name
        additionalInfoTextView.text = recipe.recipe.additionalIfo
        Glide.with(context!!).load(recipe.recipe.picture).into(recipeImageView)
    }

    companion object {
        fun newInstance(recipeId: UUID): RecipeFragment {
            val args = Bundle().apply {
                putSerializable(ARG_RECIPE_ID, recipeId)
            }
            return RecipeFragment().apply {
                arguments = args
            }
        }
    }

    private inner class IngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var ingredient: Ingredient

        private val ingredientNameTextView: TextView = itemView.findViewById(R.id.ingredient_name_text_view)
        private val ingredientAmountTextView: TextView = itemView.findViewById(R.id.ingredient_amount_text_view)

        fun bind(ingredient: Ingredient) {
            this.ingredient = ingredient
            ingredientNameTextView.text = this.ingredient.productName
            ingredientAmountTextView.text = this.ingredient.amount
        }
    }

    private inner class IngredientAdapter(var ingredients: List<Ingredient>) : RecyclerView.Adapter<IngredientViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
            val view = layoutInflater.inflate(R.layout.list_item_ingredient, parent, false)
            return IngredientViewHolder(view)
        }

        override fun getItemCount() = ingredients.size

        override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
            val ingredient = ingredients[position]
            holder.bind(ingredient)
        }
    }

    private inner class PreparationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var preparationStep: PreparationStep

        private val preparationStepNumberTextView: TextView = itemView.findViewById(R.id.preparation_step_number_text_view)
        private val preparationStepDescriptionTextView: TextView = itemView.findViewById(R.id.preparation_step_description_text_view)

        fun bind(preparationStep: PreparationStep) {
            this.preparationStep = preparationStep
            preparationStepNumberTextView.text = preparationStep.prepStep.toString()
            preparationStepDescriptionTextView.text = preparationStep.description
        }
    }

    private inner class PreparationAdapter(var preparationSteps: List<PreparationStep>) : RecyclerView.Adapter<PreparationViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreparationViewHolder {
            val view = layoutInflater.inflate(R.layout.list_item_preparation_step, parent, false)
            return PreparationViewHolder(view)
        }

        override fun getItemCount() = preparationSteps.size

        override fun onBindViewHolder(holder: PreparationViewHolder, position: Int) {
            val preparationStep = preparationSteps[position]
            holder.bind(preparationStep)
        }
    }
}