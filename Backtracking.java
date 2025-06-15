
import java.util.ArrayList;
import java.util.List;

public class Backtracking {

    private final int objetivo;
    private final List<Maquina> maquinas;
    private int estados;
    private List<Maquina> mejorSolucion;

    public Backtracking(int objetivo, List<Maquina> maquinas) {
        this.objetivo = objetivo;
        this.maquinas = maquinas;
        this.estados = 0;
        this.mejorSolucion = new ArrayList<>();
    }

    public void backtrack() {
        List<Maquina> usadas = new ArrayList<>();
        _backtrack(usadas, 0, 0);
        mostrarResultados();
    }

    /*
     * Estrategia de resolución Backtracking:
     *
     * - Árbol de exploración: Cada nodo representa una elección parcial de
     * máquinas usadas hasta el momento, con la suma actual de piezas
     * producidas. Se genera explorando recursivamente la inclusión de máquinas
     * desde la posición 'start' en adelante, permitiendo repetir máquinas (ya
     * que la llamada recursiva pasa 'i' como índice).
     *
     * - Estados finales y solución: Un estado final es aquel donde la
     * sumaActual == objetivo. Entre todos los estados finales se busca la
     * solución con la menor cantidad de máquinas usadas.
     *
     * - Podas: 1) Si ya hay una mejor solución encontrada, se podan ramas donde
     *             la cantidad de máquinas usadas más 1 (la próxima máquina a elegir) sea
     *             igual o mayor a la mejor solución actual, ya que no puede mejorar el
     *             resultado. 
     *          2) No se exploran ramas que superen el objetivo (se controla
     *             con el if en el for).
     *
     * Esta función explora todas las combinaciones posibles de máquinas (con
     * repetición) que no excedan el objetivo, buscando minimizar la cantidad de
     * máquinas usadas para alcanzarlo.
     */
    private void _backtrack(List<Maquina> usadas, int sumaActual, int start) {
        estados++;
        if (sumaActual == objetivo) {
            if (mejorSolucion.isEmpty() || usadas.size() < mejorSolucion.size()) {
                mejorSolucion = new ArrayList<>(usadas);
            }
            return;
        }
    
        if (!mejorSolucion.isEmpty() && usadas.size() + 1 >= mejorSolucion.size()) { // Poda 1: Si la solución actual ya es igual o peor que la mejor encontrada, no sigue
            return;
        }

        for (int i = start; i < maquinas.size(); i++) { 
            Maquina m = maquinas.get(i);
            if (sumaActual + m.getPiezas() <= objetivo) { // Poda 2: Solo agrega máquina si no se pasa del objetivo
                usadas.add(m);
                _backtrack(usadas, sumaActual + m.getPiezas(), i);
                usadas.remove(usadas.size() - 1);
            }
        }
    }

    private void mostrarResultados() {
        System.out.println("Backtracking");
        if (mejorSolucion == null) {
            System.out.println("No se encontro solucion.");
        } else {
            System.out.print("Solucion obtenida: ");
            System.out.println(mejorSolucion);

            System.out.println("Piezas producidas: " + objetivo);
            System.out.println("Puestas en funcionamiento: " + mejorSolucion.size());
        }
        System.out.println("Estados generados: " + estados);
    }
}
