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
        System.out.println("Relatório de Vendas:");
        for (Ingresso ingresso : ingressos) {
            System.out.println(ingresso);
        }
    }

    // Método para contar os assentos ocupados
    public static void assentosOcupados(List<Ingresso> ingressos) {
        Map<String, Integer> ocupacaoPorSetor = new HashMap<>();

        for (Ingresso ingresso : ingressos) {
            String setorNome = ingresso.getSetor().getNome();
            ocupacaoPorSetor.put(setorNome, ocupacaoPorSetor.getOrDefault(setorNome, 0) + 1);
        }

        System.out.println("Assentos Ocupados:");
        for (Map.Entry<String, Integer> entry : ocupacaoPorSetor.entrySet()) {
            System.out.println("Setor " + entry.getKey() + ": " + entry.getValue() + " assentos ocupados");
        }
    }

    // Método para encontrar a peça com mais e menos ingressos vendidos
    public static void ingressosPorPeca(List<Ingresso> ingressos) {
        Map<String, Integer> ingressosPorPeca = new HashMap<>();

        for (Ingresso ingresso : ingressos) {
            String pecaNome = ingresso.getPeca().getNome();
            ingressosPorPeca.put(pecaNome, ingressosPorPeca.getOrDefault(pecaNome, 0) + 1);
        }

        String pecaMaisVendida = null;
        String pecaMenosVendida = null;
        int maxIngressos = Integer.MIN_VALUE;
        int minIngressos = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : ingressosPorPeca.entrySet()) {
            if (entry.getValue() > maxIngressos) {
                maxIngressos = entry.getValue();
                pecaMaisVendida = entry.getKey();
            }
            if (entry.getValue() < minIngressos) {
                minIngressos = entry.getValue();
                pecaMenosVendida = entry.getKey();
            }
        }

        System.out.println("Peça com mais ingressos vendidos: " + pecaMaisVendida + " (" + maxIngressos + " ingressos)");
        System.out.println("Peça com menos ingressos vendidos: " + pecaMenosVendida + " (" + minIngressos + " ingressos)");
    }

    // Método para identificar a sessão com maior e menor ocupação de poltronas
    public static void ocupacaoMaximaMinimaPorSessao(List<Ingresso> ingressos) {
        Map<String, Integer> ocupacaoPorSessao = new HashMap<>();

        for (Ingresso ingresso : ingressos) {
            String sessaoNome = ingresso.getSessao();
            ocupacaoPorSessao.put(sessaoNome, ocupacaoPorSessao.getOrDefault(sessaoNome, 0) + 1);
        }

        String sessaoMaisOcupada = null;
        String sessaoMenosOcupada = null;
        int maxOcupacao = Integer.MIN_VALUE;
        int minOcupacao = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : ocupacaoPorSessao.entrySet()) {
            if (entry.getValue() > maxOcupacao) {
                maxOcupacao = entry.getValue();
                sessaoMaisOcupada = entry.getKey();
            }
            if (entry.getValue() < minOcupacao) {
                minOcupacao = entry.getValue();
                sessaoMenosOcupada = entry.getKey();
            }
        }

        System.out.println("Sessão com maior ocupação: " + sessaoMaisOcupada + " (" + maxOcupacao + " poltronas ocupadas)");
        System.out.println("Sessão com menor ocupação: " + sessaoMenosOcupada + " (" + minOcupacao + " poltronas ocupadas)");
    }

    // Método para identificar a peça e a sessão mais e menos lucrativas
    public static void lucroPorPecaSessao(List<Ingresso> ingressos) {
        Map<String, Integer> lucroPorPeca = new HashMap<>();
        Map<String, Integer> lucroPorSessao = new HashMap<>();

        for (Ingresso ingresso : ingressos) {
            String pecaNome = ingresso.getPeca().getNome();
            String sessaoNome = ingresso.getSessao();
            int preco = 50; // Preço fixo de R$50 por ingresso

            lucroPorPeca.put(pecaNome, lucroPorPeca.getOrDefault(pecaNome, 0) + preco);
            lucroPorSessao.put(sessaoNome, lucroPorSessao.getOrDefault(sessaoNome, 0) + preco);
        }

        String pecaMaisLucrativa = null;
        String pecaMenosLucrativa = null;
        String sessaoMaisLucrativa = null;
        String sessaoMenosLucrativa = null;
        int maxLucroPeca = Integer.MIN_VALUE;
        int minLucroPeca = Integer.MAX_VALUE;
        int maxLucroSessao = Integer.MIN_VALUE;
        int minLucroSessao = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : lucroPorPeca.entrySet()) {
            if (entry.getValue() > maxLucroPeca) {
                maxLucroPeca = entry.getValue();
                pecaMaisLucrativa = entry.getKey();
            }
            if (entry.getValue() < minLucroPeca) {
                minLucroPeca = entry.getValue();
                pecaMenosLucrativa = entry.getKey();
            }
        }

        for (Map.Entry<String, Integer> entry : lucroPorSessao.entrySet()) {
            if (entry.getValue() > maxLucroSessao) {
                maxLucroSessao = entry.getValue();
                sessaoMaisLucrativa = entry.getKey();
            }
            if (entry.getValue() < minLucroSessao) {
                minLucroSessao = entry.getValue();
                sessaoMenosLucrativa = entry.getKey();
            }
        }

        System.out.println("Peça mais lucrativa: " + pecaMaisLucrativa + " (R$" + maxLucroPeca + ")");
        System.out.println("Peça menos lucrativa: " + pecaMenosLucrativa + " (R$" + minLucroPeca + ")");
        System.out.println("Sessão mais lucrativa: " + sessaoMaisLucrativa + " (R$" + maxLucroSessao + ")");
        System.out.println("Sessão menos lucrativa: " + sessaoMenosLucrativa + " (R$" + minLucroSessao + ")");
    }

    // Método para calcular o lucro médio do teatro com todas as áreas por peças
    public static void lucroMedioPorPeca(List<Ingresso> ingressos) {
        Map<String, Integer> lucroPorPeca = new HashMap<>();
        Map<String, Integer> ingressosPorPeca = new HashMap<>();

        for (Ingresso ingresso : ingressos) {
            String pecaNome = ingresso.getPeca().getNome();
            int preco = 50; // Preço fixo de R$50 por ingresso

            lucroPorPeca.put(pecaNome, lucroPorPeca.getOrDefault(pecaNome, 0) + preco);
            ingressosPorPeca.put(pecaNome, ingressosPorPeca.getOrDefault(pecaNome, 0) + 1);
        }

        System.out.println("Lucro Médio por Peça:");
        for (Map.Entry<String, Integer> entry : lucroPorPeca.entrySet()) {
            String pecaNome = entry.getKey();
            int totalLucro = entry.getValue();
            int totalIngressos = ingressosPorPeca.get(pecaNome);
            double lucroMedio = (double) totalLucro / totalIngressos;

            System.out.println("Peça " + pecaNome + ": R$" + String.format("%.2f", lucroMedio) + " por ingresso");
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

        // Exibe as peças disponíveis
        List<Peca> pecas = teatro.getPecas();
        System.out.println("Escolha a peça:");
        for (int i = 0; i < pecas.size(); i++) {
            System.out.println((i + 1) + ". " + pecas.get(i).getNome());
        }
        int pecaEscolhida = scanner.nextInt();
        scanner.nextLine();  // Consome a nova linha

        // Verifica se a peça escolhida é válida
        if (pecaEscolhida < 1 || pecaEscolhida > pecas.size()) {
            System.out.println("Peça inválida.");
            return;
        }

        Peca peca = pecas.get(pecaEscolhida - 1);

        // Exibe os assentos disponíveis
        setor.mostrarAssentos();
        System.out.print("Escolha o número do assento: ");
        int numeroAssento = scanner.nextInt();
        scanner.nextLine();  // Consome a nova linha

        // Verifica se o assento escolhido é válido
        Assento assento = setor.getAssento(numeroAssento);
        if (assento == null || assento.isOcupado()) {
            System.out.println("Assento inválido ou já ocupado.");
            return;
        }

        // Marca o assento como ocupado
        assento.ocupar();

        // Cria um novo ingresso e adiciona à lista de ingressos
        Ingresso ingresso = new Ingresso(cpf, setor, assento, sessao, peca);
        ingressos.add(ingresso);

        // Exibe a confirmação da compra do ingresso
        System.out.println("Ingresso comprado com sucesso: " + ingresso);
    }

    // Método para exibir o menu de relatórios
    private static void mostrarMenuRelatorio() {
        System.out.println("1. Relatório de Vendas");
        System.out.println("2. Assentos Ocupados");
        System.out.println("3. Peça com Mais e Menos Ingressos Vendidos");
        System.out.println("4. Sessão com Maior e Menor Ocupação de Poltronas");
        System.out.println("5. Peça e Sessão Mais e Menos Lucrativas");
        System.out.println("6. Lucro Médio do Teatro com Todas as Áreas por Peças");
        System.out.println("7. Sair");
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
                Relatorio.ocupacaoMaximaMinimaPorSessao(ingressos);
                break;
            case 5:
                Relatorio.lucroPorPecaSessao(ingressos);
                break;
            case 6:
                Relatorio.lucroMedioPorPeca(ingressos);
                break;
            case 7:
                return;
            default:
                System.out.println("Opção inválida.");
        }
    }
}


