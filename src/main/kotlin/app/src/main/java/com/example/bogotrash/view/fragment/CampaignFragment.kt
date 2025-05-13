package com.example.bogotrash.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.cardview.widget.CardView
import com.example.bogotrash.R
import com.example.bogotrash.core.SessionManager
import com.example.bogotrash.model.Campaign
import com.example.bogotrash.repository.DatabaseConnection
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import kotlin.concurrent.thread

class CampaignFragment : Fragment() {

    private lateinit var noCampaignsMessage: TextView
    private lateinit var campaignScroll: ScrollView
    private lateinit var campaignContainer: LinearLayout
    private val session get() = SessionManager.instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_campaign, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)

        noCampaignsMessage = view.findViewById(R.id.noCampaignsMessage)
        campaignScroll      = view.findViewById(R.id.campaignScroll)
        campaignContainer   = view.findViewById(R.id.campaignContainer)

        loadActiveCampaigns()
    }

    private fun loadActiveCampaigns() {
        thread {
            val email = session.getUserEmail()
            if (email.isNullOrEmpty()) {
                activity?.runOnUiThread {
                    Toast.makeText(context, "No hay sesión activa", Toast.LENGTH_SHORT).show()
                }
                return@thread
            }

            val conn: Connection? = DatabaseConnection.getConnection()
            conn?.use { connection ->
                // 1) Recuperar userId
                val stmtUser = connection.prepareStatement("""
                    SELECT id FROM Users WHERE email = ?
                """.trimIndent()).apply {
                    setString(1, email)
                }
                val rsUser = stmtUser.executeQuery()
                if (!rsUser.next()) {
                    activity?.runOnUiThread {
                        Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                    return@thread
                }
                val userId = rsUser.getInt("id")
                rsUser.close()
                stmtUser.close()

                // 2) Cargar todas las campañas activas
                val stmtCamp = connection.prepareStatement("""
                    SELECT * FROM Campaigns
                    WHERE CURDATE() BETWEEN start_date AND end_date
                """.trimIndent())
                val rsCamp = stmtCamp.executeQuery()

                val campaigns = mutableListOf<Campaign>()
                while (rsCamp.next()) {
                    campaigns += Campaign(
                        id          = rsCamp.getInt("id"),
                        title       = rsCamp.getString("title"),
                        description = rsCamp.getString("description"),
                        startDate   = rsCamp.getDate("start_date"),
                        endDate     = rsCamp.getDate("end_date")
                    )
                }
                rsCamp.close()
                stmtCamp.close()

                activity?.runOnUiThread {
                    if (campaigns.isEmpty()) {
                        noCampaignsMessage.visibility = View.VISIBLE
                        campaignScroll.visibility    = View.GONE
                    } else {
                        noCampaignsMessage.visibility = View.GONE
                        campaignScroll.visibility    = View.VISIBLE
                        displayCampaigns(campaigns, userId)
                    }
                }
            }
        }
    }

    private fun displayCampaigns(campaigns: List<Campaign>, userId: Int) {
        campaignContainer.removeAllViews()
        val inflater = layoutInflater

        for (campaign in campaigns) {
            val cardView = inflater.inflate(R.layout.card_campaign, campaignContainer, false) as CardView

            val titleTv = cardView.findViewById<TextView>(R.id.campaignTitle)
            val descTv  = cardView.findViewById<TextView>(R.id.campaignDescription)
            val btn     = cardView.findViewById<Button>(R.id.participateButton)

            titleTv.text = campaign.title
            descTv.text  = campaign.description

            btn.setOnClickListener {
                participateInCampaign(userId, campaign.id)
            }

            campaignContainer.addView(cardView)
        }
    }

    private fun participateInCampaign(userId: Int, campaignId: Int) {
        thread {
            val conn: Connection? = DatabaseConnection.getConnection()
            conn?.use { connection ->
                // Verificar participación previa
                val stmtCheck = connection.prepareStatement("""
                    SELECT COUNT(*) FROM Participations 
                    WHERE user_id = ? AND campaign_id = ?
                """.trimIndent()).apply {
                    setInt(1, userId)
                    setInt(2, campaignId)
                }
                val rsCheck = stmtCheck.executeQuery()
                val already = rsCheck.next() && rsCheck.getInt(1) > 0
                rsCheck.close()
                stmtCheck.close()

                if (already) {
                    activity?.runOnUiThread {
                        Toast.makeText(context, "Ya participaste en esta campaña.", Toast.LENGTH_SHORT).show()
                    }
                    return@thread
                }

                // Insertar participación
                connection.prepareStatement("""
                    INSERT INTO Participations (user_id, campaign_id) VALUES (?, ?)
                """.trimIndent()).apply {
                    setInt(1, userId)
                    setInt(2, campaignId)
                    executeUpdate()
                    close()
                }

                // Sumar 30 puntos
                connection.prepareStatement("""
                    UPDATE Users SET total_points = total_points + 30 WHERE id = ?
                """.trimIndent()).apply {
                    setInt(1, userId)
                    executeUpdate()
                    close()
                }

                activity?.runOnUiThread {
                    Toast.makeText(context, "Participación registrada. ¡30 puntos sumados!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}





