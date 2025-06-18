
import java.util.ArrayList;
import java.util.List;

public class Solucion {

    private final int objetivo;
    private final List<Maquina> maquinas;
    private int estados;
    private List<Maquina> resultado;

    public Solucion(int objetivo, List<Maquina> maquinas) {
        this.objetivo = objetivo;
        this.maquinas = maquinas;
        this.estados = 0;
        this.resultado = new ArrayList<>();
    }

    public void backtracking() {
        List<Maquina> usadas = new ArrayList<>();
        this.estados = 0;
        this.resultado.clear();
        _backtracking(usadas, 0, 0);
        System.out.println("Backtracking");
        if (resultado.isEmpty() || calcularSuma(resultado) != objetivo) {
            System.out.println("No se encontro solucion.");
        } else {
            System.out.print("Solucion obtenida: ");
            System.out.println(resultado);

            System.out.println("Piezas producidas: " + calcularSuma(resultado));
            System.out.println("Puestas en funcionamiento: " + resultado.size());
        }
        System.out.println("Estados generados: " + estados);
    }

    /*
 * Estrategia de resolución Backtracking:
 * - Árbol de exploración:
 *   Se genera considerando, en cada nivel, todas las máquinas posibles desde la posición actual.
 *   Como en la llamada recursiva se vuelve a pasar el mismo índice (i), se permite repetir máquinas.
 *   Ejemplo con máquinas que producen [7, 3, 4, 1] y el objetivo es 12:
 *   []
 *   ├── [7]
 *   │   ├── [7, 3]
 *   │   │   └── [7, 3, 1]
 *   │   │       └── [7, 3, 1, 1]
 *   │   ├── [7, 4]
 *   │   │   └── [7, 4, 1]
 *   │   └── [7, 1]
 *   ├── [3]
 *   │   ├── [3, 3]
 *   │   ├── [3, 4]
 *   │   └── ...
 *   ├── [4]
 *   │   ├── [4, 4]
 *   │   └── ...
 *   └── [1]
 *       ├── [1, 1]
 *       ├── [1, 3]
 *       └── ...
 *   Cada rama representa una combinación parcial de máquinas.
 *   Las hojas pueden representar:
 *   - Estados solución (cuando la suma total es igual al objetivo)
 *   - Estados finales sin solución (cuando se supera el objetivo o no hay más opciones viables)
 * - Estados finales y solución: Un estado final es aquel donde la sumaActual == objetivo.
 *   Entre todos los estados finales se busca la solución con la menor cantidad de máquinas usadas.
 * - Podas:
 *   1) Si ya hay una mejor solución encontrada, se podan ramas donde la cantidad de máquinas usadas más 1 (la próxima máquina a elegir) sea igual o mayor a la mejor solución actual, ya que no puede mejorar el resultado.
 *   2) No se exploran ramas que superen el objetivo (se controla con el if en el for).
 * - Esta función explora todas las combinaciones posibles de máquinas (con repetición) que no excedan el objetivo, buscando minimizar la cantidad de máquinas usadas para alcanzarlo.
 * - Métrica para analizar el costo de la solución:
 *   Se utiliza la cantidad de estados generados (nodos explorados en el árbol de backtracking), representada por la variable 'estados'. Esta métrica refleja la complejidad exploratoria del algoritmo en función de las decisiones probadas para alcanzar (o no) el objetivo.
     */
    private void _backtracking(List<Maquina> usadas, int sumaActual, int start) {
        estados++;
        if (sumaActual == objetivo) {
            if (resultado.isEmpty() || usadas.size() < resultado.size()) {
                resultado = new ArrayList<>(usadas);
            }
            return;
        }
        if (!resultado.isEmpty() && usadas.size() + 1 >= resultado.size()) { // Poda 1: Si la solución actual ya es igual o peor que la mejor encontrada, no sigue
            return;
        }
        for (int i = start; i < maquinas.size(); i++) {
            Maquina m = maquinas.get(i);
            if (sumaActual + m.getPiezas() <= objetivo) { // Poda 2: Solo agrega máquina si no se pasa del objetivo
                usadas.add(m);
                _backtracking(usadas, sumaActual + m.getPiezas(), i);
                usadas.remove(usadas.size() - 1);
            }
        }
    }

    public void greedy() {
        this.estados = 0;
        this.resultado.clear();
        _greedy();
        System.out.println("Greedy");
        if (resultado.isEmpty() || calcularSuma(resultado) != objetivo) {
            System.out.println("No se encontro solucion.");
        } else {
            System.out.print("Solucion obtenida: ");
            System.out.println(resultado);

            System.out.println("Piezas producidas: " + calcularSuma(resultado));
            System.out.println("Puestas en funcionamiento: " + resultado.size());
        }
        System.out.println("Candidatos considerados: " + estados);
    }

    /*
 * Estrategia de resolución Greedy:
 * - Candidatos: el conjunto de máquinas disponibles, cada una con su capacidad de producción.
 * - Estrategia de selección: se ordenan las máquinas de mayor a menor cantidad de piezas producidas,
 *   priorizando aquellas que produzcan más en una sola puesta en funcionamiento.
 *   En cada paso se elige la máquina más productiva que no exceda el objetivo restante.
 * - La solución se construye sumando las piezas que produce cada máquina,
 *   eligiendo una máquina a la vez hasta alcanzar el objetivo total.
 *   Cada vez que se usa una máquina, produce su cantidad completa de piezas.
 *   No se puede usar solo una parte de una máquina.
 * - En cada paso se elige la máquina que más piezas produce sin pasarse del objetivo.
 *   Esta estrategia busca avanzar rápido, pero no siempre garantiza la mejor solución posible.
 * - La solución decidimos considerarla válida si la suma total de piezas es igual al objetivo, es decir que para algunos casos
 *   puede que no se encuentre una solución.
 * - Se trabaja con una copia de la lista de máquinas ordenada de mayor a menor
 *   para no modificar el orden original. Esto permite que Backtracking (si se ejecutara después que Greedy),
 *   explore todas las combinaciones posibles sin estar influenciado por el orden impuesto por Greedy.
     */
    private void _greedy() {
        List<Maquina> maquinasOrdenadas = new ArrayList<>(maquinas);
        ordenarMaquinasDesc(maquinasOrdenadas);
        int suma = 0;
        for (Maquina m : maquinasOrdenadas) {
            while (suma + m.getPiezas() <= objetivo) {  // Repite la máquina mientras no se pase del objetivo
                estados++; //Son los candidatos
                resultado.add(m);
                suma += m.getPiezas();
            }
            if (suma == objetivo) { // Ya se alcanzó el objetivo, no es necesario seguir probando más máquinas
                break;
            }
        }
    }

    // Ordena las máquinas de mayor a menor cantidad de piezas
    private void ordenarMaquinasDesc(List<Maquina> maquinas) {
        maquinas.sort((a, b) -> b.getPiezas() - a.getPiezas());
    }

    // Calcula la suma total de piezas producidas por una lista de máquinas
    private int calcularSuma(List<Maquina> lista) {
        int suma = 0;
        for (Maquina m : lista) {
            suma += m.getPiezas();
        }
        return suma;
    }
}
