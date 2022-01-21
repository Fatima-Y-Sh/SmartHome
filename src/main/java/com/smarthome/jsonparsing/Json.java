package com.smarthome.jsonparsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.smarthome.fileinputoutput.FileIO;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Json {

    private static final ObjectMapper objectMapper = getDefaultObjectMapper();

    //ObjectMapper configuration method
    @NotNull
    private static ObjectMapper getDefaultObjectMapper(){
        ObjectMapper defaultObjectMapper = new ObjectMapper();

        //Here we can add configurations to the mapper...
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return defaultObjectMapper;
    }

    //Parse a String => JsonNode | "{"name":"william", "lastname":"baaklini"}" => JsonObject
    public static JsonNode parse(String src) throws JsonProcessingException {
        return objectMapper.readTree(src);
    }

    //Return a POJO (Java Object) from JsonNode
    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return objectMapper.treeToValue(node, clazz);
    }

    //Return JsonNode from POJO
    public static JsonNode toJson(Object a){
        return objectMapper.valueToTree(a);
    }

    //Transform a node to a string without indentation
    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateString(node, false);
    }

    //Transform a node to a string with indentation
    public static String prettyPrint(JsonNode node) throws JsonProcessingException {
        return generateString(node, true);
    }

    //Transform a node to a string
    private static String generateString(JsonNode node, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();
        if (pretty)
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        return objectWriter.writeValueAsString(node);
    }

    public static <A> A jsonFromFile(String path, Class<A> clazz) throws IOException {//return objects from file after converting them from being nodes
        String src = FileIO.readAllFromFile(path);
        JsonNode node = parse(src);
        return fromJson(node, clazz);
    }

    public static void jsonToFile(String path, Object a) throws IOException {//convert objects to nodes and then to string to be written to file
        JsonNode node = toJson(a);
        String src = prettyPrint(node);
        FileIO.writeToFile(path, src);
    }
}
