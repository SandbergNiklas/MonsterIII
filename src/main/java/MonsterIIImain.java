
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.Random;


public class MonsterIIImain {
    public static void main(String[] args) throws Exception {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);

        int x = 5;
        int y = 5;
        final char player = 'X';
        final char block = '\u2588';
        final char bomb = 'O';
        terminal.setCursorPosition(x, y);
        terminal.putCharacter(player);


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
        // Try to put obstacle in different places
        Random ro = new Random();
        Position obstaclePosition = new Position(ro.nextInt(80), ro.nextInt(24));
        terminal.setCursorPosition(obstaclePosition.x, obstaclePosition.y);
        terminal.putCharacter(block);

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
        //Task 11
        boolean continueReadingInput = true;
        while (continueReadingInput) {
            KeyStroke keyStroke = null;
            do {
                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            KeyType type = keyStroke.getKeyType();
            Character c = keyStroke.getCharacter();

            System.out.println("keyStroke.getKeyType: " + type + "keyStroke.getCharacter(): " + c);
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