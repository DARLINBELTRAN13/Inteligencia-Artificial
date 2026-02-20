package ExamenD;

public class Arbol {
    private Nodo raiz;

    public Arbol() {
        this.raiz = null;
    }

    public Arbol(int valorRaiz) {
        this.raiz = new Nodo(valorRaiz);
        System.out.println("SE INSERTO " + valorRaiz + " COMO RAIZ");
    }

    public boolean vacio() {
        return raiz == null;
    }

    public boolean agregarNodo(int valor) {
        if (vacio()) {
            raiz = new Nodo(valor);
            System.out.println("SE INSERTO " + valor + " COMO RAIZ");
            return true;
        } else {
            return insertar(raiz, valor);
        }
    }

    private boolean insertar(Nodo nodo, int valor) {
        if (valor < nodo.valor) {
            if (nodo.izquierdo == null) {
                nodo.izquierdo = new Nodo(valor);
                System.out.println("SE INSERTO " + valor);
                return true;
            } else {
                return insertar(nodo.izquierdo, valor);
            }
        } else if (valor > nodo.valor) {
            if (nodo.derecho == null) {
                nodo.derecho = new Nodo(valor);
                System.out.println("SE INSERTO " + valor);
                return true;
            } else {
                return insertar(nodo.derecho, valor);
            }
        }
        return false; 
    }

    public Nodo buscarNodo(int valor) {
        return buscar(raiz, valor);
    }

    private Nodo buscar(Nodo nodo, int valor) {
        if (nodo == null || nodo.valor == valor) {
            return nodo;
        }     
        if (valor < nodo.valor) {
            return buscar(nodo.izquierdo, valor);
        } else {
            return buscar(nodo.derecho, valor);
        }
    }

    public boolean existeNodo(int valor) {
        return buscarNodo(valor) != null;
    }

    public void imprimirInorden() {
        System.out.println("RECORRIDO INORDEN:");
        inorden(raiz);
        System.out.println();
    }

    private void inorden(Nodo nodo) {
        if (nodo != null) {
            inorden(nodo.izquierdo);
            System.out.print(nodo.valor + " ");
            inorden(nodo.derecho);
        }
    }

    public void imprimirPreorden() {
        System.out.println("RECORRIDO PREORDEN:");
        preorden(raiz);
        System.out.println();
    }

    private void preorden(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.valor + " ");
            preorden(nodo.izquierdo);
            preorden(nodo.derecho);
        }
    }

    public void imprimirPostorden() {
        System.out.println("RECORRIDO POSTORDEN:");
        postorden(raiz);
        System.out.println();
    }

    private void postorden(Nodo nodo) {
        if (nodo != null) {
            postorden(nodo.izquierdo);
            postorden(nodo.derecho);
            System.out.print(nodo.valor + " ");
        }
    }

    public void vaciarArbol() {
        if (vacio()) {
            System.out.println("EL ÁRBOL YA SE ENCUENTRA VACÍO.");
        } else {
            raiz = null;
            System.out.println("EL ÁRBOL HA SIDO VACIADO.");
        }
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public int totalNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodos(nodo.izquierdo) + contarNodos(nodo.derecho);
    }
}