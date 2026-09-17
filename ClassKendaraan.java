package project_oop_uas;

//Super Class
abstract class ClassKendaraan implements Taxable
{
    protected String noPolisi;
    protected String merek;
    protected int tahun;
    protected int urutanKepemilikan;
    
    //Tarif Pajak berdasarkan urutan kepemilikan
    protected static final double TARIF_KEPEMILIKAN_1 = 0.015; //15%
    protected static final double TARIF_KEPEMILIKAN_2 = 0.020; //20%
    protected static final double TARIF_KEPEMILIKAN_3 = 0.025; //25%
    protected static final double TARIF_KEPEMILIKAN_4 = 0.030; //30%
    
    
        
   public ClassKendaraan(String noPolisi, String merek, int tahun, int urutanKepemilikan) 
    {
        validasiInput(noPolisi, merek, tahun, urutanKepemilikan);
        this.noPolisi = noPolisi.trim().toUpperCase(); // (trim) untuk Mengatasi kesalahan spasi kosong misal "n 1234 ab  " akan menjadi  "N 1234 AB" (toUpperCae) setiap inputan akan menjadi Huruf kapital nanti nya dari n 1234 ab jadi N 1234 AB
        this.merek = merek;
        this.tahun = tahun;
        this.urutanKepemilikan = urutanKepemilikan;
    }
    
    
    private void validasiInput(String noPolisi, String merek, int tahun, int urutan) 
    {
    if (noPolisi == null || noPolisi.trim().isEmpty())
        throw new PajakException("INVALID_NOPOL", "Nomor polisi tidak boleh kosong!");
    if (merek == null || merek.trim().isEmpty())
        throw new PajakException("INVALID_MEREK", "Merek tidak boleh kosong!");
    if (tahun < 1945 || tahun > 2026)
        throw new PajakException("INVALID_TAHUN", "Tahun tidak valid! (1945-2026)");
    if (urutan < 1 || urutan > 4)
        throw new PajakException("INVALID_URUTAN", "Kepemilikan harus antara 1-4!");
    }
    protected double getTarifPKB() 
    {
        return switch (urutanKepemilikan) 
        {
            case 1 -> TARIF_KEPEMILIKAN_1;
            case 2 -> TARIF_KEPEMILIKAN_2;
            case 3 -> TARIF_KEPEMILIKAN_3;
            default -> TARIF_KEPEMILIKAN_4;
        }; 
    }
    protected double getKoefisienPenyusutan() 
    {
        int umur = 2026 - this.tahun;
        double penyusutan = umur * 0.05;
        //opsi value penyusutan antara 75% atau 50%
        if (penyusutan > 0.50) penyusutan = 0.50; //Batas Maksimal penyusutan kendaraan sebesar 50%
        
        return 1.0 - penyusutan;
    }

    public String getNoPolisi()       { return noPolisi; }
    public String getMerek()          { return merek;}
    public int getTahun()             { return tahun;}
    public int getUrutanKepemilikan() {return urutanKepemilikan;}
    
    public abstract double getNJKB();
    public abstract double getSWDKLLJ();
    
    //Interface + Abstract untuk di implementasikan ke turunan nya!
    @Override
    public abstract double hitungPajak();

    @Override
    public abstract void tampilkanInfo();
    
    
    
}
