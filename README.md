# Implementación de Búsquedas en Java

## Informacion del equipo
### Integrantes
- Francis Valdivieso
- Soledad Buri
- Santiago Villamagua
- Narcisa Pinzón

El siguiente documento presenta la implementación de algunos métodos de búsqueda en **arreglos (arrays)** y **listas enlazadas simples (SLL)** acorde a los pasos indicados, también se incluyen las descripciones, algortimos empleados, casos criticos a considerarse y se detalla de la demo de pruebas

---

## **Estructura general del proyecto**

El proyecto consta de los siguientes archivos:

* `Nodo.java` → Estructura básica de nodo para SLL.
* `Search.java` → Contiene todas las funciones de búsqueda pedidas.
* `SearchDemo.java` → Ejecuta pruebas verificables.
---

# Paso 1: Primera ocurrencia

### Arrays: `indexOfFirst(int[] a, int key)`

Recorre el arreglo desde el inicio, buscando la primera aparicion de un dato

**Casos borde considerados:**

* Si un dato no aparece, el programa devuelve un resultado `-1`.
* Un solo elemento.
* Si se encuentran duplicados al inicio, medio o al final.

### SLL: `findFirst(Node head, int key)`

En una lista enlazada, el programa recorre nodo por nodo hasta dar con la primera aparicion del dato solicitado

**Casos borde:**
* Si la lista esta vacía, ya que el programa no tendria nada que valorar
* Si la lista tiene un solo nodo, ya que el programa no debe avanzar con el arreglo mas pequeño
* Si se encuentran varias veces el mismo dato, ya que el programa debe finalizar al encontrar una sola vez el datp
  
---

# Paso 2: Última ocurrencia

### Arrays: `indexOfLast(int[] a, int key)`

Se recorre toda la lista **una vez unicamente** almacenando solamente la ultima posicion en donde se encontro el valor

### SLL: `findLast(Node head, int key)`

Al igual que con arreglos: una recorrido que guarda solamente el último nodo que conincida.

**Casos considerados:**

* EL dato no existe, lo que produciria errores al mostrar resultados si no se controla
* Todas las posiciones coinciden, lo que permite demostrar que realmente el programa sigue el recorrido hasta el final

---

# Paso 3: `findAll` por predicado
Devuelve todas las apariciones de un dato

### Arrays: `findAll(int[] a, IntPredicate p)`

El programa muestra una lista de índices donde el predicado se cumple.

### SLL: `findAll(Node head, Predicate<Node> p)`

Al igual que arreglos, solo que con una lista de nodos

---

# Paso 4: Búsqueda secuencial con centinela

Se aplica solo en arrays.

### Técnica usada:

1. Guardar el último elemento.
2. Escribir `key` al final (centinela).
3. Recorrer sin verificar límites.
4. Restaurar el elemento original.
5. Determinar si el hallazgo fue real o artificial.

Permite medir **comparaciones realizadas** vs búsqueda tradicional.

---

# Paso 5: Búsqueda binaria (arrays ordenados)

### Método: `binarySearch(int[] a, int key)`

Iterativa, utilizando:

```
mid = low + (high - low) / 2;
```

Evita overflow.

**Precondición obligatoria:** el arreglo debe estar ordenado ascendentemente.

Opcional: `lowerBound`/`upperBound` (no incluidos, pero se pueden agregar).

---

# Paso 6: Pruebas y Verificación (SearchDemo)

Se probaron:

### Arrays:

* `A = {1,7,3,7,5}`
* `B = {5,5,5}`
* `C = {2,4,6,8}` (ordenado)
* `D = {10,20,30}` (ordenado)

Claves probadas:

```
7, 5, 2, 42
```

### SLL:

Lista:

```
3 → 1 → 3 → 2
```

Pruebas incluidas:

* primera ocurrencia de 3
* última ocurrencia de 3
* predicado `n.value < 3`

---

Ejemplo:

```
A = [1,7,3,7,5], key = 7
indexOfFirst → esperado 1 → observado 1
indexOfLast → esperado 3 → observado 3
```

---

# Conclusiones

El proyecto implementa correctamente todas las variantes de búsqueda solicitadas, cubriendo los casos borde, estructuras lineales y algorítmicas, así como un módulo de pruebas para verificación clara.
* Versión PDF del informe
* Versión con pseudocódigo
