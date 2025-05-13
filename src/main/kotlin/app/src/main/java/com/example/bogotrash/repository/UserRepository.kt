package com.example.bogotrash.repository

import com.example.bogotrash.model.User
import com.example.bogotrash.model.Recycler
import java.sql.Connection
import java.sql.ResultSet

object UserRepository {

    fun registerUser(user: User) {
        val conn: Connection? = DatabaseConnection.getConnection()
        val query = "INSERT INTO Users (name, email, password_hash, total_points, role) VALUES (?, ?, ?, ?, ?)"

        conn?.use {
            val stmt = it.prepareStatement(query)
            stmt.setString(1, user.name)
            stmt.setString(2, user.email)
            stmt.setString(3, user.passwordHash)
            stmt.setInt(4, user.totalPoints)
            stmt.setString(5, "user")
            stmt.executeUpdate()
            stmt.close()
        }
    }

    fun registerRecycler(user: User, recycler: Recycler) {
        val conn: Connection? = DatabaseConnection.getConnection()

        val userQuery = "INSERT INTO Users (name, email, password_hash, total_points, role) VALUES (?, ?, ?, ?, ?)"
        val stmtUser = conn?.prepareStatement(userQuery, java.sql.Statement.RETURN_GENERATED_KEYS)
        stmtUser?.setString(1, user.name)
        stmtUser?.setString(2, user.email)
        stmtUser?.setString(3, user.passwordHash)
        stmtUser?.setInt(4, user.totalPoints)
        stmtUser?.setString(5, "recycler")
        stmtUser?.executeUpdate()

        val rs = stmtUser?.generatedKeys
        var userId = -1
        if (rs != null && rs.next()) {
            userId = rs.getInt(1)
        }
        stmtUser?.close()

        if (userId != -1) {
            val recyclerQuery = "INSERT INTO Recyclers (user_id, phone, address, zone) VALUES (?, ?, ?, ?)"
            val stmtRec = conn?.prepareStatement(recyclerQuery)
            stmtRec?.setInt(1, userId)
            stmtRec?.setString(2, recycler.phone)
            stmtRec?.setString(3, recycler.address)
            stmtRec?.setString(4, recycler.zone)
            stmtRec?.executeUpdate()
            stmtRec?.close()
        }

        conn?.close()
    }

    fun loginUser(email: String, passwordHash: String): Boolean {
        val conn: Connection? = DatabaseConnection.getConnection()
        val query = "SELECT * FROM Users WHERE email = ? AND password_hash = ?"

        conn?.use { connection ->
            val stmt = connection.prepareStatement(query)
            stmt.setString(1, email)
            stmt.setString(2, passwordHash)
            val rs: ResultSet = stmt.executeQuery()
            val success = rs.next()
            rs.close()
            stmt.close()
            return success
        }

        return false
    }

    fun isRecycler(email: String): Boolean {
        val conn = DatabaseConnection.getConnection()
        val query = """
            SELECT r.user_id
            FROM Users u
            JOIN Recyclers r ON u.id = r.user_id
            WHERE u.email = ?
        """.trimIndent()

        conn?.use {
            val stmt = it.prepareStatement(query)
            stmt.setString(1, email)
            val rs = stmt.executeQuery()
            val isRecycler = rs.next()
            rs.close()
            stmt.close()
            return isRecycler
        }

        return false
    }
}
