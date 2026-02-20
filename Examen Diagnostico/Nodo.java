package ExamenD;

public class Nodo {
    public int valor;
    public Nodo izquierdo;
    public Nodo derecho;

    public Nodo(int valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
    }
}