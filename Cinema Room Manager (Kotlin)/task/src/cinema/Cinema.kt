fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow = readln().toInt()

    val cinema = Array(rows) { CharArray(seatsPerRow) { 'S' } }

    var currentIncome = 0
    var purchasedTickets = 0

    do {
        println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit")
        val option = readln().toInt()

        when (option) {
            1 -> showSeats(cinema)
            2 -> {
                val purchaseResult = buyTicket(cinema, rows, seatsPerRow)
                if (purchaseResult.success) {
                    currentIncome += purchaseResult.price
                    purchasedTickets++
                }
            }
            3 -> showStatistics(rows, seatsPerRow, purchasedTickets, currentIncome)
            0 -> println("Exiting...")
            else -> println("Invalid option. Please try again.")
        }
    } while (option != 0)
}

fun showSeats(cinema: Array<CharArray>) {
    println("\nCinema:")
    print("  ")
    for (i in cinema[0].indices) {
        print("${i + 1} ")
    }
    println()

    for (i in cinema.indices) {
        print("${i + 1} ")
        println(cinema[i].joinToString(" "))
    }
}

data class PurchaseResult(val success: Boolean, val price: Int)

fun buyTicket(cinema: Array<CharArray>, rows: Int, seatsPerRow: Int): PurchaseResult {
    var selectedRow: Int
    var selectedSeat: Int

    do {
        println("Enter a row number:")
        selectedRow = readln().toInt()

        if (selectedRow !in 1..rows) {
            println("Wrong input!")
            return PurchaseResult(false, 0)
        }

        println("Enter a seat number in that row:")
        selectedSeat = readln().toInt()

        if (selectedSeat !in 1..seatsPerRow) {
            println("Wrong input!")
            return PurchaseResult(false, 0)
        }

        if (cinema[selectedRow - 1][selectedSeat - 1] == 'B') {
            println("That ticket has already been purchased!")
            continue // Ask for input again
        }

        break // Exit the loop if a valid seat is selected

    } while (true)

    val totalSeats = rows * seatsPerRow
    val ticketPrice = if (totalSeats <= 60 || selectedRow <= 4) {
        10
    } else {
        8
    }

    println("Ticket price: $$ticketPrice")

    cinema[selectedRow - 1][selectedSeat - 1] = 'B'
    return PurchaseResult(true, ticketPrice)
}



fun showStatistics(rows: Int, seatsPerRow: Int, purchasedTickets: Int, currentIncome: Int) {
    val totalSeats = rows * seatsPerRow
    val totalIncome = if (totalSeats <= 60) {
        totalSeats * 10
    } else {
        val frontHalfRows = rows / 2
        val frontHalfSeats = frontHalfRows * seatsPerRow
        val backHalfSeats = totalSeats - frontHalfSeats
        frontHalfSeats * 10 + backHalfSeats * 8
    }

    val occupancy = purchasedTickets.toDouble() / totalSeats * 100

    println("\nNumber of purchased tickets: $purchasedTickets")
    println("Percentage: %.2f%%".format(occupancy))
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
}
