import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe Assento
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

// Classe Setor
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

// Classe Ingresso
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

// Classe Teatro
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

// Classe Relatorio
class Relatorio {
    public static void gerarRelatorio(List<Ingresso> ingressos) {
        System.out.println("Relatório de Vendas:");
        for (Ingresso ingresso : ingressos) {
            System.out.println(ingresso);
        }
    }
}

// Classe ValidadorCPF
class ValidadorCPF {
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

// Classe Principal VendaIngressos
public class ValidadorCPFTeste {
    private static List<Ingresso> ingressos = new ArrayList<>();
    private static Teatro teatro = new Teatro();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Comprar Ingresso");
            System.out.println("2. Gerar Relatório");
            System.out.println("3. Sair");

            int opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    comprarIngresso();
                    break;
                case 2:
                    Relatorio.gerarRelatorio(ingressos);
                    break;
                case 3:
                    scanner.close(); // Fechar o scanner antes de sair
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
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

        List<Setor> setores = teatro.getSetores();
        System.out.println("Escolha o setor:");

        for (int i = 0; i < setores.size(); i++) {
            System.out.println((i + 1) + ". " + setores.get(i).getNome());
        }

        int setorEscolhido = lerInteiro("Escolha um setor: ");

        if (setorEscolhido < 1 || setorEscolhido > setores.size()) {
            System.out.println("Setor inválido.");
            return;
        }

        Setor setor = setores.get(setorEscolhido - 1);
        List<Assento> assentosDisponiveis = new ArrayList<>();

        for (Assento assento : setor.getAssentos()) {
            if (!assento.isOcupado()) {
                assentosDisponiveis.add(assento);
            }
        }

        if (assentosDisponiveis.isEmpty()) {
            System.out.println("Não há assentos disponíveis neste setor.");
            return;
        }

        System.out.println("Assentos disponíveis:");

        for (Assento assento : assentosDisponiveis) {
            System.out.print(assento.getNumero() + " ");
        }

        System.out.println();

        int assentoEscolhido = lerInteiro("Escolha um assento: ");

        if (assentoEscolhido < 1 || assentoEscolhido > assentosDisponiveis.size()) {
            System.out.println("Assento inválido.");
            return;
        }
        Assento assento = assentosDisponiveis.get(assentoEscolhido - 1);

        if (assento.isOcupado()) {
            System.out.println("Assento já ocupado.");
        } else {
            assento.ocupar();
            Ingresso ingresso = new Ingresso(cpf, setor, assento);
            ingressos.add(ingresso);
            System.out.println("Ingresso comprado com sucesso: " + ingresso);
        }
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }
}