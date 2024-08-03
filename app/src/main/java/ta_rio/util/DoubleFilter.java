package ta_rio.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DoubleFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) {
            return;
        }
        if (isValidDouble(fb.getDocument().getText(0, fb.getDocument().getLength()), offset, string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text == null) {
            return;
        }
        if (isValidDouble(fb.getDocument().getText(0, fb.getDocument().getLength()), offset, text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
    }

    private boolean isValidDouble(String currentText, int offset, String newText) {
        try {
            StringBuilder sb = new StringBuilder(currentText);
            sb.insert(offset, newText);
            String finalText = sb.toString();

            if (finalText.matches("-?\\d*(\\.\\d*)?")) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
