
import java.util.LinkedList;

public class ABB<E extends Comparable<E>> {

    // Raiz da árvore
    private Node<E> raiz;

    // Construtor
    public ABB() {
        raiz = null; // Inicializa a árvore como vazia
    }

    public Node<E> getRaiz(){
        return raiz;
    }
    
    public void setRaiz(Node<E> novaRaiz) {
        this.raiz = novaRaiz;
    }
    
    // Verifica se a árvore está vazia
    public boolean isEmpty() {
        return (raiz == null);
    }

    // Insere um novo valor na árvore
    public void inserir(E valor) {
        raiz = inserir(valor, raiz);
    }

    private Node<E> inserir(E valor, Node<E> node) {
        if (node == null) {
            return new Node<>(valor); // Cria um novo nó se a posição estiver vazia
        }
        if (valor.compareTo(node.getValue()) < 0) {
            node.setFilhoEsquerdo(inserir(valor, node.getFilhoEsquerdo())); // Insere à esquerda
        } else {
            node.setFilhoDireito(inserir(valor, node.getFilhoDireito())); // Insere à direita
        }
        return node; // Retorna o nó (para reconstruir a árvore)
    }

    // Percorre a árvore em ordem
    public void emOrdem() {
        emOrdem(raiz);
    }

    private void emOrdem(Node<E> node) {
        if (node != null) {
            emOrdem(node.getFilhoEsquerdo()); // Visita o filho esquerdo
            System.out.print(node.getValue() + " "); // Visita o nó atual
            emOrdem(node.getFilhoDireito()); // Visita o filho direito
        }
    }

    // Percorre a árvore em pré-ordem
    public void preOrdem() {
        preOrdem(raiz);
    }

    private void preOrdem(Node<E> node) {
        if (node != null) {
            System.out.print(node.getValue() + " "); // Visita o nó atual
            preOrdem(node.getFilhoEsquerdo()); // Visita o filho esquerdo
            preOrdem(node.getFilhoDireito()); // Visita o filho direito
        }
    }

    // Percorre a árvore em pós-ordem
    public void posOrdem() {
        posOrdem(raiz);
    }
    

    private void posOrdem(Node<E> node) {
        if (node != null) {
            posOrdem(node.getFilhoEsquerdo()); // Visita o filho esquerdo
            posOrdem(node.getFilhoDireito()); // Visita o filho direito
            System.out.print(node.getValue() + " "); // Visita o nó atual
        }
    }

    // Percorre a árvore em nível
    public void emNivel() {
        LinkedList<Node<E>> fila = new LinkedList<>();
        fila.add(raiz); // Adiciona a raiz à fila

        while (!fila.isEmpty()) {
            Node<E> atual = fila.removeFirst(); // Remove o primeiro nó da fila
            if (atual != null) {
                System.out.print(atual.getValue() + " "); // Visita o nó atual
                fila.add(atual.getFilhoEsquerdo()); // Adiciona o filho esquerdo à fila
                fila.add(atual.getFilhoDireito()); // Adiciona o filho direito à fila
            }
        }
    }

    // Método para eliminar um valor
    public boolean eliminar(E valor) {
        if (isEmpty()) return false;
        raiz = eliminar(valor, raiz);
        return true;
    }

    private Node<E> eliminar(E valor, Node<E> node) {
        if (node == null) {
            return null; // Valor não encontrado
        }
        if (valor.compareTo(node.getValue()) < 0) {
            node.setFilhoEsquerdo(eliminar(valor, node.getFilhoEsquerdo())); // Busca à esquerda
        } else if (valor.compareTo(node.getValue()) > 0) {
            node.setFilhoDireito(eliminar(valor, node.getFilhoDireito())); // Busca à direita
        } else { // Nó encontrado
            if (node.getFilhoEsquerdo() == null) {
                return node.getFilhoDireito(); // Retorna filho direito
            } else if (node.getFilhoDireito() == null) {
                return node.getFilhoEsquerdo(); // Retorna filho esquerdo
            } else { // Nó com dois filhos
                Node<E> maior = getMax(node.getFilhoEsquerdo()); // Encontra o maior do filho esquerdo
                node.setValue(maior.getValue()); // Substitui o valor do nó
                node.setFilhoEsquerdo(eliminar(maior.getValue(), node.getFilhoEsquerdo())); // Remove o nó substituto
            }
        }
        return node; // Retorna o nó atualizado
    }

    private Node<E> getMax(Node<E> node) {
        while (node.getFilhoDireito() != null) {
            node = node.getFilhoDireito(); // Navega até o maior
        }
        return node;
    }
}
