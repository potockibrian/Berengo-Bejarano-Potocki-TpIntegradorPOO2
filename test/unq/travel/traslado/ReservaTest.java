package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.cicloReserva.*;
import unq.travel.cupos.*;
import unq.travel.traslado.*;
import unq.travel.catalogo.*;
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
	private ItemCatalogo item;
	private RangoDeFechas periodo;
	
	@BeforeEach
	void setUp() throws Exception {
		
		cliente = mock(Cliente.class);
		estrategia = mock(EstrategiaTraslado.class);
		direccion = mock(Direccion.class);
		estado = mock(EstadoDeReserva.class);
		item = mock(ItemCatalogo.class);
		items = new ArrayList<ItemCatalogo>();
		suscriptorCorreo = mock(Suscriptor.class);
		suscriptorFacturador = mock(Suscriptor.class);
		suscriptorFidelizador = mock(Suscriptor.class);
		suscriptores = new ArrayList(List.of(suscriptorCorreo, suscriptorFacturador, suscriptorFidelizador));
		periodo = mock(RangoDeFechas.class);
		
		reserva = new Reserva(cliente, direccion, estrategia, suscriptores, 1);
		reserva.cambiarEstado(estado);
		
	}
	
	@Test
	void testCambiarTraslado() {
		
		when(estrategia.costoTasasTraslado(reserva)).thenReturn(100f);
		
		reserva.elegirTraslado(estrategia);
		
		assertEquals(100f, reserva.costoTasasTraslado());
		
		verify(estrategia).costoTasasTraslado(reserva);
		
	}
	
	@Test
	void testCostoTotalReserva() {
		
		when(item.tieneDisponibilidad(any())).thenReturn(true);
		reserva.doAgregarItem(item, periodo);
		when(estrategia.costoTasasTraslado(reserva)).thenReturn(100f);
		when(item.getPrecioFinal()).thenReturn(100f);
		
		assertEquals(200f, reserva.costoTotalReserva());
		
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
	void testConfirmarReservaConDisponibilidadDeTraslado() {

		when(estrategia.hayDisponibilidad()).thenReturn(true);

		reserva.confirmarReserva();
		
		verify(estado).confirmar();
		
	}
	
	@Test
	void testConfirmarReservaSinDisponibilidadDeTraslado() {
		
		when(estrategia.hayDisponibilidad()).thenReturn(false);
		
		assertThrows(NoHayDisponibilidadException.class, () -> reserva.confirmarReserva());
		
		verify(estado, never()).confirmar();
		
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
		
		verify(estado).cancelar();
		
	}
	
	@Test
	void testCostoTraslado() {
		
		when(estrategia.costoTasasTraslado(reserva)).thenReturn(1000f);
		
		assertEquals(1000f, reserva.costoTasasTraslado());
	
	}
	
	@Test
	void testAgregarServicio() {
		
		ItemCatalogo dummy = mock(ItemCatalogo.class);
		
		reserva.agregarServicio(dummy);
		
		verify(estado).agregarItem(eq(dummy));
		
	}
	
	@Test
	void testQuitarServicio() {
		
		ItemCatalogo dummy = mock(ItemCatalogo.class);
		
		reserva.quitarServicio(dummy);
		
		verify(estado).quitarItem(eq(dummy));
		
	}
	
	@Test
	void testCliente() {
		
		assertEquals(reserva.getCliente(), cliente);
		
	}
	
	@Test
	void testCostoTotalServicios() {
		
		reserva.cambiarEstado(new EstadoPendiente(reserva));
		ItemCatalogo item = mock(ItemCatalogo.class);
		when(item.tieneDisponibilidad(any())).thenReturn(true);
		when(item.getPrecioFinal()).thenReturn(1000f);
		reserva.agregarServicio(item);
		
		assertEquals(1000f, reserva.costoServicios());

		verify(item).getPrecioFinal();
		
	}
	
	@Test
	void testCambiarEstado() {
		
		// cambiarEstado(estado) se ejectuta en el setUp
		
		EstadoDeReserva estadoNuevo = mock(EstadoDeReserva.class);
		
		reserva.cambiarEstado(estadoNuevo);
		
		verify(suscriptorCorreo).notify(reserva, estado, estadoNuevo);
		verify(suscriptorFacturador).notify(reserva, estado, estadoNuevo);
		verify(suscriptorFidelizador).notify(reserva, estado, estadoNuevo);
		
	}
	
	@Test
	void testSuscribir() {
		
		Suscriptor suscriptor = mock(Suscriptor.class);
		
		reserva.suscribir(suscriptor);
		
		assertTrue(suscriptores.contains(suscriptor));
		
	}
	
	@Test
	void testDesuscribir() {
		
		Suscriptor suscriptor = mock(Suscriptor.class);
		
		reserva.desuscribir(suscriptor);
		
		assertFalse(suscriptores.contains(suscriptor));
		
	}
	
	@Test
	void testDoConfirmarConCupoParaTodos() {
		
		List<ItemCatalogo> itemsLocal = new ArrayList<ItemCatalogo>();
		ItemCatalogo item1 = mock(ItemCatalogo.class);
		ItemCatalogo item2 = mock(ItemCatalogo.class);
		when(item1.tieneDisponibilidad(any())).thenReturn(true);
		when(item2.tieneDisponibilidad(any())).thenReturn(true);
		reserva.doAgregarItem(item1, new RangoDeFechas(LocalDate.of(2000, 1, 1), LocalDate.of(2000, 1, 2)));
		reserva.doAgregarItem(item2, new RangoDeFechas(LocalDate.of(2000, 3, 2), LocalDate.of(2000, 3, 3)));
		
		reserva.doConfirmar();
		
		verify(item1).reservar(any());
		verify(item2).reservar(any());
		
	}
	
	@Test
	void testDoConfirmarSinCupoParaTodos() {
		
		ItemCatalogo item1 = mock(ItemCatalogo.class);
		ItemCatalogo item2 = mock(ItemCatalogo.class);
		when(item1.tieneDisponibilidad(any())).thenReturn(true);
		when(item2.tieneDisponibilidad(any())).thenReturn(true);
		reserva.doAgregarItem(item1, periodo);
		reserva.doAgregarItem(item2, periodo);
		when(item2.tieneDisponibilidad(any())).thenReturn(false);
		
		assertThrows(NoHayDisponibilidadException.class, () -> reserva.doConfirmar());
		
		verify(item1, never()).reservar(any());
		verify(item2, never()).reservar(any());
		
	}
	
	@Test
	void testDoCheckin() {
		
		reserva.doCheckin();
		
	}
	
	@Test
	void testDoEjecutar() {
		
		reserva.doEjecutar();
		
	}
	
	@Test
	void testDoFinalizar() {
		
		reserva.doFinalizar();
		
	}

	@Test
	void testDoCancelar() {
		
		when(item.tieneDisponibilidad(any())).thenReturn(true);
		reserva.doAgregarItem(item, periodo);
		
		reserva.doCancelar();
		
		verify(item, never()).liberar(any());
		
	}
	
	@Test
	void testDoCancelarFromConfirmada() {
		
		when(item.tieneDisponibilidad(any())).thenReturn(true);
		reserva.doAgregarItem(item, periodo);
		
		reserva.doCancelarFromConfirmada();
		
		verify(item).liberar(any());
		
	}
	
	@Test
	void testDoCancelarFromCheckin() {
		
		when(item.tieneDisponibilidad(any())).thenReturn(true);
		when(item.getPrecioFinal()).thenReturn(200f);
		when(estrategia.costoTasasTraslado(reserva)).thenReturn(1000f);
		reserva.doAgregarItem(item, periodo);
		
		reserva.doCancelarFromCheckin();

		verify(cliente).añadirVoucher(argThat(v -> v.getMonto() == 1200f));
		
	}
	
	@Test
	void testDoCancelarFromEnCurso() {
		
		when(item.tieneDisponibilidad(any())).thenReturn(true);
		when(item.getPrecioFinal()).thenReturn(200f);
		when(estrategia.costoTasasTraslado(reserva)).thenReturn(1000f);
		reserva.doAgregarItem(item, periodo);
		
		reserva.doCancelarFromEnCurso();

		verify(cliente).añadirVoucher(argThat(v -> v.getMonto() == 200f));
		
	}
	
	@Test
	void testDoAgregarServicioConDisponibilidad() {	
		
		when(item.tieneDisponibilidad(any())).thenReturn(true);
		when(item.getPrecioFinal()).thenReturn(100f);
	
		reserva.doAgregarItem(item, periodo);
	
		assertEquals(100f, reserva.costoServicios());
		
	}
	
	@Test
	void testDoAgregarServicioSinDisponibilidad() {	
		
		when(item.tieneDisponibilidad(any())).thenReturn(false);
		when(item.getPrecioFinal()).thenReturn(100f);
		
		assertThrows(NoHayDisponibilidadException.class, () -> reserva.doAgregarItem(item, null));

		assertEquals(0f, reserva.costoServicios());
		
	}
	
	@Test
	void testDoQuitarItem() {
		
		reserva.doQuitarItem(item);
		
		assertFalse(items.contains(item));
		
	}

}