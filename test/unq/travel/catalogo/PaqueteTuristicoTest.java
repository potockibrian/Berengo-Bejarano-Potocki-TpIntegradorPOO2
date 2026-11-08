package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.cupos.CalendarioDeCupos;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;

class PaqueteTuristicoTest {

	private ServicioIndividual hotel;
	private ServicioIndividual museo;
	private ServicioIndividual traslado;
	private PaqueteTuristico packEscapadaUrbana;
	private RangoDeFechas periodo;
	private CalendarioDeCupos calendario1;
	private CalendarioDeCupos calendario2;

	@BeforeEach
	void setUp() {
		calendario1 = mock(CalendarioDeCupos.class);
		calendario2 = mock(CalendarioDeCupos.class);
		hotel = this.crearServicio("Noche de Hotel Central", Categoria.ALOJAMIENTO, 15000f, 0f);
		museo = this.crearServicio("Excursion Guiada Museo", Categoria.EXCURSION, 4000f, 0f);
		traslado = this.crearServicio("Traslado Aeropuerto-Hotel", Categoria.TRASLADO, 2000f, 0f);
		periodo = mock(RangoDeFechas.class);

		packEscapadaUrbana = new PaqueteTuristico("Pack Escapada Urbana", "Hotel, museo y traslado", 0.10f, calendario1);
		packEscapadaUrbana.agregarItem(hotel);
		packEscapadaUrbana.agregarItem(museo);
		packEscapadaUrbana.agregarItem(traslado);
		
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
		PaqueteTuristico kitGranTour = new PaqueteTuristico("Kit Gran Tour", "Pack Escapada Urbana y tour", 0.20f, calendario1);

		kitGranTour.agregarItem(packEscapadaUrbana);
		kitGranTour.agregarItem(tourGastronomico);

		assertEquals(23900f, kitGranTour.getPrecioBase(), 0.01f);
		assertEquals(19120f, kitGranTour.getPrecioFinal(), 0.01f);

	}

	@Test
	void testElDescuentoPromocionalDeUnServicioSeAcumulaConElDelPaquete() {

		ServicioIndividual hotelConPromo = this.crearServicio("Noche de Hotel Central", Categoria.ALOJAMIENTO, 15000f, 0.20f);
		PaqueteTuristico paquete = new PaqueteTuristico("Escapada con promo", "Hotel con promo y museo", 0.10f, calendario1);

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

		PaqueteTuristico kitGranTour = new PaqueteTuristico("Kit Gran Tour", "Pack Escapada Urbana", 0.20f, calendario1);

		kitGranTour.agregarItem(packEscapadaUrbana);

		assertTrue(kitGranTour.esDeCategoria(Categoria.ALOJAMIENTO));

	}

	@Test
	void testTieneDisponibilidadSiTodosSusItemsTienen() {

		when(calendario1.tieneDisponibilidad(periodo)).thenReturn(true);
		when(calendario2.tieneDisponibilidad(periodo)).thenReturn(true);
		PaqueteTuristico packSinStock = new PaqueteTuristico("Pack sin stock", "Servicio agotado", 0.10f, calendario1);
		PaqueteTuristico kitGranTour = new PaqueteTuristico("Kit Gran Tour", "Pack Escapada Urbana y pack agotado", 0.20f, calendario2);
		
		packEscapadaUrbana.agregarItem(kitGranTour);
		packEscapadaUrbana.agregarItem(packSinStock);
		
		assertTrue(packEscapadaUrbana.tieneDisponibilidad(periodo));

		when(calendario1.tieneDisponibilidad(periodo)).thenReturn(false);
		
		assertFalse(packEscapadaUrbana.tieneDisponibilidad(periodo));
		
	}

	@Test
	void testNoTieneDisponibilidadSiAlgunServicioNoTieneStock() {

		packEscapadaUrbana.agregarItem(this.crearServicioSinStock("Alquiler de Auto", Categoria.ALQUILER_AUTO));

		assertFalse(packEscapadaUrbana.tieneDisponibilidad());

	}

	@Test
	void testNoTieneDisponibilidadSiUnPaqueteAnidadoNoTiene() {

		PaqueteTuristico packSinStock = new PaqueteTuristico("Pack sin stock", "Servicio agotado", 0.10f, calendario1);
		PaqueteTuristico kitGranTour = new PaqueteTuristico("Kit Gran Tour", "Pack Escapada Urbana y pack agotado", 0.20f, calendario1);

		packSinStock.agregarItem(this.crearServicioSinStock("Tour Gastronomico", Categoria.EXCURSION));
		kitGranTour.agregarItem(packEscapadaUrbana);
		kitGranTour.agregarItem(packSinStock);

		assertFalse(kitGranTour.tieneDisponibilidad());

	}

	@Test
	void testCumpleConLaValidacionSiTieneNombreYTodosSusItemsSonValidos() {

		assertTrue(packEscapadaUrbana.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionSiTieneUnServicioInvalido() {

		ServicioIndividual servicioSinCodigo = new ServicioIndividual("Alquiler de Auto", "Auto compacto",
				null, "Operador SA", Categoria.ALQUILER_AUTO, 8000f, 0f, 10, calendario1);

		packEscapadaUrbana.agregarItem(servicioSinCodigo);

		assertFalse(packEscapadaUrbana.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionSinNombre() {

		PaqueteTuristico paqueteSinNombre = new PaqueteTuristico(null, "Hotel y museo", 0.10f, calendario1);

		paqueteSinNombre.agregarItem(hotel);
		paqueteSinNombre.agregarItem(museo);

		assertFalse(paqueteSinNombre.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionSiEstaVacio() {

		PaqueteTuristico paqueteVacio = new PaqueteTuristico("Paquete vacio", "Sin items", 0.10f, calendario1);

		assertFalse(paqueteVacio.esValido());

	}

	@Test
	void testNoCumpleConLaValidacionSiTieneUnPaqueteAnidadoInvalido() {

		PaqueteTuristico paqueteVacio = new PaqueteTuristico("Paquete vacio", "Sin items", 0.10f, calendario1);
		PaqueteTuristico kitGranTour = new PaqueteTuristico("Kit Gran Tour", "Pack Escapada Urbana y paquete vacio", 0.20f, calendario1);

		kitGranTour.agregarItem(packEscapadaUrbana);
		kitGranTour.agregarItem(paqueteVacio);

		assertFalse(kitGranTour.esValido());

	}

	@Test
	void testAlDecrementarElCupoDecrementaElDeCadaItem() {

		when(calendario1.tieneDisponibilidad(periodo)).thenReturn(true);
		ServicioIndividual hotelConUnCupo = mock(ServicioIndividual.class);
		when(hotelConUnCupo.tieneDisponibilidad(periodo)).thenReturn(true);
		PaqueteTuristico paquete = new PaqueteTuristico("Paquete", "Hotel con un cupo", 0.10f, calendario1);
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

		ServicioIndividual hotelConUnCupo = this.crearServicioConCupo("Noche de Hotel Central", 1);
		PaqueteTuristico paquete = new PaqueteTuristico("Paquete", "Hotel y auto agotado", 0.10f, calendario1);
		paquete.agregarItem(hotelConUnCupo);
		paquete.agregarItem(this.crearServicioSinStock("Alquiler de Auto", Categoria.ALQUILER_AUTO));

		assertThrows(SinStockException.class, () -> paquete.reservar(periodo));

		verify(calendario1, never()).reservar(periodo);

	}

	@Test
	void testDecrementarCupoConUnServicioRepetidoSinCupoSuficienteNoPierdeCupo() {

		when(calendario1.tieneDisponibilidad(periodo)).thenReturn(false);
		when(calendario2.tieneDisponibilidad(periodo)).thenReturn(true);
		ServicioIndividual hotelConUnCupo = this.crearServicioConCupo("Noche de Hotel Central", 1);
		PaqueteTuristico dosNoches = new PaqueteTuristico("Dos noches de hotel", "Hotel dos noches", 0.10f, calendario2);
		dosNoches.agregarItem(hotelConUnCupo);
		dosNoches.agregarItem(hotelConUnCupo);

		assertThrows(SinStockException.class, () -> dosNoches.reservar(periodo));

	}

	@Test
	void testNoSePuedeCrearConDescuentoFueraDeRango() {

		assertThrows(IllegalArgumentException.class, () -> new PaqueteTuristico("Paquete", "Descuento invalido", 1.5f, calendario1));
		assertThrows(IllegalArgumentException.class, () -> new PaqueteTuristico("Paquete", "Descuento invalido", -0.1f, calendario1));

	}

	@Test
	void testDevuelveSusItemsEnElOrdenAgregado() {

		assertEquals(List.of(hotel, museo, traslado), packEscapadaUrbana.getItems());

	}

	@Test
	void testModificarLaListaDeItemsDevueltaNoModificaElPaquete() {

		packEscapadaUrbana.getItems().clear();

		assertEquals(3, packEscapadaUrbana.getItems().size());

	}
	
	@Test
	void testCantidadDeVentasEnPeriodo() {
		
		ItemCatalogo hotelConUnCupo = mock(ItemCatalogo.class);
		when(hotelConUnCupo.cantidadDeVentasEnPeriodo(periodo)).thenReturn(3);
		PaqueteTuristico dosNoches = new PaqueteTuristico("Dos noches de hotel", "Hotel dos noches", 0.10f, calendario2);
		dosNoches.agregarItem(hotelConUnCupo);
		
		assertEquals(3, dosNoches.cantidadDeVentasEnPeriodo(periodo));
		
		verify(hotelConUnCupo).cantidadDeVentasEnPeriodo(periodo);
		
	}
	
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

	private ServicioIndividual crearServicio(String nombre, Categoria categoria, float precio, float descuento) {
		return new ServicioIndividual(nombre, "Descripcion de " + nombre, "COD-" + nombre,
				"Operador SA", categoria, precio, descuento, 10, calendario1);
	}

	private ServicioIndividual crearServicioSinStock(String nombre, Categoria categoria) {
		return new ServicioIndividual(nombre, "Descripcion de " + nombre, "COD-" + nombre,
				"Operador SA", categoria, 1000f, 0f, 0, calendario1);
	}

	private ServicioIndividual crearServicioConCupo(String nombre, int cupo) {
		return new ServicioIndividual(nombre, "Descripcion de " + nombre, "COD-" + nombre,
				"Operador SA", Categoria.ALOJAMIENTO, 1000f, 0f, cupo, calendario1);
	}

}
