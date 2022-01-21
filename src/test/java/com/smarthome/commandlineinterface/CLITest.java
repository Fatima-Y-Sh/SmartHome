package com.smarthome.commandlineinterface;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CLITest {

    @Test
    void getInputSplit() {
        Scanner scanner = new Scanner(System.in);
        String res = scanner.nextLine();
        String[] inputSplit = res.split("\\s+");
        for (String s : inputSplit) System.out.println(s);
    }
}
