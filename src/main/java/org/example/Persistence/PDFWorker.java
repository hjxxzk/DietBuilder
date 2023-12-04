package org.example.Persistence;

import org.example.Model.Category;
import org.example.Model.MealItem;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PDFWorker {

    private static final int LINE_HEIGHT = 15;

    public static void exportToPDF(HashMap<Category, ArrayList<MealItem>> list) {
        try (PDDocument document = new PDDocument()) {
            for (Map.Entry<Category, ArrayList<MealItem>> entry : list.entrySet()) {
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Category: " + entry.getKey().name());
                contentStream.newLineAtOffset(0, -20);

                for (MealItem mealItem : entry.getValue()) {
                    contentStream.showText("Product: " + mealItem.getProduct().getName());
                    contentStream.newLineAtOffset(0, -LINE_HEIGHT);
                    contentStream.showText("Weight: " + mealItem.getWeight());
                    contentStream.newLineAtOffset(0, -LINE_HEIGHT);
                }

                contentStream.endText();
                contentStream.close();
            }

            document.save(getDesktopPath());
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


