package unq.travel.catalogo;

public class SinStockException extends RuntimeException {

	public SinStockException() {
		
		super();
		
	}
	
	public SinStockException(String mensaje) {

		super(mensaje);

	}

}
