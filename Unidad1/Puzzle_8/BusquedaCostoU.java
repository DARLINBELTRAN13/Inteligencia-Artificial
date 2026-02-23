package Puzzle_8;

import java.util.*;

public class BusquedaCostoU {
    String estadoActual;
    String estadoFinal = "1238*4765";
    private List<String> camino = new ArrayList<>();
    private int nodosExplorados;  
    private int costoTotal; 
    private List<Integer> costosPorMovimiento; 
    
    private int[][] movimientos = {
        {1, 3}, {0, 2, 4}, {1, 5}, {0, 4, 6}, {1, 3, 5, 7}, {2, 4, 8}, {3, 7}, {4, 6, 8}, {5, 7}
    };
    
    public BusquedaCostoU(String e) {
        estadoActual = e;
        nodosExplorados = 0;  
        costoTotal = 0;  
        costosPorMovimiento = new ArrayList<>();  
    }
    
    public void solucionar() {
        if (uniformCostSearch()) {
            imprimirSolucion();
            System.out.println("-----------------------");
            System.out.println("Solución encontrada en : " + (camino.size() - 1) + " movimientos");
            System.out.println("Nodos totales explorados: " + nodosExplorados);
            System.out.println("Costo total de la solución: " + costoTotal);  
            System.out.println("-----------------------");
        } else {
            System.out.println("No se encontró solución.");
        }
    }
    
    private boolean uniformCostSearch() {
        // Usamos PriorityQueue con el comparator personalizado
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(new NodePriorityComparator());
        
        // Map para mantener el mejor costo encontrado para cada estado
        Map<String, Integer> mejorCostoEstado = new HashMap<>();
        
        Nodo raiz = new Nodo(estadoActual, 0, null, 0);  
        colaPrioridad.add(raiz);
        mejorCostoEstado.put(estadoActual, 0);
        nodosExplorados++;  
        
        while (!colaPrioridad.isEmpty()) {
            Nodo nodoActual = colaPrioridad.poll();
            
            // Si encontramos la solución
            if (nodoActual.estado.equals(estadoFinal)) {
                reconstruirCaminoDesdeNodo(nodoActual);
                costoTotal = nodoActual.costoAcumulado;  
                return true;
            }
            
            int posVacia = nodoActual.estado.indexOf('*');
            
            for (int destino : movimientos[posVacia]) {
                String nuevoEstado = intercambiar(nodoActual.estado, posVacia, destino);
                
                // Calcular costo del movimiento
                char numeroMovido = nodoActual.estado.charAt(destino);
                int costoMovimiento;
                if (numeroMovido == '*') {
                    costoMovimiento = 0;
                } else {
                    costoMovimiento = Character.getNumericValue(numeroMovido);
                }
                
                int nuevoCosto = nodoActual.costoAcumulado + costoMovimiento;
                
                // Si no hemos visto este estado o encontramos un camino más barato
                if (!mejorCostoEstado.containsKey(nuevoEstado) || nuevoCosto < mejorCostoEstado.get(nuevoEstado)) {
                    
                    mejorCostoEstado.put(nuevoEstado, nuevoCosto);
                    
                    Nodo nodoHijo = new Nodo(
                        nuevoEstado, 
                        nodoActual.nivel + 1, 
                        nodoActual,
                        nuevoCosto
                    );
                    
                    colaPrioridad.add(nodoHijo);
                    nodosExplorados++;  
                }
            }
        }
        
        return false;
    }
    
    public int getNodosExplorados() {
        return nodosExplorados;
    }
    
    public int getCostoTotal() {
        return costoTotal;
    }
    
    public List<Integer> getCostosPorMovimiento() {
        return costosPorMovimiento;
    }
    
    private void reconstruirCaminoDesdeNodo(Nodo nodoFinal) {
        camino.clear();
        costosPorMovimiento.clear();  
        
        Stack<String> pilaEstados = new Stack<>();
        Stack<Integer> pilaCostos = new Stack<>();  
        
        Nodo actual = nodoFinal;
        
        while (actual != null) {
            pilaEstados.push(actual.estado);
            
            if (actual.padre != null) {
                int posVaciaActual = actual.estado.indexOf('*');
                int posVaciaPadre = actual.padre.estado.indexOf('*');
                char numeroMovido = actual.estado.charAt(posVaciaPadre);
                
                int costoMovimiento;
                if (numeroMovido == '*') {
                    costoMovimiento = 0;
                } else {
                    costoMovimiento = Character.getNumericValue(numeroMovido);
                }
                
                pilaCostos.push(costoMovimiento);
            }
            
            actual = actual.padre;
        }
        
        while (!pilaEstados.isEmpty()) {
            camino.add(pilaEstados.pop());
        }
        
        while (!pilaCostos.isEmpty()) {
            costosPorMovimiento.add(pilaCostos.pop());
        }
    }
    
    private String intercambiar(String estado, int i, int j) {
        char[] chars = estado.toCharArray();
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
        return new String(chars);
    }
    
    private void imprimirSolucion() {
        System.out.println("-----------------------");
        System.out.println("Camino de solución (Costo Uniforme)");
        
        for (int i = 0; i < camino.size(); i++) {
            System.out.println("-----------------------");
            System.out.println("Paso " + i + " (nivel " + i + "):");
            if (i > 0) {
                System.out.println("Costo del movimiento: " + costosPorMovimiento.get(i-1));
                System.out.println("Costo acumulado: " + calcularCostoAcumuladoHastaPaso(i));
            }
            imprimirEstado(camino.get(i));
        }
    }
    
    private int calcularCostoAcumuladoHastaPaso(int paso) {
        int costo = 0;
        for (int i = 0; i < paso; i++) {
            costo += costosPorMovimiento.get(i);
        }
        return costo;
    }
    
    private void imprimirEstado(String estado) {
        for (int i = 0; i < 9; i++) {
            System.out.print(estado.charAt(i) + " ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
    }
}