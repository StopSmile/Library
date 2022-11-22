package com.epam.testmodule;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ChoseMode choseMode = new ChoseMode(new ConsoleMode(),new FileMode());

        choseMode.start();
    }
}
