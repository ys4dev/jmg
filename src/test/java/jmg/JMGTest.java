package jmg;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jmg.model.Child;
import jmg.model.Model1;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by sakura on 2016/10/29.
 */
public class JMGTest {
    @Test
    public void test() throws Exception {
        Model1 model = new Model1();
        model.setName1("value1");
        Child child1 = new Child();
        Child child2 = new Child();
        model.setChildren(Arrays.asList(child1, child2));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(model);
        JsonNode jsonNode = mapper.readTree(json);

        Parser parser = new Parser();
        parser.parse(jsonNode);
    }

    @Test
    public void testJson() throws Exception {
        String json = "{\"id\":1,\"name\":\"model-name\",\"value\":null,\"inners\":[{\"id\":1,\"name\":null,\"value\":null}],\"child2s\":[]}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);

        Parser parser = new Parser().capture("$.name", "setName");
        parser.parse(jsonNode);
    }
}
