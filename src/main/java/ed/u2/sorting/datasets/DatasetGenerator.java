package ed.u2.sorting.datasets;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class DatasetGenerator {

    private static final Random rnd = new Random(42);
    // Ruta donde se guardarán los archivos (creará la carpeta si no existe)
    private static final String CARPETA = "Archivos/";

    private static final String[] APELLIDOS_30 = {
            "Guerrero","Naranjo","Cedeño","Márquez","Valdiviezo","Ramírez","Lozano","Vera",
            "Carvajal","López","Paredes","Ríos","Sánchez","Mora","Aguirre","Reyes","Romero",
            "Vega","González","Jiménez","Palacios","Roldán","Mendoza","Ortega","Campos",
            "Cárdenas","Fajardo","Jara","Solano","Castro"
    };

    private static final String[] INSUMOS = {
            "Guante Nitrilo Talla M", "Alcohol 70% 1L", "Gasas 10x10", "Jeringa 5ml", "Venda Elástica 10cm",
            "Mascarilla KN95", "Termómetro Digital", "Algodón 250g", "Tijeras Médicas", "Analgésico Genérico"
    };

    public static void generarCitas100(String filename) throws IOException {
        try (FileWriter fw = new FileWriter(CARPETA + filename)) {
            fw.write("id;apellido;fechaHora\n");

            LocalDate start = LocalDate.of(2025, 3, 1);
            LocalDate end = LocalDate.of(2025, 3, 31);
            LocalTime open = LocalTime.of(8, 0);
            LocalTime close = LocalTime.of(18, 0);

            for (int i = 1; i <= 100; i++) {
                String id = String.format("CITA-%03d", i);
                String apellido = APELLIDOS_30[rnd.nextInt(APELLIDOS_30.length)];

                long days = end.toEpochDay() - start.toEpochDay();
                long randomDay = start.toEpochDay() + rnd.nextInt((int) days + 1);
                LocalDate d = LocalDate.ofEpochDay(randomDay);

                int minutesRange = (int) (close.toSecondOfDay() - open.toSecondOfDay()) / 60;
                int randomMinutes = rnd.nextInt(minutesRange + 1);
                LocalTime t = open.plusMinutes(randomMinutes);

                LocalDateTime fechaHora = d.atTime(t.getHour(), t.getMinute());

                fw.write(id + ";" + apellido + ";" + fechaHora.toString() + "\n");
            }
        }
        System.out.println("Generado: " + CARPETA + filename);
    }

    public static void generarCitas100CasiOrdenadas(String inputFilename, String outputFilename) throws IOException {
        // Leer del archivo original creado en la carpeta correcta
        List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(CARPETA + inputFilename));
        if (lines.isEmpty()) return;

        List<String> data = new ArrayList<>(lines.subList(1, lines.size()));

        // Ordenar por fecha (columna 2)
        data.sort(Comparator.comparing(o -> o.split(";")[2]));

        // Exactamente 5 swaps
        int n = data.size();
        Set<String> usedPairs = new HashSet<>();
        int swaps = 0;

        while (swaps < 5) {
            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);
            if (a == b) continue;

            // Lógica para no repetir pares
            String key = a < b ? a + "-" + b : b + "-" + a;
            if (usedPairs.contains(key)) continue;

            Collections.swap(data, a, b);
            usedPairs.add(key);
            swaps++;
        }

        try (FileWriter fw = new FileWriter(CARPETA + outputFilename)) {
            fw.write("id;apellido;fechaHora\n");
            for (String s : data) {
                fw.write(s + "\n");
            }
        }
        System.out.println("Generado: " + CARPETA + outputFilename);
    }

    public static void generarPacientes500(String filename) throws IOException {
        try (FileWriter fw = new FileWriter(CARPETA + filename)) {
            fw.write("id;apellido;prioridad\n");

            List<String> pool = new ArrayList<>();
            // Crear sesgo en apellidos
            List<String> grupoA = Arrays.asList("Ramírez", "Guerrero", "Mendoza", "Cedeño", "Carrillo");
            List<String> grupoB = Arrays.asList("Aguirre", "Márquez", "López", "Ríos", "Valdiviezo");
            List<String> grupoC = Arrays.asList("Vega", "González", "Reyes", "Ortega", "Romero");

            for (int i = 0; i < 300; i++) pool.add(grupoA.get(rnd.nextInt(grupoA.size())));
            for (int i = 0; i < 150; i++) pool.add(grupoB.get(rnd.nextInt(grupoB.size())));
            for (int i = 0; i < 50; i++) pool.add(grupoC.get(rnd.nextInt(grupoC.size())));

            Collections.shuffle(pool, rnd);

            for (int i = 1; i <= 500; i++) {
                String id = String.format("PAC-%04d", i);
                String apellido = pool.get(i - 1);
                int prioridad = 1 + rnd.nextInt(3);
                fw.write(id + ";" + apellido + ";" + prioridad + "\n");
            }
        }
        System.out.println("Generado: " + CARPETA + filename);
    }

    public static void generarInventario500Inverso(String filename) throws IOException {
        try (FileWriter fw = new FileWriter(CARPETA + filename)) {
            fw.write("id;insumo;stock\n");

            for (int i = 1; i <= 500; i++) {
                String id = String.format("ITEM-%04d", i);
                String insumo = INSUMOS[rnd.nextInt(INSUMOS.length)];
                int stock = 501 - i; // 500, 499, ... 1
                fw.write(id + ";" + insumo + ";" + stock + "\n");
            }
        }
        System.out.println("Generado: " + CARPETA + filename);
    }

    public static void main(String[] args) throws Exception {
        // 1. Asegurar que la carpeta exista
        new File(CARPETA).mkdirs();

        // 2. Generar con los NOMBRES EXACTOS del PDF
        generarCitas100("citas_100.csv");

        // Usamos el archivo recién creado como input
        generarCitas100CasiOrdenadas("citas_100.csv", "citas_100_casi_ordenadas.csv");

        generarPacientes500("pacientes_500.csv");
        generarInventario500Inverso("inventario_500_inverso.csv");
    }
}