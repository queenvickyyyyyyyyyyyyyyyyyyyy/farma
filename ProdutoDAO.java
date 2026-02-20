package com.example.lojinha.model.dao;
import com.example.lojinha.model.entities.Produto;

public interface ProdutoDAO {

    void inserir(Produto produto);

    void removerPorId(int id);

    double calcularTotalEstoque();

    void listarProdutos(int id);
}
