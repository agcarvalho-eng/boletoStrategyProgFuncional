package org.example.boletostrategyprogfuncional.model;

import org.example.boletostrategyprogfuncional.utils.ProcessadorBoletos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Boleto {
    private int id;
    private String codBanco,cpfCliente, agencia, contaBancaria;
    private LocalDate dataVencimento;
    private LocalDateTime dataPagamento;
    private double valor, multa,juros;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCodBanco() { return codBanco; }
    public void setCodBanco(String codBanco) { this.codBanco = codBanco; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }
    public String getCpfCliente() { return cpfCliente; }
    public void setCpfCliente(String cpfCliente) { this.cpfCliente = cpfCliente; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public double getMulta() { return multa; }
    public void setMulta(double multa) { this.multa = multa; }
    public double getJuros() { return juros; }
    public void setJuros(double juros) { this.juros = juros; }
    public String getAgencia() { return agencia; }
    public void setAgencia(String agencia) { this.agencia = agencia; }
    public String getContaBancaria() { return contaBancaria; }
    public void setContaBancaria(String contaBancaria) { this.contaBancaria = contaBancaria; }

    // Sobrescreve o método toString utilizado para exibir os dados do boleto de forma formatada
    @Override
    public String toString() {
        // Cria uma string formatada com o ID e código do banco
        String resultado = String.format("Id: %10d Banco: %3s", id, codBanco);

        // Inicializa uma string vazia para os dados de agência e conta, se existirem
        String agenciaConta = "";

        // Se agência e conta não forem nulas ou vazias, formata esses dados
        if (agencia != null && !agencia.isEmpty() && contaBancaria != null && !contaBancaria.isEmpty()) {
            agenciaConta = String.format(" Ag: %6s CC: %10s", agencia, contaBancaria);
        }

        // Adiciona à string principal os dados de vencimento, pagamento e valor
        resultado += agenciaConta + String.format(" Venc: %s Pag: %s Valor: %10.2f",
                ProcessadorBoletos.FORMATO_DATA.format(dataVencimento),
                ProcessadorBoletos.FORMATO_DATA_HORA.format(dataPagamento), valor);

        // Se houver multa, adiciona à string
        if (multa > 0) {
            resultado += String.format(" Multa: %10.2f", multa);
        }

        // Se houver juros, adiciona à string
        if (juros > 0) {
            resultado += String.format(" Juros: %10.2f", juros);
        }

        // Retorna a string final com todos os dados formatados
        return resultado;
    }
}
