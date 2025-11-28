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
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> ejecutarMenuPacientes(sc);
                case 2 -> ejecutarMenuCitas(sc);
                case 3 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }

        } while (opcion != 3);
    }


    // ================================================================
    // SUBMENÚ PACIENTES
    // ================================================================
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


    // ================================================================
    // SUBMENÚ CITAS
    // ================================================================
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


    // ================================================================
    //  MÉTODO 1: Búsquedas para PACIENTES (Tablas completas)
    // ================================================================
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


        // ============================================================
        // TABLA 1 — RESULTADOS DE BÚSQUEDAS
        // ============================================================
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


        // ============================================================
        // TABLA 2 — COMPARATIVA DE TIEMPOS
        // ============================================================
        System.out.println("\n+==============================================================+");
        System.out.println("|            COMPARACIÓN DE TIEMPOS DE BÚSQUEDA               |");
        System.out.println("+==============================================================+");

        System.out.printf("| %-25s | %-20s |\n", "Método", "Tiempo (ms)");
        System.out.println("+--------------------------------------------------------------+");

        System.out.printf("| %-25s | %-20.6f |\n", "Búsqueda Normal", tiempoNormal);
        System.out.printf("| %-25s | %-20.6f |\n", "Búsqueda con Centinela", tiempoCentinela);

        System.out.println("+==============================================================+");

        // Comentario automático:
        if (tiempoCentinela < tiempoNormal)
            System.out.println("\n✔ Centinela fue MÁS RÁPIDO en este dataset.\n");
        else
            System.out.println("\n✔ Búsqueda normal fue más rápida (caso poco frecuente).\n");
    }



    // ================================================================
    //  MÉTODO 2: Búsquedas para CITAS (Tablas completas)
    // ================================================================
    public static void ejecutarBusquedaCitas(String archivo, String claveCita) throws IOException {

        List<Cita> listaCitas = DatasetLoader.cargarCitas(archivo);

        // ORDENAR PARA BINARIA
        InsertionSort.insertionSort(listaCitas, (a, b) -> a.id.compareTo(b.id));

        Cita[] arr = listaCitas.toArray(new Cita[0]);

        Cita clave = new Cita(claveCita, "", null);

        long t1 = System.nanoTime();
        int pos = FindBinary.binaria(arr, clave);
        long t2 = System.nanoTime();

        double tiempoMS = (t2 - t1) / 1_000_000.0; //


        // ============================================================
        // TABLA FINAL — CITAS
        // ============================================================
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



    // ================================================================
    // MÉTODO AUXILIAR — POSICIÓN EN SLL
    // ================================================================
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
}
