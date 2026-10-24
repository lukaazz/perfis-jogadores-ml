/** Perfil de um jogador: o rótulo que o KNN prevê e que usamos para avaliar o K-Means. */
public enum Perfil {
    CASUAL("Joga de vez em quando, por diversão"),
    REGULAR("Joga com frequência, mas sem grande dedicação"),
    RANQUEADO("Joga partidas ranqueadas e treina quase todo dia");

    private final String descricao;

    Perfil(String descricao) { this.descricao = descricao; }

    public String getDescricao() { return descricao; }
}
