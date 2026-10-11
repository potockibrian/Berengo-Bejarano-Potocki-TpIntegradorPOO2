package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.awt.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PedidoExceptionTest {
	
	private Reserva reserva;
	private EstadoReserva estado;
	private ItemCatalogo item;
	private List<ItemCatalogo> items;
	
	@BeforeEach
	void setUp() {

		items = mock(List.class);
		item = mock(ItemCatalogo.class);
		reserva = mock(Reserva.class);
		estado = new EstadoPendiente(reserva);
		
	}
	
	@Test
	void testAgregarItem() {
		
		estado.agregarItem(item);
		
		verify(reserva, never()).addItem(any());
		verify(reserva, never()).cambiarEstado(any());
		
	}
	
	@Test
	void testAgregarItem() {
		
		estado.agregarItem(item, items);
		
		verify(items, never()).add(any());
		verify(reserva, never()).cambiarEstado(any());
		
	}
	
	@Test
	void testConfirmar() {
		
		estado.confirmar();
		
		verify(reserva, never()).cambiarEstado(any());
		
	}
	
	@Test
	void testConfirmar() {
		
		estado.confirmar(items);
		
		verify(reserva, never()).cambiarEstado(any());
		
	}
	
	@Test
	void testCheckin() {
		
		estado.checkin(items);
		
		verify(reserva, never()).cambiarEstado(any());
		
	}
	
	@Test
	void testEjecutar() {
		
		estado.ejecutar(items);
		
		verify(reserva, never()).cambiarEstado(any());
		
	}
	
	@Test
	void testCompletar() {
		
		estado.finalizar(items);
		
		verify(reserva, never()).cambiarEstado(any());
		
	}
	
	@Test
	void testCancelar() {
		
		estado.cancelar(items);
		
		verify(reserva, never()).cambiarEstado(any());
		
	}
	
}