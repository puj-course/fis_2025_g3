package com.example.bogotrash.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.core.widget.addTextChangedListener
import com.example.bogotrash.databinding.ActivityRecyclerConnectionBinding
import com.example.bogotrash.model.Recycler
import com.example.bogotrash.repository.DatabaseConnection
import com.example.bogotrash.view.adapter.RecyclerAdapter
import java.sql.Connection
import kotlin.concurrent.thread

class RecyclerConnectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerConnectionBinding
    private var fullRecyclerList = listOf<Recycler>()
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecyclerAdapter(listOf())
        binding.recyclerViewRecyclers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRecyclers.adapter = adapter

        binding.searchViewRecyclers.queryHint = "Buscar reciclador"
        binding.searchViewRecyclers.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterRecyclerList(newText.orEmpty())
                return true
            }
        })

        fetchRecyclersFromDB()
    }

    private fun fetchRecyclersFromDB() {
        thread {
            try {
                val conn: Connection? = DatabaseConnection.getConnection()
                val recyclers = mutableListOf<Recycler>()
                conn?.use { connection ->
                    val stmt = connection.prepareStatement("""
                        SELECT u.name, r.phone, r.address, r.zone
                        FROM Users u
                        JOIN Recyclers r ON u.id = r.user_id
                    """.trimIndent())
                    val rs = stmt.executeQuery()
                    var id = 1
                    while (rs.next()) {
                        recyclers.add(
                            Recycler(
                                id++,
                                rs.getString("name"),
                                rs.getString("phone"),
                                rs.getString("address"),
                                rs.getString("zone")
                            )
                        )
                    }
                    rs.close()
                    stmt.close()
                }
                runOnUiThread {
                    fullRecyclerList = recyclers
                    adapter.updateList(recyclers)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Error al obtener recicladores: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun filterRecyclerList(query: String) {
        val filtered = fullRecyclerList.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.zone.contains(query, ignoreCase = true)
        }
        adapter.updateList(filtered)
    }
}

