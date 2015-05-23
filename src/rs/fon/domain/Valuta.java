package rs.fon.domain;


public class Valuta {

	private String naziv;
	private double kurs;

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public double getKurs() {
		return kurs;
	}

	public void setKurs(double kurs) {
		this.kurs = kurs;
	}

	@Override
	public String toString() {
		return "Valuta [naziv=" + naziv + ", kurs=" + kurs + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Valuta other = (Valuta) obj;
		if (Double.doubleToLongBits(kurs) != Double
				.doubleToLongBits(other.kurs))
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		return true;
	}

}
