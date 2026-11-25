package unq.travel.reportes;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;

public class ExportadorTextoTest {
	
	private Map<Categoria, Cupo> cuposPorCategoria;
	private Map<RangoDeFechas, Cupo> cuposPorPeriodo;
	private Cupo cupo3;
	private Cupo cupo2;
	private Cupo cupo1;
	private RangoDeFechas periodo1;
	private RangoDeFechas periodo2;
	private LocalDate fechaInicio1;
	private LocalDate fechaFinal1;
	private LocalDate fechaInicio2;
	private LocalDate fechaFinal2;
	private ExportadorTexto exportador;
	private ReporteOcupacionYCupoDisponible reporteOcupacion;
	private ReporteExposicionProveedores reporteExposicion;
	private ReporteServiciosMasReservados reporteReservados;
	private ItemCatalogo item1;
	private ItemCatalogo item2;
	private ItemCatalogo item3;
	private RegistroDeVentas registroDeVentas;
	
	@BeforeEach
	void setUp() {

		reporteExposicion = mock(ReporteExposicionProveedores.class);
		reporteReservados = mock(ReporteServiciosMasReservados.class);
		reporteOcupacion = mock(ReporteOcupacionYCupoDisponible.class);
		fechaInicio1 = LocalDate.of(2000, 7, 4);
		fechaInicio2 = LocalDate.of(2000, 4, 6);
		fechaFinal1 = LocalDate.of(2000, 2, 11);
		fechaFinal2 = LocalDate.of(2000, 10, 20);
		periodo1 = new RangoDeFechas(fechaInicio1, fechaFinal1);
		periodo2 =new RangoDeFechas(fechaInicio2, fechaFinal2);
		cuposPorCategoria = new HashMap<>();
		cuposPorPeriodo = new HashMap<>();
		cupo1 = new Cupo(3);
		cupo2 = new Cupo(10);
		cupo3 = new Cupo(2);
		cupo3.reservar();
		item1 = mock(ItemCatalogo.class);
		item2 = mock(ItemCatalogo.class);
		item3 = mock(ItemCatalogo.class);
		when(item1.getNombre()).thenReturn("item1");
		when(item2.getNombre()).thenReturn("item2");
		when(item3.getNombre()).thenReturn("item3");
		when(item1.getPrecioFinal()).thenReturn(1000f);
		when(item2.getPrecioFinal()).thenReturn(2000f);
		when(item3.getPrecioFinal()).thenReturn(3000f);
		
		exportador = new ExportadorTexto();
		registroDeVentas = mock(RegistroDeVentas.class);
		
	}
	
	@Test
	public void exportaLaExposicionDeCadaProveedorEnTexto() {
		
		Map<String, Double> porcentajes = new HashMap<>();
		porcentajes.put("Proveedor A", 66.67);
		porcentajes.put("Proveedor B", 33.33);
		when(reporteExposicion.getPorcentajesPorProveedor()).thenReturn(porcentajes);
		
		String resultado = exportador.exportar(reporteExposicion);
		
		assertTrue(resultado.contains("Exposicion por proveedor"));
		assertTrue(resultado.contains("Proveedor A"));
		assertTrue(resultado.contains("66.67"));
		assertTrue(resultado.contains("Proveedor B"));
		assertTrue(resultado.contains("33.33"));
		
	}
	
	@Test
	public void exportaLaOcupacionYCupoDisponibleEnTexto() {
		
		//List<Cupo> lista1 = List.of(cupo1, cupo2, cupo3);
		//List<Cupo> lista2 = List.of(cupo2, cupo3);
		cuposPorCategoria.put(Categoria.ALOJAMIENTO, cupo1);
		cuposPorCategoria.put(Categoria.VUELO, cupo3);
		//cuposPorPeriodo.put(periodo1, lista1);
		//cuposPorPeriodo.put(periodo2, lista2);
		when(reporteOcupacion.getCuposPorCategoria()).thenReturn(new ArrayList<>(cuposPorCategoria.entrySet()));
		when(reporteOcupacion.periodo()).thenReturn(periodo1);
		
		String resultado = exportador.exportar(reporteOcupacion);
		
		assertTrue(resultado.contains("Ocupacion y cupo disponible"));
		assertTrue(resultado.contains("Por categoria"));
		assertTrue(resultado.contains(Categoria.ALOJAMIENTO.toString()));
		assertTrue(resultado.contains(exportador.describirCupos(cupo1)));
		assertTrue(resultado.contains(Categoria.VUELO.toString()));
		assertTrue(resultado.contains(exportador.describirCupos(cupo3)));
		
		/*
		assertTrue(resultado.contains(periodo1.toString()));
		assertTrue(resultado.contains(exportador.describirCupos(lista1)));
		assertTrue(resultado.contains(periodo2.toString()));
		assertTrue(resultado.contains(exportador.describirCupos(lista2)));
		*/
		
	}
	
	@Test
	public void exportaLosServiciosMasReservadosSegunPeriodoEnTexto() {
		
		Map<ItemCatalogo, Integer> serviciosMasReservados = new HashMap<>();
		serviciosMasReservados.put(item1, 10);
		serviciosMasReservados.put(item2, 20);
		serviciosMasReservados.put(item3, 30);
		
		when(reporteReservados.getPrecioPromedioCobrado(item1)).thenReturn(1000f);
		when(reporteReservados.getPrecioPromedioCobrado(item2)).thenReturn(2000f);
		when(reporteReservados.getPrecioPromedioCobrado(item3)).thenReturn(3000f);
		
		when(reporteReservados.getItemsOrdenados()).thenReturn(new ArrayList<>(serviciosMasReservados.entrySet()));
		when(reporteReservados.getPeriodo()).thenReturn(periodo1);
		
		String resultado = exportador.exportar(reporteReservados);
		
		assertTrue(resultado.contains("Servicios mas reservados"));
		assertTrue(resultado.contains("Periodo: " + periodo1.toString()));
		assertTrue(resultado.contains(
				item1.getNombre() + ": " +
				10 + " unidades, cantidad " +
				String.format(Locale.US, "%.2f", reporteReservados.getPrecioPromedioCobrado(item1)) + "\n"
				));
		assertTrue(resultado.contains(
				item2.getNombre() + ": " +
				20 + " unidades, cantidad " +
				String.format(Locale.US, "%.2f", reporteReservados.getPrecioPromedioCobrado(item2)) + "\n"
				));
		assertTrue(resultado.contains(
				item3.getNombre() + ": " +
				30 + " unidades, cantidad " +
				String.format(Locale.US, "%.2f", reporteReservados.getPrecioPromedioCobrado(item3)) + "\n"
				));
		
	}

}
