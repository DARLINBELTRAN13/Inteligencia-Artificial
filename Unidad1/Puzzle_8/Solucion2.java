package puzzle8;

import java.util.*;

public class Solucion2 {
    String estadoActual;
    String estadoFinal = "1238*4765";
    private List<String> camino = new ArrayList<>();
    private int nodosExplorados;  
    private int costoTotal; 
    private List<Integer> costosPorMovimiento; 
    
    private int[][] movimientos = {
        {1, 3}, {0, 2, 4}, {1, 5},{0, 4, 6}, {1, 3, 5, 7}, {2, 4, 8},{3, 7}, {4, 6, 8}, {5, 7}
    };
    
    public Solucion2(String e) {
        estadoActual = e;
        nodosExplorados = 0;  
        costoTotal = 0;  
        costosPorMovimiento = new ArrayList<>();  
    }
    
    public void solucionar() {
        if (bfs()) {
        	
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
    
    private boolean bfs() {
        Queue<Nodo> cola = new LinkedList<>();
        Set<String> visitados = new HashSet<>();
        
        Nodo raiz = new Nodo(estadoActual, 0, null, 0);  
        cola.add(raiz);
        visitados.add(estadoActual);
        nodosExplorados++;  
        
        while (!cola.isEmpty()) {
            Nodo nodoActual = cola.poll();
            
            if (nodoActual.estado.equals(estadoFinal)) {
                reconstruirCaminoDesdeNodo(nodoActual);
                costoTotal = nodoActual.costoAcumulado;  
                return true;
            }
            
            int posVacia = nodoActual.estado.indexOf('*');
            
            for (int destino : movimientos[posVacia]) {
                String nuevoEstado = intercambiar(nodoActual.estado, posVacia, destino);
                
                if (!visitados.contains(nuevoEstado)) {
                    visitados.add(nuevoEstado);
                    
                  
                    char numeroMovido = nodoActual.estado.charAt(destino);
                    int costoMovimiento;
                    if (numeroMovido == '*') {
                        costoMovimiento = 0;
                    } else {
                        costoMovimiento = Character.getNumericValue(numeroMovido);
                    }
                    
                  
                    Nodo nodoHijo = new Nodo(
                        nuevoEstado, 
                        nodoActual.nivel + 1, 
                        nodoActual,
                        nodoActual.costoAcumulado + costoMovimiento
                    );
                    
                    cola.add(nodoHijo);
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
          System.out.println("Camino de solución ");
      
        
        for (int i = 0; i < camino.size(); i++) {
        	System.out.println("-----------------------");
            System.out.println("Paso " + i + " (nivel " + i + "):");
            imprimirEstado(camino.get(i));
           
           
        }
        
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