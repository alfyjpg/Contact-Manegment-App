package pk.Kontakt;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Objects;

public class Firma extends Kontakt implements VcardExportable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8806359546111611842L;
	/**
	 * 
	 */
	private String name;
	private Adresse adresse;

	public Firma() {
		super();
	}

	public Firma(String name, String telefon, String email, Adresse adresse) {

		super(telefon, email);
		this.name = name;
		this.adresse = adresse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	public String drucke() {
		return "Name: " + this.name + "\nTelefon: " + this.getTelefon() + "\nE-Mail: " + this.getEmail() + "\n"
				+ this.adresse + "\n";

	}

	public String getSuchkriterium() {

		if (this instanceof Firma) {
			return this.getName();
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(adresse, name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Firma other = (Firma) obj;
		return Objects.equals(adresse, other.adresse) && Objects.equals(name, other.name);
	}

	@Override
	public String exportiereAlsVcard() {

		return "BEGIN:VCARD\r\n" + "VERSION:4.0\r\n" + "N:;;;;\n" + "FN:" + this.getName() + "\n" + "ORG:"
				+ this.getName() + "\n" + super.exportiereAlsVcard() + "ADR;type=WORK:;;" + adresse.exportiereAlsVcard()
				+ "END:VCARD\n";

	}

}
