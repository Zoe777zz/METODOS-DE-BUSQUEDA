package ed.u2.sorting.busqueda;

import ed.u2.sorting.estructuras.Nodo;

public class LastOcurrence {
    public static <T extends Comparable<T>> int secuencialLast(T[] datos, T objetivo) {
        for (int i = datos.length - 1; i >= 0; i--) {
            if (datos[i].compareTo(objetivo) == 0) {
                return i;
            }
        }
        return -1;
    }


    public static <T extends Comparable<T>> Nodo<T> findLast(Nodo<T> cabeza, T objetivo) {
        Nodo<T> actual = cabeza;
        Nodo<T> ultimoEncontrado = null;

        while (actual != null) {
            if (actual.dato.compareTo(objetivo) == 0) {
                ultimoEncontrado = actual;
            }
            actual = actual.siguiente;
        }

        return ultimoEncontrado;
    }
}
