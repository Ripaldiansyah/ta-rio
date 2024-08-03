package ta_rio.UI.Icon;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import java.awt.Color;

import javax.swing.Icon;

public class IconCustom {
    private String path;
    private float scale;
    private String colorDecode;

    public IconCustom(String path, float scale, String colorDecode) {
        this.path = path;
        this.scale = scale;
        this.colorDecode = colorDecode;
    }

    public Icon getIcon() {
        FlatSVGIcon icon = new FlatSVGIcon(path, scale);
        FlatSVGIcon.ColorFilter colorFilter = new FlatSVGIcon.ColorFilter();
        colorFilter.add(Color.decode("#e8eaed"),
                colorDecode != null ? Color.decode(colorDecode) : Color.decode("#ffffff"));
        icon.setColorFilter(colorFilter);
        return icon;
    }

}
