package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controller.TransaksiController;
import model.Transaksi;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainView extends JFrame {
    private JTextField tfNamaPelanggan, tfNamaObat, tfHarga, tfJumlah;
    private JButton btnTambah, btnEdit, btnHapus, btnRefresh, btnTotal;
    private JTable table;
    private DefaultTableModel tableModel;
    private TransaksiController controller;

    public MainView() {
        controller = new TransaksiController();

        setTitle("Aplikasi Transaksi Apotek");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Nama Pelanggan"));
        tfNamaPelanggan = new JTextField();
        formPanel.add(tfNamaPelanggan);

        formPanel.add(new JLabel("Nama Obat"));
        tfNamaObat = new JTextField();
        formPanel.add(tfNamaObat);

        formPanel.add(new JLabel("Harga Satuan"));
        tfHarga = new JTextField();
        formPanel.add(tfHarga);

        formPanel.add(new JLabel("Jumlah Beli"));
        tfJumlah = new JTextField();
        formPanel.add(tfJumlah);

        btnTambah = new JButton("Tambah");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        btnRefresh = new JButton("Refresh");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnTambah);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnRefresh);
        btnTotal = new JButton("Lihat Total Bayar");
        buttonPanel.add(btnTotal);


        tableModel = new DefaultTableModel(new String[]{"ID", "Nama Pelanggan", "Nama Obat", "Harga", "Jumlah"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        refreshTable();

        btnTambah.addActionListener(e -> tambahTransaksi());
        btnEdit.addActionListener(e -> editTransaksi());
        btnHapus.addActionListener(e -> hapusTransaksi());
        btnRefresh.addActionListener(e -> refreshTable());
        btnTotal.addActionListener(e -> lihatTotalBayar());


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row == -1) return;

                    try {
                        int harga = Integer.parseInt(tableModel.getValueAt(row, 3).toString());
                        int jumlah = Integer.parseInt(tableModel.getValueAt(row, 4).toString());
                        int total = harga * jumlah;
                        int diskon = 0;
                        if (jumlah > 5) {
                            diskon = (int)(total * 0.1);
                        }
                        int totalBayar = total - diskon;

                        JOptionPane.showMessageDialog(null,
                            "Harga satuan : Rp " + harga +
                            "\nJumlah beli  : " + jumlah +
                            "\nTotal        : Rp " + total +
                            "\nDiskon 10%   : Rp " + diskon +
                            "\nTotal bayar  : Rp " + totalBayar
                        );
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menghitung total.");
                    }
                }
            }
        });}


    private void tambahTransaksi() {
        try {
            String nama = tfNamaPelanggan.getText();
            String obat = tfNamaObat.getText();
            int harga = Integer.parseInt(tfHarga.getText());
            int jumlah = Integer.parseInt(tfJumlah.getText());
            Transaksi t = new Transaksi(nama, obat, harga, jumlah);
            controller.tambahTransaksi(t);
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid.");
        }
    }

    private void editTransaksi() {
        try {
            int row = table.getSelectedRow();
            if (row == -1) return;
            int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            String nama = tfNamaPelanggan.getText();
            String obat = tfNamaObat.getText();
            int harga = Integer.parseInt(tfHarga.getText());
            int jumlah = Integer.parseInt(tfJumlah.getText());
            Transaksi t = new Transaksi(id, nama, obat, harga, jumlah);
            controller.updateTransaksi(t);
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal edit.");
        }
    }

    private void hapusTransaksi() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
        controller.hapusTransaksi(id);
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Transaksi> transaksiList = controller.getTransaksiHariIni();
        for (Transaksi t : transaksiList) {
            tableModel.addRow(new Object[]{t.getId(), t.getNamaPelanggan(), t.getNamaObat(), t.getHarga(), t.getJumlah()});
        }
    }

    private void lihatTotalBayar() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu.");
            return;
        }

        try {
            int harga = Integer.parseInt(tableModel.getValueAt(row, 3).toString());
            int jumlah = Integer.parseInt(tableModel.getValueAt(row, 4).toString());
            int total = harga * jumlah;
            int diskon = 0;
            if (jumlah > 5) {
                diskon = (int)(total * 0.1);
            }
            int totalBayar = total - diskon;

            JOptionPane.showMessageDialog(this,
                "Harga satuan : Rp " + harga +
                "\nJumlah beli  : " + jumlah +
                "\nTotal        : Rp " + total +
                "\nDiskon 10%   : Rp " + diskon +
                "\nTotal bayar  : Rp " + totalBayar
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Data tidak valid.");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}
