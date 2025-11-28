package ed.u2.sorting.busqueda;

import ed.u2.sorting.estructuras.Nodo;

import java.util.ArrayList;
import java.util.List;

public class FindAllOcurrences {
    public static <T extends Comparable<T>> List<Integer> findAll(T[] datos, T objetivo) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < datos.length; i++) {
            if (datos[i].compareTo(objetivo) == 0) {
                indices.add(i);
            }
        }
        return indices;
    }

    public static <T extends Comparable<T>> List<Nodo<T>> findAll(Nodo<T> cabeza, T objetivo) {
        List<Nodo<T>> resultados = new ArrayList<>();
        Nodo<T> actual = cabeza;

        while (actual != null) {
            if (actual.dato.compareTo(objetivo) == 0) {
                resultados.add(actual); // Agregar coincidencia
            }
            actual = actual.siguiente;
        }

        return resultados; // Puede estar vacía si no encuentra nada
    }
}
