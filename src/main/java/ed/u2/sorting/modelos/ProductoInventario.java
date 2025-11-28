package ed.u2.sorting.modelos;


public class ProductoInventario implements Comparable<ProductoInventario> {
    public String id;
    public String insumo;
    public int stock;

    public ProductoInventario(String id, String insumo, int stock) {
        this.id = id;
        this.insumo = insumo;
        this.stock = stock;
    }


    @Override
    public int compareTo(ProductoInventario otro) {
        return this.id.compareToIgnoreCase(otro.id);
    }

    @Override
    public String toString() {
        return id + ";" + insumo + ";" + stock;
    }
}