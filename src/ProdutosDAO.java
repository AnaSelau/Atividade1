/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement st;
    ResultSet rs;
    ArrayList<Produtos> listagem = new ArrayList<>();
    
        public boolean conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/leilao", "root", "");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return false;
        }
    }
    
    /**
     * método para salvar os produtos do formulário
     * @param prod
     * @return 
     */
    public int salvar(Produtos prod) {
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)");
            st.setString(1, prod.getNome());
            st.setInt(2, prod.getValor());
            st.setString(3, prod.getStatus());
            status = st.executeUpdate();
            return status; // retornar 1
        } catch (Exception ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return 0;
        }
    }
    
    public Produtos getProdutos(int id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            Produtos produtos = new Produtos();

            if (rs.next()) {
                produtos.setId(id);
                produtos.setNome(rs.getString("nome"));
                produtos.setValor(rs.getInt("valor"));
                produtos.setStatus(rs.getString("status"));
                // Defina os demais atributos conforme necessário
            } else {
                return null; // Se o produto não for encontrado, retorne null
            }

            return produtos;
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }
    
    public void desconectar() {
        try {
            conn.close();
        } catch (SQLException ex) {
            //pode-se deixar vazio para evitar uma mensagem de erro desnecessária ao usuário
        }
    }
    
    public void cadastrarProduto (Produtos produto){
        
        
        //conn = new conectaDAO().connectDB();
        
        
    }
    
    public ArrayList<Produtos> listarProdutos(){
        
        return listagem;
    }
    
    
    
        
}

