package com.example.expensetracker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expensetracker.databinding.ActivityUpdateExpensesBinding

// Activity for updating expenses
class UpdateExpenses : AppCompatActivity() {

    // View binding instance for the activity
    private lateinit var binding: ActivityUpdateExpensesBinding

    // Database helper instance
    private lateinit var db: ExpensesDatabaseHelper

    // ID of the expenses being updated
    private var expensesId: Int = -1

    // Called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using view binding
        binding = ActivityUpdateExpensesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize database helper
        db = ExpensesDatabaseHelper(this)

        // Retrieve expenses ID from intent extras
        expensesId = intent.getIntExtra("expenses_id", -1)

        // If expenses ID is not valid, finish the activity
        if (expensesId == -1) {
            finish()
            return
        }

        // Retrieve expenses data by ID from the database
        val expenses = db.getExpensesByID(expensesId)

        // Populate EditText fields with expenses data
        binding.updatetitleEditText.setText(expenses.title)
        binding.updatecontentEditText.setText(expenses.content)

        // Set click listener for save button to update expenses data
        binding.updatesaveButton.setOnClickListener{
            val newTitle = binding.updatetitleEditText.text.toString()
            val newContent = binding.updatecontentEditText.text.toString()

            // Create Expenses object with updated data
            val updateExpenses = Expenses(expensesId, newTitle, newContent)

            // Update expenses data in the database
            db.updateExpenses(updateExpenses)

            // Finish the activity and show a toast message indicating successful saving
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
