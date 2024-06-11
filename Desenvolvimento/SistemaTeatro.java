package Desenvolvimento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private String sessao; // Sessão do ingresso
    private Peca peca; // Peça do ingresso

    // Construtor que inicializa o ingresso com CPF, setor, assento, sessão e peça
    public Ingresso(String cpf, Setor setor, Assento assento, String sessao, Peca peca) {
        this.cpf = cpf;
        this.setor = setor;
        this.assento = assento;
        this.sessao = sessao;
        this.peca = peca;
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

    // Método para obter a sessão do ingresso
    public String getSessao() {
        return sessao;
    }

    // Método para obter a peça do ingresso
    public Peca getPeca() {
        return peca;
    }

    // Método toString para representar o ingresso como uma string
    @Override
    public String toString() {
        return "Ingresso{" +
                "cpf='" + cpf + '\'' +
                ", setor=" + setor.getNome() +
                ", assento=" + assento.getNumero() +
                ", sessao='" + sessao + '\'' +
                ", peça='" + peca.getNome() + '\'' +
                '}';
    }
}



// Classe representando o Teatro
class Teatro {
    private List<Setor> setores; // Lista de setores no teatro
    private List<String> sessoes; // Lista de sessões disponíveis no teatro
    private List<Peca> pecas; // Lista de peças disponíveis no teatro

    // Construtor que inicializa o teatro com vários setores e seus respectivos assentos
    public Teatro() {
        setores = new ArrayList<>();
        sessoes = new ArrayList<>();
        pecas = new ArrayList<>();

        sessoes.add("Manhã");
        sessoes.add("Tarde");
        sessoes.add("Noite");

        pecas.add(new Peca("Hamlet"));
        pecas.add(new Peca("Othello"));
        pecas.add(new Peca("Macbeth"));

        setores.add(new Setor("Camarote", sessoes, 2, 5)); // 2 linhas, 5 colunas
        setores.add(new Setor("Plateia A", sessoes, 5, 10)); // 5 linhas, 10 colunas
        setores.add(new Setor("Plateia B", sessoes, 5, 10)); // 5 linhas, 10 colunas
        setores.add(new Setor("Frisa", sessoes, 3, 10)); // 3 linhas, 10 colunas
        setores.add(new Setor("Balcão Nobre", sessoes, 2, 10)); // 2 linhas, 10 colunas
    }

    // Métodos getters para lista de setores, sessões e peças
    public List<Setor> getSetores() {
        return setores;
    }

    public List<String> getSessoes() {
        return sessoes;
    }

    public List<Peca> getPecas() {
        return pecas;
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

    // Método para gerar o relatório de vendas
    public static void gerarRelatorio(List<Ingresso> ingressos) {
        Map<String, Integer> vendasPorSetor = new HashMap<>();
        Map<String, Integer> vendasPorPeca = new HashMap<>();

        for (Ingresso ingresso : ingressos) {
            String nomeSetor = ingresso.getSetor().getNome();
            String nomePeca = ingresso.getPeca().getNome();

            vendasPorSetor.put(nomeSetor, vendasPorSetor.getOrDefault(nomeSetor, 0) + 1);
            vendasPorPeca.put(nomePeca, vendasPorPeca.getOrDefault(nomePeca, 0) + 1);
        }

        System.out.println("Relatório de Vendas:");
        System.out.println("Vendas por Setor:");
        for (Map.Entry<String, Integer> entry : vendasPorSetor.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " ingressos vendidos");
        }

        System.out.println("Vendas por Peça:");
        for (Map.Entry<String, Integer> entry : vendasPorPeca.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " ingressos vendidos");
        }
    }
}

// Classe representando uma Peça
class Peca {
    private String nome; // Nome da peça

    // Construtor que inicializa a peça com um nome
    public Peca(String nome) {
        this.nome = nome;
    }

    // Método para obter o nome da peça
    public String getNome() {
        return nome;
    }
}

// Classe principal
public class SistemaTeatro {
    private static List<Ingresso> ingressos = new ArrayList<>(); // Lista de ingressos vendidos

    public static void main(String[] args) {
        Teatro teatro = new Teatro();
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\nBem-vindo ao Sistema de Venda de Ingressos do Teatro!");
            System.out.println("1. Comprar Ingresso");
            System.out.println("2. Visualizar Assentos por Setor");
            System.out.println("3. Gerar Relatório de Vendas");
            System.out.println("4. Ocupação máxima e mínima por sessão");
            System.out.println("5. Lucro por sessão");
            System.out.println("6. Sair");

            int escolha = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (escolha) {
                case 1:
                    comprarIngresso(teatro, scanner);
                    break;
                case 2:
                    visualizarAssentos(teatro, scanner);
                    break;
                case 3:
                    Relatorio.gerarRelatorio(ingressos);
                    break;
                case 4:
                    teatro.ocupacaoMaximaMinimaPorSessao();
                    break;
                case 5:
                    teatro.lucroPorSessao();
                    break;
                case 6:
                    continuar = false;
                    System.out.println("Saindo do sistema. Obrigado!");
                    break;
                default:
                    System.out.println("Escolha inválida. Tente novamente.");
                    break;
            }
        }
        scanner.close();
    }

    // Método para comprar um ingresso
    private static void comprarIngresso(Teatro teatro, Scanner scanner) {
        System.out.println("\nSelecione a peça:");
        List<Peca> pecas = teatro.getPecas();
        for (int i = 0; i < pecas.size(); i++) {
            System.out.println((i + 1) + ". " + pecas.get(i).getNome());
        }
        int escolhaPeca = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha
        Peca peca = pecas.get(escolhaPeca - 1);

        System.out.println("Selecione a sessão:");
        List<String> sessoes = teatro.getSessoes();
        for (int i = 0; i < sessoes.size(); i++) {
            System.out.println((i + 1) + ". " + sessoes.get(i));
        }
        int escolhaSessao = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha
        String sessao = sessoes.get(escolhaSessao - 1);

        System.out.println("Selecione o setor:");
        List<Setor> setores = teatro.getSetores();
        for (int i = 0; i < setores.size(); i++) {
            System.out.println((i + 1) + ". " + setores.get(i).getNome());
        }
        int escolhaSetor = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha
        Setor setor = setores.get(escolhaSetor - 1);

        System.out.println("Assentos disponíveis:");
        setor.mostrarAssentos();
        System.out.println("Selecione o número do assento:");
        int numeroAssento = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        Assento assento = setor.getAssento(numeroAssento);
        if (assento != null && !assento.isOcupado()) {
            assento.ocupar();
            System.out.println("Digite o CPF:");
            String cpf = scanner.nextLine();
            Ingresso ingresso = new Ingresso(cpf, setor, assento, sessao, peca);
            ingressos.add(ingresso);
            System.out.println("Ingresso comprado com sucesso!");
            System.out.println(ingresso);
        } else {
            System.out.println("Assento indisponível. Tente novamente.");
        }
    }

        // Método para visualizar assentos disponíveis por setor
        private static void visualizarAssentos(Teatro teatro, Scanner scanner) {
            System.out.println("\nSelecione o setor:");
            List<Setor> setores = teatro.getSetores();
            for (int i = 0; i < setores.size(); i++) {
                System.out.println((i + 1) + ". " + setores.get(i).getNome());
            }
            int escolhaSetor = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha
            Setor setor = setores.get(escolhaSetor - 1);
    
            System.out.println("Assentos no setor " + setor.getNome() + ":");
            setor.mostrarAssentos();
    
            // Continuação do método para permitir ao usuário escolher outra ação após visualizar os assentos
            System.out.println("\nO que você deseja fazer em seguida?");
            System.out.println("1. Comprar Ingresso");
            System.out.println("2. Voltar ao Menu Principal");
    
            int escolha = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha
    
            switch (escolha) {
                case 1:
                    comprarIngresso(teatro, scanner);
                    break;
                case 2:
                    // Voltar ao menu principal
                    break;
                default:
                    System.out.println("Opção inválida. Voltando ao menu principal.");
                    break;
            }
        }
    }
    