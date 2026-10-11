package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.traslado.Reserva;

class EstadoConfirmadoTest {
	
	private Reserva reserva;
	private EstadoReserva estado;
	private ItemCatalogo item;
	private List<ItemCatalogo> items;
	private Cliente cliente;
	private MailSenderI mailSender;
	
	@BeforeEach
	void setUp() {
		
		mailSender = mock(MailSenderI.class);
		cliente = mock(Cliente.class);
		items = mock(List.class);
		item = mock(ItemCatalogo.class);
		reserva = mock(Reserva.class);
		
		estado = new EstadoConfirmado(reserva);
		
	}
	
	@Test
	void testAgregarItem() {
		
		estado.agregarItem(item, items);
		
		verify(items, never()).add(any(ItemCatalogo.class));
		verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testQuitarItem() {
		
		estado.quitarItem(item, items);
		
		verify(items, never()).remove(any(ItemCatalogo.class));
		verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testConfirmar() {
		
		estado.confirmar();
		
		verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testCheckin() {
		
		estado.checkin();
		
		verify(reserva).cambiarEstado(any(EstadoEnCheckin.class));
		
	}
	
	@Test
	void testEjecutar() {
		
		estado.ejecutar();
		
		verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testCompletar() {
		
		estado.completar();
		
		verify(reserva).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testCancelar() {
		
		estado.cancelar();
		
		verify(reserva).cambiarEstado(any(EstadoCancelado.class));
		
	}
	
	@Test
	void testNotificarPorEmail() {
		
		when(cliente.getDireccionDeEmail()).thenReturn("testmail@test.domain");
		when(reserva.getCliente()).thenReturn(cliente);
		
		estado.notificarPorEmail(reserva,mailSender);
		
		verify(mailSender).enviarMail(
				  eq(reserva.getCliente().getDireccionDeEmail())
				, eq("Reserva confirmada")
				, eq("Se ha confirmado vuestra reserva")
				, eq(null));
		
	}
	
	@Test
	void testGenerarFactura() {
		
		estado.generarFactura(reserva);
		
		verify(reserva, never()).getCliente();
		
	}
	
	@Test
	void testFidelizar() {
		
		estado.fidelizar(reserva, mailSender);
		
		verify(reserva, never());
		
	}
	
}