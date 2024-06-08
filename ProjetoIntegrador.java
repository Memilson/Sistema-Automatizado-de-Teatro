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

        // Adicione aqui a lógica completa de validação de CPF, se necessário

        return true;
    }
}

// Classe Principal VendaIngressos
public class ProjetoIntegrador {
    // Variáveis globais para armazenar ingressos vendidos, teatro e scanner
    private static List<Ingresso> ingressos = new ArrayList<>();
    private static Teatro teatro = new Teatro();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Comprar Ingresso");
            System.out.println("2. Gerar Relatório");
            System.out.println("3. Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine();  // Consome a nova linha

            switch (opcao) {
                case 1:
                    comprarIngresso();
                    break;
                case 2:
                    Relatorio.gerarRelatorio(ingressos);
                    break;
                case 3:
                    scanner.close(); // Fecha o Scanner antes de sair
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    // Método para realizar a compra do ingresso
    private static void comprarIngresso() {
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();
        if (!ValidadorCPF.validar(cpf)) {
            System.out.println("CPF inválido.");
            return;
        }

        System.out.println("Escolha o setor:");
        List<Setor> setores = teatro.getSetores();
        for (int i = 0; i < setores.size(); i++) {
            System.out.println((i + 1) + ". " + setores.get(i).getNome());
        }
        int setorEscolhido = scanner.nextInt();
        scanner.nextLine();  // Consome a nova linha

        if (setorEscolhido < 1 || setorEscolhido > setores.size()) {
            System.out.println("Setor inválido.");
            return;
        }

        Setor setor = setores.get(setorEscolhido - 1);

        System.out.println("Escolha o assento:");
        List<Assento> assentos = setor.getAssentos();
        for (Assento assento : assentos) {
            if (!assento.isOcupado()) {
                System.out.print(assento.getNumero() + " ");
            }
        }
        System.out.println();

        int assentoEscolhido = scanner.nextInt();
        scanner.nextLine();  // Consome a nova linha

        if (assentoEscolhido < 1 || assentoEscolhido > assentos.size()) {
            System.out.println("Assento inválido.");
            return;
        }

        Assento assento = setor.getAssento(assentoEscolhido);

        if (assento.isOcupado()) {
            System.out.println("Assento já ocupado.");
        } else {
            assento.ocupar();
            Ingresso ingresso = new Ingresso(cpf, setor, assento);
            ingressos.add(ingresso);
            System.out.println("Ingresso comprado com sucesso: " + ingresso);
        }
    }
}
