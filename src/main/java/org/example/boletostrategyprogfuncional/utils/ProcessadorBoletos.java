package org.example.boletostrategyprogfuncional.utils;

import org.example.boletostrategyprogfuncional.model.Boleto;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ProcessadorBoletos {
    public static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * {@link Function} que possui a implementação
     * da estratégia para leitura do arquivo de retorno de boleto
     * bancário para um banco específico (como Banco do Brasil, Bradesco, etc).
     */
    private Function<URI, List<Boleto>> leituraRetorno;

    public ProcessadorBoletos(Function<URI, List<Boleto>> leituraRetorno) {
        this.leituraRetorno = leituraRetorno;
    }

    public static List<Boleto> lerBancoBrasil(URI caminhoArquivo) {
        try{
            var listaLinhas = Files.readAllLines(Paths.get(caminhoArquivo));
            List<Boleto> boletos = new ArrayList<>();
            for (String linha : listaLinhas) {
                String[] vetor = linha.split(";");
                Boleto boleto = new Boleto();
                boleto.setId(Integer.parseInt(vetor[0]));
                boleto.setCodBanco(vetor[1]);

                boleto.setDataVencimento(LocalDate.parse(vetor[2], FORMATO_DATA));
                boleto.setDataPagamento(LocalDate.parse(vetor[3], FORMATO_DATA).atTime(0, 0, 0));

                boleto.setCpfCliente(vetor[4]);
                boleto.setValor(Double.parseDouble(vetor[5]));
                boleto.setMulta(Double.parseDouble(vetor[6]));
                boleto.setJuros(Double.parseDouble(vetor[7]));
                boletos.add(boleto);
            }

            return boletos;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static List<Boleto> lerBradesco(URI caminhoArquivo) {
        try {
            // Lê todas as linhas do arquivo especificado
            var linhas = Files.readAllLines(Paths.get(caminhoArquivo));

            // Cria uma lista para armazenar os boletos processados
            List<Boleto> boletos = new ArrayList<>();

            // Para cada linha do arquivo
            linhas.forEach(linha -> {
                // Separa os campos da linha usando ';' como delimitador
                String[] campos = linha.split(";");

                // Cria uma nova instância de Boleto
                Boleto boleto = new Boleto();

                boleto.setId(Integer.parseInt(campos[0]));
                boleto.setCodBanco(campos[1]);
                boleto.setAgencia(campos[2]);
                boleto.setContaBancaria(campos[3]);
                boleto.setDataVencimento(LocalDate.parse(campos[4], FORMATO_DATA));
                boleto.setDataPagamento(LocalDateTime.parse(campos[5], FORMATO_DATA_HORA));
                boleto.setCpfCliente(campos[6]);
                boleto.setValor(Double.parseDouble(campos[7]));
                boleto.setMulta(Double.parseDouble(campos[8]));
                boleto.setJuros(Double.parseDouble(campos[9]));

                // Adiciona o boleto à lista
                boletos.add(boleto);
            });

            // Retorna a lista completa de boletos
            return boletos;
        } catch (IOException e) {
            // Lança uma exceção não-verificada em caso de erro de leitura
            throw new UncheckedIOException(e);
        }
    }

    public void processar(URI caminhoArquivo){
        // Aplica a função de leitura definida via Strategy (lerBradesco ou lerBancoBrasil)
        List<Boleto> boletos = leituraRetorno.apply(caminhoArquivo);

        // Exibe um título indicando o início do processamento
        System.out.println("Boletos processados:");

        // Imprime uma linha separadora para organização
        System.out.println("--------------------------------------------------------------------------------");

        // Itera funcionalmente sobre os boletos e imprime cada um
        boletos.forEach(System.out::println);
    }
}

