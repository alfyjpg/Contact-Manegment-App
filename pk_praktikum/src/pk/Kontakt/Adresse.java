package pk.Kontakt;

import java.io.Serializable;
import java.util.Objects;



public class Adresse implements VcardExportable, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7458311554900174805L;
	private String strasse;
	private int hausnummer;
	private int plz;
	private String ort;
	private String land;

	// Empty constructor

	public Adresse() {

	}

	// Full constructor
	public Adresse(String strasse, int hausnummer, int plz, String ort, String land) {
		this.strasse = strasse;
		this.hausnummer = hausnummer;
		this.plz = plz;
		this.ort = ort;
		this.land = land;
	}

	// Getters and Setters
	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public int getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(int hausnummer) {
		this.hausnummer = hausnummer;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String toString() {

		return "Adresse: " + strasse + " " + hausnummer + ", " + plz + ", " + ort + ", " + land + "\n";

	
	}

	@Override
	public int hashCode() {
		return Objects.hash(hausnummer, land, ort, plz, strasse);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adresse other = (Adresse) obj;
		return hausnummer == other.hausnummer && Objects.equals(land, other.land) && Objects.equals(ort, other.ort)
				&& plz == other.plz && Objects.equals(strasse, other.strasse);
	}

	@Override
	public String exportiereAlsVcard() {
		String print = this.strasse + ";" + this.ort + ";;" + this.plz + ";" + this.land + ".\n";

		return print;

	}

}
