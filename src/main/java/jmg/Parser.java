package jmg;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by sakura on 2016/10/29.
 *
 */
public class Parser {

    private Map<String, String> captures = new HashMap<>();

    private Deque<String> path = new LinkedList<>();
    {
        path.push("$");
    }

    public Parser capture(String path, String setter) {
        captures.put(path, setter);
        return this;
    }

    public void parse(JsonNode node) {
        switch (node.getNodeType()) {
            case OBJECT:
                parseObject(node);
                break;
            case ARRAY:
                parseArray(node);
                break;
            case STRING:
                parseText(node.asText());
                break;
            case NULL:
                parseNull();
                break;
            case NUMBER:
                parseNumber(node);
                break;
            default:
                System.out.println("not impl:" + node.getNodeType());
        }
    }

    private void parseNumber(JsonNode node) {
        System.out.printf("    .andExpect(jsonPath(\"%s\", is(%s)))\n", path(), node.decimalValue());
    }

    private void parseNull() {
        System.out.printf("    .andExpect(jsonPath(\"%s\").doesNotExist())\n", path());
    }

    private void parseText(String s) {
        String matcher = String.format("is(\"%s\")", s);
        String capture = captures.get(path());
        if (capture != null) {
            matcher = String.format("capturing(is(instanceOf(String.class)), this::%s)", capture);
        }
        System.out.printf("    .andExpect(jsonPath(\"%s\", %s))\n", path(), matcher);
    }

    private String path() {
        return String.join("", this.path);
    }

    private void parseArray(JsonNode node) {
        System.out.printf("    .andExpect(jsonPath(\"%s\", hasSize(%s)))\n", path(), node.size());
        int i = 0;
        for (JsonNode element : node) {
            String key = String.format("[%d]", i);
            path.offer(key);
            parse(element);
            String poped = path.pollLast();
            assert poped.equals(key);
        }
    }

    private void parseObject(JsonNode node) {
        node.fields().forEachRemaining(entry -> {
            String key = "." + entry.getKey();
            path.offer(key);
            JsonNode value = entry.getValue();
            parse(value);
            String pop = path.pollLast();
            assert pop.equals(key);
        });
    }
}
