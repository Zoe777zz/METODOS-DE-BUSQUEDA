package ed.u2.sorting.busqueda;

public class Centinela {
    public static <T extends Comparable<T>> int secuencialCentinela(T[] datos, T objetivo) {
        int n = datos.length;
        if (n == 0) return -1;

        if (datos[n - 1].compareTo(objetivo) == 0) return n - 1;

        T ultimo = datos[n - 1];
        datos[n - 1] = objetivo;

        int i = 0;
        while (datos[i].compareTo(objetivo) != 0) {
            i++;
        }

        datos[n - 1] = ultimo;

        if (i < n - 1 || ultimo.compareTo(objetivo) == 0) {
            return i;
        }

        return -1;
    }
}
