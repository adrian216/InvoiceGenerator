package com.example.invoicesezam.utils;

import com.example.invoicesezam.invoice.Invoice;
import com.example.invoicesezam.invoice.InvoiceElement;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
import java.util.Set;
import java.util.stream.Stream;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class InvoiceGeneratorPdf {

    private static Logger logger = LoggerFactory.getLogger(InvoiceGeneratorPdf.class);

    public static ByteArrayInputStream generateInvoice(Invoice invoice) throws URISyntaxException
    {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Calendar cal = Calendar.getInstance();
        cal.setTime(invoice.getCreatedAt());

        try
        {

            PdfWriter.getInstance(document, out);
            document.open();
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
            Paragraph p = new Paragraph("Faktura " + invoice.getId() + "/" + cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR), font);
            p.setAlignment(Element.ALIGN_CENTER);

        document.add(getHeader(invoice));
            document.add(getSellerAndBuyer(invoice));
            document.add(p);
            document.add(Chunk.NEWLINE);
            document.add(getProductsTable(invoice));
            document.add(getSummaryTable(invoice));
            document.add(getSignTable());

            document.close();
        }catch(DocumentException e)
        {
            logger.error(e.toString());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    private static void formatCell(PdfPCell cell)
    {
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingRight(4);
        cell.setPaddingBottom(4);
    }

    private static PdfPTable getHeader(Invoice invoice) throws IOException, BadElementException
    {
        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(100);

//        Path path = Paths.get(ClassLoader.getSystemResource("static/logoname.jpg").toURI());
//        Image img = Image.getInstance(path.toAbsolutePath().toString());
//        img.scaleAbsolute(100f, 100f);

        Image image = Image.getInstance(new URL("https://i.imgur.com/PjmutwX.png"));
        image.scaleAbsolute(100f,100f);

        PdfPCell cell1=new PdfPCell(image);
        cell1.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(cell1);

        PdfPCell cell2=new PdfPCell(new Phrase(""));
        cell2.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(cell2);

        PdfPCell cell3=new PdfPCell(new Phrase("\nData wystawienia: "+invoice.getCreatedAt()));
        cell3.setBorder(Rectangle.NO_BORDER);
        cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(cell3);
        headerTable.setSpacingAfter(50);

        return headerTable;
    }

    private static PdfPTable getSellerAndBuyer(Invoice invoice)
    {
        PdfPTable sellerBuyerTable = new PdfPTable(3);
        sellerBuyerTable.setWidthPercentage(100);
        PdfPCell cell;

        cell=new PdfPCell(new Phrase("Sprzedawca: \nNazwa Firmy\nUlica nr domu\nKod pocztowy Miasto\nNIP: 1234567890"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(30);
        sellerBuyerTable.addCell(cell);

        cell=new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        sellerBuyerTable.addCell(cell);

        cell=new PdfPCell(new Phrase("Nabywca:\n"+invoice.getCustomer().getName()+"\n"+invoice.getCustomer().getAddress()));
        cell.setBorder(Rectangle.NO_BORDER);
        sellerBuyerTable.addCell(cell);
        sellerBuyerTable.setSpacingAfter(20);

        return sellerBuyerTable;
    }

    private static PdfPTable getProductsTable(Invoice invoice) throws DocumentException
    {

        Set<InvoiceElement> invoiceElements = invoice.getInvoiceElementSet();

        PdfPTable productTable = new PdfPTable(10);
        productTable.setWidthPercentage(100);
        productTable.setWidths(new float[] { 2,9,5,2,2,4,4,3,4,4 });

        Font tableFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
        Stream.of( "Lp","Nazwa","SKU", "Ilosc", "Jm","Cena netto","Wartosc netto","Stawka VAT","Kwota VAT","Wartosc brutto")
                .forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, FontFactory.defaultEncoding);
                    headFont.setSize(10);
                    header.setBackgroundColor(new BaseColor(227,227,227));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    header.setPhrase(new Phrase(headerTitle, headFont));
                    productTable.addCell(header);
                });

        int idCount=1;

        for (InvoiceElement invoiceElement : invoiceElements)
        {
            PdfPCell idCell = new PdfPCell(new Phrase(String.valueOf(idCount), tableFont));
            formatCell(idCell);
            productTable.addCell(idCell);
            idCount++;

            PdfPCell nameCell = new PdfPCell(new Phrase(invoiceElement.getProduct().getName(), tableFont));
            formatCell(nameCell);
            productTable.addCell(nameCell);

            PdfPCell skuCell = new PdfPCell(new Phrase(invoiceElement.getProduct().getSku(), tableFont));
            formatCell(skuCell);
            productTable.addCell(skuCell);

            PdfPCell quantityCell = new PdfPCell(new Phrase(String.valueOf(invoiceElement.getQuantity()), tableFont));
            formatCell(quantityCell);
            productTable.addCell(quantityCell);

            PdfPCell jmCell = new PdfPCell(new Phrase("szt.", tableFont));
            formatCell(jmCell);
            productTable.addCell(jmCell);

            PdfPCell netCell = new PdfPCell(new Phrase(String.format("%.2f",invoiceElement.getProduct().getNetPrice()), tableFont));
            formatCell(netCell);
            productTable.addCell(netCell);

            PdfPCell netAmountCell = new PdfPCell(new Phrase(String.format("%.2f",invoiceElement.calculateNetPrice()), tableFont));
            formatCell(netAmountCell);
            productTable.addCell(netAmountCell);

            PdfPCell vatCell = new PdfPCell(new Phrase((invoiceElement.getProduct().getVat()+"%"), tableFont));
            formatCell(vatCell);
            productTable.addCell(vatCell);

            PdfPCell vatAmountCell = new PdfPCell(new Phrase(String.format("%.2f",(invoiceElement.calculateVat())), tableFont));
            formatCell(vatAmountCell);
            productTable.addCell(vatAmountCell);

            PdfPCell grossCell = new PdfPCell(new Phrase(String.valueOf(invoiceElement.calculatePrice()), tableFont));
            formatCell(grossCell);
            productTable.addCell(grossCell);
        }
        return productTable;
    }

    private static PdfPTable getSummaryTable(Invoice invoice) throws DocumentException
    {

        Font tableFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        PdfPTable summaryTable = new PdfPTable(5);
        summaryTable.setWidthPercentage(100);
        summaryTable.setWidths(new float[] { 24,4,3,4,4 });
        PdfPCell cell;

        cell=new PdfPCell(new Phrase("RAZEM:"+"   ",tableFont));
        cell.setVerticalAlignment(Element.ALIGN_RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPaddingRight(2);
        cell.setBorder(Rectangle.NO_BORDER);
        summaryTable.addCell(cell);

        cell=new PdfPCell(new Phrase(String.format("%.2f", invoice.getTotalNet()),tableFont));
        cell.setBackgroundColor(new BaseColor(227,227,227));
        formatCell(cell);
        summaryTable.addCell(cell);

        cell=new PdfPCell(new Phrase("",tableFont));
        cell.setBackgroundColor(new BaseColor(227,227,227));
        formatCell(cell);
        summaryTable.addCell(cell);

        cell=new PdfPCell(new Phrase(String.format("%.2f", (invoice.getTotalVat())),tableFont));
        cell.setBackgroundColor(new BaseColor(227,227,227));
        formatCell(cell);
        summaryTable.addCell(cell);

        cell=new PdfPCell(new Phrase(String.valueOf(invoice.getTotal()),tableFont));
        cell.setBackgroundColor(new BaseColor(227,227,227));
        formatCell(cell);
        summaryTable.addCell(cell);

        summaryTable.setSpacingAfter(120);
        return summaryTable;
    }

    private static PdfPTable getSignTable()
    {

        Font tableFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
        PdfPTable signTable = new PdfPTable(3);
        signTable.setWidthPercentage(100);
        PdfPCell cell;

        cell=new PdfPCell(new Phrase("...........................................................\nOsoba upowazniona do wystawienia faktury",tableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        signTable.addCell(cell);

        cell=new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        signTable.addCell(cell);

        cell=new PdfPCell(new Phrase("...........................................................\nOsoba upowazniona do odbioru",tableFont));
        cell.setBorder(Rectangle.NO_BORDER);
        signTable.addCell(cell);

        return signTable;

    }

}
