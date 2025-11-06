package pk.Kontakt.ui;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pk.Kontakt.Adresse;
import pk.Kontakt.Firma;
import pk.Kontakt.Kontakt;
import pk.Kontakt.Kontaktliste;

public class FirmaView extends ErfassungView {

	private Firma firma;

	public FirmaView(Stage stage, Firma firma, ObservableList<Kontakt> observableList, Kontaktliste kontaktliste) {

		super(firma, observableList, kontaktliste);
		this.initOwner(stage);
		this.initModality(Modality.WINDOW_MODAL);

	}

	public void showView() {
		super.showView();

		var fp = new FlowPane(this.name(), super.getScene().getRoot(), this.titledpane(), this.flowPaneView());

		FlowPane rootOkay = (FlowPane) fp.getChildren().get(3);
		Button btn = (Button) rootOkay.getChildren().get(0);

		/*
		 * Here comes the dirty job
		 * 
		 */
//		// Name zurückliefern
		HBox box = (HBox) fp.getChildren().get(0);
		TextField txt = (TextField) box.getChildren().get(1);

		// Email zurückliefern
		VBox box1 = (VBox) this.getScene().getRoot();
		HBox hbox1 = (HBox) box1.getChildren().get(0);
		TextField m = (TextField) hbox1.getChildren().get(1);

		// Telefone zurückliefern
		hbox1 = (HBox) box1.getChildren().get(1);
		TextField t = (TextField) hbox1.getChildren().get(1);

		// Adresse
		TitledPane tp = (TitledPane) fp.getChildren().get(2);
		VBox root = (VBox) tp.getContent();
		// Street

		HBox hb1 = (HBox) root.getChildren().get(0);
		// Street
		TextField tf1 = (TextField) hb1.getChildren().get(1);

		// Hausnummer
		HBox specialNumber = (HBox) root.getChildren().get(0);
		TextField tf2 = (TextField) specialNumber.getChildren().get(3);

		HBox specialPLZ = (HBox) root.getChildren().get(1);
		// plz
		TextField tf3 = (TextField) specialPLZ.getChildren().get(1);

		// Stadt
		hb1 = (HBox) root.getChildren().get(1);
		TextField tf4 = (TextField) hb1.getChildren().get(3);

		hb1 = (HBox) root.getChildren().get(2);

		// Land

		TextField tf5 = (TextField) hb1.getChildren().get(1);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				try {
					Adresse ad = new Adresse(tf1.getText(), Integer.parseInt(tf2.getText()),
							Integer.parseInt(tf3.getText()), tf4.getText(), tf5.getText());
					firma = new Firma(txt.getText(), t.getText(), m.getText(), ad);

					if ((FirmaView.this.getKontaktlist().hasAContact(firma))) {
						DialogUtil.showErrorDialog("Meldung", "Dublette Einträge sind nicht erlaubt");
						FirmaView.this.close();
						return;
					}
					FirmaView.this.getKontaktlist().hinzufuegen(firma);
					FirmaView.this.getObservableList().add(firma);
					FirmaView.this.close();

					if (tf2.getText().isEmpty() && tf3.getText().isEmpty())
						throw new NumberFormatException();

					DialogUtil.showMessageDialog("Meldung", firma.getName() + " ist zur Liste hinzugefügt");
					;
				} catch (NumberFormatException exp) {

					DialogUtil.showErrorDialog("Meldung",
							" Es wurde falsche Formatt entweder für Hausnummer oder PLZ eingegeben ");

				}
			}
		});

		fp.setPrefWidth(400);
		fp.setPrefHeight(450);
		fp.setOrientation(Orientation.VERTICAL);
		fp.setPadding(new Insets(9));
		fp.setVgap(5);

		this.setScene(new Scene(fp));
		this.setTitle("Erfassung einer Firma");

		this.show();

	}

	private HBox name() {

		var label = new Label("Name: ");
		var name = new TextField();

		name.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		if (firma != null)
			name.setText(firma.getName());

		var box = new HBox(label, name);
		box.setHgrow(name, Priority.SOMETIMES);
		box.setSpacing(5);
		box.setPadding(new Insets(5));
		return box;
	}

	private TitledPane titledpane() {

		TitledPane pane = super.paneVorlage();
		pane.setText("Adresse");
		if (firma != null && firma.getAdresse() != null)
			fillThePane(pane);
		return pane;
	}

	private TitledPane fillThePane(TitledPane pane) {

		/*
		 * 
		 * Diese Methode ist dafür zuständig, Eingabe Felder der Adresse, automatisch
		 * auszufüllen, Falls ein Firma als parameter übegebn würde
		 *
		 */

		VBox root = (VBox) pane.getContent();

		if (firma != null && firma.getAdresse() != null) {

			/*
			 * Setzte das hb1 für das erstes HBox in TitledPane ein
			 */
			HBox hb1 = (HBox) root.getChildren().get(0);

			// Street
			TextField tf1 = (TextField) hb1.getChildren().get(1);
			tf1.setText(firma.getAdresse().getStrasse());

			// Hausnummer
			Integer hn = firma.getAdresse().getHausnummer();
			TextField tf2 = (TextField) hb1.getChildren().get(3);
			tf2.setText(hn.toString());

			/*
			 * Setzte das hb1 für das zweites HBox in TitledPane ein
			 */
			hb1 = (HBox) root.getChildren().get(1);

			// plz
			Integer plz = firma.getAdresse().getPlz();
			TextField tf3 = (TextField) hb1.getChildren().get(1);
			tf3.setText(plz.toString());

			// Stadt
			TextField tf4 = (TextField) hb1.getChildren().get(3);
			tf4.setText(firma.getAdresse().getOrt());

			/*
			 * Setzte das hb1 für das drittes HBox in TitledPane ein
			 */

			hb1 = (HBox) root.getChildren().get(2);

			// Land
			TextField tf5 = (TextField) hb1.getChildren().get(1);
			tf5.setText(firma.getAdresse().getLand());

		}
		pane.setContent(root);
		return pane;
	}

	private FlowPane okay() {

		FlowPane rootOkay = (FlowPane) this.flowPaneView();
		Button btn = (Button) rootOkay.getChildren().get(0);

		// Name zurückliefern
		HBox box = (HBox) this.name();
		TextField txt = (TextField) box.getChildren().get(1);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				JOptionPane.showMessageDialog(null, txt.getText());
			}
		});

		return rootOkay;
	}

	private TextField getName() {
		HBox box = (HBox) this.name();
		TextField txt = (TextField) box.getChildren().get(1);
		return txt;
	}

	public Firma getFirma() {
		return firma;
	}

	public void setFirma(Firma firma) {
		this.firma = firma;
	}

}
