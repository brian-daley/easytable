//package org.vandeseer.easytable.bmd;
//
//import static org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.common.PDRectangle;
//import org.vandeseer.easytable.TableDrawer;
//import org.vandeseer.easytable.structure.Row;
//import org.vandeseer.easytable.structure.Table;
//import org.vandeseer.easytable.structure.cell.CellText;
//
//public class GroupedRowsExample {
//
//public static void main(String[] args) throws Exception {
//createDocumentWithTableOverMultiplePages();
//}
//
//private static void createDocumentWithTableOverMultiplePages() throws Exception {
//try (final PDDocument document = new PDDocument()) {
//
//TableDrawer drawer = TableDrawer.builder()
//.table(createTable())
//.startX(50f)
//.endY(50f) // note: if not set, table is drawn over the end of the page
//.build();
//
//do {
//PDPage page = new PDPage(PDRectangle.A4);
//document.addPage(page);
//
//drawer.startY(page.getMediaBox().getHeight() - 50f);
//
//try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
//drawer.contentStream(contentStream).draw();
//}
//} while (!drawer.isFinished());
//
//document.save("target/example.pdf");
//}
//}
//
//private static Table createTable() {
//final Table.TableBuilder tableBuilder = Table.builder()
//.addColumnsOfWidth(300, 120)
//.fontSize(22)
//.font(HELVETICA);
//
//createGroupedRows("Ana", 6).forEach(tableBuilder::addRow);
//createGroupedRows("Michael", 5).forEach(tableBuilder::addRow);
//createGroupedRows("Chris", 10).forEach(tableBuilder::addRow);
//createGroupedRows("Marge", 3).forEach(tableBuilder::addRow);
//createGroupedRows("Lisa", 12).forEach(tableBuilder::addRow);
//
//return tableBuilder.build();
//}
//
//private static List<Row> createGroupedRows(String name, int numberOfRows) {
//List<Row> rows = new ArrayList<>();
//rows.add(Row.builder()
//.add(CellText.builder().text(name).rowSpan(numberOfRows).borderWidth(1).build())
//.add(CellText.builder().text("0").borderWidth(1).build())
//.build());
//
//for (int i = 1; i < numberOfRows; i++) {
//rows.add(Row.builder().add(CellText.builder().text(String.valueOf(i)).borderWidth(1).build()).build());
//}
//
//return rows;
//}
//
//}
