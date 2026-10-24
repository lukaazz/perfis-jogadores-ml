import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;


public class Main {
    public static void main(String[] args) throws Exception {
        try (Connection c = DriverManager.getConnection("jdbc:h2:mem:jogos");
             Statement s = c.createStatement()) {
            for (String linha : Files.readAllLines(Path.of("jogadores.sql")))
                if (!linha.isBlank() && !linha.startsWith("--")) s.execute(linha);

            System.out.println("Linhas (via DAO): " + new JogadorDAOJdbc(c).listarTodos().size());
            consulta(s, "(a)", "SELECT perfil, COUNT(*) FROM jogador GROUP BY perfil ORDER BY perfil");
            consulta(s, "(b)", "SELECT perfil, ROUND(AVG(horas_semana),2), ROUND(AVG(partidas_semana),2), "
                    + "ROUND(AVG(gasto_mensal),2), ROUND(AVG(taxa_vitoria),2) FROM jogador GROUP BY perfil ORDER BY perfil");
            consulta(s, "(c)", "SELECT apelido, perfil, taxa_vitoria FROM jogador "
                    + "WHERE horas_semana > 20 AND taxa_vitoria > 55 ORDER BY taxa_vitoria DESC");
        }
    }

    static void consulta(Statement s, String titulo, String sql) throws SQLException {
        System.out.println("\n" + titulo);
        try (ResultSet rs = s.executeQuery(sql)) {
            int n = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i <= n; i++) sb.append(rs.getString(i)).append('\t');
                System.out.println(sb);
            }
        }
    }
}
