package Json;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class TestCat {
    public static void main(String[] args) throws IOException {
        Cat cat = new Cat();
        cat.name = "Murka";
        cat.age = 5;
        cat.weight = 3;

        StringWriter writer = new StringWriter();
        convertToJSON(writer, cat);
        System.out.println(writer.toString());
    }

    public static void convertToJSON(StringWriter writer, Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, object);
    }

    @JsonAutoDetect
    public static class Cat {
        public String name;

        public int age;

        public int weight;

        Cat() {
        }
    }
}
