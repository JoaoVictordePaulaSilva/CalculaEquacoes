
public class Node<E> {
    private E value;
    private Node<E> filhoEsquerdo;
    private Node<E> filhoDireito;

    // Construtor
    public Node(E value) {
        this.value = value;
        this.filhoEsquerdo = null;
        this.filhoDireito = null;
    }

    

    // Getters e Setters
    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public Node<E> getFilhoEsquerdo() {
        return filhoEsquerdo;
    }

    public void setFilhoEsquerdo(Node<E> filhoEsquerdo) {
        this.filhoEsquerdo = filhoEsquerdo;
    }

    public Node<E> getFilhoDireito() {
        return filhoDireito;
    }

    public void setFilhoDireito(Node<E> filhoDireito) {
        this.filhoDireito = filhoDireito;
    }
}
