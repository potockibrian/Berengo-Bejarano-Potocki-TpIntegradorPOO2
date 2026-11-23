package unq.travel.catalogo;

public interface TipoAtributo<T> {

	boolean esValorValido(T valor) throws ClassCastException;

}
