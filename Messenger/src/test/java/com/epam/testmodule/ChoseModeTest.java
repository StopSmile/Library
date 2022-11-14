package com.epam.testmodule;

import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

class ChoseModeTest {
    @Test
    @ExpectSystemExitWithStatus(0)
    public void CheckInput_WhenInputEqualsZero_ShouldExitProgram() throws IOException {
        //given
        ChoseMode choseMode = new ChoseMode();
        //when
        choseMode.checkInput("0");
    }
    @Test
    public void CheckInput_whenInputEqualsOne_ShouldRunConsoleModerRun() throws IOException {
        //given
        ChoseMode choseMode = Mockito.mock(ChoseMode.class);
        ConsoleMode consoleMode = Mockito.mock(ConsoleMode.class);
        //when
        choseMode.checkInput("1");
        consoleMode.start();
        //assert
        Mockito.verify(choseMode,Mockito.times(1)).checkInput("1");
        Mockito.verify(consoleMode,Mockito.times(1)).start();

    }
    @Test
    public void CheckInput_whenInputEqualsTwo_ShouldRunFileModerRun() throws IOException {
        //given
        ChoseMode choseMode = Mockito.mock(ChoseMode.class);
        FileMode fileMode = Mockito.mock(FileMode.class);
        //when
        choseMode.checkInput("2");
        fileMode.start();
        //assert
        Mockito.verify(choseMode,Mockito.times(1)).checkInput("2");
        Mockito.verify(fileMode,Mockito.times(1)).start();
    }

}