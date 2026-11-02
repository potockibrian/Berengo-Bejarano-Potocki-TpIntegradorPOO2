package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import libraries.PlataformaTransporte;

class TrasladoEstandarTest {
	
	private EstrategiaTraslado estrategia;
	private PlataformaTransporte libreria;
	private Reserva reserva;
	private Direccion direccion;
	
	@BeforeEach
	void setUp() {
		
		libreria = mock(PlataformaTransporte.class);
		reserva = mock(Reserva.class);
		direccion = mock(Direccion.class);
		reserva = mock(Reserva.class);
		estrategia = new TrasladoEstandar(10, direccion, libreria);
		
	}
	
	@Test
	void testCostoTraslado() {
		
		when(reserva.cantidadPasajeros()).thenReturn(1);
		when(reserva.direccion()).thenReturn(direccion);
		
		estrategia.costoTraslado(reserva);
		
		verify(libreria).estimarTraslado(1, direccion);
		
	}
	
}