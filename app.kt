package com.example.nammasantheledger

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NammaSantheLedgerApp()
        }
    }
}

data class Customer(
    val id: Int,
    val name: String,
    val phone: String
)

data class Transaction(
    val customerId: Int,
    val amount: Double,
    val type: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NammaSantheLedgerApp() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var customers by remember {
        mutableStateOf(
            listOf(
                Customer(1, "Lakshmi Bai", "9876543210"),
                Customer(2, "Raju Gowda", "9845123456"),
                Customer(3, "Suma Devi", "9731234567")
            )
        )
    }

    var transactions by remember {
        mutableStateOf(
            listOf(
                Transaction(1, 150.0, "UDARI"),
                Transaction(2, 300.0, "UDARI"),
                Transaction(1, 100.0, "PAYMENT")
            )
        )
    }

    var searchText by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    var selectedCustomer by remember { mutableStateOf<Customer?>(null) }

    var amount by remember { mutableStateOf("") }

    var transactionType by remember { mutableStateOf("UDARI") }

    fun getBalance(customerId: Int): Double {

        return transactions
            .filter { it.customerId == customerId }
            .sumOf {
                if (it.type == "UDARI") it.amount
                else -it.amount
            }
    }

    val totalOutstanding =
        customers.sumOf {
            val bal = getBalance(it.id)
            if (bal > 0) bal else 0.0
        }

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    showDialog = true
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFFF8F0))
                .padding(16.dp)
        ) {

            Text(
                text = "ನಮ್ಮ ಸಂತೆ Ledger",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFD85A30)
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Total Outstanding",
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "₹${totalOutstanding.toInt()}",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Customers: ${customers.size}",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                label = {
                    Text("Search Customer")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {

                items(
                    customers.filter {
                        it.name.contains(searchText, true)
                    }
                ) { customer ->

                    val balance = getBalance(customer.id)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable {

                                if (balance > 0) {

                                    val message =
                                        "Namaskara ${customer.name} 🙏\nYour pending due is ₹${balance.toInt()}"

                                    val url =
                                        "https://wa.me/91${customer.phone}?text=${
                                            Uri.encode(message)
                                        }"

                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(url)
                                    )

                                    context.startActivity(intent)
                                }
                            }
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column {

                                Text(
                                    text = customer.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = customer.phone
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.End
                            ) {

                                Text(
                                    text = "₹${balance.toInt()}",
                                    color =
                                    if (balance > 0)
                                        Color.Red
                                    else
                                        Color(0xFF1D9E75),

                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text =
                                    if (balance > 0)
                                        "Due"
                                    else
                                        "Clear"
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showDialog) {

            AlertDialog(

                onDismissRequest = {
                    showDialog = false
                },

                title = {
                    Text("Add Transaction")
                },

                text = {

                    Column {

                        DropdownMenuBox(
                            customers = customers,
                            selectedCustomer = selectedCustomer,
                            onCustomerSelected = {
                                selectedCustomer = it
                            }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = amount,
                            onValueChange = {
                                amount = it
                            },
                            label = {
                                Text("Amount")
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row {

                            Button(
                                onClick = {
                                    transactionType = "UDARI"
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                    if (transactionType == "UDARI")
                                        Color.Red
                                    else
                                        Color.Gray
                                )
                            ) {
                                Text("Udari")
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    transactionType = "PAYMENT"
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                    if (transactionType == "PAYMENT")
                                        Color(0xFF1D9E75)
                                    else
                                        Color.Gray
                                )
                            ) {
                                Text("Payment")
                            }
                        }
                    }
                },

                confirmButton = {

                    Button(
                        onClick = {

                            if (
                                selectedCustomer != null &&
                                amount.isNotEmpty()
                            ) {

                                transactions =
                                    transactions + Transaction(
                                        customerId = selectedCustomer!!.id,
                                        amount = amount.toDouble(),
                                        type = transactionType
                                    )

                                amount = ""
                                showDialog = false
                            }
                        }
                    ) {
                        Text("Save")
                    }
                }
            )
        }
    }
}

@Composable
fun DropdownMenuBox(
    customers: List<Customer>,
    selectedCustomer: Customer?,
    onCustomerSelected: (Customer) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Box {

        Button(
            onClick = {
                expanded = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text =
                selectedCustomer?.name
                    ?: "Select Customer"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            customers.forEach { customer ->

                DropdownMenuItem(
                    text = {
                        Text(customer.name)
                    },
                    onClick = {

                        onCustomerSelected(customer)
                        expanded = false
                    }
                )
            }
        }
    }
}
