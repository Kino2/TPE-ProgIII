
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /*
     * Lee los datos del archivo y resuelve el problema de producción de piezas
     * usando Backtracking y Greedy, mostrando las soluciones y métricas por
     * consola.
     */
    public static void main(String[] args) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get("datos.txt"));

            if (lineas.isEmpty()) {
                System.err.println("El archivo esta vacio.");
                return;
            }

            int objetivo = Integer.parseInt(lineas.get(0).trim());
            List<Maquina> maquinas = new ArrayList<>();

            // Procesar cada línea que representa una máquina
            for (int i = 1; i < lineas.size(); i++) {
                String linea = lineas.get(i).trim();

                if (linea.isEmpty()) {
                    continue; // Ignorar líneas vacías
                }
                String[] partes = linea.split(",");
                if (partes.length != 2) {
                    System.err.println("Linea invalida (esperado: nombre,piezas): " + linea);
                    continue;
                }

                try {
                    String nombre = partes[0].trim();
                    int piezas = Integer.parseInt(partes[1].trim());
                    maquinas.add(new Maquina(nombre, piezas));
                } catch (NumberFormatException e) {
                    System.err.println("Numero invalido en linea: " + linea);
                }
            }

            if (maquinas.isEmpty()) {
                System.err.println("No se cargo ninguna maquina valida.");
                return;
            }

            Solucion sol = new Solucion(objetivo, maquinas);

            sol.backtracking();

            System.out.println("------------------------------------------------");

            sol.greedy();

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("El valor del objetivo no es un numero valido.");
        }
    }
}
