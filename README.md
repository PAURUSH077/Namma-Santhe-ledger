Namma Santhe Ledger 📒
Android App Development using GenAI (Finance Domain)

A lightweight Android application designed for small village market vendors (Santhe sellers) to digitally manage customer credit (Udari), payments, and pending dues efficiently.

The app replaces traditional paper-based khata books with a fast, easy-to-use digital ledger system optimized for rural micro-businesses.

🚨 Problem Statement

Weekly village markets (Santhe) are the backbone of rural retail economies. Small vendors selling vegetables, snacks, bangles, flowers, and household items often provide goods on credit (Udari) to regular customers.

Since most vendors maintain records manually in pocket diaries:

Transactions get forgotten
Pending dues become difficult to track
Customers sometimes avoid repayments
Vendors suffer financial losses

There is a strong need for a simple digital bookkeeping solution designed specifically for rural vendors.

🌟 Vision

Namma Santhe Ledger acts as a Simplified Digital Khata for small vendors.

The application is designed for:

⚡ Fast transaction entry (within 5 seconds)
📱 Easy mobile usage
📊 Clear financial summaries
💬 Simple customer reminders
🧾 Better debt tracking

The goal is to help vendors understand:

Daily sales
Money received
Pending customer dues
Overall business performance
📱 App Features
👥 Customer Management
Add new customers
Search customers instantly
Store phone numbers
View customer-wise pending balances
📒 Udari (Credit) Entry

Quick 2-step transaction flow:

Select Customer
Enter Amount
💰 Payment Tracking
Record repayments from customers
Automatically reduce pending balance
📊 Daily Summary Dashboard

Displays:

Today's total sales
Total payment received
Pending dues
Net balance
💬 WhatsApp Reminder Integration

Send payment reminder messages directly to customers through WhatsApp.

Example:

Namaskara 🙏

Your pending due is ₹400.
Please make the payment.

Thank you!
🔍 Smart Search

Search ledger records by customer name instantly.

🔢 Large Numeric Keypad UI

Optimized for:

Fast usage
Outdoor market conditions
Easy touch interaction
🛠️ Updated Tech Stack (Android Native)
Component	Technology
Language	Kotlin
UI	Jetpack Compose
Architecture	MVVM
Database	Room Database
State Management	ViewModel + StateFlow
Dependency Injection	Hilt
Navigation	Navigation Compose
Storage	Room Persistent Storage
Messaging	WhatsApp Intent API
Minimum SDK	Android 8.0+
🧱 Recommended Project Structure

🧠 App Workflow
📌 Home Screen

Shows:

Total Outstanding Amount
Customer List
Search Bar
➕ Add Transaction

Flow:

Select Customer
Enter Amount
Choose:
Udari
Payment
📊 Summary Screen

Displays:

Today's Sales
Received Payments
Pending Dues
💬 Reminder Screen
Send WhatsApp reminders
Auto-fill customer due amount
🗄️ Database Design (Room DB)
Customer Table
@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val phone: String
)
Transaction Table
@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val customerId: Int,
    val amount: Double,
    val type: String, // UDARI or PAYMENT
    val date: Long
)
🧮 Dynamic Balance Calculation

The ViewModel dynamically calculates balances:

fun calculateBalance(transactions: List<TransactionEntity>): Double {
    return transactions.sumOf {
        if (it.type == "UDARI") it.amount
        else -it.amount
    }
}
📲 WhatsApp Reminder Intent
val message = "Your pending due is ₹400"
val url = "https://wa.me/91$phoneNumber?text=${Uri.encode(message)}"

val intent = Intent(Intent.ACTION_VIEW)
intent.data = Uri.parse(url)
startActivity(intent)
🎯 Impact Goals
💡 Financial Inclusion

Helping rural vendors adopt digital bookkeeping systems.

📉 Reduce Bad Debts

Improves repayment tracking and customer accountability.

🏪 Micro-Business Stability

Provides better control over daily cash flow.

🇮🇳 Digital India Initiative

Supports digitization of the unorganized retail sector.

✅ Success Criteria

The application will be considered successful if it:

✔ Shows total outstanding dues on the home screen
✔ Allows customer search by name
✔ Supports 2-step transaction entry
✔ Stores transactions persistently using Room DB
✔ Calculates balances dynamically
✔ Sends WhatsApp reminders successfully
🚀 Future Enhancements
Multi-language UI (Kannada/Hindi/English)
Voice-based transaction entry
Cloud backup using Firebase
QR code payment support
AI-based spending insights
Offline-first synchronization
👨‍💻 Developed By

Paurush Mishra
Android App Development using GenAI Internship Project
