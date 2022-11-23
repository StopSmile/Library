package com.epam.testmodule;

import com.epam.testmodule.template.Template;
import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FileModeTest {
    @TempDir
    File anotherTempDir = new File("E:\\TempDir");

    @ParameterizedTest
    @ValueSource(strings = {"...", "abc", "E:\\data2.txt", "file.txt", "", " "})
    void checkInput_WhenZeroIsNotProvided_ShouldReturnAnyValue(String input) {
        FileMode fileMode = new FileMode();
        assertEquals(fileMode.checkInput(input), input);
    }

    @Test
    @ExpectSystemExitWithStatus(0)
    void checkInput_WhenZeroIsProvided_ApplicationShouldExit() {
        String zero = "0";
        FileMode fileMode = new FileMode();
        fileMode.checkInput(zero);
    }

    @TestFactory
    @Tag("UnitTest")
    Iterable<DynamicTest> dynamicTests_checkFilePath_IfFilePathExist_ShouldReturnTrue() {
        Assertions.assertTrue(this.anotherTempDir.isDirectory(), "Should be a directory ");
        FileMode fileMode = new FileMode();
        String file2 = "C:\\Users\\Ivan_Pylypiv\\IdeaProjects\\Library\\Messenger\\data2.txt";
        String file3 = "C:\\Users\\Ivan_Pylypiv\\IdeaProjects\\Library\\Messenger\\data3.txt";
        String file4 = "C:\\Users\\Ivan_Pylypiv\\IdeaProjects\\Library\\Messenger\\data4.txt";
        String file5 = "C:\\Users\\Ivan_Pylypiv\\IdeaProjects\\Library\\Messenger\\data5.txt";
        return Arrays.asList(
                DynamicTest.dynamicTest("data2.txt", () -> assertEquals(true, fileMode.checkFilePath(file2))),
                DynamicTest.dynamicTest("data3.txt", () -> assertEquals(true, fileMode.checkFilePath(file3))),
                DynamicTest.dynamicTest("data4.txt", () -> assertEquals(true, fileMode.checkFilePath(file4))),
                DynamicTest.dynamicTest("data5.txt", () -> assertEquals(false, fileMode.checkFilePath(file5)))
        );

    }

    @Test
    public void getDataFromFile_WhenCorrectFile_ShouldReturnCorrectStringFromFile() throws IOException {
        FileMode fileMode = new FileMode();
        String extracted = fileMode.getDataFromFile("C:\\Users\\Ivan_Pylypiv\\IdeaProjects\\Library\\Messenger\\data2.txt");
        String expected = "Event = Halloween Data = 31 October Client mail = mike@gmail.com";
        Assertions.assertEquals(expected, extracted);
    }

    @Test
    public void parseFile_WhenParseString_ShouldReturnCorrectStringWithData() {
        FileMode fileMode = new FileMode();
        String dataFromFile = "Event = Halloween Data = 31 October Client mail = mike@gmail.com";
        String extracted = fileMode.parseFile(dataFromFile);
        String expected = "Halloween`31 October`mike@gmail.com";
        Assertions.assertEquals(expected, extracted);

    }

    @Test
    public void StartEngine_WhenCorrectString_ShouldSpiltStringAndSendMessage() {
        //given
        String parseFile = "Halloween`31 October`mike@gmail.com";
        String[] actualSplitFiles = parseFile.split("`");
        String[] expectedSplitFiles = {"Halloween", "31 October", "mike@gmail.com"};
        Template template = new Template();
        Client client = new Client();
        template.setEventName(actualSplitFiles[0]);
        template.setDate(actualSplitFiles[1]);
        client.setMail(actualSplitFiles[2]);
        FileMode fileMode = spy(new FileMode());
        //when
        fileMode.startEngine(parseFile);
        //assert
        assertArrayEquals(actualSplitFiles, expectedSplitFiles);
        verify(fileMode, times(1)).startEngine(parseFile);

    }

    @Test
    public void getInfoFromUser_ShouldReturnAnyString() {
        //given
        FileMode fileMode = new FileMode();
        String input = "E:\\TempDir\\data2.txt";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        //assert
        assertEquals("E:\\TempDir\\data2.txt", fileMode.getInfoFromUser());
    }

    @Test
    public void start_WhenUserInputCorrectPathToFile_ShouldSendMessageAndReturnTrue() throws IOException {
        //given
        FileMode fileMode = spy(new FileMode());
        FileMode fileMode1 = new FileMode();
        doReturn("E:\\TempDir\\data2.txt").when(fileMode).getInfoFromUser();
        doReturn(true).when(fileMode).checkFilePath("E:\\TempDir\\data2.txt");
        doReturn("Event = Halloween Data = 31 October Client mail = mike@gmail.com").when(fileMode).getDataFromFile("E:\\TempDir\\data2.txt");
        String actualDataFromFile = fileMode1.getDataFromFile("C:\\Users\\Ivan_Pylypiv\\IdeaProjects\\Library\\Messenger\\data2.txt");
        String expectedDataFromFile = "Event = Halloween Data = 31 October Client mail = mike@gmail.com";
        //when
        boolean successfulStart = fileMode.start();
        //assert
        Assertions.assertEquals(actualDataFromFile, expectedDataFromFile);
        Assertions.assertTrue(successfulStart);
        verify(fileMode, times(1)).start();
        verify(fileMode, times(1)).checkFilePath("E:\\TempDir\\data2.txt");
        verify(fileMode, times(1)).getInfoFromUser();
        verify(fileMode, times(1)).getDataFromFile("E:\\TempDir\\data2.txt");
        verify(fileMode, times(1)).parseFile("Event = Halloween Data = 31 October Client mail = mike@gmail.com");
        verify(fileMode, times(1)).startEngine("Halloween`31 October`mike@gmail.com");
    }

    @Test
    public void start_WhenUserInputIncorrectPathToFile_ShouldReturnFalse() throws IOException {
        //given
        String incorrectPath = "C:\\Users\\Ivan_Pylypiv\\Desktop\\Library\\Messenger\\data99.txt";
        FileMode fileMode = spy(new FileMode());
        doReturn("C:\\Users\\Ivan_Pylypiv\\Desktop\\Library\\Messenger\\data99.txt").when(fileMode).getInfoFromUser();
        doReturn(false).when(fileMode).checkFilePath(incorrectPath);
        //when
        boolean falseStart = fileMode.start();
        //assert
        Assertions.assertFalse(falseStart);

    }

}