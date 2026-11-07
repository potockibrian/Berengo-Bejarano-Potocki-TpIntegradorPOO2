package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrasladoRecepcionTest {
	
	private TrasladoRecepcion estrategia;
	private Reserva reserva;
	
	@BeforeEach
	void setUp() {
		
		reserva = mock(Reserva.class);
		estrategia = new TrasladoRecepcion(2);
		
	}
	
	@Test
	void testHayDisponibilidadCuandoSucursalNoTieneDisponibilidad() {
		
		estrategia.cantidadDeGuias = 0;
		
		assertFalse(estrategia.hayDisponibilidad());
		
	}
	
	@Test
	void testHayDisponibilidadCuandoSucursalTieneDisponibilidad() {
		
		estrategia.cantidadDeGuias = 1;
		
		assertTrue(estrategia.hayDisponibilidad());
		
	}
	
	@Test
	void testCostoTraslado() {
		
		assertEquals(0, estrategia.costoTasasTraslado(reserva));
		
	}
	
}