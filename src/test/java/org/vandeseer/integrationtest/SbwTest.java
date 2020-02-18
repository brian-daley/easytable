package org.vandeseer.integrationtest;

import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.COURIER_BOLD;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA_BOLD;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA_OBLIQUE;
import static org.vandeseer.TestUtils.createGliderImage;
import static org.vandeseer.TestUtils.createTuxImage;
import static org.vandeseer.easytable.settings.HorizontalAlignment.CENTER;
import static org.vandeseer.easytable.settings.HorizontalAlignment.JUSTIFY;
import static org.vandeseer.easytable.settings.HorizontalAlignment.LEFT;
import static org.vandeseer.easytable.settings.HorizontalAlignment.RIGHT;
import static org.vandeseer.easytable.settings.VerticalAlignment.MIDDLE;
import static org.vandeseer.easytable.settings.VerticalAlignment.TOP;

import java.awt.Color;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PageMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.junit.Test;
import org.vandeseer.easytable.TableDrawer;
import org.vandeseer.easytable.bmd.RecCheckedItem;
import org.vandeseer.easytable.bmd.RecommendationData;
import org.vandeseer.easytable.bmd.TableRec;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.ImageCell;
import org.vandeseer.easytable.structure.cell.ImageCell.ImageCellBuilder;
import org.vandeseer.easytable.structure.cell.TextCell;
import org.vandeseer.easytable.structure.cell.TextCell.TextCellBuilder;
import org.vandeseer.easytable.structure.cell.paragraph.Hyperlink;
import org.vandeseer.easytable.structure.cell.paragraph.Markup;
import org.vandeseer.easytable.structure.cell.paragraph.ParagraphCell;
import org.vandeseer.easytable.structure.cell.paragraph.StyledText;

public class SbwTest {

    private final static Color BLUE_DARK = new Color(76, 129, 190);
    private final static Color BLUE_LIGHT_1 = new Color(186, 206, 230);
    private final static Color BLUE_LIGHT_2 = new Color(218, 230, 242);

    private final static Color GRAY_LIGHT_1 = new Color(245, 245, 245);
    private final static Color GRAY_LIGHT_2 = new Color(240, 240, 240);
    private final static Color GRAY_LIGHT_3 = new Color(216, 216, 216);

    private static final float PADDING_X = 30f;
    private static final float PADDING_Y = 30f;
    private static final float PADDING_Y_BETWEEN_TABLES = 12f;

    public static final Color keyRedBackground = new Color(164, 0, 0, 255);
    public static final Color lowAlphaWhite = new Color(255, 255, 255, 50); // Alphas are not working
    public static final Color offWhite = new Color(222, 222, 222, 255);

    private final static String CHECKING_IMG_PATH = "/Users/daleyb2/Repos/easytable/src/main/resources/assets/product/checking.png";
    private final static String CREDIT_CARD_IMG_PATH = "/Users/daleyb2/Repos/easytable/src/main/resources/assets/product/credit.png";
    private final static String CARD_PROCESSING_IMG_PATH = "/Users/daleyb2/Repos/easytable/src/main/resources/assets/product/card_process.png";
    private final static String SAVINGS_IMG_PATH = "/Users/daleyb2/Repos/easytable/src/main/resources/assets/product/saving_account.png";
    private final static String LOAN_IMG_PATH = "/Users/daleyb2/Repos/easytable/src/main/resources/assets/product/business_loan.png";
    private final static String CHECKBOX_TRUE = "/Users/daleyb2/Repos/easytable/src/main/resources/assets/checkTrueBmd.png";
    private final static String CHECKBOX_FALSE = "/Users/daleyb2/Repos/easytable/src/main/resources/assets/checkFalseBmd.png";
    private final static String TOP_IMAGE_URL = "/Users/daleyb2/Repos/easytable/src/main/resources/assets/checkFalseBmd.png";
    private final static String BOTTOM_IMAGE_URL = "/Users/daleyb2/Repos/easytable/src/main/resources/assets/Darkgrey.jpg";
    private final static String KEY_LOGO_SMALL_IMAGE_URL = "/Users/daleyb2/Repos/easytable/src/main/resources/assets/key_logo.png";
    private final static String KEY_OUR_SOLUTIONS_URL = "/Users/daleyb2/Repos/easytable/src/main/resources/assets/OurSolutions.png";

    private static int borderWidth = 0;

    @Test
    public void testImageBackgroundPdf() throws IOException {

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        float initialStartY = page.getMediaBox().getHeight() - PADDING_Y;
        float startY = initialStartY;

        PDImageXObject solutionsImage = PDImageXObject.createFromFile(KEY_OUR_SOLUTIONS_URL, document);
        solutionsImage.setHeight(270);

        PDRectangle mediaBox = page.getMediaBox();
        PDRectangle cropBox = page.getCropBox();

        System.out.println("Media, width=" + mediaBox.getWidth() + ", height=" + mediaBox.getHeight()
                + ", lowLeftX=" + mediaBox.getLowerLeftX() + ", lowLeftY=" + mediaBox.getLowerLeftY()
                + ", upRightX=" + mediaBox.getUpperRightX() + ", upRightY=" + mediaBox.getUpperRightY()
        );

        System.out.println("Crop, width=" + cropBox.getWidth() + ", height=" + cropBox.getHeight()
                + ", lowLeftX=" + cropBox.getLowerLeftX() + ", lowLeftY=" + cropBox.getLowerLeftY()
                + ", upRightX=" + cropBox.getUpperRightX() + ", upRightY=" + cropBox.getUpperRightY()
        );

        //float width = mediaBox.getWidth() - 2 * PADDING_X;
        float width = cropBox.getWidth();

        final Table.TableBuilder tableImageBuilder = Table.builder()
                .addColumnOfWidth(width);

        ImageCell cellImage = ImageCell.builder()
                .image(solutionsImage)
                //.borderWidth(borderWidth)
                .padding(0)
                .width(width)
                //.backgroundColor(WHITE)
                //.textColor(keyRedBackground)
                //.fontSize(16)
                //.font(HELVETICA_BOLD)
                //.borderWidthBottom(2f)
                //.borderWidth(borderWidth)
                .build();

        tableImageBuilder.addRow(
                Row.builder()
                        .add(cellImage)
                        .verticalAlignment(TOP)
                        .build());
        Table tableImage = tableImageBuilder.build();

        final Table.TableBuilder tableTextBuilder = Table.builder()
                .addColumnOfWidth(width);

        TextCell textCell1 = TextCell.builder()
                .text("Congratulations")
                //.backgroundColor(WHITE)
                .textColor(WHITE)
                .horizontalAlignment(CENTER)
                .fontSize(14)
                .font(HELVETICA_BOLD)
                .borderWidth(borderWidth)
                .paddingTop(15f)
                .build();

        TextCell textCell2 = TextCell.builder()
                .text("Potentially Long Company Name Inc.")
                //.backgroundColor(WHITE)
                .textColor(offWhite)
                .horizontalAlignment(CENTER)
                .fontSize(14)
                .font(HELVETICA)
                .borderWidth(borderWidth)
                .paddingBottom(15f)
                .build();

        TextCell textCell3 = TextCell.builder()
                .text("You're building a stringer financial future.")
                //.backgroundColor(WHITE)
                .textColor(offWhite)
                .horizontalAlignment(CENTER)
                .fontSize(12)
                .font(HELVETICA)
                .borderWidth(borderWidth)
                .build();

        TextCell textCell4 = TextCell.builder()
                .text("Now let's complete the final steps in the process.")
                //.backgroundColor(WHITE)
                .textColor(offWhite)
                .horizontalAlignment(CENTER)
                .fontSize(12)
                .font(HELVETICA)
                .borderWidth(borderWidth)
                .paddingBottom(10f)
                .build();

        tableTextBuilder.addRow(Row.builder().add(textCell1).build());
        tableTextBuilder.addRow(Row.builder().add(textCell2).build());
        tableTextBuilder.addRow(Row.builder().add(textCell3).build());
        tableTextBuilder.addRow(Row.builder().add(textCell4).build());
        tableTextBuilder.verticalAlignment(TOP);
        Table tableText = tableTextBuilder.build();

        System.out.println("tableImage.getHeight() = " + tableImage.getHeight());
        System.out.println("tableText.getHeight() = " + tableText.getHeight());

        try (final PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            TableDrawer.builder()
                    .page(page)
                    .contentStream(contentStream)
                    .table(tableImage)
                    .startX(0)
                    .startY(cropBox.getUpperRightY())
                    .endY(0)
                    .build()
                    .draw(() -> document, () -> page, 0);

            TableDrawer.builder()
                    .page(page)
                    .contentStream(contentStream)
                    .table(tableText)
                    .startX(0)
                    .startY(0)
                    .endY(0)
                    .build()
                    .draw(() -> document, () -> page, 0);

            System.err.println("PAGE 04: " + page);
        }
        document.save("sbwImage-" + System.currentTimeMillis() + ".pdf");
        document.close();
    }

    @Test
    public void createSbwRecPdf() throws IOException {

        List<RecommendationData> resDataList = getRecommendationData();
        int pageNo = 1;

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        System.err.println("PAGE 01: " + page);
        PDDocumentOutline outline = new PDDocumentOutline();
        document.getDocumentCatalog().setDocumentOutline(outline);
        PDOutlineItem pagesOutline = new PDOutlineItem();
        pagesOutline.setTitle("All Pages");
        outline.addLast(pagesOutline);
        addPageBookMark(page, "Page " + pageNo, pagesOutline);

        float initialStartY = page.getMediaBox().getHeight() - PADDING_Y;
        float startY = initialStartY;
        List<TableRec> tables = createRecTables(document, page, resDataList);

        tables.add(0, buildWellnessBanner(document, page));
        tables.add(1, createPageTitleTable(document, page));

        PDRectangle mediaBox = page.getMediaBox();
        PDRectangle cropBox = page.getCropBox();

        System.out.println("Media, width=" + mediaBox.getWidth() + ", height=" + mediaBox.getHeight()
                + ", lowLeftX=" + mediaBox.getLowerLeftX() + ", lowLeftY=" + mediaBox.getLowerLeftY()
                + ", upRightX=" + mediaBox.getUpperRightX() + ", upRightY=" + mediaBox.getUpperRightY()
        );

        System.out.println("Crop, width=" + cropBox.getWidth() + ", height=" + cropBox.getHeight()
                + ", lowLeftX=" + cropBox.getLowerLeftX() + ", lowLeftY=" + cropBox.getLowerLeftY()
                + ", upRightX=" + cropBox.getUpperRightX() + ", upRightY=" + cropBox.getUpperRightY()
        );

        try (final PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            //contentStream.drawImage(bottomImage1, 0, 50, 700, 20);

            float congratsoffsetY = drawCongratsTable(document, page, contentStream, 20f);
            startY -= congratsoffsetY;

            boolean drawHeader = true;
            boolean drawFooter = true;
            for (int i = 0; i < tables.size(); i++) {
                System.err.println("PAGE 02: " + page);
                TableRec tableRec = tables.get(i);

                //for (final Table table : tables) {

                float nextTableHeight = tableRec.getTable().getHeight();
                System.err.println("M startY=" + startY + ", next table height=" + nextTableHeight + ", should create new page=" + (startY - nextTableHeight));

                if (startY - nextTableHeight < PADDING_Y) {
                    page = new PDPage(PDRectangle.A4);
                    addPageBookMark(page, "Page " + ++pageNo, pagesOutline);
                    document.addPage(page);
                    startY = initialStartY;
                    tableRec = createPageTitleTable(document, page);
                    i--;
                    drawHeader = true;
                    System.err.println("M Creating new page. ");
                }
                System.err.println("PAGE 03: " + page);

                if (drawHeader) {
                    //// TODO: Try drawing Header Table at very top
                    Table hdrTbl = createPageHeaderTable(document, page, pageNo).getTable();
                    Table footerTbl = createPageFooterTable(document, page).getTable();  // Can be static for this report.
                    TableDrawer.builder()
                            .page(page)
                            .contentStream(contentStream)
                            .table(hdrTbl)
                            .startX(0)
                            .startY(cropBox.getUpperRightY())
                            .endY(0)
                            .build()
                            .draw(() -> document, () -> new PDPage(PDRectangle.A4), PADDING_Y);
                    drawHeader = false;
                    //}
                    //if (drawFooter) {
                    TableDrawer.builder()
                            .page(page)
                            .contentStream(contentStream)
                            .table(footerTbl)
                            .startX(cropBox.getLowerLeftX())
                            .startY(cropBox.getLowerLeftY() + footerTbl.getHeight())
                            //.endY(0)
                            .build()
                            .draw(() -> document, () -> new PDPage(PDRectangle.A4), PADDING_Y);
                    drawHeader = false;
                }

                //RepeatedHeaderTableDrawer.builder()
                addRecBookMark(page, tableRec.getTitle(), pagesOutline, startY);
                TableDrawer.builder()
                        .page(page)
                        .contentStream(contentStream)
                        .table(tableRec.getTable())
                        .startX(PADDING_X)
                        .startY(startY)
                        .endY(PADDING_Y)
                        .build()
                        .draw(() -> document, () -> new PDPage(PDRectangle.A4), PADDING_Y);

                startY -= (tableRec.getTable().getHeight() + PADDING_Y_BETWEEN_TABLES);
                System.err.println("PAGE 04: " + page);
            }
            //if (drawFooter) {
            //    Table hdrTbl = createPageHeaderTable(document, page, pageNo).getTable();
            //    TableDrawer.builder()
            //            .page(page)
            //            .contentStream(contentStream)
            //            .table(hdrTbl)
            //            .startX(0)
            //            .startY(cropBox.getUpperRightY() + hdrTbl.getHeight())
            //            .endY(0)
            //            .build()
            //            .draw(() -> document, () -> new PDPage(PDRectangle.A4), PADDING_Y);
            //    drawHeader = false;
            //}

            pagesOutline.openNode();
            outline.openNode();

            // optional: show the outlines when opening the file
            document.getDocumentCatalog().setPageMode(PageMode.USE_OUTLINES);
        }

        //addPageNumbers(document, "Page {0}", 60, 18);

        //PDPageTree pages = document.getDocumentCatalog().getPages();
        //PDPageTree pages1 = document.getPages();
        //int count = pages.getCount();
        //for (int i = 0; i < count; i++) {
        //    PDPage pdPage = pages.get(i);
        //    addHeaderFooter(document, pdPage, i);
        //}

        //document.save(TARGET_FOLDER + "/" + outputFileName);
        document.save("sbwTest-" + System.currentTimeMillis() + ".pdf");
        document.close();
    }

    TableRec buildWellnessBanner(PDDocument document, PDPage page) {

        Table.TableBuilder tableBuilder = Table.builder().addColumnOfWidth(page.getCropBox().getWidth() - 50);
        TextCell tCell = TextCell.builder().text("Your Wellness Roadmap").font(HELVETICA).fontSize(20).textColor(WHITE).backgroundColor(keyRedBackground).verticalAlignment(MIDDLE).build();
        Table tbl = tableBuilder.borderWidth(borderWidth).addRow(Row.builder().height(50f).add(tCell).build()).horizontalAlignment(CENTER).build();
        return  new TableRec(tbl, null);
    }

    public float drawCongratsTable(PDDocument document, PDPage page, PDPageContentStream contentStream, float offsetY) throws IOException {

        PDImageXObject solutionsImage = PDImageXObject.createFromFile(KEY_OUR_SOLUTIONS_URL, document);
        solutionsImage.setHeight(270);
        PDRectangle cropBox = page.getCropBox();
        float width = cropBox.getWidth();

        final Table.TableBuilder tableImageBuilder = Table.builder().addColumnOfWidth(width);

        ImageCell cellImage = ImageCell.builder()
                .image(solutionsImage)
                .padding(0)
                .width(width)
                .build();

        tableImageBuilder.addRow(
                Row.builder().add(cellImage)
                        .verticalAlignment(TOP)
                        .build());
        Table tableImage = tableImageBuilder.build();

        final Table.TableBuilder tableTextBuilder = Table.builder().addColumnOfWidth(width);

        TextCell textCell1 = TextCell.builder()
                .text("Congratulations")
                .textColor(WHITE)
                .horizontalAlignment(CENTER)
                .fontSize(14)
                .font(HELVETICA_BOLD)
                .borderWidth(borderWidth)
                .paddingTop(15f)
                .build();

        TextCell textCell2 = TextCell.builder()
                .text("Potentially Long Company Name Inc.")
                //.backgroundColor(WHITE)
                .textColor(offWhite)
                .horizontalAlignment(CENTER)
                .fontSize(14)
                .font(HELVETICA)
                .borderWidth(borderWidth)
                .paddingBottom(15f)
                .build();

        TextCell textCell3 = TextCell.builder()
                .text("You're building a stronger financial future.")
                //.backgroundColor(WHITE)
                .textColor(offWhite)
                .horizontalAlignment(CENTER)
                .fontSize(12)
                .font(HELVETICA)
                .borderWidth(borderWidth)
                .build();

        TextCell textCell4 = TextCell.builder()
                .text("Now let's complete the final steps in the process.")
                //.backgroundColor(WHITE)
                .textColor(offWhite)
                .horizontalAlignment(CENTER)
                .fontSize(12)
                .font(HELVETICA)
                .borderWidth(borderWidth)
                .paddingBottom(10f)
                .build();

        tableTextBuilder.addRow(Row.builder().add(textCell1).build());
        tableTextBuilder.addRow(Row.builder().add(textCell2).build());
        tableTextBuilder.addRow(Row.builder().add(textCell3).build());
        tableTextBuilder.addRow(Row.builder().add(textCell4).build());
        tableTextBuilder.verticalAlignment(TOP);
        Table tableText = tableTextBuilder.build();

        System.out.println("tableImage.getHeight() = " + tableImage.getHeight());
        System.out.println("tableText.getHeight() = " + tableText.getHeight());

        TableDrawer.builder()
                .page(page)
                .contentStream(contentStream)
                .table(tableImage)
                .startX(0)
                .startY(cropBox.getUpperRightY() - offsetY)
                .endY(0)
                .build()
                .draw(() -> document, () -> page, 0);

        TableDrawer.builder()
                .page(page)
                .contentStream(contentStream)
                .table(tableText)
                .startX(0)
                .startY(cropBox.getUpperRightY() - offsetY)
                .endY(0)
                .build()
                .draw(() -> document, () -> page, 0);

        System.err.println("PAGE 04: " + page);
        return tableImage.getHeight();
    }

    public static void addPageNumbers(PDDocument document, String numberingFormat, int offset_X, int offset_Y) throws IOException {
        int page_counter = 1;
        PDImageXObject bottomImage1 = PDImageXObject.createFromFile(BOTTOM_IMAGE_URL, document);
        PDPageContentStream contentStream = null;

        for (PDPage page : document.getPages()) {
            contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);

            contentStream.drawImage(bottomImage1, 0, 50, 700, 20);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ITALIC, 10);
            //PDRectangle pageSize = page.getMediaBox();
            PDRectangle pageSize = page.getCropBox();
            float x = pageSize.getLowerLeftX();
            float y = pageSize.getLowerLeftY();
            contentStream.newLineAtOffset(x + pageSize.getWidth() - offset_X, y + offset_Y);
            String text = MessageFormat.format(numberingFormat, page_counter);
            contentStream.showText(text);
            contentStream.endText();
            ++page_counter;
            contentStream.close();
        }
    }

    private void addHeaderFooter(PDDocument document, PDPage pdPage, int pageNumber) throws IOException {
        PDImageXObject topImage = PDImageXObject.createFromFile(TOP_IMAGE_URL, document);
        PDImageXObject bottomImage1 = PDImageXObject.createFromFile(BOTTOM_IMAGE_URL, document);

        PDRectangle pageSize = pdPage.getMediaBox();
        try (PDPageContentStream contentStream = new PDPageContentStream(document, pdPage, PDPageContentStream.AppendMode.APPEND, true, true)) {
            pdPage.getResources().getFontNames();
            //contentStream.drawImage(topImage, 0, pdPage.getBBox().getHeight() - 20, 700, 25);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 9);
            contentStream.newLineAtOffset(20, 780);
            contentStream.setNonStrokingColor(Color.white);
            contentStream.showText("KeyBank - Wellness Documents");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(280, 780);
            contentStream.setFont(PDType1Font.HELVETICA, 9);
            contentStream.setNonStrokingColor(Color.white);
            contentStream.showText("Created for " + "Qwerty");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(600, 780);
            contentStream.setFont(PDType1Font.HELVETICA, 9);
            contentStream.setNonStrokingColor(Color.white);
            contentStream.showText("" + pageNumber);
            contentStream.endText();

            //headerFooter(contentStream, "", document, myPage);
            //PDImageXObject bottomImage1 = getClassPathImage(doc, "assets/Darkgrey.jpg");
            contentStream.drawImage(bottomImage1, 0, 0, 700, 20);
            // footer text
            contentStream.beginText();
            contentStream.newLineAtOffset(120, 6);
            contentStream.setFont(PDType1Font.HELVETICA, 8);
            contentStream.setNonStrokingColor(Color.white);
            contentStream.showText(
                    "KeyBank is Member FDIC | All credit products are subject to credit approval. | \u00a9 2019 KeyBank.  190926-642698");
            contentStream.endText();
            contentStream.close();

        }

    }

    private void addPageBookMark(PDPage page, String title, PDOutlineItem pagesOutline) {
        PDPageDestination dest = new PDPageFitWidthDestination();
        // If you want to have several bookmarks pointing to different areas
        // on the same page, have a look at the other classes derived from PDPageDestination.

        PDPageXYZDestination x = new PDPageXYZDestination();
        x.setPage(page);

        dest.setPage(page);
        PDOutlineItem bookmark = new PDOutlineItem();
        bookmark.setDestination(dest);
        bookmark.setTitle(title);
        pagesOutline.addLast(bookmark);
    }

    private void addRecBookMark(PDPage page, String title, PDOutlineItem pagesOutline, float startY) {

        if (title == null) {
            return;
        }
        PDPageXYZDestination dest = new PDPageXYZDestination();
        // If you want to have several bookmarks pointing to different areas
        // on the same page, have a look at the other classes derived from PDPageDestination.

        dest.setPage(page);
        dest.setLeft(0);
        dest.setTop((int) startY);
        dest.setZoom(-1);
        PDOutlineItem bookmark = new PDOutlineItem();
        bookmark.setDestination(dest);
        bookmark.setTitle("  - " + title);
        pagesOutline.addLast(bookmark);
    }

    private List<TableRec> createRecTables(PDDocument document, PDPage page, List<RecommendationData> resDataList) throws IOException {
        List<TableRec> tables = new ArrayList<>();
        for (RecommendationData recData : resDataList) {
            tables.add(createRecTable(document, page, recData));
        }
        return tables;
    }

    private TableRec createRecTable(PDDocument document, PDPage page, RecommendationData recData) throws IOException {
        PDRectangle mediaBox = page.getMediaBox();
        float width = mediaBox.getWidth() - 2 * PADDING_X;
        float widthCol1 = width * .1f;
        float widthCol2 = width * .05f;
        float widthCol3 = width * .85f;
        List<Row> rows = new ArrayList<>();

        Table.TableBuilder tblBldr = Table.builder()
                .addColumnsOfWidth(widthCol1, widthCol2, widthCol3)
                .borderColor(BLACK)
                .borderWidth(borderWidth)
                .textColor(BLACK)
                .fontSize(7)
                .font(HELVETICA);

        rows.add(createRecTitleRow(document, page, recData));
        rows.add(createRecSubTitleRow(document, page, recData));

        for (RecCheckedItem item : recData.getItems()) {
            rows.add(createRecItemRow(document, page, item));
        }

        for (Row row : rows) {
            if (row != null) {
                tblBldr.addRow(row);
            }
        }
        TableRec tableRec = new TableRec(tblBldr.build(), recData.getTitle());
        return tableRec;
    }

    // "KeyBank is Member FDIC | All credit products are subject to credit approval. | \u00a9 2019 KeyBank.  190926-642698"
    private TableRec createPageHeaderTable(PDDocument document, PDPage page, int pageNo) throws IOException {

        PDRectangle mediaBox = page.getMediaBox();
        float width = mediaBox.getWidth();

        //Color bkgrnd = new Color(164, 0, 0);
        //Color bkgrnd = RED;

        final Table.TableBuilder tableHeaderBuilder = Table.builder()
                .backgroundColor(keyRedBackground)
                .textColor(WHITE)
                .font(HELVETICA)
                .fontSize(9)
                //.addColumnOfWidth(20f)
                .addColumnOfWidth(width * .3f)
                .addColumnOfWidth(width * .4f)
                .addColumnOfWidth(width * .3f);

        TextCell headerTitleCell1 = TextCell.builder()
                .text(" KeyBank - Wellness Documents")
                //.backgroundColor(bkgrnd)
                //.textColor(WHITE)
                //.fontSize(9)
                //.font(HELVETICA)
                .borderWidth(borderWidth)
                .horizontalAlignment(LEFT)
                .verticalAlignment(MIDDLE)
                .build();

        TextCell headerTitleCell2 = TextCell.builder()
                .text("Created for A Long Company Name That Should Fit Here")
                .backgroundColor(keyRedBackground)
                //.textColor(WHITE)
                //.fontSize(9)
                //.font(HELVETICA)
                .borderWidth(borderWidth)
                .horizontalAlignment(CENTER)
                .verticalAlignment(MIDDLE)
                .build();

        TextCell headerTitleCell3 = TextCell.builder()
                .text("Page " + pageNo + "  ")
                //.backgroundColor(bkgrnd)
                //.textColor(WHITE)
                //.fontSize(9)
                //.font(HELVETICA)
                .borderWidth(borderWidth)
                .horizontalAlignment(RIGHT)
                .verticalAlignment(MIDDLE)
                .build();

        ImageCell imageCell = ImageCell.builder()
                .image(PDImageXObject.createFromFile(KEY_LOGO_SMALL_IMAGE_URL, document))
                .verticalAlignment(MIDDLE)
                .maxHeight(20f)
                .minHeight(20f)
                .borderWidth(borderWidth)
                .build();

        tableHeaderBuilder.addRow(
                Row.builder()
                        .backgroundColor(keyRedBackground)
                        .height(20f)
                        .add(headerTitleCell1)
                        .add(headerTitleCell2)
                        .add(headerTitleCell3)
                        .build());

        Table tableHeader = tableHeaderBuilder.build();
        System.out.println("Header table height=" + tableHeader.getHeight());

        return new TableRec(tableHeader, null);
    }

    // "KeyBank is Member FDIC | All credit products are subject to credit approval. | \u00a9 2019 KeyBank.  190926-642698"
    private TableRec createPageFooterTable(PDDocument document, PDPage page) throws IOException {

        PDRectangle cropBox = page.getCropBox();
        float width = cropBox.getWidth();

        final Table.TableBuilder tableHeaderBuilder = Table.builder()
                .backgroundColor(BLACK)
                .textColor(WHITE)
                .fontSize(9)
                .font(HELVETICA)
                .borderWidth(borderWidth)
                //.addColumnOfWidth(20f)
                .addColumnOfWidth(width);

        TextCell headerTitleCell1 = TextCell.builder()
                .text("KeyBank is Member FDIC | All credit products are subject to credit approval. | \u00a9 2019 KeyBank.  190926-642698")
                .borderWidth(borderWidth)
                .horizontalAlignment(CENTER)
                .verticalAlignment(MIDDLE)
                .build();

        //ImageCell imageCell = ImageCell.builder()
        //        .image(PDImageXObject.createFromFile(KEY_LOGO_SMALL_IMAGE_URL, document))
        //        .verticalAlignment(MIDDLE)
        //        .maxHeight(20f)
        //        .minHeight(20f)
        //        .borderWidth(borderWidth)
        //        .build();

        tableHeaderBuilder.addRow(
                Row.builder()
                        .height(20f)
                        //.backgroundColor(BLACK)
                        //.add(imageCell)
                        .add(headerTitleCell1)
                        .build());

        Table tableHeader = tableHeaderBuilder.build();

        System.out.println("Footer table height=" + tableHeader.getHeight());
        return new TableRec(tableHeader, null);
    }

    private TableRec createPageTitleTable(PDDocument document, PDPage page) throws IOException {

        PDRectangle mediaBox = page.getMediaBox();
        float width = mediaBox.getWidth() - 2 * PADDING_X;

        final Table.TableBuilder tableHeaderBuilder = Table.builder()
                .addColumnOfWidth(width);

        TextCell headerTitleCell = TextCell.builder()
                .text("Grow Your Business Today")
                .backgroundColor(WHITE)
                .textColor(keyRedBackground)
                .fontSize(16)
                .font(HELVETICA_BOLD)
                .borderWidthBottom(2f)
                .borderWidth(borderWidth)
                .build();

        TextCell headerSubTitleCell = TextCell.builder()
                .text("For growing your business, our solutions will make an immediate impact.")
                .backgroundColor(WHITE)
                .textColor(BLACK)
                .fontSize(12)
                .font(HELVETICA)
                .borderWidth(borderWidth)
                .build();

        tableHeaderBuilder.addRow(Row.builder().add(headerTitleCell).build());
        tableHeaderBuilder.addRow(Row.builder().add(headerSubTitleCell).build());

        Table tableHeader = tableHeaderBuilder.build();

        return new TableRec(tableHeader, null);

        // A title row is required, no check necessary
        //int rowCnt = 1;
        //if (recData.getSubTitle() != null) {
        //    rowCnt++;
        //}
        //rowCnt += recData.getItems().size();
        //
        //return Row.builder()
        //        .add(ImageCell.builder()
        //                .image(PDImageXObject.createFromFile(recData.getImagePath(), document))
        //                .rowSpan(rowCnt)
        //                .verticalAlignment(TOP)
        //                .build())
        //        .add(TextCell.builder()
        //                .borderWidth(borderWidth)
        //                .padding(6)
        //                .text(recData.getTitle())
        //                .fontSize(16)
        //                .font(HELVETICA_BOLD)
        //                .colSpan(2)
        //                .verticalAlignment(TOP)
        //                .paddingBottom(2f)
        //                .minHeight(30)
        //                .build())
        //        .backgroundColor(WHITE)
        //        .textColor(BLACK)
        //        .font(HELVETICA_BOLD)
        //        .fontSize(8)
        //        .horizontalAlignment(LEFT)
        //        .build();
    }

    private Row createRecTitleRow(PDDocument document, PDPage page, RecommendationData recData) throws IOException {

        // A title row is required, no check necessary
        int rowCnt = 1;
        if (recData.getSubTitle() != null) {
            rowCnt++;
        }
        rowCnt += recData.getItems().size();

        return Row.builder()
                .add(ImageCell.builder()
                        .image(PDImageXObject.createFromFile(recData.getImagePath(), document))
                        .rowSpan(rowCnt)
                        .verticalAlignment(TOP)
                        .build())
                .add(TextCell.builder()
                        .borderWidth(borderWidth)
                        .padding(6)
                        .text(recData.getTitle())
                        .fontSize(16)
                        .font(HELVETICA_BOLD)
                        .colSpan(2)
                        .verticalAlignment(TOP)
                        .paddingBottom(2f)
                        .minHeight(30)
                        .build())
                .backgroundColor(WHITE)
                .textColor(BLACK)
                .font(HELVETICA_BOLD)
                .fontSize(8)
                .horizontalAlignment(LEFT)
                .build();
    }

    private Row createRecSubTitleRow(PDDocument document, PDPage page, RecommendationData recData) throws IOException {
        if (recData.getSubTitle() == null) {
            return null;
        }
        TextCell tCell = TextCell.builder().borderWidth(borderWidth).padding(6).text(recData.getSubTitle()).fontSize(12).font(HELVETICA_BOLD).colSpan(2).borderWidth(borderWidth).build();
        //return Row.builder()
        //        .add(TextCell.builder().borderWidth(0).padding(6).text(recData.getSubTitle()).fontSize(12).font(HELVETICA_BOLD).colSpan(2).borderWidth(borderWidth).build())
        //        .backgroundColor(WHITE)
        //        .textColor(BLACK)
        //        .horizontalAlignment(LEFT)
        //        .verticalAlignment(MIDDLE)
        //        .build();

        ParagraphCell pCell = ParagraphCell.builder()
                .borderWidth(borderWidth)
                .padding(6)
                .lineSpacing(1.0f)
                .colSpan(2)
                .borderWidth(borderWidth)
                .horizontalAlignment(LEFT)
                .verticalAlignment(MIDDLE)
                .paragraph(ParagraphCell.Paragraph.builder()
                        //.append(StyledText.builder().text("This is some text in one font.").font(HELVETICA).build())
                        //.appendNewLine()
                        .append(StyledText.builder().text("Sign up for ").font(HELVETICA).fontSize(12f). build())
                        .append(Hyperlink.builder().text("KeyBank Online Banking").url("http://www.key.com").font(HELVETICA_OBLIQUE).fontSize(12f).color(BLUE).build())
                        //.appendNewLine(6f)
                        //.append(StyledText.builder().text("There was the link. And now we are using the default font from the cell.").build())
                        .build())
                .build();

        return Row.builder()
                .add(pCell)
                .backgroundColor(WHITE)
                .textColor(BLACK)
                .horizontalAlignment(LEFT)
                .verticalAlignment(MIDDLE)
                .build();
    }

    private Row createRecItemRow(PDDocument document, PDPage page, RecCheckedItem checkedItem) throws IOException {
        return Row.builder()
                .add(ImageCell.builder()
                        .image(PDImageXObject.createFromFile(checkedItem.isChecked() ? CHECKBOX_TRUE : CHECKBOX_FALSE, document))
                        .verticalAlignment(MIDDLE)
                        .maxHeight(20f)
                        .minHeight(20f)
                        .borderWidth(borderWidth)
                        .build())
                .add(TextCell.builder().borderWidth(0).padding(6).text(checkedItem.getText()).fontSize(12).font(HELVETICA).borderWidth(borderWidth).build())
                .backgroundColor(WHITE)
                .textColor(BLACK)
                .horizontalAlignment(LEFT)
                .verticalAlignment(MIDDLE)
                .build();
    }

    private List<RecommendationData> getRecommendationData() {

        List<RecommendationData> recDataList = new ArrayList<>();

        RecommendationData recData = new RecommendationData();
        recData.setTitle("Business Reward Checking®");
        recData.setSubTitle("Discuss applying for Overdraft Protection with no annual fee");
        recData.setImagePath(CHECKING_IMG_PATH);
        recData.addItem("Open your Business Reward Checking Account with your Banker", true);
        recDataList.add(recData);

        recData = new RecommendationData();
        recData.setTitle("Business Reward Credit Card");
        recData.setSubTitle(null);
        recData.setImagePath(CREDIT_CARD_IMG_PATH);
        recData.addItem("Apply for a Business Reward Credit Card with your Banker", true);
        recDataList.add(recData);

        recData = new RecommendationData();
        recData.setTitle("Card Processing");
        recData.setSubTitle("Your Merchant Solutions Advisor will reach out to discuss your options");
        recData.setImagePath(CARD_PROCESSING_IMG_PATH);
        recData.addItem("Have appointment with your Merchant Solutions Advisor at your business", false);
        recData.addItem("Apply and setup credit/debit card processing with your Merchant Solutions Advisor", true);
        recDataList.add(recData);

        recData = new RecommendationData();
        recData.setTitle("Business Savings Accounts");
        recData.setSubTitle("Discuss savings options with your Banker");
        recData.setImagePath(SAVINGS_IMG_PATH);
        recData.addItem("Open the appropriate Savings Account(s) with your Banker", false);
        recDataList.add(recData);

        recData = new RecommendationData();
        recData.setTitle("Business Loan");
        recData.setSubTitle("Discuss the desired Business Loan amount and usage with your Banker");
        recData.setImagePath(LOAN_IMG_PATH);
        recData.addItem("Open the appropriate Savings Account(s) with your Banker", false);
        recDataList.add(recData);

        // Repeats for testing
        recData = new RecommendationData();
        recData.setTitle("1D Business Loan");
        recData.setSubTitle("Discuss the desired Business Loan amount and usage with your Banker");
        recData.setImagePath(LOAN_IMG_PATH);
        recData.addItem("Open the appropriate Savings Account(s) with your Banker", false);
        recDataList.add(recData);

        recData = new RecommendationData();
        recData.setTitle("2D Business Loan");
        recData.setSubTitle("Discuss the desired Business Loan amount and usage with your Banker");
        recData.setImagePath(LOAN_IMG_PATH);
        recData.addItem("Open the appropriate Savings Account(s) with your Banker", false);
        recDataList.add(recData);

        recData = new RecommendationData();
        recData.setTitle("3D Business Loan");
        recData.setSubTitle("Discuss the desired Business Loan amount and usage with your Banker");
        recData.setImagePath(LOAN_IMG_PATH);
        recData.addItem("Open the appropriate Savings Account(s) with your Banker", false);
        recDataList.add(recData);

        recData = new RecommendationData();
        recData.setTitle("4D Business Loan");
        recData.setSubTitle("Discuss the desired Business Loan amount and usage with your Banker");
        recData.setImagePath(LOAN_IMG_PATH);
        recData.addItem("Open the appropriate Savings Account(s) with your Banker", false);
        recDataList.add(recData);

        recData = new RecommendationData();
        recData.setTitle("5D Business Loan");
        recData.setSubTitle("Discuss the desired Business Loan amount and usage with your Banker");
        recData.setImagePath(LOAN_IMG_PATH);
        recData.addItem("Open the appropriate Savings Account(s) with your Banker", false);
        recDataList.add(recData);

        return recDataList;
    }

    private void test() throws IOException {

        final Table.TableBuilder tableHeaderBuilder = Table.builder()
                .addColumnOfWidth(200)
                .addColumnOfWidth(200);

        TextCell dummyHeaderCell = TextCell.builder()
                .text("Header dummy")
                .backgroundColor(Color.BLUE)
                .textColor(Color.WHITE)
                .borderWidth(1F)
                .build();

        tableHeaderBuilder.addRow(
                Row.builder()
                        .add(dummyHeaderCell)
                        .add(dummyHeaderCell)
                        .build());

        Table tableHeader = tableHeaderBuilder.build();

        final Table.TableBuilder tableBuilder = Table.builder()
                .addColumnOfWidth(200)
                .addColumnOfWidth(200);

        TextCell dummyCell = TextCell.builder()
                .text("dummy")
                .borderWidth(1F)
                .build();

        for (int i = 0; i < 50; i++) {
            tableBuilder.addRow(
                    Row.builder()
                            .add(dummyCell)
                            .add(dummyCell)
                            .build());
        }

        TableDrawer drawer = TableDrawer.builder()
                .table(tableBuilder.build())
                .startX(50)
                .endY(50F) // note: if not set, table is drawn over the end of the page
                .build();

        final PDDocument document = new PDDocument();

        float startY = 100F;

        do {
            TableDrawer headerDrawer = TableDrawer.builder()
                    .table(tableHeader)
                    .startX(50)
                    .build();

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                headerDrawer.startY(startY);
                headerDrawer.contentStream(contentStream).draw();
                drawer.startY(startY - tableHeader.getHeight());
                drawer.contentStream(contentStream).draw();
            }

            startY = page.getMediaBox().getHeight() - 50;
        } while (false); //!drawer.isFinished());

        document.save("twoPageTable-repeatingHeader.pdf");
        document.close();
    }

    private Row create3rdDataRow() {
        return Row.builder()
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text("Gray Again")
                        .colSpan(2)
                        .backgroundColor(GRAY_LIGHT_2)
                        .build())
                .build();
    }

    private Row create4thDataRow() {
        return Row.builder()
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text("And Darker Gray")
                        .colSpan(2)
                        .backgroundColor(GRAY_LIGHT_3)
                        .build())
                .build();
    }

    private Row create5thDataRow() {
        return Row.builder()
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text("Right!")
                        .rowSpan(2)
                        .backgroundColor(GRAY_LIGHT_2)
                        .build())
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text("Aligned!")
                        .horizontalAlignment(RIGHT)
                        .backgroundColor(GRAY_LIGHT_2)
                        .build())
                .build();
    }

    private Row create6thDataRow() {
        return Row.builder()
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text("Left.")
                        .backgroundColor(GRAY_LIGHT_3)
                        .build())
                .build();
    }

    private Row create7thDataRow() {
        return Row.builder()
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text("Here some text.")
                        .backgroundColor(GRAY_LIGHT_2)
                        .colSpan(3)
                        .build())
                .build();
    }

    private Row create8thDataRow() throws IOException {
        return Row.builder()
                .add(createAndGetTuxTextCellBuilder().rowSpan(7).build())
                .add(createAndGetTuxImageCellBuilder().rowSpan(6).build())
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text("Darker Gray Again.")
                        .colSpan(2)
                        .backgroundColor(GRAY_LIGHT_3)
                        .build())
                .build();
    }

    private Row create9thDataRow() {
        return Row.builder()
                .add(TextCell.builder().borderWidth(1).text("Bit Lighter.").colSpan(2).backgroundColor(GRAY_LIGHT_2).build())
                .build();
    }

    private Row create10thDataRow() {
        return Row.builder()
                .add(TextCell.builder().borderWidth(1).text("Well. Actually not.").colSpan(2).backgroundColor(GRAY_LIGHT_3).build())
                .build();
    }

    private Row create11thDataRow() {
        return Row.builder().add(TextCell.builder().borderWidth(1).text("Now.").colSpan(2).backgroundColor(GRAY_LIGHT_2).build())
                .build();
    }

    private Row create12thDataRow() {
        return Row.builder().add(TextCell.builder().borderWidth(1).text("Yeah.").rowSpan(2).backgroundColor(GRAY_LIGHT_3).build())
                .add(TextCell.builder().borderWidth(1).text("This and ...")
                        .horizontalAlignment(RIGHT)
                        .backgroundColor(GRAY_LIGHT_3)
                        .build())
                .build();
    }

    private Row create13thDataRow() {
        return Row.builder()
                .add(TextCell.builder().borderWidth(1).text("... that: right aligned!")
                        .backgroundColor(GRAY_LIGHT_2)
                        .horizontalAlignment(RIGHT)
                        .build())
                .build();
    }

    private Row create14thDataRow() {
        return Row.builder()
                .add(TextCell.builder().borderWidth(1).text("Here some text, too.").backgroundColor(GRAY_LIGHT_3).colSpan(3).build())
                .build();
    }

    private ImageCellBuilder createAndGetTuxImageCellBuilder() throws IOException {
        return ImageCell.builder()
                .verticalAlignment(MIDDLE)
                .horizontalAlignment(CENTER)
                .borderWidth(1)
                .image(createTuxImage())
                .scale(0.4f);
    }

    private TextCellBuilder createAndGetTuxTextCellBuilder() {
        return TextCell.builder().borderWidth(1).text("Tux")
                .verticalAlignment(MIDDLE)
                .horizontalAlignment(CENTER)
                .backgroundColor(GRAY_LIGHT_1);
    }

    private TextCellBuilder createAndGetTorvaldsQuoteCellBuilder() {
        return TextCell.builder().borderWidth(1)
                .text("\"I'm doing a (free) operating system (just a hobby, " +
                        "won't be big and professional like gnu) for 386(486) AT clones\" \n\n " +
                        "– Linus Torvalds")
                .verticalAlignment(MIDDLE)
                .horizontalAlignment(JUSTIFY)
                .padding(14)
                .font(HELVETICA_OBLIQUE)
                .backgroundColor(GRAY_LIGHT_1);
    }

    private ImageCellBuilder createAndGetGliderImageCellBuilder() throws IOException {
        return ImageCell.builder()
                .verticalAlignment(MIDDLE)
                .horizontalAlignment(CENTER)
                .borderWidth(1)
                .image(createGliderImage())
                .scale(0.4f);
    }

    private TextCellBuilder createAndGetGliderTextCellBuilder() {
        return TextCell.builder()
                .borderWidth(1)
                .text("Glider")
                .verticalAlignment(MIDDLE)
                .horizontalAlignment(CENTER)
                .backgroundColor(GRAY_LIGHT_1);
    }

}
