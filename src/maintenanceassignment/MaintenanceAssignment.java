/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maintenanceassignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Leanne
 */
class Coordinate{
    private int x;
    private int y;
    
    Coordinate(int x, int y){
        this.x = x;
        this.y = x;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}
//class Snake{ 
//    Coordinate
//}


public class MaintenanceAssignment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    
    Record user = new Record();
    user.writeToFile();
    user.readFromFile();
    
    }
    
}

class Record{
    String name;
    String capName;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
 

    
    public String getName(){
        System.out.println("Enter your name: ");
        Scanner input = new Scanner(System.in);
        String name = input.nextLine().toString();
        capName = name.substring(0,1).toUpperCase() + name.substring(1);
        return capName;
    }
    
    public void readFromFile(){
        
        BufferedReader reader;
        
        try{
        reader = new BufferedReader(new FileReader("record.txt"));
        String line = reader.readLine();
            System.out.println("Reading File...");
        while (line!=null){
            System.out.println(line);
            line = reader.readLine();
        }
        reader.close();
        }catch(IOException e){
            System.out.println("ERROR.");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void writeToFile() {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("record.txt", true));
            writer.println("[" + dtf.format(now) + "]");
            writer.println("Your name: " + getName());
            writer.println("Your score: ");
            writer.close();
            System.out.println("Name saved.");
        } catch(IOException e){
            System.out.println("ERROR.");
            e.printStackTrace();
            System.exit(1);
}
    }
}
