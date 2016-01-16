
package cardgame;

import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class CardDeck {//is a Queue
    
    Card[] deck;
    int front;
    int rear;
    
    public CardDeck(Card[] deck, boolean shuffle){
        if(shuffle){
            this.deck = shuffleDeck(deck);
        }
        else{
            this.deck = deck;
        }
        front = 0;
        rear = deck.length-1;
    }          
    
    public boolean isEmpty(){
        if(front > rear){
            return true;
        }
        else{
            return false;
        }
    }
    
    public Card dequeue(){
        if(isEmpty()){
            throw new NoSuchElementException("The deck is empty. Check with 'isEmpty()' before 'dequeue()'");
        }
        else{
            return deck[front++];
        }
    }
    
    public int cardsRemaining(){
        return rear-front;
    }
    
    public void changeScreenSize(int newWidth, int newHeight){
        Card.changeScreenSize(newWidth, newHeight);
        for(int n = 0; n< deck.length; n++){
            deck[n].returnDefaultSize();
        }
    }
    
    private Card[] shuffleDeck(Card[] deck){
        Card[] shuffled = new Card[deck.length];
        Random rand = new Random();
        int End = deck.length, num;
        
        for(int n = 0; n < shuffled.length; n++ ){
            num = rand.nextInt(End);
            shuffled[n] = deck[num];
            for(int i = num; i < End-1; i++){
                deck[i] = deck[i+1];
            }
            End--;
        }
        return shuffled;
    }
    
    public void saveQueue(PrintWriter save){
        for(int n = 0; n< deck.length; n++){
            save.println(deck[n].getX());
            save.println(deck[n].getY());
            save.println(deck[n].getRotation());
            save.println(deck[n].getImageNumber());
            save.println(deck[n].isInPlay());
            save.println(deck[n].isRotated());
            save.println(deck[n].isDarkSide());
            save.println(deck[n].isInHand());
            save.println(deck[n].isHandHidden());
            save.println(deck[n].isDiscarded());
        }
        save.println(front);
        save.println(rear);
    }
    
    public void loadQueue(Scanner load, BufferedImage[] deckImages){
        for(int n = 0; n< deck.length; n++){
            deck[n].setX(load.nextInt());
            deck[n].setY(load.nextInt());
            deck[n].setRotationAngle(load.nextInt());
            deck[n].setImageNumber(load.nextInt());
            deck[n].changeInPlay(Boolean.valueOf(load.next()));
            deck[n].rotateCard(Boolean.valueOf(load.next()));
            deck[n].changeDarkSide(Boolean.valueOf(load.next()));
            deck[n].changeHandStatus(Boolean.valueOf(load.next()));
            deck[n].changeHiddenHand(Boolean.valueOf(load.next()));
            deck[n].changeDiscarded(Boolean.valueOf(load.next()));

            deck[n].changeImage(deckImages[deck[n].getImageNumber()]);
            deck[n].returnDefaultWidth();
            deck[n].trueDefaultHeight();
        }

            front = load.nextInt();
            rear = load.nextInt();
    }
    
}
