package Models;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public abstract class ToolTipHandler {
    private final static Tooltip toolTipMarketPlaceScene = new Tooltip();
    private final static Tooltip toolTipMinimize = new Tooltip();
    private final static Tooltip toolTipClose = new Tooltip();
    private final static Tooltip toolTipSettings = new Tooltip();
    private final static Tooltip toolTipAdmin = new Tooltip();
    private final static Tooltip toolTipSale = new Tooltip();
    private final static Tooltip toolTipPreviewScene = new Tooltip();
    private final static Tooltip toolTipSellScene = new Tooltip();

    public static void getToolTipSettings(Button button) {
        toolTipSettings.setText("Click to go to settings");
        button.setTooltip(toolTipSettings);
    }

    public static void getToolTipSellScene(Button button) {
        toolTipSellScene.setText("Click to sell an item");
        button.setTooltip(toolTipSellScene);
    }

    public static void getToolTipPreview(Button button) {
        toolTipPreviewScene.setText("When pressing this button, you will see a preview of how the sale will look like");
        button.setTooltip(toolTipPreviewScene);
    }

    public static void getToolTipAdmin(Button button) {
        toolTipAdmin.setText("Click to change account and item related data");
        button.setTooltip(toolTipAdmin);
    }

    public static void getToolTipSale(Button button){
        toolTipSale.setText("When pressing this button, you will list your item for sale");
        button.setTooltip(toolTipSale);
    }

    public static void getTooltipMarketPlace(Button button){
        toolTipMarketPlaceScene.setText("Click to get back to marketplace");
        button.setTooltip(toolTipMarketPlaceScene);
    }

    public static void getToolTipCloseButton(Button button){
        toolTipClose.setText("Click to close the program");
        button.setTooltip(toolTipClose);
    }

    public static void getToolTipMinimizeButton(Button button){
        toolTipMinimize.setText("click to minimize");
        button.setTooltip(toolTipMinimize);
    }
}
