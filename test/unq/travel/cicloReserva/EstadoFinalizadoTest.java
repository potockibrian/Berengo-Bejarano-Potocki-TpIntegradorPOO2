package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.awt.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoFinalizadoTest {
	
	private Reserva reserva;
	private EstadoReserva estado;
	private ItemCatalogo item;
	private List<ItemCatalogo> items;
	private Cliente cliente;
	
	@BeforeEach
	void setUp() {

		cliente = mock(Cliente.class);
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
		
		verify(reserva).cambiarEstado(any(PedidoException.class));
		
	}
	
	@Test
	void testCancelar() {
		
		estado.cancelar(items);
		
		verify(reserva).cambiarEstado(any(PedidoException.class));
		
	}
	
	@Test
	void testNotificarPorEmail() {
		
		when(cliente.getDireccionEmail()).thenReturn("testmail@test.domain");
		when(reserva.getCliente()).thenReturn(cliente);
		
		estado.notificarPorEmail(reserva,mailSender);
		
		verify(mailServer).enviarMail(
				cliente.getDireccionDeEmail()
				,"Reserva finalizada"
				, "Se ha finalizado vuestra reserva"
				, null);
		
	}
	
	@Test
	void testGenerarFactura() {
		
		when(reserva.getCliente()).thenReturn(cliente);
		estado.generarFactura(reserva);
		
		verify(cliente).añadirComprobante(any(Comprobante.class));
		verify(cliente).añadirVoucher(any(Voucher.class));
		
	}
	
	@Test
	void testFidelizar() {
		
		estado.fidelizar(reserva, mailSender);
		
		verify(reserva, never());
		
	}
	
}