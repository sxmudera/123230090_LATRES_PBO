package controller;

import model.Transaksi;
import db.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class TransaksiController {
    public void tambahTransaksi(Transaksi transaksi) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO transaksi (nama_pelanggan, nama_obat, harga, jumlah, created_at) VALUES (?, ?, ?, ?, NOW())";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, transaksi.getNamaPelanggan());
            stmt.setString(2, transaksi.getNamaObat());
            stmt.setInt(3, transaksi.getHarga());
            stmt.setInt(4, transaksi.getJumlah());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaksi> getTransaksiHariIni() {
        List<Transaksi> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM transaksi WHERE DATE(created_at) = CURDATE()";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Transaksi t = new Transaksi(
                    rs.getInt("id"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("nama_obat"),
                    rs.getInt("harga"),
                    rs.getInt("jumlah")
                );
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateTransaksi(Transaksi transaksi) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE transaksi SET nama_pelanggan=?, nama_obat=?, harga=?, jumlah=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, transaksi.getNamaPelanggan());
            stmt.setString(2, transaksi.getNamaObat());
            stmt.setInt(3, transaksi.getHarga());
            stmt.setInt(4, transaksi.getJumlah());
            stmt.setInt(5, transaksi.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void hapusTransaksi(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM transaksi WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}