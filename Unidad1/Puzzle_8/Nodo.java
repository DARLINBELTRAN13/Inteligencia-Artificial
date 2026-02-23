package puzzle8;

public class Nodo {
    String estado;
    int nivel;       
    Nodo padre;
    int costoAcumulado;  
    
    public Nodo(String estado, int nivel, Nodo padre, int costoAcumulado) {
        this.estado = estado;
        this.nivel = nivel;
        this.padre = padre;
        this.costoAcumulado = costoAcumulado;
    }
    
    @Override
    public String toString() {
        return "Nodo{estado='" + estado + "', nivel=" + nivel + ", costo=" + costoAcumulado + "}";
    }
}