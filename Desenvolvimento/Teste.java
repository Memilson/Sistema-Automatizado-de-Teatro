package Desenvolvimento;

im1port java.util.ArrayList; // Importa a classe ArrayList da biblioteca util
import java.util.List; // Importa a interface List da biblioteca util
import java.util.Scanner; // Importa a classe Scanner da biblioteca util

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
            for (int j = 0; j < colunas; j++) {
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
            for (int j = 0; j < colunas; j++) {
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
        // Contagem de ingressos por sessão
        int[] contagem = new int[3]; // Array para contar ingressos vendidos por sessão (0: Manhã, 1: Tarde, 2: Noite)
        for (Ingresso ingresso : ingressos) {
            String sessao = ingresso.getSetor().getSessoes().get(0); // Assumindo que cada setor possui apenas uma sessão
            if (sessao.equals("Manhã")) {
                contagem[0]++;
            } else if (sessao.equals("Tarde")) {
                contagem[1]++;
            } else if (sessao.equals("Noite")) {
                contagem[2]++;
            }
        }

        // Encontrar a sessão com mais e menos ingressos vendidos
        int maxIngressos = Integer.MIN_VALUE;
        int minIngressos = Integer.MAX_VALUE;
        String sessaoMax = null;
        String sessaoMin = null;

        if (contagem[0] > maxIngressos) {
            maxIngressos = contagem[0];
            sessaoMax = "Manhã";
        }
        if (contagem[0] < minIngressos) {
            minIngressos = contagem[0];
            sessaoMin = "Manhã";
        }
        if (contagem[1] > maxIngressos) {
            maxIngressos = contagem[1];
            sessaoMax = "Tarde";
        }
        if (contagem[1] < minIngressos) {
            minIngressos = contagem[1];
            sessaoMin = "Tarde";
        }
        if (contagem[2] > maxIngressos) {
            maxIngressos = contagem[2];
            sessaoMax = "Noite";
        }
        if (contagem[2] < minIngressos) {
            minIngressos = contagem[2];
            sessaoMin = "Noite";
        }

        System.out.println("Sessão com mais ingressos vendidos: " + sessaoMax + " (" + maxIngressos + " ingressos)");
        System.out.println("Sessão com menos ingressos vendidos: " + sessaoMin + " (" + minIngressos + " ingressos)");
    }
}

// Classe principal do programa
public class Teste {
    public static void main(String[] args) {
        Teatro teatro = new Teatro(); // Instanciação do objeto Teatro
        List<Ingresso> ingressos = new ArrayList<>(); // Lista para armazenar os ingressos vendidos
        Scanner scanner = new Scanner(System.in); // Scanner para entrada de dados pelo usuário

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Comprar ingresso");
            System.out.println("2. Mostrar assentos disponíveis");
            System.out.println("3. Gerar relatório de vendas");
            System.out.println("4. Listar assentos ocupados");
            System.out.println("5. Mostrar peça com mais e menos ingressos vendidos");
            System.out.println("6. Mostrar setor com maior e menor ocupação de poltronas");
            System.out.println("7. Mostrar lucro por sessão");
            System.out.println("8. Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    // Processo de compra de ingresso
                    System.out.println("Informe seu CPF:");
                    String cpf = scanner.nextLine();

                    System.out.println("Escolha o setor:");
                    for (Setor setor : teatro.getSetores()) {
                        System.out.println(setor.getNome());
                    }
                    String nomeSetor = scanner.nextLine();
                    Setor setorEscolhido = teatro.getSetor(nomeSetor);

                    if (setorEscolhido == null) {
                        System.out.println("Setor inválido.");
                        break;
                    }

                    setorEscolhido.mostrarAssentos();
                    System.out.println("Escolha o número do assento:");
                    int numeroAssento = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer do scanner
                    Assento assentoEscolhido = setorEscolhido.getAssento(numeroAssento);

                    if (assentoEscolhido == null || assentoEscolhido.isOcupado()) {
                        System.out.println("Assento inválido ou já ocupado.");
                        break;
                    }

                    assentoEscolhido.ocupar();
                    Ingresso ingresso = new Ingresso(cpf, setorEscolhido, assentoEscolhido);
                    ingressos.add(ingresso);
                    System.out.println("Ingresso comprado com sucesso!");
                    break;
                case 2:
                    // Mostrar assentos disponíveis por setor
                    System.out.println("Escolha o setor:");
                    for (Setor setor : teatro.getSetores()) {
                        System.out.println(setor.getNome());
                    }
                    nomeSetor = scanner.nextLine();
                    setorEscolhido = teatro.getSetor(nomeSetor);

                    if (setorEscolhido == null) {
                        System.out.println("Setor inválido.");
                        break;
                    }

                    setorEscolhido.mostrarAssentos();
                    break;
                case 3:
                    // Gerar relatório de vendas
                    Relatorio.gerarRelatorio(ingressos);
                    break;
                case 4:
                    // Listar assentos ocupados
                    Relatorio.assentosOcupados(ingressos);
                    break;
                case 5:
                    // Mostrar peça com mais e menos ingressos vendidos
                    Relatorio.ingressosPorPeca(ingressos);
                    break;
                case 6:
                    // Mostrar setor com maior e menor ocupação de poltronas
                    teatro.ocupacaoMaximaMinimaPorSessao();
                    break;
                case 7:
                    // Mostrar lucro por sessão
                    teatro.lucroPorSessao();
                    break;
                case 8:
                    // Sair do programa
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }
}
