package com.example.tetris;

import com.example.tetris.view.ConsoleView;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import jline.console.ConsoleReader;
import jline.console.KeyMap;
import jline.console.Operation;

public class Main {
    public static void main(String[] args) {
        final Tetris tetris = new Tetris();
        tetris.setView(new ConsoleView());
//        tetris.setView(new Observer() {
//            @Override
//            public void update(Observable o, Object arg) {
//                System.out.println("arena: " + o);
//            }
//        });
        tetris.start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try (ConsoleReader reader = new ConsoleReader()) {
                    KeyMap km = KeyMap.keyMaps().get("vi-insert");
                    while (true) {
                        Object c = null;
                        try {
                            c = reader.readBinding(km);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (c == Operation.BACKWARD_CHAR) { // left arrow
                            tetris.moveLeft();
                        } else if (c == Operation.FORWARD_CHAR) { // right arrow
                            tetris.moveRight();
                        } else if (c == Operation.NEXT_HISTORY) { // down arrow
                            tetris.descend();
                        } else if (c == Operation.PREVIOUS_HISTORY) { // up arrow
                            tetris.rotateClockwise();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }).start();
    }
}
