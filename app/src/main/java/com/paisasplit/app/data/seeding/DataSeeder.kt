package com.paisasplit.app.data.seeding

import com.paisasplit.app.data.database.entities.Account
import com.paisasplit.app.data.database.entities.AccountType
import com.paisasplit.app.data.database.entities.Group
import com.paisasplit.app.data.database.entities.Member
import com.paisasplit.app.data.repository.PaisaRepository
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSeeder @Inject constructor(
    private val repository: PaisaRepository
) {
    suspend fun seedIfEmpty() {
        val accounts = repository.getAllAccounts()
        val groups = repository.getAllGroups()
        if (accounts.isEmpty()) {
            repository.insertAccount(
                Account(
                    name = "Cash",
                    type = AccountType.CASH,
                    openingBalance = BigDecimal("1000.00"),
                    currentBalance = BigDecimal("1000.00"),
                    icon = "cash",
                    color = "#0F766E"
                )
            )
        }

        if (groups.isEmpty()) {
            val group = Group(name = "Friends")
            repository.insertGroup(group)
            val members = listOf(
                Member(groupId = group.id, displayName = "You", isSelf = true, avatarColor = "#0F766E"),
                Member(groupId = group.id, displayName = "Alice", avatarColor = "#2563EB"),
                Member(groupId = group.id, displayName = "Bob", avatarColor = "#F59E0B")
            )
            repository.insertMembers(members)
        }
    }
}




