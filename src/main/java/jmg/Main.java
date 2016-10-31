package jmg;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;
import java.util.List;

/**
 * Created by sakura on 2016/10/29.
 *
 */
public class Main {
    public static void main(String[] args) throws IOException {
        OptionParser optionParser = new OptionParser();
        ArgumentAcceptingOptionSpec<String> captureOptionSpec = optionParser.accepts("capture").withRequiredArg().ofType(String.class);

        OptionSet option = optionParser.parse(args);
        List<String> capture = option.valuesOf(captureOptionSpec);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(System.in);
        Parser parser = new Parser();

        for (String c : capture) {
            String[] split = c.split("=", 2);
            if (split.length != 2) {
                System.err.println("ignore:" + c);
                continue;
            }
            parser.capture(split[0], split[1]);
        }

        parser.parse(jsonNode);
    }
}
