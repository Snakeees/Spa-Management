import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleItem  extends JPanel {
    private final List<JButton> buttons =  new ArrayList<>();

//    private final List<Icon> buttons = Arrays.asList(new Icon("user.png"), new Icon("edit"));


    protected SingleItem(List<String> items) {
        super();
        setOpaque(true);
        for (String str : items) {
            JButton b=new JButton(str);
            b.setFocusable(false);
            b.setRolloverEnabled(false);
            buttons.add(b);
            add(b);
        }
    }

    protected List<JButton> getButtons() {
        return buttons;
    }
}