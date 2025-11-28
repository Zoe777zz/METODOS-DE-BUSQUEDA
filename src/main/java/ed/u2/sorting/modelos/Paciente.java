package ed.u2.sorting.modelos;


public class Paciente implements Comparable<Paciente> {
    public String id;
    public String apellido;
    public int prioridad;

    public Paciente(String id, String apellido, int prioridad) {
        this.id = id;
        this.apellido = apellido;
        this.prioridad = prioridad;
    }

    @Override
    public int compareTo(Paciente otro) {
        return this.apellido.compareTo(otro.apellido);
    }

    @Override
    public String toString() {
        return id + ";" + apellido + ";" + prioridad;
    }
}