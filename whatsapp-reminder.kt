val message = "Your pending due is ₹400"
val url = "https://wa.me/91$phoneNumber?text=${Uri.encode(message)}"

val intent = Intent(Intent.ACTION_VIEW)
intent.data = Uri.parse(url)
startActivity(intent)
