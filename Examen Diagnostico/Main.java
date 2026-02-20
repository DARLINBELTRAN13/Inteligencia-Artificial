package ExamenD;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        Arbol arbol = new Arbol(80);
 
        arbol.agregarNodo(90);
        arbol.agregarNodo(50);
        arbol.agregarNodo(55);
        arbol.agregarNodo(70);
        arbol.agregarNodo(35);
        arbol.agregarNodo(100);
        
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\n=================== ÁRBOL BINARIO ===================");
            System.out.println("1. INSERTAR NODO");
            System.out.println("2. MOSTRAR ÁRBOL");
            System.out.println("3. VACIAR ÁRBOL");
            System.out.println("4. BUSCAR NODO");
            System.out.println("5. VERIFICAR SI ESTÁ VACÍO");
            System.out.println("6. TOTAL DE NODOS");
            System.out.println("7. SALIR");
            System.out.println("=====================================================");
            System.out.print("SELECCIONE UNA OPCIÓN => ");
            
            int opc = sc.nextInt();
            System.out.println();
            
            switch (opc) {
                case 1:
                    System.out.print("INGRESE EL VALOR DEL NODO A INSERTAR: ");
                    int nuevoNodo = sc.nextInt();
                    if (arbol.agregarNodo(nuevoNodo)) {
                        System.out.println("NODO " + nuevoNodo + " INSERTADO CORRECTAMENTE.");
                    } else {
                        System.out.println("EL NODO " + nuevoNodo + " YA EXISTE.");
                    }
                    break;
                    
                case 2:
                    System.out.println("\n--- RECORRIDOS DEL ÁRBOL ---");
                    System.out.println("1.INORDEN----------------------------------------");
            		System.out.println("2.PREORDEN---------------------------------------");
            		System.out.println("3.POSTORDEN--------------------------------------");
            		System.out.println("SELECCIONE EL RECORRIDO---->");
            		int opc2 = sc.nextInt();
            		switch (opc2) {
            		case 1:arbol.imprimirInorden();;break;
            		case 2: arbol.imprimirPreorden();;break;
            		case 3:arbol.imprimirPostorden();;break;
            		default:System.out.println("ERROR, INGRESE OTRO NUMERO: ");
            		}break;
                case 3:
                    arbol.vaciarArbol();  break;
                    
                case 4:
                    System.out.print("INGRESE EL NODO A BUSCAR: ");
                    int valorBuscar = sc.nextInt();
                    Nodo encontrado = arbol.buscarNodo(valorBuscar);
                    if (encontrado != null) {
                        System.out.println("NODO " + valorBuscar + " ENCONTRADO EN EL ÁRBOL");
                    } else {
                        System.out.println("NODO " + valorBuscar + " NO EXISTE EN EL ÁRBOL");
                    } break;
                    
                case 5:
                    System.out.println("¿EL ÁRBOL ESTÁ VACÍO? "); 
                    if (arbol.vacio() == true) {
						System.out.println("SI");
					} else {
                        System.out.println("NO");
					} break;                  
                case 6:
                    System.out.println("TOTAL DE NODOS EN EL ÁRBOL: " + arbol.totalNodos()); break;
                    
                case 7:
                    continuar = false;
                    System.out.println("LISTO"); break;
                    
                default:
                    System.out.println("OPCIÓN NO VÁLIDA. INTENTE DE NUEVO.");
            }
        }
        
        sc.close();
    }
}