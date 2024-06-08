import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Assento {
    private int numero; // Número do assento
    private boolean ocupado; // Status do assento: true se estiver ocupado, false caso contrário

    // Construtor que inicializa o assento com um número e define como desocupado
    public Assento(int numero) {
        this.numero = numero;
        this.ocupado = false;
    }

    // Método para obter o número do assento
    public int getNumero() {
        return numero;
    }

    // Método para verificar se o assento está ocupado
    public boolean isOcupado() {
        return ocupado;
    }

    // Método para marcar o assento como ocupado
    public void ocupar() {
        this.ocupado = true;
    }
}

class Setor {
    private String nome; // Nome do setor
    private List<Assento> assentos; // Lista de assentos no setor

    // Construtor que inicializa o setor com um nome e uma quantidade de assentos
    public Setor(String nome, int quantidadeAssentos) {
        this.nome = nome;
        this.assentos = new ArrayList<>();
        for (int i = 1; i <= quantidadeAssentos; i++) {
            assentos.add(new Assento(i)); // Adiciona assentos numerados ao setor
        }
    }

    // Método para obter o nome do setor
    public String getNome() {
        return nome;
    }

    // Método para obter a lista de assentos do setor
    public List<Assento> getAssentos() {
        return assentos;
    }

    // Método para obter um assento específico pelo seu número
    public Assento getAssento(int numero) {
        return assentos.get(numero - 1);
    }
}

class Ingresso {
    private String cpf; // CPF do comprador
    private Setor setor; // Setor do ingresso
    private Assento assento; // Assento do ingresso

    // Construtor que inicializa o ingresso com CPF, setor e assento
    public Ingresso(String cpf, Setor setor, Assento assento) {
        this.cpf = cpf;
        this.setor = setor;
        this.assento = assento;
    }

    // Método para obter o CPF do comprador
    public String getCpf() {
        return cpf;
    }

    // Método para obter o setor do ingresso
    public Setor getSetor() {
        return setor;
    }

    // Método para obter o assento do ingresso
    public Assento getAssento() {
        return assento;
    }

    // Método toString para representar o ingresso como uma string
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
    private List<Setor> setores; // Lista de setores no teatro

    // Construtor que inicializa o teatro com vários setores e seus respectivos assentos
    public Teatro() {
        setores = new ArrayList<>();
        setores.add(new Setor("Camarote", 10));
        setores.add(new Setor("Plateia A", 50));
        setores.add(new Setor("Plateia B", 50));
        setores.add(new Setor("Frisa", 30));
        setores.add(new Setor("Balcão Nobre", 20));
    }

    // Método para obter a lista de setores do teatro
    public List<Setor> getSetores() {
        return setores;
    }

    // Método para obter um setor específico pelo seu nome
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
    // Método estático para gerar um relatório de vendas dos ingressos
    public static void gerarRelatorio(List<Ingresso> ingressos) {
        System.out.println("Relatório de Vendas:");
        for (Ingresso ingresso : ingressos) {
            System.out.println(ingresso);
        }
    }
}

// Classe ValidadorCPF
class ValidadorCPF {
    // Método estático para validar um CPF
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


        return true; // Retorna true se passar por todas as verificações
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

        // Exibe os setores disponíveis
        System.out.println("Escolha o setor:");
        List<Setor> setores = teatro.getSetores();
        for (int i = 0; i < setores.size(); i++) {
            System.out.println((i + 1) + ". " + setores.get(i).getNome());
        }
        int setorEscolhido = scanner.nextInt();
        scanner.nextLine();  // Consome a nova linha

        // Verifica se o setor escolhido é válido
        if (setorEscolhido < 1 || setorEscolhido > setores.size()) {
            System.out.println("Setor inválido.");
            return;
        }

        Setor setor = setores.get(setorEscolhido - 1);

        // Exibe os assentos disponíveis no setor escolhido
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

        // Verifica se o assento escolhido é válido
        if (assentoEscolhido < 1 || assentoEscolhido > assentos.size()) {
            System.out.println("Assento inválido.");
            return;
        }

        Assento assento = setor.getAssento(assentoEscolhido);

        // Verifica se o assento já está ocupado
        if (assento.isOcupado()) {
            System.out.println("Assento já ocupado.");
        } else {
            assento.ocupar(); // Marca o assento como ocupado
            Ingresso ingresso = new Ingresso(cpf, setor, assento);
            ingressos.add(ingresso); // Adiciona o ingresso à lista de ingressos vendidos
            System.out.println("Ingresso comprado com sucesso: " + ingresso);
        }
    }
}
