package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.traslado.Reserva;

class EstadoPendienteTest {
	
	private Reserva reserva;
	private EstadoDeReserva estado;
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
	void testAgregarItemConDisponibilidad() {
		
		// TODO: contemplar el caso en el que no hay disponibilidad
		
		when(item.tieneDisponibilidad()).thenReturn(true);
		
		estado.agregarItem(item, items);
		
		verify(items).add(any(ItemCatalogo.class));
		
	}
	
	@Test
	void testAgregarItemSinDisponibilidad() {
		
		when(item.tieneDisponibilidad()).thenReturn(false);
		
		assertThrows(RuntimeException.class, () -> estado.agregarItem(item, items));
		
		verify(items,never()).add(any(ItemCatalogo.class));
		
	}
	
	@Test
	void testQuitarItem() {
		
		estado.quitarItem(item, items);
		
		verify(items).remove(any(ItemCatalogo.class));
		verify(reserva, never()).cambiarEstado(any(ReservaException.class));
		
	}
	
	@Test
	void testConfirmar() {
		
		// TODO: Testear e implementar la lógica de disponibilidad
		
		List<ItemCatalogo> itemsLocal = new ArrayList<ItemCatalogo>();
		ItemCatalogo item1 = mock(ItemCatalogo.class);
		ItemCatalogo item2 = mock(ItemCatalogo.class);
		itemsLocal.add(item1);
		itemsLocal.add(item2);
		
		estado.confirmar(itemsLocal);
		
		verify(item1).decrementarCupo();
		verify(item2).decrementarCupo();
		verify(reserva).cambiarEstado(any(EstadoConfirmado.class));
		
	}
	
	@Test
	void testCheckin() {
		
		estado.checkin();
		
		verify(reserva).cambiarEstado(any(ReservaException.class));
		
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
		
		estado.cancelar(items);
		
		verify(reserva).cambiarEstado(any(EstadoCancelado.class));
		
	}
	
	@Test
	void testNotificarPorEmail() {
		
		estado.notificarPorEmail(reserva,mailSender);
		
		verify(mailSender, never()).enviarMail(any(),any(),any(),any());
		
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