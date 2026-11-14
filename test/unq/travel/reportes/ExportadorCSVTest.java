package unq.travel.reportes;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

public class ExportadorCSVTest {
	
	private Map<Categoria, List<Cupo>> cuposPorCategoria;
	private Map<RangoDeFechas, List<Cupo>> cuposPorPeriodo;
	private Cupo cupo3;
	private Cupo cupo2;
	private Cupo cupo1;
	private RangoDeFechas periodo1;
	private RangoDeFechas periodo2;
	private LocalDate fechaInicio1;
	private LocalDate fechaFinal1;
	private LocalDate fechaInicio2;
	private LocalDate fechaFinal2;
	private ExportadorCSV exportador;
	private ReporteOcupacionYCupoDisponible reporteOcupacion;
	private ReporteExposicionProveedores reporteExposicion;
	private ReporteServiciosMasReservados reporteReservados;
	private ItemCatalogo item1;
	private ItemCatalogo item2;
	private ItemCatalogo item3;
	private RegistroDeVentas registroDeVentas;
	
	
	@BeforeEach
	void setUp() {

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
		
		exportador = new ExportadorCSV();
		registroDeVentas = mock(RegistroDeVentas.class);
		
	}
	
	@Test
	public void exportaLaExposicionDeCadaProveedorEnCSV() {
		
		Map<String, Double> porcentajes = new HashMap<>();
		porcentajes.put("Proveedor A", 66.67);
		porcentajes.put("Proveedor B", 33.33);
		
		reporteExposicion = new ReporteExposicionProveedores(porcentajes);
		
		String resultado = exportador.exportar(reporteExposicion);
		
		assertTrue(resultado.contains("Proveedor;Exposicion (%)"));
        assertTrue(resultado.contains("Proveedor A"));
        assertTrue(resultado.contains("66,67"));
        assertTrue(resultado.contains("Proveedor B"));
        assertTrue(resultado.contains("33,33"));
        
	}
	
	@Test
	public void exportaLaOcupacionYCupoDisponibleEnCSV() {
		
		List<Cupo> lista1 = List.of(cupo1, cupo2, cupo3);
		List<Cupo> lista2 = List.of(cupo2, cupo3);
		cuposPorCategoria.put(Categoria.ALOJAMIENTO, List.of(cupo1));
		cuposPorCategoria.put(Categoria.VUELO, List.of(cupo2, cupo3));
		cuposPorPeriodo.put(periodo1, lista1);
		cuposPorPeriodo.put(periodo2, lista2);
		
		reporteOcupacion = new ReporteOcupacionYCupoDisponible(cuposPorCategoria, cuposPorPeriodo);
		
		String resultado = exportador.exportar(reporteOcupacion);
		
		assertTrue(resultado.contains("Agrupacion;Valor;Cupos totales;Cupos reservados;Cupos libres\n"));
		assertTrue(resultado.contains(Categoria.VUELO.toString()));
		assertTrue(resultado.contains(Categoria.ALOJAMIENTO.toString()));
		assertTrue(resultado.contains(String.valueOf(lista1.stream().mapToInt(c -> c.getTopeMaximo()).sum())));
		assertTrue(resultado.contains(String.valueOf(lista1.stream().mapToInt(c -> c.cantidadDeVentas()).sum())));
		assertTrue(resultado.contains(String.valueOf(lista1.stream().mapToInt(c -> c.cantidadDeCuposDisponibles()).sum())));
		assertTrue(resultado.contains(String.valueOf(lista2.stream().mapToInt(c -> c.getTopeMaximo()).sum())));
		assertTrue(resultado.contains(String.valueOf(lista2.stream().mapToInt(c -> c.cantidadDeVentas()).sum())));
		assertTrue(resultado.contains(String.valueOf(lista2.stream().mapToInt(c -> c.cantidadDeCuposDisponibles()).sum())));
		
	}
	
	@Test
	public void exportaLosServiciosMasReservadosSegunPeriodo() {
		
		Map<ItemCatalogo, Integer> serviciosMasReservados = new HashMap<>();
		serviciosMasReservados.put(item1, 10);
		serviciosMasReservados.put(item2, 20);
		serviciosMasReservados.put(item3, 30);
		
		reporteReservados = new ReporteServiciosMasReservados(
				periodo1, serviciosMasReservados, registroDeVentas);
		
		String resultado = exportador.exportar(reporteReservados);
		
		assertTrue(resultado.contains("Servicio;Unidades vendidas;Precio promedio cobrado;Periodo\n"));
		assertTrue(resultado.contains(
				exportador.escapar(item1.getNombre()) + ";" +
				10 + ";" +
				String.format(Locale.US, "%.2f", reporteReservados.getPrecioPromedioCobrado(item1)) + ";" +
				exportador.escapar(String.valueOf(reporteReservados.getPeriodo())) + "\n"
				));
		assertTrue(resultado.contains(
				exportador.escapar(item2.getNombre()) + ";" +
				20 + ";" +
				String.format(Locale.US, "%.2f", reporteReservados.getPrecioPromedioCobrado(item2)) + ";" +
				exportador.escapar(String.valueOf(reporteReservados.getPeriodo())) + "\n"
				));
		assertTrue(resultado.contains(
				exportador.escapar(item3.getNombre()) + ";" +
				30 + ";" +
				String.format(Locale.US, "%.2f", reporteReservados.getPrecioPromedioCobrado(item3)) + ";" +
				exportador.escapar(String.valueOf(reporteReservados.getPeriodo())) + "\n"
				));
		
	}
	
	@Test
	public void testEscapar() {
		
		String texto = ";" + "\"" + "\n" + "\r";
		
		assertEquals("\"" + texto.replace("\"", "\"\"") + "\"", exportador.escapar(texto));
		
	}
}
