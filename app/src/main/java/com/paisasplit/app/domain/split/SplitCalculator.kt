package com.paisasplit.app.domain.split

import java.math.BigDecimal
import java.math.RoundingMode

class SplitCalculator {
    fun calculateEqualSplit(
        totalAmount: BigDecimal,
        memberCount: Int,
        payerIncluded: Boolean,
        roundingMode: RoundingMode = RoundingMode.HALF_UP
    ): List<BigDecimal> {
        val divisor = if (payerIncluded) memberCount else memberCount - 1
        if (divisor <= 0) return List(memberCount) { BigDecimal.ZERO }
        val baseAmount = totalAmount.divide(BigDecimal(divisor), 2, roundingMode)
        val remainder = totalAmount.subtract(baseAmount.multiply(BigDecimal(divisor)))
        return distributeWithRemainder(baseAmount, remainder, memberCount)
    }

    private fun distributeWithRemainder(
        baseAmount: BigDecimal,
        remainder: BigDecimal,
        count: Int
    ): List<BigDecimal> {
        if (count <= 0) return emptyList()
        val paise = remainder.multiply(BigDecimal(100)).toInt()
        return List(count) { idx ->
            val extraPaise = if (idx < paise) BigDecimal("0.01") else BigDecimal.ZERO
            baseAmount.add(extraPaise)
        }
    }
}


