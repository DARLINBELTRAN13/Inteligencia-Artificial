package Puzzle_8;

public class Main {
    public static void main(String[] args) {
        String estadoInicial = "5674*8321";
        Solucion2 solucion = new Solucion2(estadoInicial);
        solucion.solucionar();
    }
}
