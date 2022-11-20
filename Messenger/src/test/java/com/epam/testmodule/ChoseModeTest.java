package com.epam.testmodule;

import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.*;

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
        ConsoleMode consoleMode = Mockito.spy(new ConsoleMode());
        FileMode fileMode = Mockito.spy(new FileMode());
        ChoseMode choseMode = new ChoseMode(consoleMode,fileMode);
        doReturn("JAVA DAY").when(consoleMode).getEventNameFromConsole();
        doReturn("10.10.2023").when(consoleMode).getEventDateFromConsole();
        doReturn("client@gmail.com").when(consoleMode).getClientEmailFromConsole();
        //when
        choseMode.checkInput("1");
        //assert
        verify(consoleMode).start();
        verify(consoleMode).getEventDateFromConsole();
        verify(consoleMode).getClientEmailFromConsole();
        verify(consoleMode).getEventNameFromConsole();
    }
    @Test
    public void CheckInput_whenInputEqualsTwo_ShouldRunFileModerRun() throws IOException {
        //given
        ConsoleMode consoleMode = Mockito.spy(new ConsoleMode());
        FileMode fileMode = Mockito.spy(new FileMode());
        ChoseMode choseMode = new ChoseMode(consoleMode,fileMode);
        doReturn("E:\\TempDir\\data4.txt").when(fileMode).getInfoFromUser();
        doReturn(true).when(fileMode).checkFilePath("E:\\TempDir\\data4.txt");
        //when
        choseMode.checkInput("2");
        //assert
        verify(fileMode).start();
        verify(fileMode).getInfoFromUser();
        verify(fileMode).checkFilePath("E:\\TempDir\\data4.txt");
    }

}