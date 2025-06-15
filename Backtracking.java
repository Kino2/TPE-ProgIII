
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

    private void _backtrack(List<Maquina> usadas, int sumaActual, int start) {
        estados++;
        if (sumaActual == objetivo) {
            if (mejorSolucion.isEmpty() || usadas.size() < mejorSolucion.size()) {
                mejorSolucion = new ArrayList<>(usadas);
            }
            return;
        }

        if (!mejorSolucion.isEmpty() && usadas.size() + 1 >= mejorSolucion.size()) {
            return;
        }
        for (int i = start; i < maquinas.size(); i++) {
            Maquina m = maquinas.get(i);
            if (sumaActual + m.getPiezas() <= objetivo) {
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
