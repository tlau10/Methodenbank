package iterator.gui.javafx;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyStage extends Stage {

	private int stageId = 0;
	private TableauController tabController;
	

	public MyStage(StageStyle style, int id) {
		super(style);
		
		//Set Icon
		this.getIcons().add(new Image(getClass().getResourceAsStream("img_tableau.png")));
		
		this.stageId = id;
		
	
	}

	public int getStageId() {
		return stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	
	
	
	public void setSceneHandler() {
		
		this.addEventHandler(Event.ANY, new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				
				if(isFocused()) {
					
					BasicWindowController.focusedTableau(stageId);
				}
				
			}
		});
		
	}

	public TableauController getTabController() {
		return tabController;
	}

	public void setTabController(TableauController tabController) {
		this.tabController = tabController;
	}
	
	

}
