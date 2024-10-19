package com.example.expensetracker

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

// Adapter class to bind expenses data to RecyclerView
// RecyclerView= display a scrollable list or grid of items.
class ExpensesAdapter(private var expenses: List<Expenses>, context: Context) : RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder>() {

    // Database helper instance
    private val db: ExpensesDatabaseHelper = ExpensesDatabaseHelper(context)

    // ViewHolder class to hold references to views
    class ExpensesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    // Create new ViewHolders (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        // Inflate the item layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expenses_item, parent, false)
        // Return a new ViewHolder
        return ExpensesViewHolder(view)
    }

    // Return the size of the dataset (invoked by the layout manager)
    override fun getItemCount(): Int = expenses.size

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        // Get expense at the specified position
        val expense = expenses[position]
        // Bind expense data to the ViewHolder's views
        holder.titleTextView.text = expense.title
        holder.contentTextView.text = expense.content

        // Set click listener for update button
        holder.updateButton.setOnClickListener{
            // Create intent to navigate to UpdateExpenses activity with expense id
            val intent = Intent (holder.itemView.context, UpdateExpenses::class.java).apply {
                putExtra("expenses_id", expense.id)
            }
            // Start the activity
            holder.itemView.context.startActivity(intent)
        }

        // Set click listener for delete button
        holder.deleteButton.setOnClickListener{
            // Delete expense from database
            db.deleteExpenses(expense.id)
            // Refresh adapter data after deletion
            refreshData(db.getAllExpenses())
            // Show toast message indicating successful deletion
            Toast.makeText(holder.itemView.context, "Expense Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to refresh adapter data with new expenses list
    fun refreshData(newExpenses: List<Expenses>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }
}
