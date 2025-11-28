package ed.u2.sorting.util;

import java.nio.file.*;
import java.util.*;
import java.io.IOException;

public class CSVLoader {
    public static List<String[]> load(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        List<String[]> data = new ArrayList<>();

        if (lines.isEmpty()) return data;

        // CAMBIO CRÍTICO: i = 0 (Leemos todo, el DatasetLoader filtrará el encabezado)
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            data.add(line.split(";", -1));
        }
        return data;
    }
}