import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;

public class ThermalPrinterExample {

    public static void main(String[] args) {
        // Membuat JFrame
        JFrame frame = new JFrame("Thermal Printer Example");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Membuat JTextArea
        JTextArea textArea = new JTextArea(8, 30);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);

        // Membuat tombol Print
        JButton printButton = new JButton("Print");
        frame.add(printButton);

        // Menambahkan ActionListener pada tombol Print
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printText(textArea.getText());
            }
        });

        // Menampilkan JFrame
        frame.setVisible(true);
    }

    // Metode untuk mencetak teks
    private static void printText(String text) {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }
            
            // Mengatur ukuran kertas khusus untuk printer thermal
            Paper paper = new Paper();
            double width = 3 * 72;  // Lebar kertas dalam titik (3 inci)
            double height = 8 * 72; // Tinggi kertas dalam titik (8 inci)
            paper.setSize(width, height);
            paper.setImageableArea(0, 0, width, height);

            PageFormat format = new PageFormat();
            format.setPaper(paper);

            // Mengatur ukuran font untuk printer thermal
            graphics.setFont(new Font("Monospaced", Font.PLAIN, 12));
            graphics.drawString(text, 10, 10);

            return PAGE_EXISTS;
        });

        boolean printAccepted = printerJob.printDialog();
        if (printAccepted) {
            try {
                printerJob.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }
}
