import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EsqueletoJarbas {

    // Matrizes para armazenar dados
    private static final String[] PECAS = {"Peça 1", "Peça 2", "Peça 3"};
    private static final String[] SESSOES = {"Manhã", "Tarde", "Noite"};
    private static final String[] AREAS = {"Plateia A", "Plateia B", "Frisa", "Camarote", "Balcão Nobre"};
    private static final double[] PRECOS_AREAS = {40.00, 60.00, 120.00, 80.00, 250.00};
    private static final boolean[][][] POLTRONAS_OCUPADAS = new boolean[PECAS.length][SESSOES.length][AREAS.length];
    private static final List<Venda> VENDAS = new ArrayList<>(); // Lista para armazenar vendas

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);

        // Menu principal
        while (true) {
            System.out.println("\n\nSistema de Venda de Ingressos Teatro ABC");
            System.out.println("-----------------------------------------");
            System.out.println("1. Comprar Ingresso");
            System.out.println("2. Imprimir Ingresso");
            System.out.println("3. Consultar Estatísticas de Vendas");
            System.out.println("4. Sair");
            System.out.print("Digite sua opção: ");

            int opcao = ler.nextInt();
            ler.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    comprarIngresso(ler);
                    break;
                case 2:
                    imprimirIngresso(ler);
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

    // Classe ValidadorCPF
    static class ValidadorCPF {
        public static boolean validar(String cpf) {
            cpf = cpf.replaceAll("[^0-9]", "");

            if (cpf.length() != 11) {
                return false;
            }

            int num1, num2, num3, num4, num5, num6, num7, num8, num9, num10, num11;
            int soma1, soma2;
            double resto1, resto2;

            num1 = Character.getNumericValue(cpf.charAt(0));
            num2 = Character.getNumericValue(cpf.charAt(1));
            num3 = Character.getNumericValue(cpf.charAt(2));
            num4 = Character.getNumericValue(cpf.charAt(3));
            num5 = Character.getNumericValue(cpf.charAt(4));
            num6 = Character.getNumericValue(cpf.charAt(5));
            num7 = Character.getNumericValue(cpf.charAt(6));
            num8 = Character.getNumericValue(cpf.charAt(7));
            num9 = Character.getNumericValue(cpf.charAt(8));
            num10 = Character.getNumericValue(cpf.charAt(9));
            num11 = Character.getNumericValue(cpf.charAt(10));

            if (num1 == num2 && num2 == num3 && num3 == num4 && num4 == num5 &&
                    num5 == num6 && num6 == num7 && num7 == num8 && num8 == num9 &&
                    num9 == num10 && num10 == num11) {
                return false;
            } else {
                soma1 = num1 * 10 + num2 * 9 + num3 * 8 + num4 * 7 + num5 * 6 +
                        num6 * 5 + num7 * 4 + num8 * 3 + num9 * 2;

                resto1 = (soma1 * 10) % 11;

                if (resto1 == 10) {
                    resto1 = 0;
                }

                soma2 = num1 * 11 + num2 * 10 + num3 * 9 + num4 * 8 + num5 * 7 +
                        num6 * 6 + num7 * 5 + num8 * 4 + num9 * 3 + num10 * 2;

                resto2 = (soma2 * 10) % 11;

                if (resto1 == num10 && resto2 == num11) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    // Funcionalidades do sistema
    private static void comprarIngresso(Scanner ler) {
        System.out.print("Digite o CPF: ");
        String cpf = ler.nextLine();
        if (!ValidadorCPF.validar(cpf)) {
            System.out.println("CPF inválido.");
            return;
        }

        // Exibe as peças disponíveis
        System.out.println("Escolha a peça:");
        for (int i = 0; i < PECAS.length; i++) {
            System.out.println((i + 1) + ". " + PECAS[i]);
        }
        int pecaEscolhida = ler.nextInt();
        ler.nextLine();  // Consome a nova linha

        // Verifica se a peça escolhida é válida
        if (pecaEscolhida < 1 || pecaEscolhida > PECAS.length) {
            System.out.println("Peça inválida.");
            return;
        }

        // Exibe as sessões disponíveis
        System.out.println("Escolha a sessão:");
        for (int i = 0; i < SESSOES.length; i++) {
            System.out.println((i + 1) + ". " + SESSOES[i]);
        }
        int sessaoEscolhida = ler.nextInt();
        ler.nextLine();  // Consome a nova linha

        // Verifica se a sessão escolhida é válida
        if (sessaoEscolhida < 1 || sessaoEscolhida > SESSOES.length) {
            System.out.println("Sessão inválida.");
            return;
        }

        // Exibe as áreas disponíveis
        System.out.println("Escolha a área:");
        for (int i = 0; i < AREAS.length; i++) {
            System.out.println((i + 1) + ". " + AREAS[i] + " - R$" + PRECOS_AREAS[i]);
        }
        int areaEscolhida = ler.nextInt();
        ler.nextLine();  // Consome a nova linha

        // Verifica se a área escolhida é válida
        if (areaEscolhida < 1 || areaEscolhida > AREAS.length) {
            System.out.println("Área inválida.");
            return;
        }

        // Verifica se há poltronas disponíveis na área escolhida
        if (POLTRONAS_OCUPADAS[pecaEscolhida - 1][sessaoEscolhida - 1][areaEscolhida - 1]) {
            System.out.println("Todas as poltronas desta área estão ocupadas.");
            return;
        }

        // Registra a venda
        Venda venda = new Venda(cpf, PECAS[pecaEscolhida - 1], SESSOES[sessaoEscolhida - 1], AREAS[areaEscolhida - 1], PRECOS_AREAS[areaEscolhida - 1]);
        VENDAS.add(venda);
        POLTRONAS_OCUPADAS[pecaEscolhida - 1][sessaoEscolhida - 1][areaEscolhida - 1] = true; // Marca a poltrona como ocupada

        System.out.println("Ingresso comprado com sucesso: " + venda);
    }

    private static void imprimirIngresso(Scanner ler) {
        // TODO: Implementar a função de impressão de ingresso
        System.out.println("Funcionalidade não implementada.");
    }

    private static void gerarEstatisticasVendas() {
        // TODO: Implementar a função de geração de estatísticas de vendas
        System.out.println("Funcionalidade não implementada.");
    }
}

class Venda {
    private String cpf;
    private String peca;
    private String sessao;
    private String area;
    private double valor;

    public Venda(String cpf, String peca, String sessao, String area, double valor) {
        this.cpf = cpf;
        this.peca = peca;
        this.sessao = sessao;
        this.area = area;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "cpf='" + cpf + '\'' +
                ", peca='" + peca + '\'' +
                ", sessao='" + sessao + '\'' +
                ", area='" + area + '\'' +
                ", valor=" + valor +
                '}';
    }
}
