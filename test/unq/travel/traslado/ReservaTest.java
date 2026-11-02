package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.cicloReserva.Cliente;
import unq.travel.cicloReserva.EstadoDeReserva;
import unq.travel.catalogo.ItemCatalogo;
import unq.travel.notificaciones.Suscriptor;

class ReservaTest {
	
	private Reserva reserva;
	private Direccion direccion;
	private EstadoDeReserva estado;
	private EstrategiaTraslado estrategia;
	private Cliente cliente;
	private List<ItemCatalogo> items;
	private Suscriptor suscriptorCorreo;
	private Suscriptor suscriptorFacturador;
	private Suscriptor suscriptorFidelizador;
	private List<Suscriptor> suscriptores;
	
	@BeforeEach
	void setUp() throws Exception {
		
		cliente = mock(Cliente.class);
		estrategia = mock(EstrategiaTraslado.class);
		direccion = mock(Direccion.class);
		estado = mock(EstadoDeReserva.class);
		items = mock(List.class);
		suscriptorCorreo = mock(Suscriptor.class);
		suscriptorFacturador = mock(Suscriptor.class);
		suscriptorFidelizador = mock(Suscriptor.class);
		suscriptores = List.of(suscriptorCorreo, suscriptorFacturador, suscriptorFidelizador);
		
		reserva = new Reserva(cliente, direccion, estrategia, suscriptores, 1);
		reserva.cambiarEstado(estado);
		
	}
	
	@Test
	void testDireccion() {
		
		assertEquals(reserva.direccion(), direccion);
		
	}
	
	@Test
	void testCantidadPasajeros() {
		
		assertEquals(reserva.cantidadPasajeros(), 1);
		
	}
	
	@Test
	void testConfirmarReserva() {
		
		reserva.confirmarReserva();
		
		verify(estado).confirmar(any(List.class));
		
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
		
		verify(estado).completar();
		
	}
	
	@Test
	void testCancelarReserva() {
		
		reserva.cancelarReserva();
		
		verify(estado).cancelar(any(List.class));
		
	}
	
	@Test
	void testCostoTraslado() {
		
		reserva.costoTraslado();
		
		verify(estrategia).costoTraslado(reserva);
	
	}
	
	@Test
	void testAgregarServicio() {
		
		ItemCatalogo dummy = mock(ItemCatalogo.class);
		
		reserva.agregarServicio(dummy);
		
		verify(estado).agregarItem(eq(dummy), any(ArrayList.class));
		
	}
	
	@Test
	void testQuitarServicio() {
		
		ItemCatalogo dummy = mock(ItemCatalogo.class);
		
		reserva.quitarServicio(dummy);
		
		verify(estado).quitarItem(eq(dummy), any(ArrayList.class));
		
	}
	
	@Test
	void testCliente() {
		
		assertEquals(reserva.getCliente(), cliente);
		
	}
	
	@Test
	void testPrecioTotalServicios() {
		
		reserva.getPrecioTotalServicios();
		
	}
	
	@Test
	void testCambiarEstado() {
		
		// .cambiarEstado(estado) se ejectuta en el setUp
		
		verify(suscriptorCorreo).notify(reserva, estado);
		verify(suscriptorFacturador).notify(reserva, estado);
		verify(suscriptorFidelizador).notify(reserva, estado);
		
	}

}