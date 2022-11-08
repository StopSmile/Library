package com.epam.ld.module2.testing;

import java.io.IOException;
import java.util.Scanner;

public class ChoseMode {
    public ChoseMode() {

    }

    public void start() throws IOException {
        ChoseMode choseMode = new ChoseMode();
        System.out.println("Hello, this is Messenger. Please choose the mode for the application. In the first mode, you should set data from the console.");
        System.out.println("In the second mode, you should set the directory path where is your file on pc. For the first mode push \"1\" and for the second push \"2\". " +
                "For exit push \"0\"");
        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        String inPut = in.next();
        System.out.println("Your number is : " + inPut);
        choseMode.checkInput(inPut);

    }

    public void checkInput(String input) throws IOException {
        if (input.equals("0")){
            System.out.println("Good bye!");
            System.exit(0);
        }
        if (!input.equals("1") && !input.equals("2")) {
            System.out.println("Please insert correct data");
            ChoseMode choseMode = new ChoseMode();
            choseMode.start();
        }
        if (input.equals("1")){
            ConsoleMode consoleMode = new ConsoleMode();
            consoleMode.start();
        }
        if (input.equals("2")){
            FileMode fileMode = new FileMode();
            fileMode.start();
        }
    }
}



