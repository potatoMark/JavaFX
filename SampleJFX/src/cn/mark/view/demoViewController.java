package cn.mark.view;


import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.sun.media.jfxmedia.events.NewFrameEvent;

import cn.mark.MainApp;
import cn.mark.bean.Person;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;

public class demoViewController {

	//
	@FXML
	private ChoiceBox<Object> cc;

	@FXML
	private TreeView<String> treeView;

	@FXML
	private TableView<Person> tableView;

	@FXML
	private ListView<Object> lv;
	@FXML
	private TableColumn<Person, String> firstName;
	@FXML
	private TableColumn<Person, String> lastName;

	@FXML
	private Slider slidar ;

	@FXML
	private ProgressBar pBar;

	@FXML
	private ProgressIndicator pInd;

	@FXML
	private Hyperlink hLink;
	@FXML
	private WebView browser;

	@FXML
	private Text text;

	@FXML
	private Button button;



    // Reference to the main application.
    private MainApp mainApp;
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public demoViewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Add some sample data
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
        // Initialize the person table with the two columns.
//    	cc.getItems().addAll("A","X",new Separator(),"C");

    	cc.setItems(FXCollections.observableArrayList( "New Document", "Open ",
    		    new Separator(), "Save", "Save as"));
    	lv.setItems(FXCollections.observableArrayList( "New Document", "Open ",
    		     "Save", "Save as"));
    	lv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    	firstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
    	lastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    	tableView.setItems(personData);

    	firstName.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
//    	firstName.setOnEditCommit(
//            (CellEditEvent<Person, String> t) -> {
//                ((Person) t.getTableView().getItems().get(
//                        t.getTablePosition().getRow())
//                        ).setFirstName(t.getNewValue());
//        });

    	TreeItem<String> rootItem = new TreeItem<String>("main");
    	rootItem.setExpanded(true);
    	TreeItem<String> itemA = new TreeItem<String>("A");
    	TreeItem<String> itemB = new TreeItem<String>("C");
    	for (int i = 0; i < 6; i++) {
    		TreeItem<String> item = new TreeItem<String>("book"+i);
    		itemA.getChildren().add(item);
		}
    	for (int i = 0; i < 5; i++) {
    		TreeItem<String> item = new TreeItem<String>("B"+i);
    		itemB.getChildren().add(item);
		}
    	rootItem.getChildren().add(itemA);
    	rootItem.getChildren().add(itemB);
    	treeView.setRoot(rootItem);

        treeView.setEditable(true);
        treeView.setCellFactory(TextFieldTreeCell.forTreeView());
//        treeView.setCellFactory((TreeView<String> p) ->
//        new TextFieldTreeCellImpl());


        slidar.valueProperty().addListener(
	        (observable,oldValue,newValue)->{
	        	pBar.setProgress((newValue.doubleValue()/100));
	        	pInd.setProgress((newValue.doubleValue()/100));
	        }
        );



        text.setCache(true);
        text.setText("FUJITSU");
        text.setFill(Color.RED);
        text.setFont(Font.font(null, FontWeight.BOLD, 36));
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        Reflection r = new Reflection();
        r.setFraction(0.7f);
//        text.setEffect(new GaussianBlur());
//        text.setEffect(ds);
          text.setEffect(r);

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
    }
}

final class TextFieldTreeCellImpl extends TreeCell<String> {

	private final ContextMenu addMenu = new ContextMenu();

    public TextFieldTreeCellImpl() {
        MenuItem addMenuItem = new MenuItem("Add Employee");
        addMenu.getItems().add(addMenuItem);
        addMenuItem.setOnAction((ActionEvent t) -> {
            TreeItem newEmployee =  new TreeItem<>("New Employee");
            getTreeItem().getChildren().add(newEmployee);
        });
    }

	@Override
	public void startEdit() {
		super.startEdit();
	}

	@Override
	public void commitEdit(String newValue) {
		super.commitEdit(newValue);
	}

	@Override
	public void cancelEdit() {
		// TODO Auto-generated method stub
		super.cancelEdit();
	}


}