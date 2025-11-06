package pk.Kontakt.ui;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pk.Kontakt.Adresse;
import pk.Kontakt.Kontakt;
import pk.Kontakt.Kontaktliste;
import pk.Kontakt.Person;

/*
 *  Damit ist einfacher für die Person, die den Code begutachtet, 
 * Das Eingabefenster ist auf verschiedene Panes und Layouts vertreilt 
 * Am Ende sind alle Fensters und Layout in einem HBox verpackt. 
 * 
 * Jede Pane bzw Layout ist durch Ihre eigene Methode definiert 
 * Am Ende wurden alle Methoden in einem Pane / Layout  aufgerufen
 * 
 */
public class PersonView extends ErfassungView {

	private Person contact;

	public PersonView(Stage stage, Person person, ObservableList<Kontakt> observableList, Kontaktliste kontaktliste) {
		super(person, observableList, kontaktliste);
		this.initOwner(stage);
		this.initModality(Modality.WINDOW_MODAL);
	}

	public void showView() {
		// Call the superclass showView method
		super.showView();
		var root = this.getScene().getRoot();

		// Adding root node so as every other layout to the big Pane
		var box = new VBox(this.namen(), root, this.dp(), this.titledPane1(), this.titledPane2(), this.flowPaneView());

		// Okay Button
		FlowPane rootOkay = (FlowPane) box.getChildren().get(5);
		Button btn = (Button) rootOkay.getChildren().get(0);

		// Namen TextFieldern
		HBox root1 = (HBox) box.getChildren().get(0);
		TextField vorName = (TextField) root1.getChildren().get(1);
		TextField nachName = (TextField) root1.getChildren().get(3);

		// Geburtstag
		HBox root2 = (HBox) box.getChildren().get(2);
		DatePicker dp = (DatePicker) root2.getChildren().get(1);

		// Email TextField
		VBox box1 = (VBox) this.getScene().getRoot();
		HBox hbox1 = (HBox) box1.getChildren().get(0);
		TextField m = (TextField) hbox1.getChildren().get(1);

		// Telefone zurückliefern
		hbox1 = (HBox) box1.getChildren().get(1);
		TextField t = (TextField) hbox1.getChildren().get(1);

		// TitledPanes:

		/*
		 * 1st TitledPane (Adresse Private ):
		 */

		// Root of the first Titled Pane
		TitledPane tp = (TitledPane) box.getChildren().get(3);
		VBox root3 = (VBox) tp.getContent();

		// Street
		HBox hb1 = (HBox) root3.getChildren().get(0);
		TextField tf1 = (TextField) hb1.getChildren().get(1);

		// Hausnummer
		HBox specialNumber = (HBox) root3.getChildren().get(0);
		TextField tf2 = (TextField) specialNumber.getChildren().get(3);

		// plz
		HBox specialPLZ = (HBox) root3.getChildren().get(1);
		TextField tf3 = (TextField) specialPLZ.getChildren().get(1);

		// Stadt
		hb1 = (HBox) root3.getChildren().get(1);
		TextField tf4 = (TextField) hb1.getChildren().get(3);

		// Land
		hb1 = (HBox) root3.getChildren().get(2);
		TextField tf5 = (TextField) hb1.getChildren().get(1);

		/*
		 * 2nd TitledPane (Adresse beruflich ):
		 */

		TitledPane tp2 = (TitledPane) box.getChildren().get(4);
		VBox root4 = (VBox) tp2.getContent();

		// Street
		HBox hb02 = (HBox) root4.getChildren().get(0);
		TextField tf01 = (TextField) hb02.getChildren().get(1);

		// Hausnummer
		HBox specialNumber0 = (HBox) root4.getChildren().get(0);
		TextField tf02 = (TextField) specialNumber0.getChildren().get(3);

		// plz
		HBox specialPLZ0 = (HBox) root4.getChildren().get(1);
		TextField tf03 = (TextField) specialPLZ0.getChildren().get(1);

		// Stadt
		hb02 = (HBox) root4.getChildren().get(1);
		TextField tf04 = (TextField) hb02.getChildren().get(3);

		// Land
		hb02 = (HBox) root4.getChildren().get(2);
		TextField tf05 = (TextField) hb02.getChildren().get(1);
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try {
					Adresse adressp = new Adresse(tf1.getText(), Integer.parseInt(tf2.getText()),
							Integer.parseInt(tf3.getText()), tf4.getText(), tf5.getText());
					Adresse adressb = new Adresse(tf01.getText(), Integer.parseInt(tf02.getText()),
							Integer.parseInt(tf03.getText()), tf04.getText(), tf05.getText());
					Person perso = new Person(nachName.getText(), vorName.getText(), dp.getValue(), t.getText(),
							m.getText(), adressp, adressb);

					if ((PersonView.this.getKontaktlist().getContacts().contains(perso))) {
						DialogUtil.showErrorDialog("Meldung", "Dublette Einträge sind nicht erlaubt");
						PersonView.this.close();
						return;
					}

					PersonView.this.getObservableList().add(perso);
					PersonView.this.getKontaktlist().hinzufuegen(perso);
					DialogUtil.showMessageDialog("Meldung", perso + " ist zur Liste hinzugefügt");
					PersonView.this.close();
				} catch (NumberFormatException ex) {
					DialogUtil.showErrorDialog("Meldung",
							" Es wurde falsche Formatt entweder für Hausnummer oder PLZ eingegeben ");
				}
			
			}
		});

		box.setSpacing(5);
		box.setPadding(new Insets(10));
		this.setScene(new Scene(box));
		this.setTitle("Erfassung einer Person");
		this.show();

	}

	private HBox namen() {

		// Vorname und Nachname Controls
		List<Control> list = new ArrayList<Control>();
		Label lbl0 = new Label("Vorname: ");
		var vname = new TextField();
		Label lbl1 = new Label("Nachname: ");
		var nname = new TextField();

		if (contact != null) {
			vname.setText(contact.getVorName());
			nname.setText(contact.getVorName());
		}
		list.add(lbl0);
		list.add(vname);
		list.add(lbl1);
		list.add(nname);

		// Adding the vorname and nachname controls to a hbox.
		var hbox = new HBox();
		hbox.getChildren().addAll(list);
		hbox.setSpacing(5);
		hbox.setPadding(new Insets(10));

		return hbox;
	}

// Diese Methode beeinhaltet die DatePicker
	private HBox dp() {
		var geburtstag = new Label("Geburtstag: ");
		LocalDate ld = LocalDate.now();
		DateTimeFormatter dtm = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		ld.format(dtm);
		DatePicker dp = new DatePicker(ld);
		if (contact != null) {
			dp.setValue(contact.getGeburtstag());
		}

		// Hbox of the datepicker
		var hbox = new HBox(geburtstag, dp);
		hbox.setSpacing(5);
		hbox.setPadding(new Insets(10));

		return hbox;
	}

	// Erstellen von TitledPane aber private.
	private TitledPane titledPane1() {

		TitledPane pane = this.paneVorlage();
		pane.setText("Adresse (Private)");
		if (contact != null && contact.getadressePrivate() != null)
			this.fillThePane(pane);
		return pane;
	}

//Erstellen von TitledPane aber beruflich.
	private TitledPane titledPane2() {

		TitledPane pane = super.paneVorlage();
		pane.setText("Adresse(beruflich)");
		if (contact != null && contact.getadresseBeruflich() != null)
			this.fillThePane(pane);
		return pane;
	}

//	Diese Methode ist dafür zuständig, Eingabe Felder der Adresse, egal ob
//	  beruflich oder Private automatisch auszufüllen
	private TitledPane fillThePane(TitledPane pane) {

		VBox root = (VBox) pane.getContent();

		if (contact != null && contact.getadresseBeruflich() != null) {
			// Setzte das hb1 für das erstes HBox in TitledPane ein
			HBox hb1 = (HBox) root.getChildren().get(0);

			// Street
			TextField tf1 = (TextField) hb1.getChildren().get(1);
			tf1.setText(contact.getadresseBeruflich().getStrasse());

			// Hausnummer
			Integer hn = contact.getadresseBeruflich().getHausnummer();
			TextField tf2 = (TextField) hb1.getChildren().get(3);
			tf2.setText(hn.toString());

			// Setzte das hb1 für das zweites HBox in TitledPane ein
			hb1 = (HBox) root.getChildren().get(1);

			// plz
			Integer plz = contact.getadresseBeruflich().getPlz();
			TextField tf3 = (TextField) hb1.getChildren().get(1);
			tf3.setText(plz.toString());

			// Stadt
			TextField tf4 = (TextField) hb1.getChildren().get(3);
			tf4.setText(contact.getadresseBeruflich().getOrt());

			// Setzte das hb1 für das drittes HBox in TitledPane ein
			hb1 = (HBox) root.getChildren().get(2);

			// Land
			TextField tf5 = (TextField) hb1.getChildren().get(1);
			tf5.setText(contact.getadresseBeruflich().getLand());

		}

		pane.setContent(root);
		return pane;
	}

}
