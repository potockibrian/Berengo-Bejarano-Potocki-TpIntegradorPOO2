package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import libraries.TrasladoVIP;
import unq.travel.cupos.RangoDeFechas;

class EstrategiaTrasladoVIPTest {
	
	private EstrategiaTraslado estrategia;
	private Reserva reserva;
	private TrasladoVIP libreria;
	private Direccion direccion;
	
	@BeforeEach
	void setUp() {
		
		reserva = mock(Reserva.class);
		libreria = mock(TrasladoVIP.class);
		estrategia = new EstrategiaTrasladoVIP(libreria);
		
	}
	
	@Test
	void testCostoTraslado() {
		
		when(reserva.costoServicios()).thenReturn(1000f);
		
		estrategia.costoTasasTraslado(reserva);

		verify(libreria).calcularCosto(1000f);
		
	}
	
	@Test
	void testHayDisponibilidad() {
		
		assertTrue(estrategia.hayDisponibilidad(mock(RangoDeFechas.class)));
		
	}
	
	@Test
	void testLiberar() {
		
		estrategia.liberar(mock(RangoDeFechas.class));
		
	}
	
	@Test
	void testReservar() {
		
		estrategia.reservar(mock(RangoDeFechas.class));
		
	}
	
}