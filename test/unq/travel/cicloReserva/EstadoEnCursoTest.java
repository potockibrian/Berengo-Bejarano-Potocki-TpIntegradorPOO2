package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.awt.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoEnCursoTest {
	
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
		
		estado.agregarItem(item, items);
		
		verify(items, never()).add(any(ItemCatalogo.class));
		verify(reserva).cambiarEstado(any(PedidoException.class));
		
	}
	
	@Test
	void testQuitarItem() {
		
		estado.quitarItem(item, items);
		
		verify(items, never()).remove(any(ItemCatalogo.class));
		verify(reserva).cambiarEstado(any(PedidoException.class));
		
	}
	
	@Test
	void testConfirmar() {
		
		estado.confirmar(items);
		
		verify(reserva).cambiarEstado(any(PedidoException.class));
		
	}
	
	@Test
	void testCheckin() {
		
		estado.checkin(items);
		
		verify(reserva).cambiarEstado(any(PedidoException.class));
		
	}
	
	@Test
	void testEjecutar() {
		
		estado.ejecutar(items);
		
		verify(reserva).cambiarEstado(any(PedidoException.class));
		
	}
	
	@Test
	void testCompletar() {
		
		estado.completar(items);
		
		verify(reserva).cambiarEstado(any(EstadoFinalizado.class));
		
	}
	
	@Test
	void testCancelar() {
		
		estado.cancelar(items);
		
		verify(reserva).cambiarEstado(any(EstadoCancelado.class));
		
	}
	
	@Test
	void testNotificarPorEmail() {
		
		when(cliente.getDireccionEmail()).thenReturn("testmail@test.domain");
		when(reserva.getCliente()).thenReturn(cliente);
		
		estado.notificarPorEmail(reserva,mailSender);
		
		verify(mailServer).enviarMail(
				cliente.getDireccionDeEmail()
				,"Reserva en curso"
				, "Se ha ejecutado vuestra reserva"
				, null);
		
	}
	
	@Test
	void testGenerarFactura() {
		
		estado.generarFactura(reserva);
		
		verify(reserva, never());
		
	}
	
	@Test
	void testFidelizar() {
		
		estado.fidelizar(reserva, mailSender);
		
		verify(reserva, never());
		
	}
	
}