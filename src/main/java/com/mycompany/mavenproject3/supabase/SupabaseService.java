package com.mycompany.mavenproject3.supabase;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SupabaseService {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String SUPABASE_URL = dotenv.get("SUPABASE_URL");
    private static final String API_KEY = dotenv.get("SUPABASE_API_KEY");

    public static boolean usuarioExiste(String id) {
        try {
            String resposta = get("/rest/v1/usuarios?id=eq." + id + "&select=id", true);
            return resposta != null && resposta.trim().startsWith("[") && resposta.length() > 2;
        } catch (Exception e) {
            System.err.println("Erro ao verificar existência de usuário: " + e.getMessage());
            return false;
        }
    }

    public static String login(String email, String senha) {
        try {
            JSONObject credenciais = new JSONObject();
            credenciais.put("email", email);
            credenciais.put("password", senha);
            return post("/auth/v1/token?grant_type=password", credenciais.toString(), false);
        } catch (Exception e) {
            System.err.println("Erro no login: " + e.getMessage());
            return null;
        }
    }

    public static String registrarComMetadados(String email, String senha, String nome, String telefone) {
        try {
            JSONObject corpo = new JSONObject();
            corpo.put("email", email);
            corpo.put("password", senha);

            JSONObject data = new JSONObject();
            data.put("display_name", nome);
            data.put("phone", telefone);

            JSONObject options = new JSONObject();
            options.put("data", data);

            corpo.put("options", options);

            String resposta = post("/auth/v1/signup", corpo.toString(), false);
            if (resposta != null) {
                JSONObject obj = new JSONObject(resposta);
                if (obj.has("user")) {
                    return obj.getJSONObject("user").getString("id");
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao registrar: " + e.getMessage());
        }
        return null;
    }

    public static boolean salvarDadosComplementares(String id, String nome, String cpf, String nascimento, String telefone) {
        try {
            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("nome", nome);
            json.put("cpf", cpf);
            json.put("nascimento", nascimento);
            json.put("telefone", telefone);
            json.put("assinatura_id", "2e83a0bc-b99b-4b71-9fb6-1c175aebe5ea"); // Plano Grátis

            String resposta = post("/rest/v1/usuarios", json.toString(), true);
            if (resposta != null && !resposta.toLowerCase().contains("error")) {
                return true;
            } else {
                System.err.println("Erro ao inserir usuário: " + resposta);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Exceção ao salvar dados complementares: " + e.getMessage());
            return false;
        }
    }

    public static String post(String endpoint, String jsonBody, boolean auth) throws IOException {
        URL url = new URL(SUPABASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Prefer", "return=representation");
        conn.setRequestProperty("apikey", API_KEY);
        if (auth) conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
        }

        InputStream is = conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST
                ? conn.getInputStream() : conn.getErrorStream();

        try (Scanner scanner = new Scanner(is)) {
            return scanner.useDelimiter("\\A").hasNext() ? scanner.next() : null;
        }
    }

    public static String get(String endpoint, boolean auth) throws IOException {
        URL url = new URL(SUPABASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("apikey", API_KEY);
        if (auth) conn.setRequestProperty("Authorization", "Bearer " + API_KEY);

        InputStream is = conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST
                ? conn.getInputStream() : conn.getErrorStream();

        try (Scanner scanner = new Scanner(is)) {
            return scanner.useDelimiter("\\A").hasNext() ? scanner.next() : null;
        }
    }

    public static String patch(String endpoint, String jsonBody, boolean auth, boolean preferReturn) throws IOException {
        URL url = new URL(SUPABASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST"); // override PATCH
        conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
        conn.setRequestProperty("Content-Type", "application/json");

        if (preferReturn) {
            conn.setRequestProperty("Prefer", "return=representation, resolution=merge-duplicates");
        } else {
            conn.setRequestProperty("Prefer", "resolution=merge-duplicates");
        }

        conn.setRequestProperty("apikey", API_KEY);
        if (auth) conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
        }

        InputStream is = conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST
                ? conn.getInputStream()
                : conn.getErrorStream();

        try (Scanner scanner = new Scanner(is)) {
            return scanner.useDelimiter("\\A").hasNext() ? scanner.next() : null;
        }
    }

    // ✅ Método simplificado para repositórios: PATCH com auth=true e preferReturn=false
    public static boolean patch(String endpoint, String jsonBody) {
        try {
            String resposta = patch(endpoint, jsonBody, true, false);
            return resposta != null && !resposta.toLowerCase().contains("error");
        } catch (IOException e) {
            System.err.println("Erro no patch simplificado: " + e.getMessage());
            return false;
        }
    }

    public static String buscarAssinaturaId(String usuarioId) {
        try {
            String resposta = get("/rest/v1/usuarios?id=eq." + usuarioId + "&select=assinatura_id", true);
            JSONArray arr = new JSONArray(resposta);
            if (arr.length() > 0) {
                JSONObject obj = arr.getJSONObject(0);
                return obj.optString("assinatura_id", null);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar assinatura: " + e.getMessage());
        }
        return null;
    }
    public static boolean emailJaExiste(String email) {
        try {
            String url = SUPABASE_URL + "/rest/v1/rpc/get_user_id_by_email";
            URL obj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("apikey", API_KEY);
            con.setRequestProperty("Authorization", "Bearer " + API_KEY);
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String jsonInputString = "{\"p_email\": \"" + email + "\"}";
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                String response = new Scanner(con.getInputStream()).useDelimiter("\\A").next();
                return response != null && !response.equals("null") && response.contains("-");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;}}