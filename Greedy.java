
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

    public void resolver() {
        ordenarMaquinasDesc();
        int suma = 0;
        for (Maquina m : maquinas) {
            while (suma + m.getPiezas() <= objetivo) {
                estados++;
                solucion.add(m);
                suma += m.getPiezas();
            }
            if (suma == objetivo) {
                break;
            }
        }
        mostrarResultados();
    }

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
