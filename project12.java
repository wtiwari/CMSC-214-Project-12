/*
 * Class: CMSC214 
 * Instructor:Estep
 * Description: Write a JavaFX GUI program that views, 
 * inserts, and updates staff information stored in a database, 
 * as shown in the following figure. The view button displays a 
 * record with a specified ID.
 * Due: 05/07/2023
 * I pledge that I have completed the programming assignment independently.
   I have not copied the code from a student or any source.
   I have not given my code to any student.
   Print your Name here: Will Tiwari
*/

import java.sql.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class project12 extends Application{
	// Statement for executing queries
	 private PreparedStatement preparedStatement;
	 private PreparedStatement preparedStatement2;
	  private TextField tfLastName = new TextField();
	  private TextField tfFirstName = new TextField();
	  private TextField tfMi = new TextField();
	  private TextField tfAddress = new TextField();
	  private TextField tfId = new TextField();
	  private TextField tfCity = new TextField();
	  private TextField tfState = new TextField();
	  private TextField tfPhone = new TextField();
	  private Label lblStatus = new Label();
	  private TextArea infoDisplay = new TextArea();
	  
	  @Override // Override the start method in the Application class
	  public void start(Stage primaryStage) {
	    // Initialize database connection and create a Statement object
	    initializeDB();

	    BorderPane pane = new BorderPane();
	    GridPane paneForInfo = new GridPane();
	    Button btView = new Button("View");
	    Button btInsert = new Button("Insert");
	    Button btUpdate = new Button("Update");
	    Button btClear = new Button("Clear");
	    HBox hBox1 = new HBox(5);
	    HBox hBox2 = new HBox(5);
	    HBox hBox3 = new HBox(5);
	    HBox hBox4 = new HBox(5);
	    HBox hBox5 = new HBox(5);
	    HBox hBox6 = new HBox(5);
	    
	    
	    hBox1.getChildren().addAll(new Label("ID"), tfId);
	    hBox2.getChildren().addAll(new Label("Last Name"), tfLastName, new Label("First Name"), 
	  	      tfFirstName, new Label("mi"), tfMi);
	    hBox3.getChildren().addAll(new Label("Address"),tfAddress);
	    hBox4.getChildren().addAll(new Label("city"), tfCity, new Label("State"),
	    		tfState);
	    hBox5.getChildren().addAll(new Label("Telephone"), tfPhone);
	    hBox6.getChildren().addAll( btView, btInsert, btUpdate, btClear);
	       

	    VBox vBox= new VBox(10);
	    vBox.getChildren().addAll(hBox1, hBox2, hBox3, hBox4, hBox5, hBox6);
	    //paneForInfo.getChildren().addAll(vBox);
	    pane.setTop(vBox);
	    pane.setCenter(new ScrollPane(infoDisplay));
	    
	    
	    tfLastName.setPrefColumnCount(6);
	    tfFirstName.setPrefColumnCount(6);
	    tfMi.setPrefColumnCount(6);
	    tfAddress.setPrefColumnCount(6);
	    tfId.setPrefColumnCount(6);
	    tfCity.setPrefColumnCount(6);
	    tfState.setPrefColumnCount(6);
	    tfPhone.setPrefColumnCount(6);
	    btInsert.setOnAction(e -> insert());
	    btView.setOnAction(e -> show());
	    
	    // Create a scene and place it in the stage
	    Scene scene = new Scene(pane, 450, 330);
	    primaryStage.setTitle("Project 12"); // Set the stage title
	    primaryStage.setScene(scene); // Place the scene in the stage
	    primaryStage.show(); // Display the stage   
	  }

	  private void initializeDB() {
	    try {
	      // Load the JDBC driver
	      Class.forName("com.mysql.cj.jdbc.Driver");

	      System.out.println("Driver loaded");

	      // Establish a connection
	      Connection connection = DriverManager.getConnection
	        ("jdbc:mysql://localhost/javabook", "scott", "tiger");
//	    
	      System.out.println("Database connected");

	      // Create a statement
	      preparedStatement = connection.prepareStatement 
		      		("insert into Staff (id, firstName, mi, lastName, address, city,  state, telephone, email) " 
		      + "values (?, ?, ?, ?, ?, ?, ?, ?, email)");
	      preparedStatement2 = connection.prepareStatement 
		      		("SELECT id, firstName, mi, lastName, address, city,  state, telephone, email FROM staff WHERE id=?");
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }

	  private void insert() {
		  
		  String lastName = tfLastName.getText();
		    String firstName = tfFirstName.getText();
		    String mi = tfMi.getText();
		    char m = mi.charAt(0);
		    String address = tfAddress.getText();
		    String city = tfCity.getText();
		    String state = tfState.getText();
		    String telephone = tfPhone.getText();
		    String id = tfId.getText();
		    
		    
		    try {
		    	preparedStatement.setString(1, id);
		      	preparedStatement.setString(2, firstName);
		      	preparedStatement.setString(3, mi);
		      	preparedStatement.setString(4, lastName);
		      	preparedStatement.setString(5, address);
		      	preparedStatement.setString(6, city);
		      	preparedStatement.setString(7, state);
		      	preparedStatement.setString(8, telephone);
		      	
		      	int rows = preparedStatement.executeUpdate();
		      	 
		      	if(rows > 0) {
		      		System.out.println("Insert completed " + id);
		      	}else {
		      		System.out.println("Insert failed");
		      	}
		      	
		      	
		      	} catch (Exception ex) {
		      		ex.printStackTrace();
		      	}
		      
		  
	  }
	    
	      
	  private void show() {
		    String id = tfId.getText();
		    try {
		      preparedStatement2.setString(1, id);
		      ResultSet rset = preparedStatement2.executeQuery();

		      if (rset.next()) {
		    	  String lastName = rset.getString(2);
				    String firstName = rset.getString(4);
				    String mi = rset.getString(3);
				    String address = rset.getString(5);
				    String city = rset.getString(6);
				    String state = rset.getString(7);
				    String telephone = rset.getString(8);
	
		    	  

		        // Display result in a label
		       
		        infoDisplay.appendText(firstName + " " + mi +
				          " " + lastName +"\n" + " Address: " + address + " " + city + ", " +
			        		state + " \n" + "telephone: " + telephone + "\n");
		      } else {
		        lblStatus.setText("Not found");
		      }
		    }
		    catch (SQLException ex) {
		      ex.printStackTrace();
		    }
		  }
	  public static void main(String[] args) {
		  launch(args);
	  }
	    
}
