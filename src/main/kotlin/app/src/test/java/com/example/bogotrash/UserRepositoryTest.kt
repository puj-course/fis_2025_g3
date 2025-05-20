package com.example.bogotrash

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.*
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet


class UserRepositoryTest {

    @Test
    fun loginUserSuccess() {
        val conn = mock(Connection::class.java)
        val stmt = mock(PreparedStatement::class.java)
        val rs = mock(ResultSet::class.java)

        `when`(conn.prepareStatement(anyString())).thenReturn(stmt)
        `when`(stmt.executeQuery()).thenReturn(rs)
        `when`(rs.next()).thenReturn(true)

        val result = loginUser("test@email.com", "1234", conn)
        assertTrue(result)
    }

    @Test
    fun loginUserFails() {
        val conn = mock(Connection::class.java)
        val stmt = mock(PreparedStatement::class.java)
        val rs = mock(ResultSet::class.java)

        `when`(conn.prepareStatement(anyString())).thenReturn(stmt)
        `when`(stmt.executeQuery()).thenReturn(rs)
        `when`(rs.next()).thenReturn(false)

        val result = loginUser("bad@email.com", "wrong", conn)
        assertFalse(result)
    }

    private fun loginUser(email: String, password: String, conn: Connection): Boolean {
        val stmt = conn.prepareStatement("SELECT * FROM Users WHERE email = ? AND password_hash = ?")
        stmt.setString(1, email)
        stmt.setString(2, password)
        val rs = stmt.executeQuery()
        val result = rs.next()
        rs.close()
        stmt.close()
        return result
    }
}
