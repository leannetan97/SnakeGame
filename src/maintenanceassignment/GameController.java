//package maintenanceassignment;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author Tan Lay Yan, Heng Xin Ting, Nabila
 */
enum Direction {
    UP, DOWN, LEFT, RIGHT
};

class Coordinate {

    private int x;
    private int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
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

class Grid {

    private Node[][] nodeList = new Node[GameController.BOR_HEIGHT][GameController.BOR_WIDTH];

    Grid() {
        initGrid();
    }

    private void initGrid() {
        for (int y = 0; y < nodeList.length; y++) {
            for (int x = 0; x < nodeList[y].length; x++) {
                if (x == 0 || y == 0 || x == GameController.BOR_WIDTH - 1 || y == GameController.BOR_HEIGHT - 1) {
                    nodeList[y][x] = new Node(true);
                } else {
                    nodeList[y][x] = new Node(false);
                }
            }
        }
    }

    public void CleanScreen() throws IOException, InterruptedException { // Clear Console
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    public void printGrid(int life, int score) throws IOException, InterruptedException {
        CleanScreen();
        printLifeScore(life, score);
        try {
            BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
            for (Node[] nodeList1 : nodeList) {
                for (Node item : nodeList1) {
                    log.write(item.getElementIcon());
                }
                log.newLine();
            }
            log.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetGrid(int foodCoorX, int foodCoorY, ArrayList<Coordinate> snakeList) {
        for (int y = 1; y < nodeList.length - 1; y++) {
            for (int x = 1; x < nodeList[y].length - 1; x++) {
                nodeList[y][x].setIsSnakeBody(false);
                nodeList[y][x].setIsSnakeHead(false);
                nodeList[y][x].setIsFood(false);
            }
        }

        for (int y = 1; y < nodeList.length - 1; y++) {
            for (int x = 1; x < nodeList[y].length - 1; x++) {
                if (y == foodCoorY && x == foodCoorX) {
                    nodeList[y][x].setIsFood(true);
                } else if (y == snakeList.get(0).getY() && x == snakeList.get(0).getX()) {
                    nodeList[y][x].setIsSnakeHead(true);
                } else {
                    for (int i = 1; i < snakeList.size(); i++) {
                        if (x == snakeList.get(i).getX() && y == snakeList.get(i).getY()) {
                            nodeList[y][x].setIsSnakeBody(true);
                        }
                    }
                }
            }
        }
    }

    public void printLifeScore(int life, int score) {
        System.out.println("Life: " + life + "\t\t\t Score: " + score);

    }
}

class Node {

    private String elementIcon;

    Node(boolean isBorder) {
        if (isBorder) {
            elementIcon = "!";
        } else {
            elementIcon = " ";
        }

    }

    public void setIsSnakeHead(boolean isSnakeHead) {
        if (isSnakeHead) {
            setElementIcon("#");
        } else {
            setElementIcon(" ");
        }
    }

    public void setIsSnakeBody(boolean isSnakeBody) {
        if (isSnakeBody) {
            setElementIcon("0");
        } else {
            setElementIcon(" ");
        }

    }

    public void setIsFood(boolean isFood) {
        if (isFood) {
            setElementIcon("F");
        } else {
            setElementIcon(" ");
        }
    }

    private void setElementIcon(String elementIcon) {
        this.elementIcon = elementIcon;
    }

    public String getElementIcon() {
        return elementIcon;
    }

}

class Food {

    private final Coordinate FOOD_COORDINATE;

    Food() {
        this.FOOD_COORDINATE = new Coordinate(new Random().nextInt(GameController.BOR_WIDTH - 2) + 1, new Random().nextInt(GameController.BOR_HEIGHT - 2) + 1);
    }

    public int getX() {
        return FOOD_COORDINATE.getX();
    }

    public int getY() {
        return FOOD_COORDINATE.getY();
    }

}

class Snake {

    private ArrayList<Coordinate> snakeCoordinateList;
    private Direction snakeDirection;
    private int length;

    Snake() { // Just Born
        snakeCoordinateList = new ArrayList<>();
        length = 5;
        initSnakeBody(length);
        snakeDirection = Direction.RIGHT;
    }

    Snake(int length) { //Reborn
        this.length = length;
        snakeCoordinateList = new ArrayList<>();
        initSnakeBody(length);
        snakeDirection = Direction.RIGHT;
    }

    private void initSnakeBody(int length) {
        for (int i = 0; i < length; i++) {
            snakeCoordinateList.add(new Coordinate(15 - i, 10));
        }
    }

    public Coordinate getSnakeHeadCoordinate() {
        return snakeCoordinateList.get(0);
    }

    public Direction getSnakeDirection() {
        return snakeDirection;
    }

    public ArrayList<Coordinate> getSnakeList() {
        return snakeCoordinateList;
    }

    public void setSnakeCoordinateX(int x) {
        snakeCoordinateList.get(0).setX(x);
    }

    public void setSnakeCoordinateY(int y) {
        snakeCoordinateList.get(0).setY(y);
    }

    public void setSnakeDirection(Direction direction) {
        snakeDirection = direction;
    }

    public void increaseLength() {
        appeadABodyAndMoveHead();
        length = snakeCoordinateList.size();

    }

    private void appeadABodyAndMoveHead() {
        snakeCoordinateList.add(1, new Coordinate(snakeCoordinateList.get(0).getX(), snakeCoordinateList.get(0).getY()));
        moveHead();
    }

    public int getLength() {
        return length;
    }

    public void move() {
        for (int i = length - 1; i > 0; i--) {
            snakeCoordinateList.get(i).setX(snakeCoordinateList.get(i - 1).getX());
            snakeCoordinateList.get(i).setY(snakeCoordinateList.get(i - 1).getY());
        }
        moveHead();
    }

    private void moveHead() { //Move Head
        if (snakeDirection == Direction.LEFT) {
            snakeCoordinateList.get(0).setX(snakeCoordinateList.get(0).getX() - 1);
        }

        if (snakeDirection == Direction.RIGHT) {
            snakeCoordinateList.get(0).setX(snakeCoordinateList.get(0).getX() + 1);
        }

        if (snakeDirection == Direction.UP) {
            snakeCoordinateList.get(0).setY(snakeCoordinateList.get(0).getY() - 1);
        }

        if (snakeDirection == Direction.DOWN) {
            snakeCoordinateList.get(0).setY(snakeCoordinateList.get(0).getY() + 1);
        }
    }

    public boolean isCollied() {
        if (getSnakeHeadCoordinate().getX() >= GameController.BOR_WIDTH - 1 || getSnakeHeadCoordinate().getY() <= 0 || getSnakeHeadCoordinate().getX() <= 0 || getSnakeHeadCoordinate().getY() >= GameController.BOR_HEIGHT - 1) {
            return true;
        }

        for (int i = length - 1; i > 0; i--) {
            if ((getSnakeHeadCoordinate().getX() == snakeCoordinateList.get(i).getX()) && (getSnakeHeadCoordinate().getY() == snakeCoordinateList.get(i).getY())) {
                return true;
            }
        }
        return false;
    }

}

final class Game {

    private Food food;
    private Snake snake;
    private int life = 3;
    private int score = 0;

    Game() {
        snake = new Snake();
        food = new Food();
    }

    private void decreaseLife() {
        life--;
    }

    private void increaseScore() {
        score++;
    }

    public int getLife() {
        return life;
    }

    public int getScore() {
        return score;
    }

    // For Movement Class
    public Direction getSnakeDirection() {
        return snake.getSnakeDirection();
    }

    // For SetGrid
    public ArrayList<Coordinate> getSnakeCoordinateList() {
        return snake.getSnakeList();
    }

    public int getFoodCoordinateX() {
        return food.getX();
    }

    public int getFoodCoordinateY() {
        return food.getY();
    }

    // Update From Movement Class
    public void setSnakeDirection(Direction direction) {
        snake.setSnakeDirection(direction);
    }

    private void locateNewFood() {
        food = new Food();
    }

    private void newSnakeReborn(int length) {
        snake = new Snake(length);
    }

    public void checkCollision() {
        if (snake.isCollied()) {
            decreaseLife();
            newSnakeReborn(snake.getLength());
        }
    }

    public void checkHeadFoodPosition() {
        if (snake.getSnakeHeadCoordinate().getX() == food.getX() && snake.getSnakeHeadCoordinate().getY() == food.getY()) {
            increaseScore();
            snake.increaseLength();
            locateNewFood();
        }
    }

    public void makeSnakeMove() {
        snake.move();
    }

}

class Record {

    private String name;
    private final int SCORE;
    private final String TIMESTAMP;

    Record(String name, int score) {
        this.name = name;
        this.SCORE = score;
        TIMESTAMP = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now());
    }

    public String getName() {
        formatFirstLetterOfWordToUpperCase();
        return name;
    }

    public void formatFirstLetterOfWordToUpperCase() {
        String[] nameArr = name.split(" ");
        name = "";
        for (String element : nameArr) {
            String newName = Character.toString(Character.toUpperCase(element.charAt(0))) + element.substring(1, element.length());
            name += newName + " ";
        }
    }

    public void readFromFile() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("record.txt"));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
            System.exit(1);
        } catch (IOException e) {
            System.out.println("IOException:" + e);
        }
    }

    public void writeToFile() {

        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("record.txt", true))) {
                writer.write("Player Name: " + getName());
                writer.newLine();
                writer.write("Played Date: " + TIMESTAMP);
                writer.newLine();
                writer.write("Score: " + SCORE);
                writer.newLine();
                writer.write("--------------------------------------------");
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("IOException:" + e);
        }
    }

}

public class GameController extends JFrame {
//Extend to addKeyListener

    private static Game game;
    private static Record record;
    private static boolean isPlaying = false;
    private static boolean gameOver = false;
    public static final int BOR_WIDTH = 61;
    public static final int BOR_HEIGHT = 31;
    private static final int DELAY = 140;

    GameController() {
        initBoard();
        game = new Game();
    }

    private void initBoard() {
        this.setSize(BOR_WIDTH, BOR_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("KeyListener");
        this.setVisible(true);
        this.addKeyListener(new Movement());
    }

    private static void gameOver() {
        String msg = "All lives completed\nBetter Luck Next Time!!!\nPlease key in your name:";
        System.out.println(msg);
        record = new Record(new Scanner(System.in).nextLine(), game.getScore());
        saveRecord();
    }

    private void updateSnakeDirection(Direction direction) {
        game.setSnakeDirection(direction);
    }

    public static void viewRecord() {
        record.readFromFile();
    }

    public static void saveRecord() {
        record.writeToFile();
    }

    private class Movement extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (game.getSnakeDirection() != Direction.RIGHT)) {
                updateSnakeDirection(Direction.LEFT);
            }

            if ((key == KeyEvent.VK_RIGHT) && (game.getSnakeDirection() != Direction.LEFT)) {
                updateSnakeDirection(Direction.RIGHT);
            }

            if ((key == KeyEvent.VK_UP) && (game.getSnakeDirection() != Direction.DOWN)) {
                updateSnakeDirection(Direction.UP);
            }

            if ((key == KeyEvent.VK_DOWN) && (game.getSnakeDirection() != Direction.UP)) {
                updateSnakeDirection(Direction.DOWN);
            }

            if (key == KeyEvent.VK_ESCAPE) {
                isPlaying = false;
                gameOver = true;
            }

            if (key != KeyEvent.VK_ESCAPE && key != KeyEvent.VK_DOWN && key != KeyEvent.VK_UP && key != KeyEvent.VK_RIGHT && key != KeyEvent.VK_LEFT) {
                isPlaying = !isPlaying;
            }
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new GameController();
        Grid g = new Grid();

        g.CleanScreen();
        System.out.println("Welcome to the mini Snake game.\n");
        System.out.println("Game instructions:\nYou will be provided foods at the several coordinates of the screen which you have to eat.\nEverytime you eat a food the length of the snake will be increased by 1 element and thus the score.\nHere you are provided with three lives. Your life will decrease as you hit the wall or snake's body.\nYou can pause the game in its middle by pressing any key. To continue the paused game press any other key once again\nIf you want to exit press esc.");
        System.out.println("\nPress any key [Other then 'Up', 'Down', 'Left', 'Right', 'ESC'] to play game...");

        while (!gameOver) {
            while (isPlaying) {
                g.resetGrid(game.getFoodCoordinateX(), game.getFoodCoordinateY(), game.getSnakeCoordinateList());
                g.printGrid(game.getLife(), game.getScore());
                game.checkHeadFoodPosition();
                game.checkCollision();
                if (game.getLife() < 0) {
                    isPlaying = false;
                    gameOver = true;
                    break;
                } else {
                    game.makeSnakeMove();
                }
                Thread.sleep(DELAY);
            }
            Thread.sleep(DELAY);
        }

        if (gameOver) {
            g.CleanScreen();
            gameOver();
            viewRecord();
        }

    }
}
