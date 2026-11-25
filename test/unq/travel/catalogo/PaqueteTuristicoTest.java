package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.cupos.CalendarioDeCupos;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;

class PaqueteTuristicoTest {

	private LocalDate fecha1 = LocalDate.of(2026, 1, 1);
	private LocalDate fecha2 = LocalDate.of(2026, 1, 2);
	private LocalDate fecha3 = LocalDate.of(2026, 1, 3);
	private LocalDate fecha4 = LocalDate.of(2026, 1, 4);
	
	private RangoDeFechas periodo1 = new RangoDeFechas(fecha2, fecha3);
	private RangoDeFechas periodo2 = new RangoDeFechas(fecha1, fecha4);

	private ServicioIndividual hotel;
	private ServicioIndividual museo;
	private ServicioIndividual traslado;
	private PaqueteTuristico packEscapadaUrbana;
	private RangoDeFechas periodo;
	private CalendarioDeCupos calendario1;
	private CalendarioDeCupos calendario2;
	private CalendarioDeCupos calendario3;
	private Cupo cupo;

	@BeforeEach
	void setUp() {
		calendario1 = mock(CalendarioDeCupos.class);
		calendario2 = mock(CalendarioDeCupos.class);
		calendario3 = new CalendarioDeCupos();
		hotel = this.crearServicio("Noche de Hotel Central", Categoria.ALOJAMIENTO, 15000f, 0f);
		museo = this.crearServicio("Excursion Guiada Museo", Categoria.EXCURSION, 4000f, 0f);
		traslado = this.crearServicio("Traslado Aeropuerto-Hotel", Categoria.TRASLADO, 2000f, 0f);
		periodo = mock(RangoDeFechas.class);
		cupo = new Cupo(10, 5);

		packEscapadaUrbana = new PaqueteTuristico("Pack Escapada Urbana", "Hotel, museo y traslado", 0.10f);
		packEscapadaUrbana.agregarItem(hotel);
		packEscapadaUrbana.agregarItem(museo);
		packEscapadaUrbana.agregarItem(traslado);
		
	}

	@Test
	void testCuposEnPeriodoDeCategoria() {
		
		// periodo1 es un periodo de 2 días
		packEscapadaUrbana.agregarCupoEnPeriodo(periodo1, 1);
		
		when(calendario1.cuposEnPeriodo(periodo1)).thenReturn(cupo);
		
		assertEquals(5, packEscapadaUrbana.cuposEnPeriodoDeCategoria(periodo1, Categoria.ALOJAMIENTO).cantidadDeCuposDisponibles());
		
		assertEquals(5, packEscapadaUrbana.cuposEnPeriodoDeCategoria(periodo1, Categoria.EXCURSION).cantidadDeCuposDisponibles());
		
		assertEquals(5, packEscapadaUrbana.cuposEnPeriodoDeCategoria(periodo1, Categoria.TRASLADO).cantidadDeCuposDisponibles());
		
	}
	
	@Test
	void testCantidadYTotalCobradoDeVentasEnPeriodo() {

		packEscapadaUrbana.agregarCupoEnPeriodo(periodo1, 1);
		
		when(calendario1.tieneDisponibilidad(periodo1)).thenReturn(true);
		
		packEscapadaUrbana.reservar(periodo1);
		
		assertEquals(1, packEscapadaUrbana.cantidadYTotalCobradoDeVentasEnPeriodo(periodo1).cantidadVentas());
		assertEquals(packEscapadaUrbana.getPrecioFinal(), packEscapadaUrbana.cantidadYTotalCobradoDeVentasEnPeriodo(periodo1).precioPagado());
		assertEquals(periodo1.fechas(), packEscapadaUrbana.cantidadYTotalCobradoDeVentasEnPeriodo(periodo1).periodo().fechas());
		
	}

	@Test
	void testValidarAtributosConNombreNullLevantaExcepcion() {
		
		assertThrows(AtributoSinValorException.class, () -> new PaqueteTuristico(null, "descripción", 0.1f));
		
	}
	
	@Test
	void testValidarAtributosConDescripcionNullLevantaExcepcion() {
		
		assertThrows(AtributoSinValorException.class, () -> new PaqueteTuristico("nombre", null, 0.1f));
		
	}
	
	@Test
	void testElPrecioBaseEsLaSumaDeLosPreciosDeSusItems() {

		assertEquals(21000f, packEscapadaUrbana.getPrecioBase(), 0.01f);

	}

	@Test
	void testElPrecioFinalAplicaElDescuentoDelPaquete() {

		assertEquals(18900f, packEscapadaUrbana.getPrecioFinal(), 0.01f);

	}

	@Test
	void testUnPaqueteAnidadoAportaSuPrecioFinal() {

		ServicioIndividual tourGastronomico = this.crearServicio("Tour Gastronomico", Categoria.EXCURSION, 5000f, 0f);
		PaqueteTuristico kitGranTour = new PaqueteTuristico("Kit Gran Tour", "Pack Escapada Urbana y tour", 0.20f);

		kitGranTour.agregarItem(packEscapadaUrbana);
		kitGranTour.agregarItem(tourGastronomico);

		assertEquals(23900f, kitGranTour.getPrecioBase(), 0.01f);
		assertEquals(19120f, kitGranTour.getPrecioFinal(), 0.01f);

	}

	@Test
	void testElDescuentoPromocionalDeUnServicioSeAcumulaConElDelPaquete() {

		ServicioIndividual hotelConPromo = this.crearServicio("Noche de Hotel Central", Categoria.ALOJAMIENTO, 15000f, 0.20f);
		PaqueteTuristico paquete = new PaqueteTuristico("Escapada con promo", "Hotel con promo y museo", 0.10f);

		paquete.agregarItem(hotelConPromo);
		paquete.agregarItem(museo);

		assertEquals(14400f, paquete.getPrecioFinal(), 0.01f);

	}

	@Test
	void testEsDeUnaCategoriaSiAlgunoDeSusItemsLoEs() {

		assertTrue(packEscapadaUrbana.esDeCategoria(Categoria.EXCURSION));

	}

	@Test
	void testNoEsDeUnaCategoriaSiNingunoDeSusItemsLoEs() {

		assertFalse(packEscapadaUrbana.esDeCategoria(Categoria.ALQUILER_AUTO));

	}

	@Test
	void testEsDeUnaCategoriaSiUnPaqueteAnidadoLaTiene() {

		PaqueteTuristico kitGranTour = new PaqueteTuristico("Kit Gran Tour", "Pack Escapada Urbana", 0.20f);

		kitGranTour.agregarItem(packEscapadaUrbana);

		assertTrue(kitGranTour.esDeCategoria(Categoria.ALOJAMIENTO));

	}

	@Test
	void testTieneDisponibilidadSiTodosSusItemsTienen() {

		when(calendario1.tieneDisponibilidad(periodo)).thenReturn(true);
		when(calendario2.tieneDisponibilidad(periodo)).thenReturn(true);
		PaqueteTuristico packSinCupo = new PaqueteTuristico("Pack sin cupo", "Servicio agotado", 0.10f);
		PaqueteTuristico kitGranTour = new PaqueteTuristico("Kit Gran Tour", "Pack Escapada Urbana y pack agotado", 0.20f);
		
		packEscapadaUrbana.agregarItem(kitGranTour);
		packEscapadaUrbana.agregarItem(packSinCupo);
		
		assertTrue(packEscapadaUrbana.tieneDisponibilidad(periodo));

		when(calendario1.tieneDisponibilidad(periodo)).thenReturn(false);
		
		assertFalse(packEscapadaUrbana.tieneDisponibilidad(periodo));
		
	}

	@Test
	void testTieneDisponibilidadSiSusItemsTienenAunqueElCalendarioDelPaqueteNoTenga() {

		CalendarioDeCupos calendarioDelHotel = mock(CalendarioDeCupos.class);
		CalendarioDeCupos calendarioDelPaquete = mock(CalendarioDeCupos.class);
		when(calendarioDelHotel.tieneDisponibilidad()).thenReturn(true);
		when(calendarioDelPaquete.tieneDisponibilidad()).thenReturn(false);
		ServicioIndividual hotelDisponible = new ServicioIndividual("Noche de Hotel Central", "Habitación doble",
				"HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0f, calendarioDelHotel);
		PaqueteTuristico paquete = new PaqueteTuristico("Paquete", "Hotel con disponibilidad", 0.10f);

		paquete.agregarItem(hotelDisponible);

		assertTrue(paquete.tieneDisponibilidad());

	}

	@Test
	void testNoTieneDisponibilidadSiAlgunServicioNoTieneCupo() {

		packEscapadaUrbana.agregarItem(this.crearServicio("Alquiler de Auto", Categoria.ALQUILER_AUTO, 1000f, 0f));

		assertFalse(packEscapadaUrbana.tieneDisponibilidad());

	}

	@Test
	void testNoTieneDisponibilidadSiUnPaqueteAnidadoNoTiene() {

		PaqueteTuristico packSinCupo = new PaqueteTuristico("Pack sin cupo", "Servicio agotado", 0.10f);
		PaqueteTuristico kitGranTour = new PaqueteTuristico("Kit Gran Tour", "Pack Escapada Urbana y pack agotado", 0.20f);

		packSinCupo.agregarItem(this.crearServicio("Tour Gastronomico", Categoria.EXCURSION, 1000f, 0f));
		kitGranTour.agregarItem(packEscapadaUrbana);
		kitGranTour.agregarItem(packSinCupo);

		assertFalse(kitGranTour.tieneDisponibilidad());

	}

	/*
	@Test
	void testCumpleConLaValidacionSiTieneNombreYTodosSusItemsSonValidos() {

		assertTrue(packEscapadaUrbana.esValido());

	}
	*/

	/*
	@Test
	void testNoCumpleConLaValidacionSiTieneUnServicioInvalido() {

		ServicioIndividual servicioSinCodigo = new ServicioIndividual("Alquiler de Auto", "Auto compacto",
				null, "Operador SA", Categoria.ALQUILER_AUTO, 8000f, 0f, calendario1);

		packEscapadaUrbana.agregarItem(servicioSinCodigo);

		assertFalse(packEscapadaUrbana.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionSinNombre() {

		PaqueteTuristico paqueteSinNombre = new PaqueteTuristico(null, "Hotel y museo", 0.10f);

		paqueteSinNombre.agregarItem(hotel);
		paqueteSinNombre.agregarItem(museo);

		assertFalse(paqueteSinNombre.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionSiEstaVacio() {

		PaqueteTuristico paqueteVacio = new PaqueteTuristico("Paquete vacio", "Sin items", 0.10f);

		assertFalse(paqueteVacio.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionSiTieneUnPaqueteAnidadoInvalido() {

		PaqueteTuristico paqueteVacio = new PaqueteTuristico("Paquete vacio", "Sin items", 0.10f);
		PaqueteTuristico kitGranTour = new PaqueteTuristico("Kit Gran Tour", "Pack Escapada Urbana y paquete vacio", 0.20f);

		kitGranTour.agregarItem(packEscapadaUrbana);
		kitGranTour.agregarItem(paqueteVacio);

		assertFalse(kitGranTour.esValido());

	}
	*/

	@Test
	void testAlDecrementarElCupoDecrementaElDeCadaItem() {

		when(calendario1.tieneDisponibilidad(periodo)).thenReturn(true);
		ServicioIndividual hotelConUnCupo = mock(ServicioIndividual.class);
		when(hotelConUnCupo.tieneDisponibilidad(periodo)).thenReturn(true);
		PaqueteTuristico paquete = new PaqueteTuristico("Paquete", "Hotel con un cupo", 0.10f);
		paquete.agregarItem(hotelConUnCupo);

		assertTrue(hotelConUnCupo.tieneDisponibilidad(periodo));

		paquete.reservar(periodo);
		
		verify(hotelConUnCupo).reservar(periodo);

	}

	@Test
	void testAlIncrementarElCupoIncrementaElDeCadaItem() {

		ServicioIndividual hotelSinCupo = mock(ServicioIndividual.class);
		PaqueteTuristico paquete = mock(PaqueteTuristico.class);
		packEscapadaUrbana.agregarItem(hotelSinCupo);
		packEscapadaUrbana.agregarItem(paquete);

		packEscapadaUrbana.liberar(periodo);

		verify(hotelSinCupo).liberar(periodo);
		verify(paquete).liberar(periodo);
		
	}

	@Test
	void testDecrementarCupoSinDisponibilidadLanzaExcepcionYNoModificaNingunItem() {

		ServicioIndividual hotelConUnCupo = this.crearServicio("Noche de Hotel Central", Categoria.ALOJAMIENTO, 1000f, 0f);
		PaqueteTuristico paquete = new PaqueteTuristico("Paquete", "Hotel y auto agotado", 0.10f);
		paquete.agregarItem(hotelConUnCupo);
		paquete.agregarItem(this.crearServicio("Alquiler de Auto", Categoria.ALQUILER_AUTO, 1000f, 0f));

		assertThrows(SinStockException.class, () -> paquete.reservar(periodo));

		verify(calendario1, never()).reservar(periodo);

	}

	@Test
	void testDecrementarCupoConUnServicioRepetidoSinCupoSuficienteNoPierdeCupo() {

		CalendarioDeCupos calendarioDelHotel = mock(CalendarioDeCupos.class);
		when(calendarioDelHotel.tieneDisponibilidad(periodo)).thenReturn(true);
		doNothing().doThrow(new SinStockException()).when(calendarioDelHotel).reservar(periodo);
		ServicioIndividual hotelConUnCupo = new ServicioIndividual("Noche de Hotel Central", "Habitación doble",
				"HTL-001", "Hoteles Centro SA", Categoria.ALOJAMIENTO, 15000f, 0f, calendarioDelHotel);
		PaqueteTuristico dosNoches = new PaqueteTuristico("Dos noches de hotel", "Hotel dos noches", 0.10f);
		dosNoches.agregarItem(hotelConUnCupo);
		dosNoches.agregarItem(hotelConUnCupo);

		assertThrows(SinStockException.class, () -> dosNoches.reservar(periodo));

		verify(calendarioDelHotel).liberar(periodo);

	}

	@Test
	void testNoSePuedeCrearConDescuentoFueraDeRango() {

		assertThrows(IllegalArgumentException.class, () -> new PaqueteTuristico("Paquete", "Descuento invalido", 1.5f));
		assertThrows(IllegalArgumentException.class, () -> new PaqueteTuristico("Paquete", "Descuento invalido", -0.1f));

	}
	
	@Test
	void testCategorias() {
		
		assertTrue(packEscapadaUrbana.categorias().containsAll(List.of(Categoria.ALOJAMIENTO, Categoria.EXCURSION, Categoria.TRASLADO)));
		
	}
	
	@Test
	void testProveedores() {
		
		assertEquals(1, packEscapadaUrbana.proveedores().proveedores().size());
		assertEquals(1, packEscapadaUrbana.proveedores().cantidadDeProveedores());
		assertEquals(3, packEscapadaUrbana.proveedores().ocurrenciasDeProveedor("Operador SA"));
		assertEquals(List.of("Operador SA"), packEscapadaUrbana.proveedores().proveedores());
				
	}

	/*
	@Test
	void testDevuelveSusItemsEnElOrdenAgregado() {

		assertEquals(List.of(hotel, museo, traslado), packEscapadaUrbana.getItems());

	}
	*/
	
	/*
	@Test
	void testModificarLaListaDeItemsDevueltaNoModificaElPaquete() {

		packEscapadaUrbana.getItems().clear();

		assertEquals(3, packEscapadaUrbana.getItems().size());

	}
	*/
	
	/*
	@Test
	void testCantidadDeVentasEnPeriodo() {
		
		ItemCatalogo hotelConUnCupo = mock(ItemCatalogo.class);
		when(hotelConUnCupo.cantidadDeVentasEnPeriodo(periodo)).thenReturn(3);
		PaqueteTuristico dosNoches = new PaqueteTuristico("Dos noches de hotel", "Hotel dos noches", 0.10f, calendario2);
		dosNoches.agregarItem(hotelConUnCupo);
		
		assertEquals(3, dosNoches.cantidadDeVentasEnPeriodo(periodo));
		
		verify(hotelConUnCupo).cantidadDeVentasEnPeriodo(periodo);
		
	}
	*/
	
	/*
	@Test
	void testCupos() {
		
		List<Cupo> cupos = new ArrayList<>();
		ItemCatalogo hotelConUnCupo = mock(ItemCatalogo.class);
		when(hotelConUnCupo.cupos()).thenReturn(cupos);
		PaqueteTuristico dosNoches = new PaqueteTuristico("Dos noches de hotel", "Hotel dos noches", 0.10f, calendario2);
		dosNoches.agregarItem(hotelConUnCupo);
		
		assertEquals(cupos, dosNoches.cupos());
		
		verify(hotelConUnCupo).cupos();
		
	}
	*/

	private ServicioIndividual crearServicio(String nombre, Categoria categoria, float precio, float descuento) {
		return new ServicioIndividual(nombre, "Descripcion de " + nombre, "COD-" + nombre,
				"Operador SA", categoria, precio, descuento, calendario1);
	}

	private ServicioIndividual crearServicioImplementado(String nombre, Categoria categoria, float precio, float descuento) {
		return new ServicioIndividual(nombre, "Descripcion de " + nombre, "COD-" + nombre,
				"Operador SA", categoria, precio, descuento, calendario3);
	}

	private static void setCampo(Object objeto, String nombre, Object valor) throws Exception {
		
		Field campo;
		try { campo = objeto.getClass().getDeclaredField(nombre); }
		catch (Exception e) { campo = objeto.getClass().getSuperclass().getDeclaredField(nombre); }
		campo.setAccessible(true);
		campo.set(objeto, valor);
		
	}

}
