/*
Problem Set 1:
Simple BlackJack

Phillip Bell CSI-319
 */
    //calculate the score of the cards in the players hand
    fun calculateScore(cards: List<String>): Int{
        var score = 0
        var hasAce = false

        for (card in cards){
            //loop through all cards
            try {
                score += card.toInt() //Add to total score
            }
            catch(exception: NumberFormatException) {
                //String that isn't a number
                if(card == "A"){ //has an ace
                    hasAce = true
                } else { //has a J, Q, K
                    score += 10
                }
            }
        }

        //If there is an ace in the hand
        //either add 1 or 11 accordingly
        if (hasAce){
            score += if((score + 11) > 21){
                1
            } else {
                11
            }
        }

        return score
    }

    //print the players and the dealers totals
    fun printStatus(playerCards: List<String>, dealerCards: List<String>){
        println() //blank line

        //print player's total, followed by their cards
        println("Player's Total is ${calculateScore(playerCards)}:")
        for(card in playerCards){
            print("$card, ")
        }
        println()

        //print dealer's total, followed by their cards
        println("Dealer's total is ${calculateScore(dealerCards)}:")
        for(card in dealerCards){
            print("$card, ")
        }
        println()
    }


    //main game loop
    fun main() {
        //create deck and hands
        val deck = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A")
        val playerCards = mutableListOf<String>()
        val dealerCards = mutableListOf<String>()

        deck.shuffle()

        //dealing cards
        println("Dealer draws first card.")
        dealerCards.add(deck.removeAt(0))

        println("Player receices two cards.")
        playerCards.add(deck.removeAt(0))
        playerCards.add(deck.removeAt(0))

        printStatus(playerCards, dealerCards)

        while(true) { //player decision loop
            println("Do you want to (H)it, (S)tay, or (Q)uit?")
            val selection: String? = readLine()
            val select = selection?.uppercase() ?: "Z"

            if(select == "H"){ //If player wants card
                playerCards.add(deck.last())
                deck.removeLast()           //gives the card
                printStatus(playerCards, dealerCards)

                if(calculateScore(playerCards) > 21){ //tests if busted
                    println("You busted! You lose!")
                    return
                }
            }else if(select == "S") { //stay
                break
            }else if(select == "Q") { //quit
                return
            }else { //incase something else was inputted
                println("That's not H, S, or Q")
            }
        }

        //Dealer finishes drawing
        while(calculateScore(dealerCards) < 17){
            dealerCards.add(deck.last())
            deck.removeLast()
        }
        printStatus(playerCards, dealerCards)

        if(calculateScore(dealerCards) > 21) { // dealer busted
            println("Dealer busts! You win!")
        } else if(calculateScore(dealerCards) > calculateScore(playerCards)) { //dealer higher score
            println("Dealer wins!")
        } else if(calculateScore(dealerCards) < calculateScore(playerCards)){ //player higher score
            println("You win!")
        } else { //must be a tie
            println("It's a tie!")
        }
        return

    }