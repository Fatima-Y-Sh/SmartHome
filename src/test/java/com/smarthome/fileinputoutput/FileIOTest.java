package com.smarthome.fileinputoutput;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.smarthome.jsonparsing.Json;
import com.smarthome.operating.Operation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {

    private String testPath = "C:\\Users\\User\\IdeaProjects\\SmartHome\\src\\main\\resources\\operations.json";
    private String testString = "{\n" +
            "  \"id\": 1,\n" +
            "  \"operator\": \"william\",\n" +
            "  \"room\": 1,\n" +
            "  \"operationDate\": \"1620042283092\",\n" +
            "  \"accessory\": \"Security\",\n" +
            "  \"statuses\": [\"turned on\", \"temp 10\", \"fan off\"]\n" +
            "}\n";

    @Test
    void writeToFile() throws IOException {
        FileIO.writeToFile(testPath, testString);
    }

    @Test
    void readAllFromFile() throws IOException {
        String s = FileIO.readAllFromFile(testPath);
        System.out.println(s);
    }

    @Test
    void JsonParseFromFile() throws IOException {
        String src = FileIO.readAllFromFile("C:\\Users\\User\\IdeaProjects\\SmartHome\\src\\main\\resources\\operation.json");
        JsonNode node = Json.parse(src);
        Operation operation = Json.fromJson(node, Operation.class);
    }
}
