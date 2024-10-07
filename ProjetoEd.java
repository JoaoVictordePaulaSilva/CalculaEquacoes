
import java.util.Scanner;
import java.util.LinkedList;;

public class ProjetoEd { 
//funções

    public static int Char_para_Int(char valor) {
         return valor - '0'; // Converte o caractere para o valor inteiro correspondente
    }

    

    public static Node<Character> criarArvorePosfixa(String posfixa) {
    LinkedList<Node<Character>> pilha = new LinkedList<>();

    for (char c : posfixa.toCharArray()) {
        if (operando(c)) {
            // Cria um nó folha para cada operando e adiciona à pilha
            pilha.addLast(new Node<>(c));
        } else if (operador(c)) {
            // Cria um nó operador com dois filhos
            Node<Character> direito = pilha.removeLast(); // Remove o último nó (filho direito)
            Node<Character> esquerdo = pilha.removeLast(); // Remove o penúltimo nó (filho esquerdo)
            Node<Character> operadorNode = new Node<>(c);
            operadorNode.setFilhoEsquerdo(esquerdo);
            operadorNode.setFilhoDireito(direito);
            pilha.addLast(operadorNode); // Adiciona o nó operador à pilha
        }
    }

    return pilha.removeLast(); // Retorna a raiz da árvore
}
    public static String infixaParaPosfixa(String equacao) {
        char[] operadores = new char[equacao.length()];
        int topo = -1; // Índice do topo da "pilha"
        StringBuilder saida = new StringBuilder();
    
        for (char c : equacao.toCharArray()) {
            if (operando(c)) {
                saida.append(c); // Adiciona operandos diretamente à saída
            } else if (c == '(') {
                operadores[++topo] = c; // Empilha parêntese esquerdo
            } else if (c == ')') {
                // Desempilha até encontrar o parêntese esquerdo
                while (topo != -1 && operadores[topo] != '(') {
                    saida.append(operadores[topo--]);
                }
                topo--; // Remove o parêntese esquerdo
            } else if (operador(c)) {
                while (topo != -1 && precedencia(operadores[topo]) >= precedencia(c)) {
                    saida.append(operadores[topo--]); // Desempilha operadores de maior ou igual precedência
                }
                operadores[++topo] = c; // Empilha o operador atual
            }
        }
    
        // Desempilha o restante da pilha
        while (topo != -1) {
            saida.append(operadores[topo--]);
        }
    
        return saida.toString();
    }


    public static int resolverExpressao(Node<Character> no) {
        if (no == null) return 0;
    
        // Se o nó é folha, retorna o valor numérico
        if (no.getFilhoEsquerdo() == null && no.getFilhoDireito() == null) {
            return Char_para_Int(no.getValue());
        }
    
        // Resolve subárvores
        int valorEsquerdo = resolverExpressao(no.getFilhoEsquerdo());
        int valorDireito = resolverExpressao(no.getFilhoDireito());
    
        // Aplica a operação
        switch (no.getValue()) {
            case '+':
                return valorEsquerdo + valorDireito;
            case '-':
                return valorEsquerdo - valorDireito;
            case '*':
                return valorEsquerdo * valorDireito;
            case '/':
                if (valorDireito == 0) throw new ArithmeticException("Divisão por zero");
                return valorEsquerdo / valorDireito;
            default:
                throw new IllegalArgumentException("Operador desconhecido: " + no.getValue());
        }
    }
    //funções que verifica se os dados da equação estão corretos
    public static boolean operador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Método para verificar se o caractere é um operando
    public static boolean operando(char c) {
        return (c >= '0' && c <= '9');
    }

    public static int precedencia(char c){
        if(c == '+' || c == '-') return 1;
        if(c == '*' || c == '/') return 2; 
        return 0;
    }
    public static char[] verificaEquacao(String equacao) {
        char[] equacaoArray = equacao.toCharArray();
        for (char c : equacaoArray) {
            if (!operador(c) && !operando(c) && c != '(' && c != ')') {
                return null; 
            }
        }
        return equacaoArray; 
    }
   
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ABB<Character> abb = new ABB<Character>();
        int opcaozinha = 0;
        char[] equacaoArray = null;
        
        while (opcaozinha != 5) {
            System.out.println("Menu de opçõeszinhas:");
            System.out.println("Opção 1 - Entrada de expressão notação infixa;");
            System.out.println("Opção 2 - Criação da árvore binária de expressão aritmética;");
            System.out.println("Opção 3 - Exibição da árvore binária de expressão aritmética;");
            System.out.println("Opção 4 - Cálculo da expressão;");
            System.out.println("Opção 5 - Encerrar Programa");
            System.out.println("Escolha uma opçãozinha:");

           opcaozinha = input.nextInt();
           input.nextLine();

            switch (opcaozinha) {
                case 1:
                    System.out.println("Opção 1 selecionada: Entrada da expressão aritmética.");
                    while (true) {
                        System.out.println("Digite a expressão aritimética: ");
                        String equacao = input.nextLine();
                        equacao = equacao.replaceAll("\\s+", "");
                        equacaoArray = verificaEquacao(equacao);
                        if (equacaoArray != null) {
                            System.out.println("Expressão válida!");
                            break;
                        } else {
                            System.out.println("Expressão inválida!");
                        }
                    }
                    System.out.println("Expressão obtida com sucesso");
                    break;

                    case 2:
                    if (equacaoArray == null) {
                        System.out.println("Insira a expressão aritmética válida primeiro (opção 1).");
                    } else {
                        System.out.println("Opção 2: Criação da árvore binária de expressão aritmética.");
                        String equacao = new String(equacaoArray);
                        String posfixa = infixaParaPosfixa(equacao);
                        abb.setRaiz(criarArvorePosfixa(posfixa));
                        System.out.println("Árvore criada com sucesso.");
                    }
                    break;

                case 3:
                    //Importante : Tratar o erro caso a árvore binária não tenha sido criada
                    System.out.println("Opção 3 selecionada: Exibição da árvore binária.");
                    if (abb.getRaiz() == null) {
                        System.out.println("A Árvore ainda não foi criada, vá para opção 2.");
                        break;
                    }
                    
                    //Exibição a árvore em pré-ordem
                    System.out.println("Árvore em Pré-Ordem");
                    abb.preOrdem(); //Chama ela que ela vem.
                    System.out.println();


                    //Exibição da árvore em ordem:
                    System.out.println("Árvore em Ordem: ");
                    abb.emOrdem(); //Chama ela que ela vem.
                    System.out.println();

                    

                    //Exibição da árvore pós-ordem:
                    System.out.println("Árvore em Pós-Ordem:");
                    abb.posOrdem(); //Chama ela que ela vem.
                    System.out.println();

                    break;

                case 4:
                System.out.println("Opção 4 selecionada: Cálculo da expressão.");
                if (abb.getRaiz() == null) {
                    System.out.println("A árvore ainda não foi criada, vá para opção 2.");
                    break;
                }
                int resultado = resolverExpressao(abb.getRaiz());
                System.out.println("Resultado da expressão: " + resultado);
                break;
        
                case 5:
                    System.out.println("Encerrando o programa...");
                    System.out.println("Obrigado por utilizar o programa de árvoreszinhas.");
                    Referênciaszinhas.exibirReferencias();
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }  
        }
        input.close();
    }
}
