import java.util.Arrays;  //Trabajar con arreglos.
import java.util.LinkedList;
import java.util.List;       //Declarar listas
import java.util.Queue;

public class FordFulkersonUIS {

    // Una COLA busca un camino desde s (0) hasta t (ultimo vertice).
    static boolean buscarCamino(int[][] residual, int[] padre) {
        Arrays.fill(padre, -1);
        Queue<Integer> cola = new LinkedList<>();
        cola.add(0);
        padre[0] = 0;
        int t = residual.length - 1;

        while (!cola.isEmpty()) {
            int u = cola.remove();
            for (int v = 0; v < residual.length; v++) {
                if (padre[v] == -1 && residual[u][v] > 0) {
                    padre[v] = u;
                    if (v == t) return true;
                    cola.add(v);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] capacidad = {
                {0, 20, 10,  0},
                {0,  0, 30, 10},
                {0,  0,  0, 20},
                {0,  0,  0,  0}
        };
        String[] nombre = {"s", "u", "v", "t"};
        int n = capacidad.length;
        int[] padre = new int[n]; //recordar desde qué vértice llegamos a otro
        int[][] residual = new int[n][n];
        for (int i = 0; i < n; i++) {  //filas de matriz
            residual[i] = capacidad[i].clone();
        }

        int flujoTotal = 0;
        while (buscarCamino(residual, padre)) {
            // Una LISTA guarda los vertices del camino encontrado.
            List<Integer> camino = new LinkedList<>();
            for (int v = n - 1; v != 0; v = padre[v]) {
                camino.add(0, v);
            }
            camino.add(0, 0);

            // Buscar la capacidad minima del camino.
            int aumento = Integer.MAX_VALUE;
            for (int i = 0; i < camino.size() - 1; i++) {
                int u = camino.get(i);
                int v = camino.get(i + 1); //vertices de la conexion
                aumento = Math.min(aumento, residual[u][v]);
            }

            //recorre las conexiones del camino.
            for (int i = 0; i < camino.size() - 1; i++) {
                int u = camino.get(i); //vertice i
                int v = camino.get(i + 1);
                residual[u][v] -= aumento; //Resta el flujo enviado a la capacidad residual de la conexión directa.
                residual[v][u] += aumento;
            }

            flujoTotal += aumento;
            System.out.print("Camino: ");
            for (int i = 0; i < camino.size(); i++) {
                if (i > 0) System.out.print(" -> ");
                System.out.print(nombre[camino.get(i)]);
            }
            System.out.println(" | Aumento: " + aumento + " | Total: " + flujoTotal);
        }
        System.out.println("FLUJO MAXIMO = " + flujoTotal);
    }
}