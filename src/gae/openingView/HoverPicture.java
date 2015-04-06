package gae.openingView;

import gae.gameView.Main;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * HoverPicture is a specific object used by ImagePanel. Each HoverPicture represents an image of
 * the game type as well as an overlying text with the label. Text disappears when author hovers
 * over the image.
 * 
 * @author Brandon Choi
 *
 */

public class HoverPicture implements UIObject {

    private static final double IMAGE_WIDTH = Main.SCREEN_WIDTH / 4;
    private static final double IMAGE_HEIGHT = Main.SCREEN_HEIGHT / 4;
    private StackPane view;
    private ImageView image;
    private Text label;
    private boolean selected;

    public HoverPicture (ImageView i, Text subtext) {
        view = new StackPane();
        image = i;
        image.getStyleClass().add("hoverPicture");
        i.setFitHeight(IMAGE_HEIGHT);
        i.setFitWidth(IMAGE_WIDTH);
        label = subtext;
        label.getStyleClass().add("gameLabel");
        view.getChildren().addAll(i, label);
        setHoverEffect();
        selected = false;
        setSelectEffect();
    }

    @Override
    public Node getObject () {
        return view;
    }

    public boolean selectStatus () {
        return selected;
    }

    /**
     * sets up the hovering effect by altering the text's visibility
     */
    private void setHoverEffect () {
        if (!selected) {
            image.setOnMouseEntered(e -> {
                label.setVisible(false);
            });
            image.setOnMouseExited(e -> {
                label.setVisible(true);
            });
        }
    }

    /**
     * sets up what happens when it is clicked and selected
     */
    private void setSelectEffect () {
        if (selected) {
            view.setOnMouseClicked(e -> {
                selected = false;
            });
        }
        else {
            view.setOnMouseClicked(e -> {
                selected = true;
            });
        }
    }
}