/**
 * Classe que serve de base para todas as cartas do jogo que possuem poderes especiais.
 * Estende a classe Carta, funcionando como um elo intermédio na hierarquia
 * para agrupar comportamentos ou propriedades exclusivas das cartas especiais (como o Elefante).
 */
public class CartaEspecial extends Carta {
    public CartaEspecial(int id, String simbolo) {
        super(id, simbolo);
    }
}