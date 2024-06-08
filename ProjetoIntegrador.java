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
        // Implementação simples para validar CPF (exemplo simplificado)
        return cpf != null && cpf.matches("\\d{11}");
    }
}

// Classe Principal VendaIngressos
public class VendaIngressos {
    private static List<Ingresso> ingressos = new ArrayList<>();
    private static Teatro teatro = new Teatro();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Comprar Ingresso");
            System.out.println("2. Gerar Relatório");
            System.out.println("3. Sair");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    comprarIngresso();
                    break;
                case 2:
                    Relatorio.gerarRelatorio(ingressos);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void comprarIngresso() {
        System.out.print("Digite o CPF: ");
        String cpf = scanner.next();
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
