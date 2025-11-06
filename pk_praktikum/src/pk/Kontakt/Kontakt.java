package pk.Kontakt;

import java.util.Objects;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.Comparable;

public abstract class Kontakt implements Comparable<Kontakt>, VcardExportable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8852061076756548545L;
	private String telefon;
	private String email;

	public Kontakt() {
		
	}
	
	// Full constructor
	public Kontakt(String telefon, String email) {
		this.telefon = telefon;
		this.email = email;
	}

	// Setters and Getters

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return " Telefon = " + telefon + ", Email = " + email + " ";
	}

	public abstract String drucke();

	public abstract String getSuchkriterium();

	@Override
	public int hashCode() {
		return Objects.hash(email, telefon);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kontakt other = (Kontakt) obj;
		return Objects.equals(email, other.email) && Objects.equals(telefon, other.telefon);
	}

	@Override
	public int compareTo(Kontakt o) {
		// Referenz vergleich

		if (this == o) {
			return 0;
		}

		// Nullität absichern

		if (this.getSuchkriterium() == null) {
			if (o.getSuchkriterium() == null) {
				return 0;
			}
			return -1;
		} else if (o.getSuchkriterium() == null) {
			return 1;
		}
		return this.getSuchkriterium().compareTo(o.getSuchkriterium());
	}

	public String exportiereAlsVcard() {

		return "EMAIL;type=INTERNET;type=pref:" + this.email + "\r\n" + "TEL;type=VOICE:" + this.telefon + "\n";
	}

}
