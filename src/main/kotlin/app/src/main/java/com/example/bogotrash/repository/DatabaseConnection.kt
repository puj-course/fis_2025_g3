package com.example.bogotrash.repository

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DatabaseConnection {

    private const val URL = "jdbc:mariadb://metro.proxy.rlwy.net:58943/railway"
    private const val USER = "root"
    private const val PASSWORD = "GmQHnjsCpsQSprnCxiSdPqpNIpXesnUz"

    init {
        try {
            Class.forName("com.mariadb.jdbc.Driver")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun getConnection(): Connection? {
        return try {
            DriverManager.getConnection(URL, USER, PASSWORD)
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }
}
