package com.epam.testmodule;


import com.epam.testmodule.template.Template;
import com.epam.testmodule.template.TemplateEngine;

import java.io.*;
import java.util.Scanner;

public class FileMode {
    public FileMode() {
    }

    public String getInfoFromUser() {
        System.out.println("You chose file mode.");
        System.out.println("Please enter the path to the file with the data. Or push \"0\" to exit");
        Scanner in = new Scanner(System.in);
        String info = in.next();
        return checkInput(info);
    }

    public String checkInput(String input) {
        if (input.equals("0")) {
            System.out.println("Good Bye");
            System.exit(0);
        }
        return input;
    }

    public boolean checkFilePath(String pathFile) {
        File file = new File(pathFile);
        return file.exists();
    }

    public String getDataFromFile(String pathFile) throws IOException {
        File file = new File(pathFile);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = br.readLine()) != null) {
            result.append(" ").append(line);
        }
        br.close();
        fr.close();
        return result.toString().trim();
    }

    public String parseFile(String dataFromFile) {
        int eventIndexStart = dataFromFile.indexOf(" = ") + 3;
        int eventIndexEnd = dataFromFile.indexOf(" Data");
        int dataIndexStart = dataFromFile.indexOf("Data = ") + 7;
        int dataIndexEnd = dataFromFile.indexOf(" Client");
        int mailIndexStart = dataFromFile.indexOf("mail = ") + 7;
        int mailIndexEnd = dataFromFile.length();
        String event = dataFromFile.substring(eventIndexStart, eventIndexEnd);
        String date = dataFromFile.substring(dataIndexStart, dataIndexEnd);
        String mail = dataFromFile.substring(mailIndexStart, mailIndexEnd);
        String result = "";
        result = result + event + "`" + date + "`" + mail;
        return result;
    }

    public void startEngine(String parseFile) {
        String[] data = parseFile.split("`");
        Template template = new Template();
        Client client = new Client();
        template.setEventName(data[0]);
        template.setDate(data[1]);
        client.setMail(data[2]);
        Messenger messenger = new Messenger(new MailServer(), new TemplateEngine());
        messenger.sendMessage(client, template);
    }


    public void start() throws IOException {
        String pathToFile = getInfoFromUser();
        boolean checkFile = checkFilePath(pathToFile);
        String dataFromFile;
        String parsedFile;
        if (checkFile) {
            dataFromFile = getDataFromFile(pathToFile);
            parsedFile = parseFile(dataFromFile);
            startEngine(parsedFile);
        }else {
            ChoseMode choseMode = new ChoseMode();
            choseMode.start();
        }
    }


}
