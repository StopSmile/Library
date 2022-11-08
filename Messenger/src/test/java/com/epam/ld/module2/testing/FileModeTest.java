package com.epam.ld.module2.testing;

import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String file2 = "E:\\TempDir\\data2.txt";
        String file3 = "E:\\TempDir\\data3.txt";
        String file4 = "E:\\TempDir\\data4.txt";
        String file5 = "E:\\TempDir\\data5.txt";
        return Arrays.asList(
                DynamicTest.dynamicTest("data2.txt",() -> assertEquals(true,fileMode.checkFilePath(file2))),
                DynamicTest.dynamicTest("data3.txt",() -> assertEquals(true,fileMode.checkFilePath(file3))),
                DynamicTest.dynamicTest("data4.txt",() -> assertEquals(true,fileMode.checkFilePath(file4))),
                DynamicTest.dynamicTest("data5.txt",() -> assertEquals(false,fileMode.checkFilePath(file5)))
        );

    }
    @Test
    public void getDataFromFile_WhenCorrectFile_ShouldReturnCorrectStringFromFile() throws IOException {
        FileMode fileMode = new FileMode();
        String extracted = fileMode.getDataFromFile("E:\\TempDir\\data2.txt");
        String expected = "Event = Helloween Data = 31 October Client mail = mike@gmail.com";
        Assertions.assertEquals(expected,extracted);
    }

    @Test
    public void parseFile_WhenParseString_ShouldReturnCorrectStringWithData(){
        FileMode fileMode = new FileMode();
        String dataFromFile = "Event = Helloween Data = 31 October Client mail = mike@gmail.com";
        String extracted = fileMode.parseFile(dataFromFile);
        String expected = "Helloween`31 October`mike@gmail.com";
        Assertions.assertEquals(expected,extracted);
    }
}