package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoPendienteTest {
	
	private Reserva reserva;
	private EstadoReserva estado;
	private ItemCatalogo item;
	private List<ItemCatalogo> items;
	private MailSenderI mailSender;
	
	@BeforeEach
	void setUp() {
		
		items = mock(List.class);
		item = mock(ItemCatalogo.class);
		reserva = mock(Reserva.class);
		mailSender = mock(MailSenderI.class);
		
		estado = new EstadoPendiente(reserva);
		
	}
	
	@Test
	void testAgregarItem() {
		
		estado.agregarItem(item, items);
		
		verify(items).add(any(ItemCatalogo.class));
		verify(reserva, never()).cambiarEstado(any(PedidoException.class));
		
	}
	
	@Test
	void testQuitarItem() {
		
		estado.quitarItem(item, items);
		
		verify(items).remove(any(ItemCatalogo.class));
		verify(reserva, never()).cambiarEstado(any(PedidoException.class));
		
	}
	
	@Test
	void testConfirmar() {
		
		estado.confirmar(items);
		
		verify(reserva).cambiarEstado(any(EstadoConfirmado.class));
		
	}
	
	@Test
	void testCheckin() {
		
		estado.checkin();
		
		verify(reserva).cambiarEstado(any(PedidoException.class));
		
	}
	
	@Test
	void testEjecutar() {
		
		estado.ejecutar();
		
		verify(reserva).cambiarEstado(any(PedidoException.class));
		
	}
	
	@Test
	void testCompletar() {
		
		estado.completar();
		
		verify(reserva).cambiarEstado(any(PedidoException.class));
		
	}
	
	@Test
	void testCancelar() {
		
		estado.cancelar();
		
		verify(reserva).cambiarEstado(any(EstadoCancelado.class));
		
	}
	
	@Test
	void testNotificarPorEmail() {
		
		estado.notificarPorEmail(reserva,mailSender);
		
		verify(mailServer, never()).enviarMail(any(),any(),any(),any());
		
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