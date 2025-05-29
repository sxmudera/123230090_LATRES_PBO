package model;

public class Transaksi {
    private int id;
    private String namaPelanggan;
    private String namaObat;
    private int harga;
    private int jumlah;

    public Transaksi(int id, String namaPelanggan, String namaObat, int harga, int jumlah) {
        this.id = id;
        this.namaPelanggan = namaPelanggan;
        this.namaObat = namaObat;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public Transaksi(String namaPelanggan, String namaObat, int harga, int jumlah) {
        this(0, namaPelanggan, namaObat, harga, jumlah);
    }

    public int getId() { return id; }
    public String getNamaPelanggan() { return namaPelanggan; }
    public String getNamaObat() { return namaObat; }
    public int getHarga() { return harga; }
    public int getJumlah() { return jumlah; }
    public int getTotal() {
        int total = harga * jumlah;
        if (jumlah > 5) {
            total *= 0.9; // diskon 10%
        }
        return total;
    }

    public void setId(int id) { this.id = id; }
    public void setNamaPelanggan(String namaPelanggan) { this.namaPelanggan = namaPelanggan; }
    public void setNamaObat(String namaObat) { this.namaObat = namaObat; }
    public void setHarga(int harga) { this.harga = harga; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
}