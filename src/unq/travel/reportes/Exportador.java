package unq.travel.reportes;

public interface Exportador<T> {
	String exportar(T reporte);
}
