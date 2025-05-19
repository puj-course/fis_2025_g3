package com.example.bogotrash

import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet


class RewardLogicTest {

    @Test
    fun rewardsSmsParticipantsSucceeds() {
        val conn = mock(Connection::class.java)
        val stmt = mock(PreparedStatement::class.java)
        val rs = mock(ResultSet::class.java)

        `when`(conn.prepareStatement(anyString())).thenReturn(stmt)
        `when`(stmt.executeQuery()).thenReturn(rs)
        `when`(rs.next()).thenReturn(true)
        `when`(rs.getInt("id")).thenReturn(1)

        val result = rewardSmsParticipants(conn, "user@bogotrash.com", "321654987")
        assertTrue(result)
    }

    private fun rewardSmsParticipants(conn: Connection, senderEmail: String, recyclerPhone: String): Boolean {
        val stmtUser = conn.prepareStatement("SELECT id FROM Users WHERE email = ?")
        stmtUser.setString(1, senderEmail)
        val rsUser = stmtUser.executeQuery()
        if (!rsUser.next()) return false
        val senderId = rsUser.getInt("id")
        rsUser.close()
        stmtUser.close()

        val stmtRecycler = conn.prepareStatement("""
            SELECT u.id FROM Users u JOIN Recyclers r ON u.id = r.user_id WHERE r.phone = ?
        """.trimIndent())
        stmtRecycler.setString(1, recyclerPhone)
        val rsRecycler = stmtRecycler.executeQuery()
        if (!rsRecycler.next()) return false
        val recyclerId = rsRecycler.getInt("id")
        rsRecycler.close()
        stmtRecycler.close()

        val stmtUpdateUser = conn.prepareStatement("UPDATE Users SET total_points = total_points + 15 WHERE id = ?")
        stmtUpdateUser.setInt(1, senderId)
        stmtUpdateUser.executeUpdate()
        stmtUpdateUser.close()

        val stmtUpdateRecycler = conn.prepareStatement("UPDATE Users SET total_points = total_points + 20 WHERE id = ?")
        stmtUpdateRecycler.setInt(1, recyclerId)
        stmtUpdateRecycler.executeUpdate()
        stmtUpdateRecycler.close()

        return true
    }
}
