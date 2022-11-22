package com.epam.testmodule;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        boolean start = true;
        while (start) {
            start = false;
            ChoseMode choseMode = new ChoseMode(new ConsoleMode(), new FileMode());
            boolean chek = choseMode.start();
            if (!chek){
                start = true;
            }
        }
    }
}
