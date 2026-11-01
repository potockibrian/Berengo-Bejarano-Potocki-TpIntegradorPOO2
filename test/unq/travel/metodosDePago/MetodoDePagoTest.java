package unq.travel.metodosDePago;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MetodoDePagoTest {
	
	private MetodoDePago metodo;
	private List<String> pasos;
	
	@BeforeEach
	void setUp() {
		pasos = new ArrayList<>();
		metodo = new MetodoDePago() {
			@Override
			protected boolean validarDatos(float monto) {
				pasos.add("validar");
				return true;
			}
			
			@Override
			protected void reservarFondos() {
				pasos.add("reservar");
			}
			
			@Override
			protected String ejecutarTransaccion() {
				pasos.add("ejecutar");
				return "OP-123";
			}
		};
	}

	@Test
	void testPagarEjecutaLosPasosEnOrden() {
		metodo.pagar(3000f);
		
		assertEquals(
				List.of("validar", "reservar", "ejecutar"), 
				pasos);
		assertEquals("OP-123", metodo.getCodigoTransaccion());
	}
	
	@Test
	void testPagarConDatosInvalidosLanzaExcepcion() {
		MetodoDePago metodoInvalido = new MetodoDePago() {
			@Override
			protected boolean validarDatos(float monto) {
				pasos.add("validar");
				return false;
			}
			
			@Override
			protected void reservarFondos() {
				pasos.add("reservar");
			}
			
			@Override
			protected String ejecutarTransaccion() {
				pasos.add("ejecutar");
				return "OP-123";
			}
		};
		
		assertThrows(
				IllegalArgumentException.class,
				() -> metodoInvalido.pagar(3000f));
		
		assertEquals(List.of("validar"), pasos);
	}
	
	@Test
	void testNotificacionPredeterminadaRegistraCodigo() {
		metodo.pagar(3000f);
		
		assertEquals("OP-123", metodo.getCodigoTransaccion());
	}
}