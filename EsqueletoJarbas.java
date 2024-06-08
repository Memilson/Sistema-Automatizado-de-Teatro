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

    public static boolean validar(String cpf) {
        if (cpf == null) {
            return false;
        }

        // Remove caracteres não numéricos do CPF
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (o que invalida o CPF)
        if ("00000000000".equals(cpf) ||
            "11111111111".equals(cpf) ||
            "22222222222".equals(cpf) ||
            "33333333333".equals(cpf) ||
            "44444444444".equals(cpf) ||
            "55555555555".equals(cpf) ||
            "66666666666".equals(cpf) ||
            "77777777777".equals(cpf) ||
            "88888888888".equals(cpf) ||
            "99999999999".equals(cpf)) {
            return false;
        }

        // TODO: Adicionar a validação dos dígitos verificadores do CPF

        return true; // Retorna true se passar por todas as verificações
    }

    // Funcionalidades do sistema
    private static void comprarIngresso(Scanner ler) {
        System.out.print("Digite o CPF: ");
        String cpf = ler.nextLine();
        if (!validar(cpf)) {
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
