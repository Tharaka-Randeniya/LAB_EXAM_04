package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    // View binding instance for the activity
    private lateinit var binding: ActivityMainBinding

    // Database helper instance
    private lateinit var db: ExpensesDatabaseHelper

    // Adapter instance for expenses RecyclerView
    //the class that bridges the gap between the client and the adaptee.
    // It implements the interface the client expects to use and delegates the calls to the adaptee
    private lateinit var expensesAdapter: ExpensesAdapter

    // Called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize database helper
        db = ExpensesDatabaseHelper(this)

        // Initialize expenses adapter with data from the database
        expensesAdapter = ExpensesAdapter(db.getAllExpenses(), this)

        // Set layout manager and adapter for expenses RecyclerView
        binding.expensesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.expensesRecyclerView.adapter = expensesAdapter

        // Set click listener for add button to navigate to AddExpenses activity
        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddExpenses::class.java)
            try {
                startActivity(intent)
            } catch (e: Exception) {
                // Log any errors that occur during activity start
                Log.e("MainActivity", "Error starting Add_Expenses activity: ${e.message}")
            }
        }
    }

    // Called when the activity is resumed
    override fun onResume() {
        super.onResume()
        // Refresh adapter data with latest expenses from the database
        expensesAdapter.refreshData(db.getAllExpenses())
    }
}
