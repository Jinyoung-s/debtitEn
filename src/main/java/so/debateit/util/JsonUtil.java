/**
 * The JsonUtil class implements the feature for making Json
 * and listing the post
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/14/2022
 */
package so.debateit.util;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class JsonUtil {

    private static volatile ObjectMapper objectMapper =  new ObjectMapper();

    public static ObjectMapper mapper() {
        return objectMapper;
    }


    public static ObjectWriter writer() {
        return mapper().writer().withDefaultPrettyPrinter();
    }


    public static JsonNode parse(String var0) {
        try {
            return mapper().readTree(var0);
        } catch (Throwable var2) {
            throw new RuntimeException(var2);
        }
    }


    public static JsonNode parse(Map map) {
        StringWriter stringify = new StringWriter();
        ObjectNode objectNode = null;
        try {
            mapper().writeValue(stringify, map);
            objectNode = (ObjectNode) mapper().readTree(stringify.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectNode;
    }

    public static ObjectNode newObject() {
        return mapper().createObjectNode();
    }

    public static ArrayNode newArray() {
        return mapper().createArrayNode();
    }

    public static <T> T toObject(String jsonModel, Class<T> targetClass) throws IOException, Exception {
        try {
            return mapper().readValue(jsonModel, mapper().getTypeFactory().constructType(targetClass));
        } catch (RuntimeException e) {
            throw new Exception();
        }
    }

    public static <T> String toJsonString(T model) throws IOException, Exception {
        ObjectWriter ow = writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(model);
        } catch (RuntimeException e) {
            throw new Exception();
        }
    }

    public static <T> List<T> jsonArrayToList(String jsonArr, Class<T> targetClass) throws IOException, Exception {
        try {
            return mapper().readValue(jsonArr, mapper().getTypeFactory().constructCollectionType(List.class, targetClass));
        } catch (RuntimeException e) {
            throw new Exception();
        }
    }

    public static <T> String listToJsonArray(List<T> list) throws IOException, Exception {
        try {
            return writer().writeValueAsString(list);
        } catch (RuntimeException e) {
            throw new Exception();
        }
    }

    public static void write(Writer writer, Object value) throws IOException {
        new ObjectMapper().writeValue(writer, value);
    }

}


