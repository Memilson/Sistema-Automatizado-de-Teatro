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
}

// Classe representando um Setor
class Setor {
    private String nome; // Nome do setor
    private List<String> sessoes; // Lista de sessões disponíveis para o setor
    private Assento[][] assentos; // Matriz de assentos no setor

    // Construtor que inicializa o setor com um nome, sessões e uma quantidade de assentos
    public Setor(String nome, List<String> sessoes, int linhas, int colunas) {
        this.nome = nome;
        this.sessoes = sessoes;
        this.assentos = new Assento[linhas][colunas];
        int numeroAssento = 1;
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                assentos[i][j] = new Assento(numeroAssento++);
            }
        }
    }

    // Métodos getters para nome do setor e matriz de assentos
    public String getNome() {
        return nome;
    }

    public Assento[][] getAssentos() {
        return assentos;
    }

    // Método para obter as sessões disponíveis para o setor
    public List<String> getSessoes() {
        return sessoes;
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
                    System.out.print("[" + assentos[i][j].getNumero() + "] "); // Número do assento
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
    private List<String> sessoes; // Lista de sessões disponíveis no teatro

    // Construtor que inicializa o teatro com vários setores e seus respectivos assentos
    public Teatro() {
        setores = new ArrayList<>();
        sessoes = new ArrayList<>();
        sessoes.add("Manhã");
        sessoes.add("Tarde");
        sessoes.add("Noite");

        setores.add(new Setor("Camarote", sessoes, 2, 5)); // 2 linhas, 5 colunas
        setores.add(new Setor("Plateia A", sessoes, 5, 10)); // 5 linhas, 10 colunas
        setores.add(new Setor("Plateia B", sessoes, 5, 10)); // 5 linhas, 10 colunas
        setores.add(new Setor("Frisa", sessoes, 3, 10)); // 3 linhas, 10 colunas
        setores.add(new Setor("Balcão Nobre", sessoes, 2, 10)); // 2 linhas, 10 colunas
    }

    // Métodos getters para lista de setores e sessões
    public List<Setor> getSetores() {
        return setores;
    }

    public List<String> getSessoes() {
        return sessoes;
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

    // Método para contar assentos ocupados em um setor específico
    public int assentosOcupadosPorSetor(Setor setor) {
        int ocupados = 0;
        Assento[][] assentos = setor.getAssentos();
        for (int i = 0; i < assentos.length; i++) {
            for (int j = 0; j < assentos[i].length; j++) {
                if (assentos[i][j].isOcupado()) {
                    ocupados++;
                }
            }
        }
        return ocupados;
    }

    // Métodos adicionais para ocupação máxima e mínima por sessão, lucro por sessão, etc.

    // Método para identificar a sessão com maior e menor ocupação de poltronas
    public void ocupacaoMaximaMinimaPorSessao() {
        List<Setor> setores = getSetores();
        int maxOcupacao = Integer.MIN_VALUE;
        int minOcupacao = Integer.MAX_VALUE;
        Setor maxSetor = null;
        Setor minSetor = null;

        for (Setor setor : setores) {
            int ocupacao = assentosOcupadosPorSetor(setor);
            if (ocupacao > maxOcupacao) {
                maxOcupacao = ocupacao;
                maxSetor = setor;
            }
            if (ocupacao < minOcupacao) {
                minOcupacao = ocupacao;
                minSetor = setor;
            }
        }

        if (maxSetor != null && minSetor != null) {
            System.out.println("Setor com maior ocupação: " + maxSetor.getNome() + " (" + maxOcupacao + " assentos ocupados)");
            System.out.println("Setor com menor ocupação: " + minSetor.getNome() + " (" + minOcupacao + " assentos ocupados)");
        }
    }

    // Método para calcular lucro por sessão
    public void lucroPorSessao() {
        List<Setor> setores = getSetores();
        for (Setor setor : setores) {
            int ingressosVendidos = assentosOcupadosPorSetor(setor);
            int lucro = ingressosVendidos * 50; // Assumindo preço fixo de R$50 por ingresso
            System.out.println("Lucro para " + setor.getNome() + ": R$" + lucro);
        }
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

    // Método para listar todos os assentos ocupados
    public static void assentosOcupados(List<Ingresso> ingressos) {
        System.out.println("Assentos ocupados:");
        for (Ingresso ingresso : ingressos) {
            System.out.println("Setor: " + ingresso.getSetor().getNome() + ", Assento: " + ingresso.getAssento().getNumero());
        }
    }

    // Método para identificar a peça com mais e menos ingressos vendidos
    public static void ingressosPorPeca(List<Ingresso> ingressos) {
        // Contagem de ingressos por setor
        int[] ingressosPorSetor = new int[5]; // Cada índice representa um setor (0 a 4)

        for (Ingresso ingresso : ingressos) {
            Setor setor = ingresso.getSetor();
            int indexSetor = getIndexSetor(setor); // Função auxiliar para obter o índice do setor
            ingressosPorSetor[indexSetor]++;
        }

        // Encontrar setor com mais e menos ingressos
        int maxIngressos = Integer.MIN_VALUE;
        int minIngressos = Integer.MAX_VALUE;
        int indexMax = 0;
        int indexMin = 0;

        for (int i = 0; i < ingressosPorSetor.length; i++) {
            if (ingressosPorSetor[i] > maxIngressos) {
                maxIngressos = ingressosPorSetor[i];
                indexMax = i;
            }
            if (ingressosPorSetor[i] < minIngressos && ingressosPorSetor[i] > 0) {
                minIngressos = ingressosPorSetor[i];
                indexMin = i;
            }
        }

        // Mostrar resultados
        List<Setor> setores = new Teatro().getSetores(); // Obter todos os setores do teatro
        System.out.println("Setor com mais ingressos vendidos: " + setores.get(indexMax).getNome() + " (" + maxIngressos + " ingressos)");
        System.out.println("Setor com menos ingressos vendidos: " + setores.get(indexMin).getNome() + " (" + minIngressos + " ingressos)");
    }

    // Método auxiliar para obter o índice do setor
    private static int getIndexSetor(Setor setor) {
        List<Setor> setores = new Teatro().getSetores();
        for (int i = 0; i < setores.size(); i++) {
            if (setores.get(i).getNome().equals(setor.getNome())) {
                return i;
            }
        }
        return -1; // Caso não encontre
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

        int num1, num2, num3, num4, num5, num6, num7, num8, num9, num10, num11;
        int soma1, soma2;
        int resto1, resto2;

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

        // Verifica se todos os números são iguais
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

            if (resto1 != num10) {
                return false;
            }

            soma2 = num1 * 11 + num2 * 10 + num3 * 9 + num4 * 8 + num5 * 7 +
                    num6 * 6 + num7 * 5 + num8 * 4 + num9 * 3 + num10 * 2;

            resto2 = (soma2 * 10) % 11;
            if (resto2 == 10) {
                resto2 = 0;
            }

            return resto2 == num11;
        }
    }
}

// Classe principal para Venda de Ingressos
public class CodigoPrincipal {
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
                    mostrarMenuRelatorio();
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
        List<Setor> setores = teatro.getSetores();
        System.out.println("Escolha o setor:");
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

        // Exibe as sessões disponíveis para o setor escolhido
        List<String> sessoes = setor.getSessoes();
        System.out.println("Sessões disponíveis para " + setor.getNome() + ":");
        for (int i = 0; i < sessoes.size(); i++) {
            System.out.println((i + 1) + ". " + sessoes.get(i));
        }
        int sessaoEscolhida = scanner.nextInt();
        scanner.nextLine();  // Consome a nova linha

        // Verifica se a sessão escolhida é válida
        if (sessaoEscolhida < 1 || sessaoEscolhida > sessoes.size()) {
            System.out.println("Sessão inválida.");
            return;
        }

        String sessao = sessoes.get(sessaoEscolhida - 1);

        // Exibe a matriz de assentos disponíveis na sessão e setor escolhidos
        setor.mostrarAssentos();

        System.out.println("Escolha o assento (número):");
        int assentoEscolhido = scanner.nextInt();
        scanner.nextLine();  // Consome a nova linha

        // Verifica se o assento escolhido é válido
        Assento assento = setor.getAssento(assentoEscolhido);
        if (assento == null || assentoEscolhido < 1) {
            System.out.println("Assento inválido.");
            return;
        }

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

    // Método para exibir o menu de relatórios
    private static void mostrarMenuRelatorio() {
        System.out.println("Escolha o relatório:");
        System.out.println("1. Relatório de Vendas");
        System.out.println("2. Assentos Ocupados");
        System.out.println("3. Peça com Mais e Menos Ingressos Vendidos");
        System.out.println("4. Ocupação Máxima e Mínima por Sessão");
        System.out.println("5. Lucro por Sessão");
        int opcao = scanner.nextInt();
        scanner.nextLine();  // Consome a nova linha

        switch (opcao) {
            case 1:
                Relatorio.gerarRelatorio(ingressos);
                break;
            case 2:
                Relatorio.assentosOcupados(ingressos);
                break;
            case 3:
                Relatorio.ingressosPorPeca(ingressos);
                break;
            case 4:
                teatro.ocupacaoMaximaMinimaPorSessao();
                break;
            case 5:
                teatro.lucroPorSessao();
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }
}

