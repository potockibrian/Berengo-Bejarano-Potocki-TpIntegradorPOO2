package unq.travel.reportes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class ExportadorCSVTest {
	@Test
	public void exportaLaExposicionDeCadaProveedorEnCSV() {
		Map<String, Double> porcentajes = new HashMap<>();
		porcentajes.put("Proveedor A", 66.67);
		porcentajes.put("Proveedor B", 33.33);
		
		ReporteExposicionProveedores reporte =
				new ReporteExposicionProveedores(porcentajes);
		
		ExportadorCSV exportador = new ExportadorCSV();
		
		String resultado = exportador.exportar(reporte);
		
		assertTrue(resultado.contains("Proveedor;Exposicion (%)"));
        assertTrue(resultado.contains("Proveedor A"));
        assertTrue(resultado.contains("66,67"));
        assertTrue(resultado.contains("Proveedor B"));
        assertTrue(resultado.contains("33,33"));
	}
}
