package org.example.Persistence;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.example.Model.Category;
import org.example.Model.MealItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PDFWorker {
        private static final int PAGE_MARGIN = 50;
        private static final int LINE_HEIGHT = 15;

        public static void exportToPDF(HashMap <Category, ArrayList <MealItem>> shoppingList){
            try {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);

                int yOffset = (int) (PDRectangle.A4.getHeight() - PAGE_MARGIN);

                contentStream.beginText();
                contentStream.newLineAtOffset(PAGE_MARGIN, yOffset);
                contentStream.showText("Shopping List");
                contentStream.newLineAtOffset(0, -LINE_HEIGHT);
                contentStream.showText("--------------------------------------------");
                contentStream.endText();

                yOffset -= LINE_HEIGHT * 2;

                for (Map.Entry<Category, ArrayList<MealItem>> entry : shoppingList.entrySet()) {
                    Category category = entry.getKey();
                    ArrayList<MealItem> items = entry.getValue();

                    if (yOffset - LINE_HEIGHT * (items.size() + 2) < PAGE_MARGIN) {

                        contentStream.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
                        yOffset = (int) PDRectangle.A4.getHeight() - PAGE_MARGIN;
                    }

                    yOffset -= LINE_HEIGHT;   //space before category name

                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(PAGE_MARGIN, yOffset);
                    contentStream.showText("  " + category.toString());
                    contentStream.endText();

                    yOffset -= LINE_HEIGHT; //space after category name

                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
                    for (MealItem item : items) {
                        yOffset -= LINE_HEIGHT;
                        String itemText = String.format("    %s - %.2f g", item.getProduct().getName(), item.getWeight());
                        contentStream.beginText();
                        contentStream.newLineAtOffset(PAGE_MARGIN * 2, yOffset);
                        contentStream.showText(itemText);
                        contentStream.endText();
                    }

                    yOffset -= LINE_HEIGHT; //space between categories
                }

                contentStream.close();

                document.save(getDesktopPath());
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private static String getDesktopPath() {
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop";
        return desktopPath + File.separator + "Shopping_List.pdf";
    }
}


