package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.busquedaCatalogo.Criterio;
import unq.travel.cupos.RangoDeFechas;
import unq.travel.reportes.Exportador;
import unq.travel.reportes.ReporteExposicionProveedores;
import unq.travel.reportes.ReporteOcupacionYCupoDisponible;
import unq.travel.reportes.ReporteServiciosMasReservados;

class CatalogoTest {

	private Catalogo catalogo;
	private ItemCatalogo hotel;
	private ItemCatalogo museo;
	private RangoDeFechas periodo;
	private Exportador exportador;

	@BeforeEach
	void setUp() {
		
		exportador = mock(Exportador.class);
		periodo = new RangoDeFechas(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 10));
		catalogo = new Catalogo();
		hotel = mock(ItemCatalogo.class);
		museo = mock(ItemCatalogo.class);
	
	}

	@Test
	void testUnCatalogoNuevoNoTieneItems() {

		assertTrue(catalogo.getItems().isEmpty());

	}

	@Test
	void testAlAgregarUnItemElCatalogoLoContiene() {

		catalogo.agregarItem(hotel);

		assertEquals(List.of(hotel), catalogo.getItems());

	}

	@Test
	void testAlAgregarDosItemsLosContieneEnElOrdenAgregado() {

		catalogo.agregarItem(hotel);
		catalogo.agregarItem(museo);

		assertEquals(List.of(hotel, museo), catalogo.getItems());

	}

	@Test
	void testAlEliminarUnItemDejaDeContenerloYConservaLosDemas() {

		catalogo.agregarItem(hotel);
		catalogo.agregarItem(museo);

		catalogo.eliminarItem(hotel);

		assertEquals(List.of(museo), catalogo.getItems());

	}

	@Test
	void testEliminarUnItemQueNoEstaEnElCatalogoLanzaExcepcion() {

		catalogo.agregarItem(hotel);

		assertThrows(ItemInexistenteException.class, () -> catalogo.eliminarItem(museo));

	}

	@Test
	void testModificarLaListaDeItemsDevueltaNoModificaElCatalogo() {

		catalogo.agregarItem(hotel);

		catalogo.getItems().clear();

		assertEquals(List.of(hotel), catalogo.getItems());

	}

	@Test
	void testBuscaEntreSusItemsLosQueCumplenElCriterio() {

		Criterio criterio = mock(Criterio.class);
		when(criterio.cumpleConCriterio(museo)).thenReturn(true);
		catalogo.agregarItem(hotel);
		catalogo.agregarItem(museo);

		assertEquals(List.of(museo), catalogo.buscar(criterio));

	}
	
	@Test
	void testCrearReporteReservados() {
		
		when(exportador.exportar(any(ReporteServiciosMasReservados.class))).thenReturn("Contenido del reporte exportado");
		
		catalogo.crearReporteReservados(periodo);
		
		assertEquals(1, catalogo.exportarReportesEnFormato(exportador).size());
		assertEquals("Contenido del reporte exportado", catalogo.exportarReportesEnFormato(exportador).getFirst());
		
	}
	
	@Test
	void testCrearReporteOcupacionYCupoDisponible() {
		
		when(exportador.exportar(any(ReporteOcupacionYCupoDisponible.class))).thenReturn("Contenido del reporte exportado");
		
		catalogo.crearReporteOcupacionYCupoDisponible(periodo);
		
		assertEquals(1, catalogo.exportarReportesEnFormato(exportador).size());
		assertEquals("Contenido del reporte exportado", catalogo.exportarReportesEnFormato(exportador).getFirst());
		
	}
	
	@Test
	void testCrearReporteProveedores() {
		
		when(exportador.exportar(any(ReporteExposicionProveedores.class))).thenReturn("Contenido del reporte exportado");
		
		catalogo.crearReporteProveedores();
		
		assertEquals(1, catalogo.exportarReportesEnFormato(exportador).size());
		assertEquals("Contenido del reporte exportado", catalogo.exportarReportesEnFormato(exportador).getFirst());
		
	}
	
	@Test
	void testLimpiarReportes() {
		
		catalogo.crearReporteReservados(periodo);
		
		catalogo.limpiarReportes();
		
		assertTrue(catalogo.exportarReportesEnFormato(exportador).isEmpty());
		
	}
	
	@Test
	void testCuandoSeExportanLosReportesSeExportanTodosLosReportesExistentes() {

		when(exportador.exportar(any(ReporteServiciosMasReservados.class))).thenReturn("Contenido del reporte servicios exportado");
		when(exportador.exportar(any(ReporteExposicionProveedores.class))).thenReturn("Contenido del reporte proveedores exportado");
		
		catalogo.crearReporteReservados(periodo);
		catalogo.crearReporteProveedores();
		
		assertEquals(2, catalogo.exportarReportesEnFormato(exportador).size());
		assertEquals("Contenido del reporte servicios exportado", catalogo.exportarReportesEnFormato(exportador).getFirst());
		assertEquals("Contenido del reporte proveedores exportado", catalogo.exportarReportesEnFormato(exportador).getLast());
		
	}

}
