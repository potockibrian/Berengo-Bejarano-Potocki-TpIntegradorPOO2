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
		metodo = new MetodoDePagoDePrueba(pasos);
	}

	@Test
	void testPagarEjecutaLosPasosEnOrden() {
		metodo.pagar(3000f);
		
		assertEquals(
				List.of("validar", "reservar", "ejecutar", "notificar"), 
				pasos
		);
	}
	
	private class MetodoDePagoDePrueba extends MetodoDePago {
		private List<String> pasos;
		
		public MetodoDePagoDePrueba(List<String> pasos) {
			this.pasos = pasos;
		}
		
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
		protected void ejecutarTransaccion() {
			pasos.add("ejecutar");
		}
		
		@Override
		protected void notificar() {
			pasos.add("notificar");
		}
	}
	
	@Test
	void testPagarConDatosInvalidosLanzaExcepcionYDetieneElProceso() {
		metodo = new MetodoDePagoConDatosInvalidos(pasos);
		
		assertThrows(
			IllegalArgumentException.class,
			() -> metodo.pagar(3000f)
		);
		
		assertEquals(List.of("validar"), pasos);
	}
	
	private class MetodoDePagoConDatosInvalidos extends MetodoDePagoDePrueba {
		public MetodoDePagoConDatosInvalidos(List<String> pasos) {
			super(pasos);
		}
		
		@Override
		protected boolean validarDatos(float monto) {
			pasos.add("validar");
			return false;
		}
	}
}