package com.epam.testmodule;

import java.io.IOException;
import java.util.Scanner;

public class ChoseMode {
    private final ConsoleMode consoleMode = new ConsoleMode();
    private final FileMode fileMode = new FileMode();

    public ConsoleMode getConsoleMode() {
        return consoleMode;
    }

    public FileMode getFileMode() {
        return fileMode;
    }

    public ChoseMode() {
    }
    public void start() throws IOException {
        System.out.println("Hello, this is Messenger. Please choose the mode for the application. In the first mode, you should set data from the console.");
        System.out.println("In the second mode, you should set the directory path where is your file on pc. For the first mode push \"1\" and for the second push \"2\". " +
                "For exit push \"0\"");
        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        String inPut = in.next();
        System.out.println("Your number is : " + inPut);
        checkInput(inPut);
    }

    public void checkInput(String input) throws IOException {
        if (input.equals("0")) {
            System.out.println("Good bye!");
            System.exit(0);
        }
        if (!input.equals("1") && !input.equals("2")) {
            System.out.println("Please insert correct data");
            start();
        }
        if (input.equals("1")) {
            consoleMode.start();
        }
        if (input.equals("2")) {
            fileMode.start();
        }
    }
}



