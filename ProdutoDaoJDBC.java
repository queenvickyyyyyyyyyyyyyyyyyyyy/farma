package com.example.lojinha.model.dao.impl;

import com.example.lojinha.db.DB;
import com.example.lojinha.model.dao.ProdutoDAO;
import com.example.lojinha.model.entities.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProdutoDaoJDBC implements ProdutoDAO {
    private Connection conn;

    public ProdutoDaoJDBC() {
        conn = DB.conectar();
    }
    public void inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, quantidade, categoria) VALUES (?, ?, ?,?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, produto.getNome());
            st.setDouble(2, produto.getPreco());
            st.setInt(3, produto.getQuantidade());
            st.setString(4, produto.getCategoria());

            st.executeUpdate();

            System.out.println("Produto inserido com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir produto", e);
        }

        System.out.println("Inserindo produto...");
    }

    public void removerPorId(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, id);

            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Produto removido com sucesso!");
            } else {
                System.out.println("Nenhum produto encontrado com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover produto", e);
        }
    }

    public double calcularTotalEstoque() {

        String sql = "SELECT SUM(preco * quantidade) AS total FROM produto";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total");
            }

            return 0.0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular total do estoque", e);
        }
    }

    @Override
    public void listarProdutos(int id) {

    }

    public List<Produto> listarProdutos() {

        String sql = "SELECT id, nome, preco, quantidade, categoria FROM produto";

        List<Produto> lista = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {

                Produto produto = new Produto();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setDescricao(rs.getString("descricao"));

                lista.add(produto);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos", e);
        }

        return lista;
    }
}
