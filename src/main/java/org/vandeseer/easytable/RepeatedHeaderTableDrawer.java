package org.vandeseer.easytable;

import lombok.experimental.SuperBuilder;
import lombok.extern.log4j.Log4j2;

import java.awt.geom.Point2D;

@SuperBuilder
public class RepeatedHeaderTableDrawer extends TableDrawer {

    @Override
    protected void drawPage(PageData pageData) {
        System.err.println("R pageData.firstRowOnPage: " + pageData.firstRowOnPage + ", pageData.firstRowOnNextPage: " + pageData.firstRowOnNextPage);
        System.err.println("R startX=" + this.startX + ", startY=" + this.startY + ", endY=" + this.endY + "\n");

        if (pageData.firstRowOnPage != 0) {
            drawRow(new Point2D.Float(this.startX, this.startY), table.getRows().get(0), 0, (drawer, drawingContext) -> {
                drawer.drawBackground(drawingContext);
                drawer.drawContent(drawingContext);
                drawer.drawBorders(drawingContext);
            });
        }

        drawerList.forEach(drawer ->
                drawWithFunction(pageData, new Point2D.Float(this.startX, this.startY), drawer)
        );
    }

}
