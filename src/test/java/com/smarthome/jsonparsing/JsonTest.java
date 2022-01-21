package com.smarthome.jsonparsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.smarthome.operating.Operation;
import com.smarthome.operating.OperationRoom;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest {

    private String operation1 = "{\n" +
            "  \"id\": 1,\n" +
            "  \"operator\": \"mom\",\n" +
            "  \"room\": 1,\n" +
            "  \"operationDateMillis\": \"1620042283092\",\n" +
            "  \"accessory\": \"security\",\n" +
            "  \"statuses\": [\"turned on\", \"temp 10\", \"fan off\"]\n" +
            "}";

    private String operation3 = "{\n" +
            "  \"operations\": [\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"operator\": \"william\",\n" +
            "      \"room\": 1,\n" +
            "      \"operationDate\": \"1620042283092\",\n" +
            "      \"accessory\": \"Security\",\n" +
            "      \"statuses\": [\n" +
            "        \"turned on\",\n" +
            "        \"temp 10\",\n" +
            "        \"fan off\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"operator\": \"fatima\",\n" +
            "      \"room\": 5,\n" +
            "      \"operationDate\": \"1620042483092\",\n" +
            "      \"accessory\": \"Security\",\n" +
            "      \"statuses\": [\n" +
            "        \"turned off\",\n" +
            "        \"temp 15\",\n" +
            "        \"fan on\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"operator\": \"will\",\n" +
            "      \"room\": 2,\n" +
            "      \"operationDate\": \"1620053283092\",\n" +
            "      \"accessory\": \"Security\",\n" +
            "      \"statuses\": [\n" +
            "        \"turned on\",\n" +
            "        \"temp 0\",\n" +
            "        \"fan on\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    private String operation2 = "[\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"operator\": \"william\",\n" +
            "      \"room\": 1,\n" +
            "      \"operationDate\": \"1620042283092\",\n" +
            "      \"accessory\": \"Security\",\n" +
            "      \"statuses\": [\n" +
            "        \"turned on\",\n" +
            "        \"temp 10\",\n" +
            "        \"fan off\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"operator\": \"fatima\",\n" +
            "      \"room\": 5,\n" +
            "      \"operationDate\": \"1620042483092\",\n" +
            "      \"accessory\": \"Security\",\n" +
            "      \"statuses\": [\n" +
            "        \"turned off\",\n" +
            "        \"temp 15\",\n" +
            "        \"fan on\"\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"operator\": \"will\",\n" +
            "      \"room\": 2,\n" +
            "      \"operationDate\": \"1620053283092\",\n" +
            "      \"accessory\": \"Security\",\n" +
            "      \"statuses\": [\n" +
            "        \"turned on\",\n" +
            "        \"temp 0\",\n" +
            "        \"fan on\"\n" +
            "      ]\n" +
            "    }\n" +
            "  ]";

    @Test //Transform String to JsonNode
    void parseSingleJsonNode() throws JsonProcessingException {

        JsonNode node = Json.parse(operation1);

        //Checks if they are equal and throws an AssertionError if not
        assertEquals(node.get("id").asText(), "1");
        assertEquals(node.get("operator").asText(), "mom");
        assertEquals(node.get("room").asText(), "1");
        assertEquals(node.get("operationDateMillis").asText(), "1620042283092");
        assertEquals(node.get("accessory").asText(), "security");
        assertFalse(node.get("statuses").isEmpty());
    }

    @Test
    void singleOperationFromJson() throws JsonProcessingException {

        JsonNode node = Json.parse(operation1);
        Operation op = Json.fromJson(node, Operation.class);

        assertEquals(op.getId(), node.get("id").asInt());
        System.out.println(op.getId());

        assertEquals(op.getOperator(), node.get("operator").asText());
        System.out.println(op.getOperator());

        assertEquals(op.getRoom(), node.get("room").asInt());
        System.out.println(op.getRoom());

        assertEquals(op.getOperationDateMillis(), node.get("operationDateMillis").asLong());
        System.out.println(op.getOperationDateMillis());

        assertEquals(op.getAccessory(), node.get("accessory").asText());
        System.out.println(op.getAccessory());

        for (String status : op.getStatuses()) System.out.println(status);
    }

    @Test
    void singleOperationPrettyPrint() throws JsonProcessingException {
        JsonNode node = Json.parse(operation1);
        String str = Json.prettyPrint(node);
        System.out.println(str);
    }

    @Test //Transform String to JsonNode
    void parseMultipleJsonNode() throws JsonProcessingException {
        JsonNode node = Json.parse(operation3);
    }

    @Test
    void multipleOperationFromJson() throws JsonProcessingException {
        JsonNode node = Json.parse(operation3);
        OperationRoom or  = Json.fromJson(node, OperationRoom.class);
    }

    @Test
    void multipleOperationPrettyPrint() throws JsonProcessingException {
        JsonNode node = Json.parse(operation3);
        String str = Json.prettyPrint(node);
        System.out.println(str);
    }

    @Test
    void arrayFromJson() throws JsonProcessingException {
        JsonNode node = Json.parse(operation2);
        List<Operation> operations = Arrays.asList(Json.fromJson(node, Operation[].class));
    }

    @Test
    void jsonFromFile() throws IOException {
        Operation[] operations = Json.jsonFromFile("C:\\Users\\User\\IdeaProjects\\SmartHome\\src\\main\\resources\\operations.json", Operation[].class);
    }

    @Test
    void jsonToFile() throws IOException {
        JsonNode node = Json.parse(operation2);
        List<Operation> operations = Arrays.asList(Json.fromJson(node, Operation[].class));
        Json.jsonToFile("C:\\Users\\User\\IdeaProjects\\SmartHome\\src\\main\\resources\\operations.json", operations);
    }
}
