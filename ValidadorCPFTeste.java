import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Assento {
    private int numero;
    private boolean ocupado;

    public Assento(int numero) {
        this.numero = numero;
        this.ocupado = false;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void ocupar() {
        this.ocupado = true;
    }
}

class Setor {
    private String nome;
    private List<Assento> assentos;

    public Setor(String nome, int quantidadeAssentos) {
        this.nome = nome;
        this.assentos = new ArrayList<>();
        for (int i = 1; i <= quantidadeAssentos; i++) {
            assentos.add(new Assento(i));
        }
    }

    public String getNome() {
        return nome;
    }

    public List<Assento> getAssentos() {
        return assentos;
    }

    public Assento getAssento(int numero) {
        return assentos.get(numero - 1);
    }
}

class Ingresso {
    private String cpf;
    private Setor setor;
    private Assento assento;

    public Ingresso(String cpf, Setor setor, Assento assento) {
        this.cpf = cpf;
        this.setor = setor;
        this.assento = assento;
    }

    public String getCpf() {
        return cpf;
    }

    public Setor getSetor() {
        return setor;
    }

    public Assento getAssento() {
        return assento;
    }

    @Override
    public String toString() {
        return "Ingresso{" +
                "cpf='" + cpf + '\'' +
                ", setor=" + setor.getNome() +
                ", assento=" + assento.getNumero() +
                '}';
    }
}

class Teatro {
    private List<Setor> setores;

    public Teatro() {
        setores = new ArrayList<>();
        setores.add(new Setor("Camarote", 10));
        setores.add(new Setor("Plateia A", 50));
        setores.add(new Setor("Plateia B", 50));
        setores.add(new Setor("Frisa", 30));
        setores.add(new Setor("Balcão Nobre", 20));
    }

    public List<Setor> getSetores() {
        return setores;
    }

    public Setor getSetor(String nome) {
        for (Setor setor : setores) {
            if (setor.getNome().equalsIgnoreCase(nome)) {
                return setor;
            }
        }
        return null;
    }
}

class Relatorio {
    public static void gerarRelatorio(List<Ingresso> ingressos) {
        System.out.println("Relatório de Vendas:");
        for (Ingresso ingresso : ingressos) {
            System.out.println(ingresso);
        }
    }
}

class ValidadorCPF {
    public static boolean validar(String cpf) {
        if (cpf == null) {
            return false;
        }
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) {
            return false;
        }
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
        return true;
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

public class ProjetoIntegrador {
    private static List<Ingresso> ingressos = new ArrayList<>();
    private static Teatro teatro = new Teatro();
    private static Scanner scanner = new Scanner(System.in);
    
    private static final String[] PECAS = {"Peça 1", "Peça 2", "Peça 3"};
    private static final String[] SESSOES = {"Manhã", "Tarde", "Noite"};
    private static final String[] AREAS = {"Plateia A", "Plateia B", "Frisa", "Camarote", "Balcão Nobre"};
    private static final double[] PRECOS_AREAS = {40.00, 60.00, 120.00, 80.00, 250.00};
    private static final boolean[][][] POLTRONAS_OCUPADAS = new boolean[PECAS.length][SESSOES.length][AREAS.length];
    private static final List<Venda> VENDAS = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n\nSistema de Venda de Ingressos Teatro ABC");
            System.out.println("-----------------------------------------");
            System.out.println("1. Comprar Ingresso");
            System.out.println("2. Imprimir Ingresso");
            System.out.println("3. Consultar Estatísticas de Vendas");
            System.out.println("4. Gerar Relatório");
            System.out.println("5. Sair");
            System.out.print("Digite sua opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    comprarIngresso();
                    break;
                case 2:
                    imprimirIngresso();
                    break;
                case 3:
                    gerarEstatisticasVendas();
                    break;
                case 4:
                    Relatorio.gerarRelatorio(ingressos);
                    break;
                case 5:
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void comprarIngresso() {
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();
        if (!ValidadorCPF.validar(cpf)) {
            System.out.println("CPF inválido.");
            return;
        }

        System.out.println("Escolha a peça:");
        for (int i = 0; i < PECAS.length; i++) {
            System.out.println((i + 1) + ". " + PECAS[i]);
        }
        int pecaEscolhida = scanner.nextInt();
        scanner.nextLine();
        if (pecaEscolhida < 1 || pecaEscolhida > PECAS.length) {
            System.out.println("Peça inválida.");
            return;
        }

        System.out.println("Escolha a sessão:");
        for (int i = 0; i < SESSOES.length; i++) {
            System.out.println((i + 1) + ". " + SESSOES[i]);
        }
        int sessaoEscolhida = scanner.nextInt();
        scanner.nextLine();
        if (sessaoEscolhida < 1 || sessaoEscolhida > SESSOES.length) {
            System.out.println("Sessão inválida.");
            return;
        }

        System.out.println("Escolha a área:");
        for (int i = 0; i < AREAS.length; i++) {
            System.out.println((i + 1) + ". " + AREAS[i] + " - R$" + PRECOS_AREAS[i]);
        }
        int areaEscolhida = scanner.nextInt();
        scanner.nextLine();
        if (areaEscolhida < 1 || areaEscolhida > AREAS.length) {
            System.out.println("Área inválida.");
            return;
        }

        if (POLTRONAS_OCUPADAS[pecaEscolhida - 1][sessaoEscolhida - 1][areaEscolhida - 1]) {
            System.out.println("Todas as poltronas desta área estão ocupadas.");
            return;
        }

        Venda venda = new Venda(cpf, PECAS[pecaEscolhida - 1], SESSOES[sessaoEscolhida - 1], AREAS[areaEscolhida - 1], PRECOS_AREAS[areaEscolhida - 1]);
        VENDAS.add(venda);
        POLTRONAS_OCUPADAS[pecaEscolhida - 1][sessaoEscolhida - 1][areaEscolhida - 1] = true;

        System.out.println("Ingresso comprado com sucesso: " + venda);
    }

    private static void imprimirIngresso() {
        // TODO: Implementar a função de impressão de ingresso
        System.out.println("Funcionalidade não implementada.");
    }

    private static void gerarEstatisticasVendas() {
        double totalVendas = 0;
        int totalIngressos = 0;

        for (Venda venda : VENDAS) {
            totalVendas += venda.valor;
            totalIngressos++;
        }

        System.out.println("Estatísticas de Vendas:");
        System.out.println("Total de ingressos vendidos: " + totalIngressos);
        System.out.println("Total arrecadado: R$" + totalVendas);
    }
}
