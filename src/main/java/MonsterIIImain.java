
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;



public class MonsterIIImain {
    public static void main(String[] args) throws Exception {
        TerminalSize ts = new TerminalSize(100, 120);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);

        int x = 5;
        int y = 5;
        final char player = 'X';
        final char block = '\u2588';
        final char bomb = 'O';
        terminal.setCursorPosition(x, y);
        terminal.putCharacter(player);
        String wall = String.valueOf(block);
        wall = wall.repeat(10);


        //create obstacles array
        Position[] obstacles = new Position[10];
        for (int i = 0; i<10; i++) {
            obstacles[i] = new Position(10+i, 10);
        }
        // use obstacles array to print to lanterna
        for (Position p : obstacles) {
            terminal.setCursorPosition(p.x, p.y);
            terminal.putCharacter(block);
        }


        // Try to put obstacle in different places tar bort 1 block random
        Random ro = new Random();/*
        Position obstaclePosition = new Position(ro.nextInt(120), ro.nextInt(100));
        terminal.setCursorPosition(obstaclePosition.x, obstaclePosition.y);
        terminal.putCharacter(block);
           */
          int randomKolumn = ro.nextInt(110);
/*
        for (int i = 0; i < wall.length(); i++) {
            terminal.setCursorPosition(randomKolumn+i, 0);
            terminal.putCharacter(wall.charAt(i));
        }*/
        terminal.flush();

        /*
        //create obstacles array2
        Position[] obstacles2 = new Position[10];
        for (int i = 0; i<10; i++) {
            obstacles2[i] = new Position(ro.nextInt(80), ro.nextInt(80));
        }
        // use obstacles array2 to print to lanterna
        for (Position o : obstacles2) {
            terminal.setCursorPosition(ro.nextInt(80), ro.nextInt(24));
            terminal.putCharacter(block);
        }
*/
        //Task 12
        Random r = new Random();
        Position bombPosition = new Position(r.nextInt(80), r.nextInt(24));
        terminal.setCursorPosition(bombPosition.x, bombPosition.y);
        terminal.putCharacter(bomb);

        terminal.flush();
        //Moving wall
        int i = 0;
        int j = 0;
        int rok = ThreadLocalRandom.current().nextInt(100);
        Position[] wallPositions = new Position[10];
        boolean continueReadingInput = true;
        while (continueReadingInput) {
            KeyStroke keyStroke = null;

            do {
                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();
                if ( i % 100 == 0) {
                    if (j < 100) {
                        for (int k = 0; k< 10; k++) {
                            terminal.setCursorPosition(rok + k, j);
                            terminal.putCharacter(block);
                            terminal.setCursorPosition(rok + k, j-1);
                            terminal.putCharacter(' ');
                            wallPositions[k] = new Position(rok + k, j);
                            //wallPositions[k].y = j;
                        }
                        j++;
                        //System.out.println(j);
                        for (Position go : wallPositions ) {

                             if (go.getX() == x) {
                                 System.out.println("Game Over");
                             }
                        }


                        terminal.flush();
                    }


                }
                i++;
            } while (keyStroke == null);



            KeyType type = keyStroke.getKeyType();
            Character c = keyStroke.getCharacter();

            //System.out.println("keyStroke.getKeyType: " + type + "keyStroke.getCharacter(): " + c);
            if (c == Character.valueOf('q')) {
                continueReadingInput = false;
                terminal.close();
                System.out.println("quit");
            }
            int oldX = x;
            int oldY = y;
            switch (keyStroke.getKeyType()) {
                case ArrowDown:
                    y += 1;
                    break;
                case ArrowUp:
                    y -= 1;
                    break;
                case ArrowRight:
                    x += 1;
                    break;
                case ArrowLeft:
                    x -= 1;
                    break;
            }
            //detect if player tries to run into obstacle
            boolean chrashIntoObstacle = false;
            for (Position p : obstacles) {
                if (p.x == x && p.y == y) {
                    chrashIntoObstacle = true;
                }
            }
            if (chrashIntoObstacle) {
                x = oldX;
                y = oldY;
            }
            else {

                terminal.setCursorPosition(oldX, oldY);
                terminal.putCharacter(' ');
                terminal.setCursorPosition(x, y);
                terminal.putCharacter(player);
            }
            //check if player runs into bomb
            if (bombPosition.x == x && bombPosition.y == y) {
                terminal.close();
            }
            terminal.flush();

        }



    }

}