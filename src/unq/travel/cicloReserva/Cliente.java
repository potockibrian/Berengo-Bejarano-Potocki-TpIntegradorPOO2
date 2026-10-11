package unq.travel.cicloReserva;

import java.util.ArrayList;
import java.util.List;

import unq.travel.traslado.Voucher;

public class Cliente {

	private List<Comprobante> comprobantes = new ArrayList<Comprobante>();
	private List<Voucher> vouchers = new ArrayList<Voucher>();

	public String getDireccionDeEmail() {

		return "";
		
	}

	public void añadirVoucher(Voucher any) {

		vouchers.add(any);
		
	}

	public void añadirComprobante(Comprobante any) {

		comprobantes.add(any);
		
	}

}
