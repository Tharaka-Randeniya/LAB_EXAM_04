package com.example.expensetracker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expensetracker.databinding.ActivityAddexpensesBinding


//It is a feature that makes the interaction of code with views easy.
// It generates a binding class for each XML layout. The binding class contains a reference to
// all views that have an id in the layout

// Import required classes and libraries
class AddExpenses : AppCompatActivity() {
    private lateinit var binding: ActivityAddexpensesBinding // View binding for the activity
    private lateinit var db: ExpensesDatabaseHelper // Database helper instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up the activity layout
        try {
            binding = ActivityAddexpensesBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Initialize database helper
            db = ExpensesDatabaseHelper(this)

            // Set click listener for save button
            //Using binding, you directly access the views defined in the layout XML without the need for findViewById
            binding.saveButton.setOnClickListener {
                // Retrieve input data from EditText fields
                val title = binding.titleEditText.text.toString()
                val content = binding.contentEditText.text.toString()

                // Create Expenses object with input data
                val expenses = Expenses(0, title, content)

                // Insert expenses data into the database
                db.insertExpenses(expenses)

                // Finish the activity after saving data
                finish()


                Toast.makeText(this, "Expenses Saved", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {

            e.printStackTrace()

            Toast.makeText(this, "Error loading AddExpenses activity", Toast.LENGTH_SHORT).show()

            // Close the activity if an error occurs
            finish()
        }
    }
}
