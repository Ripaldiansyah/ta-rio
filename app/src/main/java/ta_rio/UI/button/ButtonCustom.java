package ta_rio.UI.button;

import java.awt.event.ActionListener;

import javax.swing.Icon;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;

public class ButtonCustom extends FlatButton {

    public ButtonCustom(String label, Icon icon, String backroundColor, String foregroundColor,
            ActionListener actionListener) {
        setText(label);
        setSquareSize(true);
        setIcon(icon);
        putClientProperty(FlatClientProperties.STYLE, ""

                + "margin:1,2,1,2;"
                + "arc:40;"
                + "borderWidth:0;"
                + "Foreground:" + (foregroundColor != null ? foregroundColor : "#fff") + ";"
                + "background:" + (backroundColor != null ? backroundColor : "#2f7ab1") + ";"
                + "focusWidth:0;"
                + "innerFocusWidth:0");

        addActionListener(actionListener);
    }

    public FlatButton getButton() {
        return this;
    }
}
