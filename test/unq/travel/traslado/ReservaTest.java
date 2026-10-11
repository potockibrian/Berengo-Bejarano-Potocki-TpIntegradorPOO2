package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservaTest {
	
	private Reserva reserva;
	private Direccion direccion;
	private EstadoReserva estado;
	private EstrategiaTraslado estrategia;
	
	@BeforeEach
	void setUp() {
		
		direccion = mock(Direccion.class);
		estado = mock(EstadoReserva.class);
		reserva = new Reserva(direccion, estrategia);
		
		reserva.cambiarEstado(estado);
		
	}
	
	@Test
	void testConfirmarReserva() {
		
		reserva.confirmarReserva();
		
		verify(estado).confirmar();
		
	}
	
	@Test
	void testCheckinReserva() {
		
		reserva.checkinReserva();
		
		verify(estado).checkin();
		
	}
	
	@Test
	void testEjecutarReserva() {
		
		reserva.ejecutarReserva();
		
		verify(estado).ejecutar();
		
	}
	
	@Test
	void testFinalizarReserva() {
		
		reserva.finalizarReserva();
		
		verify(estado).finalizar();
		
	}
	
	@Test
	void cancelarReserva() {
		
		reserva.cancelarReserva();
		
		verify(estado).cancelar();
		
	}
	
	@Test
	void testCostoTraslado() {
		
		reserva.costoTraslado();
		
		verify(estrategia).costoTraslado();
	
	}
	
	@Test
	void testAgregarServicio() {
		
		ItemCatalogo dummy = mock(ItemCatalogo.class);
		
		reserva.agregarServicio(dummy);
		
		verify(estado).agregarServicio(dummy, any(List.class));
		
	}
	
	@Test
	void testQuitarServicio() {
		
		ItemCatalogo dummy = mock(ItemCatalogo.class);
		
		reserva.quitarServicio(dummy);
		
		verify(estado).quitarServicio(dummy, any(List.class));
		
	}
	
}