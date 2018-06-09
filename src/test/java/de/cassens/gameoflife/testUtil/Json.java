package de.cassens.gameoflife.testUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class Json {
    private Json() {
    }
    public static String getJson(String fileName) throws IOException {
        final Stream<String> lines = Files.lines(Paths.get("src/test/resources/" + fileName));
        final Optional<String> optional = lines.findFirst();
        return optional.orElse("");
    }
}
