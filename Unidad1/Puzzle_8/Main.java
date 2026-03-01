package Puzzle_8; 

import java.util.Scanner;

public class Main {
    public static void TipoBusqueda(String estadoInicial) {
        Scanner sc = new Scanner(System.in);
        int opc;
        
        do {
            System.out.println("=== MENÚ DE BÚSQUEDA ===");
            System.out.println("1. Búsqueda Anchura");
            System.out.println("2. Búsqueda Profundidad");
            System.out.println("3. Búsqueda Costo Uniforme");
            System.out.println("4. Búsqueda Heurística");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            
            opc = sc.nextInt();
            
            switch (opc) {
                case 1:
                    BusquedaAnchura solucion = new BusquedaAnchura(estadoInicial);
                    solucion.solucionar();
                    break;
                case 2:
                    BusquedaPofundida solucion1 = new BusquedaPofundida(estadoInicial);
                    solucion1.solucionar();
                    break;
                case 3:
                    BusquedaCostoU solucion2 = new BusquedaCostoU(estadoInicial);
                    solucion2.solucionar();
                    break;
                case 4:
                    BusquedaHeu solucion3 = new BusquedaHeu(estadoInicial);
                    solucion3.solucionar();
                    break;
                case 5:
                    System.out.println("Programa cerrado.");
                    break;
                default:
                    System.out.println("Error, ingrese otro número.");
            }
        } while (opc != 5);
        
        sc.close();  
    }
    
    public static void main(String[] args) {
        String estadoInicial = "5674*8321";
        TipoBusqueda(estadoInicial);
    }
}