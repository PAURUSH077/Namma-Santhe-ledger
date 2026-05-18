fun calculateBalance(transactions: List<TransactionEntity>): Double {
    return transactions.sumOf {
        if (it.type == "UDARI") it.amount
        else -it.amount
    }
}
