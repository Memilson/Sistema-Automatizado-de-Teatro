import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe representando um Assento
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

    // Método para desocupar o assento
    public void desocupar() {
        this.ocupado = false;
    }
}

// Classe representando um Setor
class Setor {
    private String nome; // Nome do setor
    private Assento[][] assentos; // Matriz de assentos no setor

    // Construtor que inicializa o setor com um nome e uma quantidade de assentos
    public Setor(String nome, int linhas, int colunas) {
        this.nome = nome;
        this.assentos = new Assento[linhas][colunas];
        int numeroAssento = 1;
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                assentos[i][j] = new Assento(numeroAssento++);
            }
        }
    }

    // Método para obter o nome do setor
    public String getNome() {
        return nome;
    }

    // Método para obter a matriz de assentos do setor
    public Assento[][] getAssentos() {
        return assentos;
    }

    // Método para obter um assento específico pelo seu número
    public Assento getAssento(int numero) {
        for (int i = 0; i < assentos.length; i++) {
            for (int j = 0; j < assentos[i].length; j++) {
                if (assentos[i][j].getNumero() == numero) {
                    return assentos[i][j];
                }
            }
        }
        return null; // Se não encontrar o assento
    }

    // Método para imprimir a matriz de assentos
    public void mostrarAssentos() {
        for (int i = 0; i < assentos.length; i++) {
            for (int j = 0; j < assentos[i].length; j++) {
                if (assentos[i][j].isOcupado()) {
                    System.out.print("[X] "); // Assento ocupado
                } else {
                    System.out.print("[ ] "); // Assento disponível
                }
            }
            System.out.println();
        }
    }
}

// Classe representando um Ingresso
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

// Classe representando o Teatro
class Teatro {
    private List<Setor> setores; // Lista de setores no teatro

    // Construtor que inicializa o teatro com vários setores e seus respectivos assentos
    public Teatro() {
        setores = new ArrayList<>();
        setores.add(new Setor("Camarote", 2, 5)); // 2 linhas, 5 colunas
        setores.add(new Setor("Plateia A", 5, 10)); // 5 linhas, 10 colunas
        setores.add(new Setor("Plateia B", 5, 10)); // 5 linhas, 10 colunas
        setores.add(new Setor("Frisa", 3, 10)); // 3 linhas, 10 colunas
        setores.add(new Setor("Balcão Nobre", 2, 10)); // 2 linhas, 10 colunas
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

// Classe para gerar Relatório
class Relatorio {
    // Método estático para gerar um relatório de vendas dos ingressos
    public static void gerarRelatorio(List<Ingresso> ingressos) {
        System.out.println("Relatório de Vendas:");
        for (Ingresso ingresso : ingressos) {
            System.out.println(ingresso);
        }
    }
}

// Classe para validar CPF
class ValidadorCPF {
    // Método para validar o CPF
    public static boolean validar(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", ""); // Remove caracteres não numéricos

        if (cpf.length() != 11) {
            return false; // CPF deve ter 11 dígitos
        }

        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 == 10 || digito1 == 11) {
            digito1 = 0;
        }
        if (digito1 != cpf.charAt(9) - '0') {
            return false;
        }

        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 == 10 || digito2 == 11) {
            digito2 = 0;
        }
        if (digito2 != cpf.charAt(10) - '0') {
            return false;
        }

        return true;
    }
}

// Classe principal para Venda de Ingressos
public class ValidadorCPFTeste {
    // Variáveis globais
    private static List<Ingresso> ingressos = new ArrayList<>();
    private static Teatro teatro = new Teatro();
    private static Scanner scanner = new Scanner(System.in);

    // Método principal
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
        int opcaoSetor = scanner.nextInt();
        scanner.nextLine();  // Consome a nova linha

        // Verifica se a opção do setor escolhido é válida
        if (opcaoSetor < 1 || opcaoSetor > setores.size()) {
            System.out.println("Opção de setor inválida.");
            return;
        }

        Setor setorEscolhido = setores.get(opcaoSetor - 1);

        // Exibe os assentos disponíveis no setor escolhido
        setorEscolhido.mostrarAssentos();

        // Pede ao usuário para escolher o número do assento
        System.out.print("Escolha o assento (número): ");
        int numeroAssento = scanner.nextInt();
        scanner.nextLine();  // Consome a nova linha

        // Obtém o assento escolhido
        Assento assentoEscolhido = setorEscolhido.getAssento(numeroAssento);

        // Verifica se o assento escolhido é válido
        if (assentoEscolhido == null || assentoEscolhido.isOcupado()) {
            System.out.println("Assento inválido ou já ocupado.");
            return;
        }

        // Marca o assento como ocupado
        assentoEscolhido.ocupar();

        // Cria o ingresso e adiciona à lista de ingressos vendidos
        Ingresso ingresso = new Ingresso(cpf, setorEscolhido, assentoEscolhido);
        ingressos.add(ingresso);

        // Confirmação da compra
        System.out.println("Ingresso comprado com sucesso: " + ingresso);
    }
}
