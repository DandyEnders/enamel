package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Popup box controller.
 * @author Jinho Hwang
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utility.AuthoringUtil;
import utility.ErrorUtil;
import utility.Language;
import utility.Phrase;

public class CreateCommandPopUpBoxController extends Controller implements Returnable<List<Phrase>> {

    @FXML
    private Button createButton;

    @FXML
    private Button discardButton;

    @FXML
    private ComboBox<String> phraseTypeComboBox;

    @FXML
    private Pane firstArgumentPane;

    @FXML
    private Pane secondArgumentPane;
    
    @FXML
    private TextField firstArgumentTextField;
    
    @FXML
    private TextField secondArgumentTextField;
    
    @FXML
    private Label firstArgText;
    
    @FXML
    private CheckBox pairCheck;

    @FXML
    private Label secondArgText;
    
    @FXML
    private ComboBox<String> firstArgList;
    
   /* 
    
    List<Phrase> phraseList;
    String pos;
    int selectedIndex;*/
    
    private List<File> audioFileList;
    private ObservableList<String> obsAudioFileList;
    ObservableList<String> obsPhraseTypeList;
    Phrase returnPhrase;
    List<Phrase> returnPhraseList;
    
    public CreateCommandPopUpBoxController(){
    	String[] typeList = AuthoringUtil.getTypeList();
    	obsPhraseTypeList = FXCollections.observableArrayList(typeList);
    	obsAudioFileList = FXCollections.observableArrayList();
    	obsPhraseTypeList.add("emptyLine");
    	obsPhraseTypeList.add("speak");
    	returnPhraseList = new ArrayList<Phrase>();
    	
    	
    }
    
    public void loadSoundFiles() {
    	audioFileList = AuthoringUtil.getAudioFiles(Language.audioPath);
    	if(audioFileList != null) {
			for(File file : audioFileList) {
				obsAudioFileList.add(file.getName());
			}
    	}
    }
    
    @FXML
    public void comboSelected(ActionEvent event) {
    	String comboBoxText = phraseTypeComboBox.getSelectionModel().getSelectedItem();
    	
    	firstArgList.setVisible(false);
		firstArgList.setDisable(true);
		firstArgumentTextField.setVisible(true);
		firstArgumentTextField.setDisable(false);

		secondArgumentTextField.setVisible(true);
		pairCheck.setVisible(false);
		//pairCheck.setDisable(true);
		
		if(comboBoxText != null) {
			
		}
		
    	if(comboBoxText.equals("/~sound:")) {
    		setArgumentText("Sound file name", "");
    		firstArgumentTextField.setVisible(false);
    		firstArgumentTextField.setDisable(true);
    		
    		firstArgList.setVisible(true);
    		firstArgList.setDisable(false);
    		
    		firstArgList.setItems(obsAudioFileList);
    		
    		
    	}else if(comboBoxText.equals("/~skip:")) {
    		setArgumentText("Jump to String");
    		pairCheck.setVisible(true);
    		pairCheck.setDisable(false);
    		secondArgumentTextField.setVisible(false);
    		
    	}else if(comboBoxText.equals("/~pause:")) {
    		setArgumentText("Seconds to pause");
    		
    	}else if(comboBoxText.equals("/~repeat-button:")) {
    		setArgumentText("Index of button");
    		
    	}else if(comboBoxText.equals("/~repeat")) {
    		setArgumentText();
    		
    	}else if(comboBoxText.equals("/~endrepeat")) {
    		setArgumentText();
    		
    	}else if(comboBoxText.equals("/~reset-buttons")) {
    		setArgumentText();
    		
    	}else if(comboBoxText.equals("/~skip-button:")) {
    		setArgumentText("Index of button", "Jump to String");
    		
    	}else if(comboBoxText.equals("/~disp-clearAll")) {
    		setArgumentText();
    		
    	}else if(comboBoxText.equals("/~disp-cell-pins:")) {
    		setArgumentText("Index of cell", "Cell pins (eight 0 or 1)");
    		
    	}else if(comboBoxText.equals("/~disp-string:")) {
    		setArgumentText("String to display");
    		
    	}else if(comboBoxText.equals("/~disp-cell-char:")) {
    		setArgumentText("Index of cell", "English alphabet");
    		
    	}else if(comboBoxText.equals("/~disp-cell-raise:")) {
    		setArgumentText("Index of cell", "Index of a pin of the cell");
    		
    	}else if(comboBoxText.equals("/~disp-cell-lower:")) {
    		setArgumentText("Index of cell", "Index of a pin of the cell");
    		
    	}else if(comboBoxText.equals("/~disp-cell-clear:")) {
    		setArgumentText("Index of cell");
    		
    	}else if(comboBoxText.equals("/~user-input")) {
    		setArgumentText();
    		
    	}else if(comboBoxText.equals("emptyLine")) {
    		setArgumentText();
    		
    	}else if(comboBoxText.equals("speak")) {
    		setArgumentText("Sentence to speak");
    		
    	}else {
    		setArgumentText();
    	}
    	
    	
    }
    
    
    private void setArgumentText() {
    	setArgumentText("","");
    }
    
    private void setArgumentText(String first) {
    	setArgumentText(first,"");
    }
    
    private void setArgumentText(String first, String second) {
    	firstArgText.setText(first);
    	secondArgText.setText(second);
    	
    	firstArgumentTextField.setPromptText(first);
    	secondArgumentTextField.setPromptText(second);
    	
    	firstArgList.setPromptText(first);
    	
    	boolean firstVisible = false;
    	boolean secondVisible = false;
    	
    	if(first.equals(""))
    		firstVisible = true;
    	if(second.equals(""))
    		secondVisible = true;
    
		setPaneDisable(firstVisible,secondVisible);
		
	
    }
    
    private void setPaneDisable(boolean first, boolean second) {
    	firstArgumentPane.setDisable(first);
    	firstArgumentPane.setVisible(!first);
    	secondArgumentPane.setDisable(second);
    	secondArgumentPane.setVisible(!second);
    	
    }
    
    @FXML
    public void create() {
    	String command, firstArg, secondArg;
    	
    	command = phraseTypeComboBox.getSelectionModel().getSelectedItem();
    	if(command != null) {
    		
    		
    		// TODO: make firstarg / secondarg into separate method
	    	firstArg = firstArgumentTextField.getText() == null ? firstArg = null : (firstArg = firstArgumentTextField.getText());
	    	
	    	if(firstArgList.isVisible() && firstArgList.getSelectionModel().getSelectedIndex() != -1) {
	    		firstArg = firstArgList.getSelectionModel().getSelectedItem();
	    	}
	    	
	    	secondArg = secondArgumentTextField.getText() == null ? secondArg = null : (secondArg = secondArgumentTextField.getText());
	    	
	    	
	    
	    	try {
		    	if(command.equals("emptyLine")){
		    		returnPhrase = new Phrase("emptyLine", "", "");
		    	}else if(command.equals("speak")){
		    		returnPhrase = new Phrase("speak",firstArg, "");
		    	}else {
		    		returnPhrase = AuthoringUtil.phraseThisLine(command + firstArg + " " + secondArg);
		    	}
		    	returnPhraseList.add(returnPhrase);
		    	if(returnPhrase.getType().equals("/~skip:") && pairCheck.isSelected()) {
		    		returnPhraseList.add(new Phrase("/~", firstArg));
		    	}else if(returnPhrase.getType().equals("/~repeat")) {
		    		returnPhraseList.add(new Phrase("/~endrepeat"));
		    	}
		    	
		    	close();
	    	}catch(IOException e) {
				ErrorUtil.alertMessageShowException("Invalid command.", e.getLocalizedMessage(), e);
			}
    	}
    }
    
    
    public void setComboBoxItems() {
    	phraseTypeComboBox.setItems(obsPhraseTypeList);
    }
    

    @Override
	public List<Phrase> getReturn() {
		return this.returnPhraseList; 
	}
	
	
	@FXML
	protected void keyPressed(KeyEvent event) {
    	if(event.getCode().equals(Language.openKey)) {
    		if(event.getSource().equals(createButton)) {
    			create();
    		}else if(event.getSource().equals(discardButton)) {
    			close();
    		}
    	}
    }

     
   
}
