package gui.util;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Utils {
    
    public static Stage currentStage(ActionEvent event){
        return (Stage) ((Node)event.getSource()).getScene().getWindow();
    }
    
    public static Integer tryParseToInt(String str){    //função que transforma string em int, porém caso seja inválido, returna null.
        try{
            return Integer.parseInt(str);
        }
        catch(NumberFormatException e){
            return null;
        }
        
    }

}
