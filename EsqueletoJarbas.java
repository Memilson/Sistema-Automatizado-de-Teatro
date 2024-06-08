import java.util.Scanner;

public class EsqueletoJarbas {

    // Matrizes para armazenar dados
    private static final String[] PECAS = {"Peça 1", "Peça 2", "Peça 3"};
    private static final String[] SESSOES = {"Manhã", "Tarde", "Noite"};
    private static final String[] AREAS = {"Plateia A", "Plateia B", "Frisa", "Camarote", "Balcão Nobre"};
    private static final double[] PRECOS_AREAS = {40.00, 60.00, 120.00, 80.00, 250.00};
    private static final boolean[][][] POLTRONAS_OCUPADAS = new boolean[PECAS.length][SESSOES.length][AREAS.length];
    private static final Venda[] VENDAS = new Venda[100]; // Armazena até 100 vendas

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Menu principal
        while (true) {
            System.out.println("\n\nSistema de Venda de Ingressos Teatro ABC");
            System.out.println("-----------------------------------------");
            System.out.println("1. Comprar Ingresso");
            System.out.println("2. Imprimir Ingresso");
            System.out.println("3. Consultar Estatísticas de Vendas");
            System.out.println("4. Sair");
            System.out.print("Digite sua opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    comprarIngresso(scanner);
                    break;
                case 2:
                    imprimirIngresso(scanner);
                    break;
                case 3:
                    gerarEstatisticasVendas();
                    break;
                case 4:
                    System.out.println("Saindo do sistema...");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Funcionalidades do sistema

    private static void comprarIngresso(Scanner scanner) {
        // Implementar lógica de compra de ingresso
    }

    private static void imprimirIngresso(Scanner scanner) {
        // Implementar lógica de impressão de ingresso
    }

    private static void gerarEstatisticasVendas() {
        // Implementar lógica de geração de estatísticas
    }
}

class Venda {
    // Armazenar dados da venda (CPF, peça, sessão, área, poltrona, valor)
}