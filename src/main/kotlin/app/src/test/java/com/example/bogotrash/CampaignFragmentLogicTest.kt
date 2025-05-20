package com.example.bogotrash

import org.junit.Test
import org.mockito.Mockito.*
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import kotlin.test.assertTrue

class CampaignFragmentLogicTest {

    @Test
    fun `participation is inserted when not already registered`() {
        val conn = mock(Connection::class.java)
        val stmtCheck = mock(PreparedStatement::class.java)
        val rsCheck = mock(ResultSet::class.java)

        val stmtInsert = mock(PreparedStatement::class.java)
        val stmtUpdate = mock(PreparedStatement::class.java)

        `when`(conn.prepareStatement(startsWith("SELECT COUNT"))).thenReturn(stmtCheck)
        `when`(stmtCheck.executeQuery()).thenReturn(rsCheck)
        `when`(rsCheck.next()).thenReturn(true)
        `when`(rsCheck.getInt(1)).thenReturn(0)

        `when`(conn.prepareStatement(startsWith("INSERT INTO"))).thenReturn(stmtInsert)
        `when`(conn.prepareStatement(startsWith("UPDATE"))).thenReturn(stmtUpdate)

        val userId = 5
        val campaignId = 10

        val success = registerCampaignParticipation(conn, userId, campaignId)
        assertTrue(success)
    }

    private fun registerCampaignParticipation(conn: Connection, userId: Int, campaignId: Int): Boolean {
        val stmtCheck = conn.prepareStatement("""
            SELECT COUNT(*) FROM Participations 
            WHERE user_id = ? AND campaign_id = ?
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
