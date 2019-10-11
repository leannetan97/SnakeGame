/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maintenanceassignment;

/**
 *
 * @author user
 */
public class display {
    
    public void printWelcome(){
        System.out.println("\tWelcome to the mini Snake game.\n(press any key to continue)");
        //getch()
    }
    
    public void printInstruction(){
        System.out.println("Game instructions:\nYou will be provided foods at the several coordinates of the screen which you have to eat.\nEverytime you eat a food the length of the snake will be increased by 1 element and thus the score.\nHere you are provided with three lives. Your life will decrease as you hit the wall or snake's body.\nYou can pause the game in its middle by pressing any key. To continue the paused game press any other key once again\nIf you want to exit press esc.");
        System.out.println("\nPress any key to play game...");
        //getch
        //if esc, exit
    }
    
}
