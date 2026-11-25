package unq.travel.reportes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.Catalogo;
import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.RangoDeFechas;
import unq.travel.cupos.RegistroVentaPeriodo;

class ReporteServiciosMasReservadosTest {

	private LocalDate fecha1 = LocalDate.of(2026, 1, 1);;
	private LocalDate fecha2 = LocalDate.of(2026, 1, 2);;
	private LocalDate fecha3 = LocalDate.of(2026, 1, 3);;
	private LocalDate fecha4 = LocalDate.of(2026, 1, 4);
	
	private RangoDeFechas periodo1 = new RangoDeFechas(fecha2, fecha3);
	private RangoDeFechas periodo2 = new RangoDeFechas(fecha1, fecha4);

	private ReporteServiciosMasReservados reporte;
	private Catalogo catalogo;
	private ItemCatalogo item1;
	private ItemCatalogo item2;
	private ItemCatalogo itemInexistente;
	private RegistroVentaPeriodo registro1;
	private RegistroVentaPeriodo registro2;
	private Exportador exportador;
	
	@BeforeEach
	void setUp() throws Exception {
	
		exportador = mock(Exportador.class);
		
		item1 = mock(ItemCatalogo.class);
		item2 = mock(ItemCatalogo.class);
		itemInexistente = mock(ItemCatalogo.class);
		catalogo = mock(Catalogo.class);
		
		registro1 = new RegistroVentaPeriodo(periodo1, 1000f, 10);
		registro2 = new RegistroVentaPeriodo(periodo2, 2000f, 15);
		
		when(catalogo.getItems()).thenReturn(List.of(item1, item2));
		when(item1.cantidadYTotalCobradoDeVentasEnPeriodo(periodo1)).thenReturn(registro1);
		when(item2.cantidadYTotalCobradoDeVentasEnPeriodo(periodo1)).thenReturn(registro2);
		
		reporte = new ReporteServiciosMasReservados(catalogo, periodo1);
	
	}

	@Test
	void testItemsOrdenados() {
		
		assertEquals(List.of( 
				Map.entry(item2, registro2.cantidadVentas()),
				Map.entry(item1, registro1.cantidadVentas()))
				, reporte.getItemsOrdenados());
		
	}
	
	@Test
	void testPrecioPromedioCobrado() {
		
		assertEquals(0, reporte.getPrecioPromedioCobrado(itemInexistente));
		assertEquals(registro1.precioPromedio(), reporte.getPrecioPromedioCobrado(item1));
		assertEquals(registro2.precioPromedio(), reporte.getPrecioPromedioCobrado(item2));
		
	}
	
	@Test
	void testPeriodo() {
		
		assertEquals(periodo1, reporte.getPeriodo());
		
	}
	
	@Test
	void testExportar() {
		
		when(exportador.exportar(reporte)).thenReturn("Exportado");
		
		assertEquals("Exportado", reporte.exportar(exportador));
		
	}

}
