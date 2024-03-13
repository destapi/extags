package works.hop.eztag.template;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.cli.*;
import works.hop.eztag.parser.JContext;
import works.hop.eztag.parser.JElement;
import works.hop.eztag.parser.JParser;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/*
 * java -cp app/build/libs/app.jar works.hop.eztag.template.GenTemplates \
 * -w "www/todos" \
 * -i "/todos/todo-htmx-app" \
 * -o "index.hmtl" \
 * -d "{ \"todos\": [ {\"id\": 1, \"title\": \"Read book\", \"done\": false} ] }"
 * */
public class GenTemplates {

    public static void main(String[] args) throws XMLStreamException, IOException, ParseException {
        // create an Options object
        Options options = new Options();

        // add s option
        options.addOption("w", "www", true, "static web resources folder");
        options.addOption("d", "data", true, "model data in json format");
        options.addOption("o", "output", true, "target output file name");
        options.addOption("i", "input", true, "path to input file in resources folder");

        // parse command line arg
        CommandLineParser lineParser = new DefaultParser();
        CommandLine cmd = lineParser.parse(options, args);

        // generate file
        String staticResources = null;
        if (cmd.hasOption("www")) {
            staticResources = cmd.getOptionValue("w");
        }

        String inputFilePath = null;
        if (cmd.hasOption("input")) {
            inputFilePath = cmd.getOptionValue("i");
        }

        String outputFileName = null;
        if (cmd.hasOption("output")) {
            outputFileName = cmd.getOptionValue("o");
        }

        Map<String, Object> data = Collections.emptyMap();
        if (cmd.hasOption("data")) {
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, Object>>() {
            }.getType();
            data = gson.fromJson(cmd.getOptionValue("d"), mapType);
        }

        JContext processor = new JContext(data);
        JParser parser = new JParser(inputFilePath, processor);
        JElement root = parser.parse();
        String markup = processor.process(root);
        Files.writeString(Path.of(
                        Objects.requireNonNull(staticResources, "static web resources folder is not optional"),
                        Objects.requireNonNull(outputFileName, "name of the generated file is not optional")),
                markup,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);


    }
}
