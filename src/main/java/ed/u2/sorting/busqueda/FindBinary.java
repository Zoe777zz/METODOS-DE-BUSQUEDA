package ed.u2.sorting.busqueda;

public class FindBinary {
    public static <T extends Comparable<T>> int binaria(T[] datos, T objetivo) {
        int inicio = 0;
        int fin = datos.length - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            int comparacion = datos[medio].compareTo(objetivo);

            if (comparacion == 0) return medio;

            if (comparacion < 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return -1;
    }
}
