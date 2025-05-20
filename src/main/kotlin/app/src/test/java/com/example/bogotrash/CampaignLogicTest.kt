package com.example.bogotrash

import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class CampaignLogicTest {

    @Test
    fun testRegisterCampaignSuccess() {
        val conn = mock(Connection::class.java)
        val stmt = mock(PreparedStatement::class.java)
        val rs = mock(ResultSet::class.java)

        `when`(conn.prepareStatement(anyString())).thenReturn(stmt)
        `when`(stmt.executeQuery()).thenReturn(rs)
        `when`(rs.next()).thenReturn(false) // No ha participado

        val result = registerCampaignParticipation(conn, 1, 10)
        assertTrue(result)
    }

    private fun registerCampaignParticipation(conn: Connection, userId: Int, campaignId: Int): Boolean {
        val stmtCheck = conn.prepareStatement("""
            SELECT COUNT(*) FROM Participations WHERE user_id = ? AND campaign_id = ?
        """.trimIndent())
        stmtCheck.setInt(1, userId)
        stmtCheck.setInt(2, campaignId)
        val rsCheck = stmtCheck.executeQuery()
        val already = rsCheck.next() && rsCheck.getInt(1) > 0
        rsCheck.close()
        stmtCheck.close()

        if (already) return false

        conn.prepareStatement("INSERT INTO Participations (user_id, campaign_id) VALUES (?, ?)").apply {
            setInt(1, userId)
            setInt(2, campaignId)
            executeUpdate()
            close()
        }

        conn.prepareStatement("UPDATE Users SET total_points = total_points + 30 WHERE id = ?").apply {
            setInt(1, userId)
            executeUpdate()
            close()
        }

        return true
    }
}
