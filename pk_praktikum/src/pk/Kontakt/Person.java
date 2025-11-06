
package pk.Kontakt;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Person extends Kontakt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4107463702487716258L;
	private String nachName;
	private String vorName;
	private LocalDate geburtstag;
	private Adresse adressePrivate;
	private Adresse adresseBeruflich;

	public Person() {

	}

	public Person(String nachName, String vorName, String telefon, String email, Adresse adressePrivate,
			Adresse adresseBeruflich) {
		super(telefon, email);
		this.nachName = nachName;
		this.vorName = vorName;
		this.geburtstag = LocalDate.of(1989, 7, 8);
		this.adresseBeruflich = adresseBeruflich;
		this.adressePrivate = adressePrivate;

	}

	public Person(String nachName, String vorName, LocalDate geburtstag, String telefon, String email,
			Adresse adressePrivate, Adresse adresseBeruflich) {
		super(telefon, email);
		this.nachName = nachName;
		this.vorName = vorName;
		this.geburtstag = geburtstag;
		this.adresseBeruflich = adresseBeruflich;
		this.adressePrivate = adressePrivate;
	}

	public String getNachName() {
		return nachName;
	}

	public void setNachName(String nachName) {
		this.nachName = nachName;
	}

	public String getVorName() {
		return vorName;
	}

	public void setVorName(String vorName) {
		this.vorName = vorName;
	}

	public LocalDate getGeburtstag() {
		return geburtstag;
	}

	public void setGeburtstag(LocalDate geburtstag) {
		this.geburtstag = geburtstag;
	}

	public Adresse getadressePrivate() {
		return adressePrivate;
	}

	public void setadressePrivate(Adresse adressePrivate) {
		this.adressePrivate = adressePrivate;
	}

	public Adresse getadresseBeruflich() {
		return adresseBeruflich;
	}

	public void setadresseBeruflich(Adresse adresseBeruflich) {
		this.adresseBeruflich = adresseBeruflich;
	}

	private String changeDate() {
		// I made this in one way, could have make it on two Steps
		return this.geburtstag.format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
	}

	@Override
	public String toString() {
		return nachName + " , " + vorName;
	}

	@Override
	public String drucke() {

		return "Name: " + this.getVorName() + " " + this.getNachName() + "\nTelefon: " + this.getTelefon()
				+ "\nE-Mail: " + this.getEmail() + "\nGeburtstag: " + this.changeDate() + "\nAdresse (private) : "
				+ this.adressePrivate + "Adresse (beruflich): " + this.adresseBeruflich + "\n";

	}

	@Override
	public String getSuchkriterium() {
		if (this instanceof Person) {
			return this.getNachName();
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(adresseBeruflich, adressePrivate, geburtstag, nachName, vorName);
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
		Person other = (Person) obj;
		return Objects.equals(adresseBeruflich, other.adresseBeruflich)
				&& Objects.equals(adressePrivate, other.adressePrivate) && Objects.equals(geburtstag, other.geburtstag)
				&& Objects.equals(nachName, other.nachName) && Objects.equals(vorName, other.vorName);
	}

	@Override
	public String exportiereAlsVcard() {
		String print1 = "";
		String print2 = "";
		if (this.adressePrivate != null) {
			print1 = "ADR;type=HOME:;;" + adressePrivate.exportiereAlsVcard();
		}
		if (this.adresseBeruflich != null) {
			print2 = "ADR;type=WORK:;;" + adresseBeruflich.exportiereAlsVcard();
		}

		return "BEGIN:VCARD\r\n" + "VERSION:4.0\n" + "N:" + this.nachName + ";" + this.vorName + ";;;\n" + "FN:"
				+ this.nachName + "\n" + super.exportiereAlsVcard() + print1 + print2 + "BDAY:" + this.changeDate()
				+ "\n" + "END:VCARD\n";

	}

}