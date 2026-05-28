package Puzzle_8;

import java.util.Comparator;

public class NodePriorityComparator implements Comparator<Nodo> {
 
    @Override
    public int compare(Nodo x, Nodo y) {
        //mayor prioridad que el segundo, por lo que debe ir antes en la cola.
        if (x.costoAcumulado < y.costoAcumulado) {
            return -1;
        }
        //menor prioridad que el segundo, por lo que debe ir despu
        if (x.costoAcumulado > y.costoAcumulado) {
            return 1;
        }
        return 0;
    }
}