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
    private String nome;
    private List<String> sessoes;
    private Map<String, Assento[][]> assentos; // Mapa de matrizes de assentos por sessão
    private double preco;
    

    public Setor(String nome, List<String> sessoes, int linhas, int colunas, double preco) {
        this.nome = nome;
        this.sessoes = sessoes;
        this.assentos = new HashMap<>();
        this.preco = preco;

        // Inicializar matrizes de assentos para cada sessão
        for (String sessao : sessoes) {
            Assento[][] matrizAssentos = new Assento[linhas][colunas];
            int numeroAssento = 1;
            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    matrizAssentos[i][j] = new Assento(numeroAssento++);
                }
            }
            assentos.put(sessao, matrizAssentos);
        }
    }

    public Assento[][] getAssentos(String sessao) {
        return assentos.get(sessao);
    }

    public double getPreco() {
        return preco;
    }

    // Métodos getters para nome do setor e sessões
    public String getNome() {
        return nome;
    }

    public List<String> getSessoes() {
        return sessoes;
    }

    // Método para obter um assento específico pelo seu número
    public Assento getAssento(int numero) {
        for (Assento[][] matrizAssentos : assentos.values()) {
            for (int i = 0; i < matrizAssentos.length; i++) {
                for (int j = 0; j < matrizAssentos[i].length; j++) {
                    if (matrizAssentos[i][j].getNumero() == numero) {
                        return matrizAssentos[i][j];
                    }
                }
            }
        }
        return null; // Se não encontrar o assento
    }

    // Método para imprimir a matriz de assentos de uma sessão específica
    public void mostrarAssentos(String sessao) {
        Assento[][] matrizAssentos = assentos.get(sessao);
        if (matrizAssentos == null) {
            System.out.println("Sessão não encontrada para este setor.");
            return;
        }

        for (int i = 0; i < matrizAssentos.length; i++) {
            for (int j = 0; j < matrizAssentos[i].length; j++) {
                if (matrizAssentos[i][j].isOcupado()) {
                    System.out.print("[X] "); // Assento ocupado
                } else {
                    System.out.print("[" + matrizAssentos[i][j].getNumero() + "] "); // Número do assento
                }
            }
            System.out.println();
        }
    }
}

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

    // Construtor que inicializa o teatro com vários setores e seus respectivos
    // assentos
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

        setores.add(new Setor("Camarote1", sessoes, 2, 5, 80.0));
        setores.add(new Setor("Camarote2", sessoes, 2, 5, 80.0));
        setores.add(new Setor("Camarote3", sessoes, 2, 5, 80.0));
        setores.add(new Setor("Camarote4", sessoes, 2, 5, 80.0));
        setores.add(new Setor("Camarote5", sessoes, 2, 5, 80.0));
        setores.add(new Setor("Plateia A", sessoes, 5, 10, 40.0));
        setores.add(new Setor("Plateia B", sessoes, 5, 10, 60.0));
        setores.add(new Setor("Frisa1", sessoes, 1, 5, 120.0));
        setores.add(new Setor("Frisa2", sessoes, 1, 5, 120));
        setores.add(new Setor("Frisa3", sessoes, 1, 5, 120.0));
        setores.add(new Setor("Frisa4", sessoes, 1, 5, 120.0));
        setores.add(new Setor("Frisa5", sessoes, 1, 5, 120.0));
        setores.add(new Setor("Frisa6", sessoes, 1, 5, 120.0));
        setores.add(new Setor("Balcão Nobre", sessoes, 2, 10, 250.0));
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
    public void definirSessoesParaSetores() {
        for (Setor setor : setores) {
            setor.getSessoes().addAll(sessoes); // Adiciona as sessões disponíveis a cada setor
        }
    }

    // Método para definir sessões para um setor específico
    public void definirSessoesParaSetor(String nomeSetor, List<String> novasSessoes) {
        Setor setor = getSetor(nomeSetor);
        if (setor != null) {
            setor.getSessoes().clear();
            setor.getSessoes().addAll(novasSessoes);
        } else {
            System.out.println("Setor não encontrado.");
        }
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
        
        // Suponha que você queira contar os assentos ocupados para a primeira sessão disponível
        List<String> sessoes = setor.getSessoes();
        if (!sessoes.isEmpty()) {
            String primeiraSessao = sessoes.get(0);
            Assento[][] assentos = setor.getAssentos(primeiraSessao);
            
            // Agora percorra a matriz de assentos da sessão escolhida
            for (int i = 0; i < assentos.length; i++) {
                for (int j = 0; j < assentos[i].length; j++) {
                    if (assentos[i][j].isOcupado()) {
                        ocupados++;
                    }
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
            System.out.println(
                    "Setor com maior ocupação: " + maxSetor.getNome() + " (" + maxOcupacao + " assentos ocupados)");
            System.out.println(
                    "Setor com menor ocupação: " + minSetor.getNome() + " (" + minOcupacao + " assentos ocupados)");
        }
    }

    // Método para calcular lucro por sessão
    public void lucroPorSessao() {
        List<Setor> setores = getSetores();
        for (Setor setor : setores) {
            int ingressosVendidos = assentosOcupadosPorSetor(setor);
            double precoPorIngresso = setor.getPreco();
            double lucro = ingressosVendidos * precoPorIngresso;
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
                    mostrarAssentos(teatro, scanner);
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

    // Método para mostrar os assentos de todas as sessões do setor
    private static void mostrarAssentos(Teatro teatro, Scanner scanner) {
    List<Setor> setores = teatro.getSetores();

    System.out.println("\nVisualizar Assentos por Setor");
    System.out.println("1. Manhã");
    System.out.println("2. Tarde");
    System.out.println("3. Noite");

    int escolha = pedirInteiro(scanner, "Escolha a sessão: ", 1, 3);
    String sessaoEscolhida;

    switch (escolha) {
        case 1:
            sessaoEscolhida = "Manhã";
            break;
        case 2:
            sessaoEscolhida = "Tarde";
            break;
        case 3:
            sessaoEscolhida = "Noite";
            break;
        default:
            System.out.println("Opção inválida.");
            return;
    }

    System.out.println("\nAssentos disponíveis por setor na sessão " + sessaoEscolhida + ":");
    for (Setor setor : setores) {
        System.out.println("\nSetor: " + setor.getNome());
        setor.mostrarAssentos(sessaoEscolhida); // Chama o método mostrarAssentos de Setor passando a sessão escolhida como argumento
    }
}


    private static void comprarIngresso(Teatro teatro, Scanner scanner) {
    System.out.print("Digite o CPF: ");
    String cpf = scanner.nextLine();
    while (!ValidadorCPF.validar(cpf)) {
        System.out.println("CPF inválido.");
        System.out.print("Digite o CPF novamente: ");
        cpf = scanner.nextLine();
    }

    System.out.println("\nSelecione a peça:");
    List<Peca> pecas = teatro.getPecas();
    for (int i = 0; i < pecas.size(); i++) {
        System.out.println((i + 1) + ". " + pecas.get(i).getNome());
    }
    int escolhaPeca = pedirInteiro(scanner, "Escolha a peça: ", 1, pecas.size());
    Peca peca = pecas.get(escolhaPeca - 1);

    System.out.println("\nSelecione a sessão:");
    List<String> sessoes = teatro.getSessoes();
    for (int i = 0; i < sessoes.size(); i++) {
        System.out.println((i + 1) + ". " + sessoes.get(i));
    }
    int escolhaSessao = pedirInteiro(scanner, "Escolha a sessão: ", 1, sessoes.size());
    String sessaoEscolhida = sessoes.get(escolhaSessao - 1);

    System.out.println("\nSelecione o setor:");
    List<Setor> setores = teatro.getSetores();
    for (int i = 0; i < setores.size(); i++) {
        Setor setor = setores.get(i);
        System.out.println((i + 1) + ". " + setor.getNome() + " - R$" + setor.getPreco());
    }
    int escolhaSetor = pedirInteiro(scanner, "Escolha o setor: ", 1, setores.size());
    Setor setorEscolhido = setores.get(escolhaSetor - 1);

    // Mostrar assentos disponíveis para o setor e sessão escolhidos
    Assento[][] assentosDisponiveis = setorEscolhido.getAssentos(sessaoEscolhida);
    System.out.println("\nAssentos disponíveis para " + setorEscolhido.getNome() + " na sessão " + sessaoEscolhida + ":");
    for (int i = 0; i < assentosDisponiveis.length; i++) {
        for (int j = 0; j < assentosDisponiveis[i].length; j++) {
            if (assentosDisponiveis[i][j].isOcupado()) {
                System.out.print("[X] ");
            } else {
                System.out.print("[" + assentosDisponiveis[i][j].getNumero() + "] ");
            }
        }
        System.out.println();
    }

    // Pedir ao usuário o número do assento
    int numeroAssento = pedirInteiro(scanner, "Escolha o número do assento: ", 1,
            assentosDisponiveis.length * assentosDisponiveis[0].length);

    Assento assento = assentosDisponiveis[(numeroAssento - 1) / assentosDisponiveis[0].length][(numeroAssento - 1)
            % assentosDisponiveis[0].length];
    if (assento != null && !assento.isOcupado()) {
        assento.ocupar();
        Ingresso ingresso = new Ingresso(cpf, setorEscolhido, assento, sessaoEscolhida, peca);
        ingressos.add(ingresso);
        System.out.println("\nIngresso comprado com sucesso!");
        System.out.println(ingresso);
        System.out.println("Valor total: R$" + setorEscolhido.getPreco()); // Exibe o valor total pago
    } else {
        System.out.println("\nAssento indisponível. Tente novamente.");
    }
}

    

    // Método utilitário para pedir um número inteiro dentro de um intervalo
    // específico
    private static int pedirInteiro(Scanner scanner, String mensagem, int min, int max) {
        int escolha;
        do {
            System.out.print(mensagem);
            while (!scanner.hasNextInt()) {
                System.out.print("Escolha inválida. " + mensagem);
                scanner.next(); // Consumir entrada inválida
            }
            escolha = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha após o número inteiro
        } while (escolha < min || escolha > max);
        return escolha;
    }
}