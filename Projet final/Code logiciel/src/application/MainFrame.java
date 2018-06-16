package application;

	

import controller.javafx.ConfigurationController;
import controller.javafx.HomeController;
import controller.javafx.MenuBarController;
import controller.javafx.SetupController;
import controller.javafx.ZonesAnalyseController;
import controller.javafx.ZonesManagementController;
import controller.sensor.GenerateData;
import controller.sql.Connexion;
import controller.sql.GeneratorTable;
import controller.sql.InitialisationDB;
import controller.sql.Queries;
import controller.sql.ReaderSqlData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.BotanicalPark;
import model.Zone;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class MainFrame extends Application {
	private Stage primaryStage;
	private Stage setupStage;
	
	private BorderPane rootLayout;
    private BotanicalPark parc;
    
	private MenuBarController menuBarController;
    private HomeController homeController;
    private ZonesManagementController zonesManagementController;
    private ZonesAnalyseController zonesAnalyseController;
    private ConfigurationController configurationController;
    private SetupController setupController;
    
    private Queries queriessql;
    
	public void initMainFrame(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFrame.class.getResource("/view/fxml/MainFrame.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void showHome(){
		try{
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(MainFrame.class.getResource("/view/fxml/Home.fxml"));
			
			AnchorPane homePanel = (AnchorPane) loader.load();
			homeController = loader.getController();
			homeController.setMainApp(this);
			rootLayout.setCenter(homePanel);
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void showMenuBar(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFrame.class.getResource("/view/fxml/MenuBar.fxml"));
			
			MenuBar menuBar = (MenuBar) loader.load();
			
			menuBarController = loader.getController();
			menuBarController.setMainApp(this);
			rootLayout.setTop(menuBar);
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void showZonesManagementController(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFrame.class.getResource("/view/fxml/ZonesManagement.fxml"));
			
			TabPane tabPane = (TabPane) loader.load();
			
			zonesManagementController = loader.getController();
			zonesManagementController.setMainApp(this);
			
			for(int i = 0 ; i < parc.getZones().size() ; i++){
				Zone zone = parc.getZones().get(i);
				zonesManagementController.addPane(zone);
			}
			
			rootLayout.setCenter(tabPane);
		} catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public void showAnalyse(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFrame.class.getResource("/view/fxml/ZonesAnalyse.fxml"));
			
			TabPane tabPane = (TabPane) loader.load();
			
			zonesAnalyseController = loader.getController();
			zonesAnalyseController.setMainApp(this);
			
			for(int i = 0 ; i < parc.getZones().size() ; i++){
				Zone zone = parc.getZones().get(i);
				zonesAnalyseController.addPane(zone);
			}
			
			rootLayout.setCenter(tabPane);
		} catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public void showConfiguration(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFrame.class.getResource("/view/fxml/Configuration.fxml"));
			
			TabPane tabPane = (TabPane) loader.load();
			
			configurationController = loader.getController();
			configurationController.setMainApp(this);
			
			rootLayout.setCenter(tabPane);
		} catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public void showSetup(){
		try{
			setupStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFrame.class.getResource("/view/fxml/Setup.fxml"));
			
			AnchorPane anchor = (AnchorPane) loader.load();
			
			setupController = loader.getController();
			setupController.setMainApp(this);
			
			Scene scene = new Scene(anchor);
			
			setupStage.setScene(scene);
			setupStage.show();
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public ZonesManagementController getZonesManagement(){
		return zonesManagementController;
	}
	
	public ZonesAnalyseController getZonesAnalyse(){
		return zonesAnalyseController;
	}
	
	public BotanicalPark getBotanicalPark(){
		return parc;
	}
	
	public Queries getQueries(){
		return queriessql;
	}
	
	@Override
	public void start(Stage primaryStage) throws InterruptedException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("JardiGestion");
		this.primaryStage.getIcons().add(new Image("/resource/image/icon.jpg"));
		this.primaryStage.setResizable(false);
		
		initMainFrame();
		showMenuBar();
		showHome();
		
		//showSetup();
		Connexion connexion = new Connexion();				
		queriessql = new Queries(connexion);
		GeneratorTable.generate(connexion);
		ReaderSqlData reader = new ReaderSqlData(connexion);
		parc = reader.readAllDataWithoutGUI();
		//setupStage.close();
	}
	
	
	public static void main(String[] args) {
		
		
		launch(args);
		//GenerateData data = new GenerateData(new Connexion(), 1000);
		//data.run();
	}
}
