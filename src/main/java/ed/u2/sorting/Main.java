package ed.u2.sorting;

import ed.u2.sorting.busqueda.*;
import ed.u2.sorting.datasets.DatasetLoader;
import ed.u2.sorting.estructuras.ListaEnlazadaSLL;
import ed.u2.sorting.estructuras.Nodo;
import ed.u2.sorting.modelos.Cita;
import ed.u2.sorting.modelos.Paciente;
import ed.u2.sorting.ordenamientos.InsertionSort;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n==============================================");
            System.out.println("            SISTEMA DE BÚSQUEDAS ");
            System.out.println("==============================================");
            System.out.println("1. Ejecutar búsquedas secuencial en archivo Pacientes");
            System.out.println("2. Ejecutar búsquedas binarias en archivo Citas");
            System.out.println("3. Ejecutar casos de prueba");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> ejecutarMenuPacientes(sc);
                case 2 -> ejecutarMenuCitas(sc);
                case 3 -> ejecutarPruebasDemo();
                case 4 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }

        } while (opcion != 4);
    }

    public static void ejecutarMenuPacientes(Scanner sc) {

        System.out.println("\n===== BÚSQUEDAS EN PACIENTES =====");

        final String archivo = "Archivos/pacientes.csv";

        System.out.println("Selecciona un apellido a buscar:");
        System.out.println("\t1. Ramírez");
        System.out.println("\t2. Aguirre");
        System.out.println("\t3. Guerrero");
        System.out.println("\t4. Romero");
        System.out.println("\t5. Ortega");
        System.out.print("Opción: ");

        int opcion = sc.nextInt();
        String clave;

        switch (opcion) {
            case 1 -> clave = "Ramírez";
            case 2 -> clave = "Aguirre";
            case 3 -> clave = "Guerrero";
            case 4 -> clave = "Romero";
            case 5 -> clave = "Ortega";
            default -> {
                System.out.println("Opción inválida. Regresando...");
                return;
            }
        }

        try {
            System.out.println("\n→ Ejecutando búsqueda para: " + clave);
            ejecutarBusquedaPacientes(archivo, clave);
        } catch (Exception e) {
            System.err.println("Error ejecutando la búsqueda de pacientes:");
            e.printStackTrace();
        }
    }


    public static void ejecutarMenuCitas(Scanner sc) {

        System.out.println("\n===== BÚSQUEDAS EN CITAS =====");

        final String archivo = "Archivos/citas.csv";

        System.out.println("Selecciona un ID de cita a buscar:");
        System.out.println("\t1. CITA-010");
        System.out.println("\t2. CITA-020");
        System.out.println("\t3. CITA-050");
        System.out.println("\t4. CITA-075");
        System.out.println("\t5. CITA-095");
        System.out.print("Opción: ");

        int opcion = sc.nextInt();
        String clave;

        switch (opcion) {
            case 1 -> clave = "CITA-010";
            case 2 -> clave = "CITA-020";
            case 3 -> clave = "CITA-050";
            case 4 -> clave = "CITA-075";
            case 5 -> clave = "CITA-095";
            default -> {
                System.out.println("Opción inválida. Regresando...");
                return;
            }
        }

        try {
            System.out.println("\n→ Ejecutando búsqueda para: " + clave);
            ejecutarBusquedaCitas(archivo, clave);
        } catch (Exception e) {
            System.err.println("Error ejecutando la búsqueda de citas:");
            e.printStackTrace();
        }
    }

    public static void ejecutarBusquedaPacientes(String archivo, String claveApellido) throws IOException {

        List<Paciente> listaPac = DatasetLoader.cargarPacientes(archivo);
        Paciente[] arrPac = listaPac.toArray(new Paciente[0]);

        ListaEnlazadaSLL<Paciente> listaSLL = new ListaEnlazadaSLL<>();
        for (Paciente p : arrPac) listaSLL.pushBack(p);

        Paciente clave = new Paciente("", claveApellido, 0);

        // ---------------- ARRAY – TIEMPO NORMAL ----------------
        long tnStart = System.nanoTime();
        int first = FirstOcurrence.secuencialFirst(arrPac, clave);
        long tnEnd = System.nanoTime();
        double tiempoNormal = (tnEnd - tnStart) / 1_000_000.0; // ms

        int last = LastOcurrence.secuencialLast(arrPac, clave);
        List<Integer> all = FindAllOcurrences.findAll(arrPac, clave);

        // ---------------- SLL ----------------
        Nodo<Paciente> head = listaSLL.getCabeza();
        Nodo<Paciente> firstSLL = FirstOcurrence.findFirst(head, clave);
        Nodo<Paciente> lastSLL = LastOcurrence.findLast(head, clave);
        List<Nodo<Paciente>> allSLL = FindAllOcurrences.findAll(head, clave);

        int posFirstSLL = (firstSLL != null) ? obtenerPosicionSLL(head, firstSLL) : -1;
        int posLastSLL = (lastSLL != null) ? obtenerPosicionSLL(head, lastSLL) : -1;

        List<Integer> posicionesSLL = new ArrayList<>();
        for (Nodo<Paciente> n : allSLL) posicionesSLL.add(obtenerPosicionSLL(head, n));

        // ---------------- CENTINELA ----------------
        long tcStart = System.nanoTime();
        int posC = Centinela.secuencialCentinela(arrPac, clave);
        long tcEnd = System.nanoTime();
        double tiempoCentinela = (tcEnd - tcStart) / 1_000_000.0; // ms


        System.out.println("\n+==============================================================+");
        System.out.println("|               RESULTADOS DE BÚSQUEDAS SECUENCIALES             |");
        System.out.println("+==============================================================+");

        System.out.printf("| %-20s | %-15s | %-25s |\n", "TIPO", "RESULTADO", "DETALLE");
        System.out.println("+--------------------------------------------------------------+");

        System.out.printf("| %-20s | %-15s | %-25s |\n",
                "First (Array)", first, (first != -1 ? "pos: " + first : "No encontrado"));

        System.out.printf("| %-20s | %-15s | %-25s |\n",
                "First (SLL)", (firstSLL != null ? firstSLL.dato.apellido : "null"),
                (firstSLL != null ? "pos: " + posFirstSLL : "No encontrado"));

        System.out.printf("| %-20s | %-15s | %-25s |\n",
                "Last (Array)", last, (last != -1 ? "pos: " + last : "No encontrado"));

        System.out.printf("| %-20s | %-15s | %-25s |\n",
                "Last (SLL)", (lastSLL != null ? lastSLL.dato.apellido : "null"),
                (lastSLL != null ? "pos: " + posLastSLL : "No encontrado"));

        System.out.printf("| %-20s | %-15s | %-25s |\n",
                "FindAll (Array)", all.size(), all);

        System.out.printf("| %-20s | %-15s | %-25s |\n",
                "FindAll (SLL)", posicionesSLL.size(), posicionesSLL);

        System.out.printf("| %-20s | %-15s | %-25s |\n",
                "Centinela (pos)", posC, (posC != -1 ? "OK" : "No encontrado"));

        System.out.println("+==============================================================+");

        System.out.println("\n+==============================================================+");
        System.out.println("|            COMPARACIÓN DE TIEMPOS DE BÚSQUEDA               |");
        System.out.println("+==============================================================+");

        System.out.printf("| %-25s | %-20s |\n", "Método", "Tiempo (ms)");
        System.out.println("+--------------------------------------------------------------+");

        System.out.printf("| %-25s | %-20.6f |\n", "Búsqueda Normal", tiempoNormal);
        System.out.printf("| %-25s | %-20.6f |\n", "Búsqueda con Centinela", tiempoCentinela);

        System.out.println("+==============================================================+");

    }

    public static void ejecutarBusquedaCitas(String archivo, String claveCita) throws IOException {

        List<Cita> listaCitas = DatasetLoader.cargarCitas(archivo);

        InsertionSort.insertionSort(listaCitas, (a, b) -> a.id.compareTo(b.id));

        Cita[] arr = listaCitas.toArray(new Cita[0]);

        Cita clave = new Cita(claveCita, "", null);

        long t1 = System.nanoTime();
        int pos = FindBinary.binaria(arr, clave);
        long t2 = System.nanoTime();

        double tiempoMS = (t2 - t1) / 1_000_000.0; //


        System.out.println("\n+==============================================================+");
        System.out.println("|               RESULTADOS DE BÚSQUEDA BINARIA                 |");
        System.out.println("+==============================================================+");

        System.out.printf("| %-20s | %-15s | %-25s |\n",
                "Campo", "Valor", "Detalle");
        System.out.println("+--------------------------------------------------------------+");

        System.out.printf("| %-20s | %-15s | %-25s |\n",
                "Clave buscada", claveCita, "");

        System.out.printf("| %-20s | %-15s | %-25s |\n",
                "Resultado",
                pos,
                (pos != -1 ? "Cita encontrada" : "No existe"));

        System.out.printf("| %-20s | %-15s | %-25s |\n",
                "Tiempo (ms)",
                String.format("%.6f", tiempoMS),
                "");

        System.out.println("+==============================================================+");
    }
    public static <T> int obtenerPosicionSLL(Nodo<T> cabeza, Nodo<T> nodoBuscado) {
        int index = 0;
        Nodo<T> actual = cabeza;

        while (actual != null) {
            if (actual == nodoBuscado) return index;
            actual = actual.siguiente;
            index++;
        }
        return -1;
    }

    public static void ejecutarPruebasDemo() {

        System.out.println("\n========== PRUEBAS DE VERIFICACIÓN (DEMO) ==========\n");

        Integer[] A = {1, 3, 7, 7, 9};
        Integer[] B = {5, 5, 5, 8};
        Integer[] C = {2, 4, 6, 8};
        Integer[] D = {10, 20, 30};
        Integer[] VACIO = {};

        int[] claves = {7, 5, 2, 42};

        ejecutarPruebaArray("A", A, claves);
        ejecutarPruebaArray("B", B, claves);
        ejecutarPruebaArray("C", C, claves);
        ejecutarPruebaArray("D", D, claves);
        ejecutarPruebaArray("VACÍO", VACIO, claves);

        ListaEnlazadaSLL<Integer> lista = new ListaEnlazadaSLL<>();
        lista.pushBack(3);
        lista.pushBack(1);
        lista.pushBack(3);
        lista.pushBack(2);

        Nodo<Integer> head = lista.getCabeza();

        Nodo<Integer> first3 = FirstOcurrence.findFirst(head, 3);
        Nodo<Integer> last3 = LastOcurrence.findLast(head, 3);


        List<Nodo<Integer>> menores3 = new ArrayList<>();
        Nodo<Integer> aux = head;
        while (aux != null) {
            if (aux.dato < 3) menores3.add(aux);
            aux = aux.siguiente;
        }

        System.out.println("\n+====================================================================+");
        System.out.println("|                    PRUEBAS CON LISTA ENLAZADA (SLL)               |");
        System.out.println("+====================================================================+");

        System.out.println("\nContenido de la lista SLL:");
        System.out.println("3 → 1 → 3 → 2\n");

        System.out.printf("| %-20s | %-15s | %-10s |\n",
                "Operación", "Resultado", "Posición");
        System.out.println("+--------------------------------------------------------------+");

        System.out.printf("| %-20s | %-15s | %-10d |\n",
                "First(3)",
                (first3 != null ? first3.dato : "null"),
                (first3 != null ? obtenerPosicionSLL(head, first3) : -1));

        System.out.printf("| %-20s | %-15s | %-10d |\n",
                "Last(3)",
                (last3 != null ? last3.dato : "null"),
                (last3 != null ? obtenerPosicionSLL(head, last3) : -1));

        System.out.printf("| %-20s | %-15s | %-10s |\n",
                "Valores < 3",
                menores3.toString(),
                "N/A");

        System.out.println("+====================================================================+");


        System.out.println("+====================================================================+");
        System.out.println("✔ Pruebas finalizadas correctamente.\n");
    }
    public static void ejecutarPruebaArray(String nombre, Integer[] arr, int[] claves) {

        System.out.println("\n+======================================================================+");
        System.out.println("|                   PRUEBAS SOBRE ARREGLO: " + nombre + "                      |");
        System.out.println("+======================================================================+");

        // Mostrar contenido del arreglo
        System.out.println("Contenido → " + Arrays.toString(arr) + "\n");

        System.out.printf("| %-10s | %-10s | %-10s | %-10s | %-15s |\n",
                "Array", "Clave", "First", "Last", "FindAll");
        System.out.println("+----------------------------------------------------------------------+");

        for (int clave : claves) {

            System.out.printf("| %-10s | %-10d | %-10d | %-10d | %-15s |\n",
                    nombre,
                    clave,
                    FirstOcurrence.secuencialFirst(arr, clave),
                    LastOcurrence.secuencialLast(arr, clave),
                    FindAllOcurrences.findAll(arr, clave).toString());
        }

        System.out.println("+======================================================================+");
    }

}