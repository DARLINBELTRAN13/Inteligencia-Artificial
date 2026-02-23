package puzzle8;

public class main2 {
    public static void main(String[] args) {
        String estadoInicial = "5674*8321";
        System.out.println("BÚSQUEDA EN PROFUNDIDAD");
        System.out.println("Estado inicial: " + estadoInicial);
        System.out.println("Estado final: 1238*4765");
        System.out.println();
        
        BusquedaPofundida solucion = new BusquedaPofundida(estadoInicial);
        solucion.solucionar();
    }
}