package pk.Kontakt.ui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pk.Kontakt.Kontakt;
import pk.Kontakt.Kontaktliste;

public abstract class ErfassungView extends Stage {

	private Kontakt contact;
	private ObservableList<Kontakt> observableList;
	private Kontaktliste kontaktlist;

	public ErfassungView(Kontakt contact, ObservableList<Kontakt> observableList, Kontaktliste kontaktList) {
		this.contact = contact;
		this.observableList = observableList;
		this.kontaktlist = kontaktList;
	}

	public void showView() {

		// creating labels
		var lbl0 = new Label("E-Mail: ");
		var lbl1 = new Label("Telefon: ");

		lbl0.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		lbl1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		// creating TextFields
		var mail = new TextField("");
		var telefon = new TextField("");

		mail.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		telefon.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		if (contact != null) {
			mail.setText(contact.getEmail());
			telefon.setText(contact.getTelefon());
		}

		var hbox1 = new HBox(lbl0, mail);
		hbox1.setPadding(new Insets(5.0));
		hbox1.setSpacing(3);
		HBox.setHgrow(lbl0, Priority.NEVER);
		HBox.setHgrow(mail, Priority.ALWAYS);
		var hbox2 = new HBox(lbl1, telefon);
		hbox2.setPadding(new Insets(5.0));
		hbox2.setSpacing(3);
		HBox.setHgrow(lbl1, Priority.NEVER);
		HBox.setHgrow(telefon, Priority.ALWAYS);

		var bigBox = new VBox(hbox1, hbox2);
		bigBox.setPadding(new Insets(5.0));
		bigBox.setSpacing(3);
		bigBox.setVgrow(hbox1, Priority.ALWAYS);
		bigBox.setVgrow(hbox2, Priority.ALWAYS);
		Scene scene = new Scene(bigBox);
		this.setScene(scene);

	}

	// Protected, weil man an der Klasse auf die Methode zugreift
	protected TitledPane paneVorlage() {

		/*
		 * Diese Methode beeinhlatet die Vorlage für TitledPane Elemente bzw Elemente
		 * der Adresse Private und Beruflich wie Straße, PLZ, Hausnummer etc
		 * 
		 * Durch diese Methode kann man einfach TitledPane erstellen
		 *
		 */

		var straße = new Label("Straße: ");
		var TextField1 = new TextField();
		var nr = new Label("Nr: ");
		var TextField2 = new TextField();
		var plz = new Label("PLZ: ");
		var TextField3 = new TextField();
		var stadt = new Label("Stadt");
		var TextField4 = new TextField();
		var land1 = new Label("Land: ");
		var textField5 = new TextField();

		var hbox21 = new HBox(straße, TextField1, nr, TextField2);
		hbox21.setSpacing(5);
		hbox21.setPadding(new Insets(10));

		var hbox31 = new HBox(plz, TextField3, stadt, TextField4);
		hbox31.setSpacing(5);
		hbox31.setPadding(new Insets(10));

		var hbox41 = new HBox(land1, textField5);
		hbox41.setSpacing(5);
		hbox41.setPadding(new Insets(10));

		var bigBox = new VBox(hbox21, hbox31, hbox41);
		bigBox.setSpacing(5);
		bigBox.setPadding(new Insets(10));
		var tp = new TitledPane("", bigBox);
		tp.setPadding(new Insets(10));

		return tp;
	}

	//
	/*
	 * This Method contains only the Okay and Abbruch buttons Protected, weil man an
	 * der Klasse auf die Methode zugreift
	 *
	 */
	protected FlowPane flowPaneView() {

		Button okay = new Button("Okay");

		var abbrechen = new Button("Abbrechen");

		abbrechen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ErfassungView.this.close();
			}
		});

		var fp = new FlowPane(Orientation.HORIZONTAL);

		fp.setAlignment(Pos.CENTER);
		fp.getChildren().addAll(okay, abbrechen);
		fp.setHgap(20);
		fp.setPadding(new Insets(10));

		return fp;

	}

	public Kontakt getContact() {
		return contact;
	}

	public ObservableList<Kontakt> getObservableList() {
		return observableList;
	}

	public Kontaktliste getKontaktlist() {
		return kontaktlist;
	}

}