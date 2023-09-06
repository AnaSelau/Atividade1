
import com.mysql.cj.Query;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



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
    listagem.clear(); // Limpa a lista para evitar duplicatas

    try {
        String sql = "SELECT * FROM produtos";
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Produtos produto = new Produtos();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setValor(rs.getInt("valor"));
            produto.setStatus(rs.getString("status"));
            // Defina os demais atributos conforme necessário

            listagem.add(produto);
        }
    } catch (SQLException e) {
        System.out.println("Erro ao listar produtos: " + e.getMessage());
    }

    return listagem;
}
    
    public ArrayList<Produtos> listarProdutosVendidos() {
    listagem.clear(); // Limpa a lista para evitar duplicatas

    try {
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Produtos produto = new Produtos();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setValor(rs.getInt("valor"));
            produto.setStatus(rs.getString("status"));
            // Defina os demais atributos conforme necessário

            listagem.add(produto);
        }
    } catch (SQLException e) {
        System.out.println("Erro ao listar produtos vendidos: " + e.getMessage());
    }

    return listagem;
}

    
    public int venderProduto(int id) {
    try {
        // Primeiro, verifique se o produto com o ID especificado existe
        String selectSql = "SELECT * FROM produtos WHERE id = ?";
        PreparedStatement selectStmt = conn.prepareStatement(selectSql);
        selectStmt.setInt(1, id);
        ResultSet rs = selectStmt.executeQuery();

        if (rs.next()) {
            // Produto encontrado, atualize o status para "Vendido"
            String updateSql = "UPDATE produtos SET status = ? WHERE id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, "Vendido");
            updateStmt.setInt(2, id);
            
            int status = updateStmt.executeUpdate();

            return status; // Retorne o número de linhas afetadas pela atualização (deve ser 1 se bem-sucedido)
        } else {
            // Produto não encontrado com o ID especificado
            return 0; // Ou outra forma de indicar que o produto não foi encontrado
        }
    } catch (SQLException e) {
        System.out.println("Erro ao vender produto: " + e.getMessage());
        return -1; // Ou outra forma de indicar erro
    }
}

       
}

