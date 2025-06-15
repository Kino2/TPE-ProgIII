
import java.util.ArrayList;
import java.util.List;

public class Greedy {

    private final int objetivo;
    private final List<Maquina> maquinas;
    private int estados;
    private final List<Maquina> solucion;

    public Greedy(int objetivo, List<Maquina> maquinas) {
        this.objetivo = objetivo;
        this.maquinas = maquinas;
        this.estados = 0;
        this.solucion = new ArrayList<>();
    }

    /*
 * Estrategia de resolución Greedy:
 * 
 * - Candidatos: el conjunto de máquinas disponibles, cada una con su capacidad de producción.
 * 
 * - Estrategia de selección: se ordenan las máquinas de mayor a menor cantidad de piezas producidas,
 *   priorizando aquellas que produzcan más en una sola puesta en funcionamiento.
 *   En cada paso se elige la máquina más productiva que no exceda el objetivo restante.
 * 
 * - La solución se construye sumando las piezas que produce cada máquina,
 *   eligiendo una máquina a la vez hasta alcanzar el objetivo total.
 *   Cada vez que se usa una máquina, produce su cantidad completa de piezas.
 *   No se puede usar solo una parte de una máquina.
 * 
 * - En cada paso se elige la máquina que más piezas produce sin pasarse del objetivo.
 *   Esta estrategia busca avanzar rápido, pero no siempre garantiza la mejor solución posible.
 * 
 * */
    public void resolver() {
        ordenarMaquinasDesc();
        int suma = 0;
        for (Maquina m : maquinas) {
            while (suma + m.getPiezas() <= objetivo) {  // Repite la máquina mientras no se pase del objetivo
                estados++;
                solucion.add(m);
                suma += m.getPiezas();
            }
            if (suma == objetivo) { // Ya se alcanzó el objetivo, no es necesario seguir probando más máquinas
                break;
            }
        }
        mostrarResultados(); // Muestra la solución encontrada y la cantidad de estados
    }

    // Ordena las máquinas de mayor a menor cantidad de piezas
    public void ordenarMaquinasDesc() {
        maquinas.sort((a, b) -> b.getPiezas() - a.getPiezas());
    }

    private void mostrarResultados() {
        System.out.println("Greedy");
        if (solucion.isEmpty() || calcularSuma(solucion) != objetivo) {
            System.out.println("No se encontro solucion.");
        } else {
            System.out.print("Solucion obtenida: ");
            System.out.println(solucion);

            System.out.println("Piezas producidas: " + calcularSuma(solucion));
            System.out.println("Puestas en funcionamiento: " + solucion.size());
        }
        System.out.println("Estados generados: " + estados);
    }

    private int calcularSuma(List<Maquina> lista) {
        int suma = 0;
        for (Maquina m : lista) {
            suma += m.getPiezas();
        }
        return suma;
    }
}
