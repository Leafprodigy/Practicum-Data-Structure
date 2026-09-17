package project_oop_uas;
 
//Sub Class dari ClassKendaraan
public class Mobil extends ClassKendaraan 
{
    private int kapasitasMesin;
    private double SWDKLLJ;
   
    
    private static final double NJKB_DIBAWAH_1000CC  = 80_000_000;
    private static final double NJKB_1000_1500CC     = 150_000_000;
    private static final double NJKB_1500_2000CC     = 250_000_000;
    private static final double NJKB_DIATAS_2000CC   = 400_000_000;
    
    private static final double SWDKLLJ_MOBIL = 143_000;
    
   public Mobil(String noPolisi, String merek, int tahun,int urutanKepemilikan,int kapasitasMesin ) 
   {
    super(noPolisi, merek, tahun, urutanKepemilikan);
    if (kapasitasMesin <= 0)
        throw new PajakException("INVALID_CC", "Kapasitas mesin harus lebih dari 0 CC!");
    if (kapasitasMesin > 10000)
        throw new PajakException("INVALID_CC", "Kapasitas mesin tidak realistis!");
    this.kapasitasMesin = kapasitasMesin;
    this.SWDKLLJ       = SWDKLLJ_MOBIL;
    }
    
   @Override
    public double getNJKB()
    {
      double hargaDasar;
        if (kapasitasMesin < 1000)
            hargaDasar = NJKB_DIBAWAH_1000CC;
        else if (kapasitasMesin <= 1500)
            hargaDasar = NJKB_1000_1500CC;
        else if (kapasitasMesin <= 2000)
            hargaDasar = NJKB_1500_2000CC;
        else
            hargaDasar = NJKB_DIATAS_2000CC;  
        
        return hargaDasar * getKoefisienPenyusutan();  //
    }
   
   @Override
    public double getSWDKLLJ() { return SWDKLLJ; }
    
   @Override
    public double hitungPajak() 
    {
        return (getNJKB() * getTarifPKB()) + getSWDKLLJ();
    }
    
    @Override
    public void tampilkanInfo(){
        
       
        // Menghitung komponen pajak agar bisa ditampilkan rinciannya
        double njkbAkhirMobil = getNJKB();
        double tarifPersenMobil = getTarifPKB() * 100; // di konversi ke persen
        double pkbPokokMobil = getNJKB() * getSWDKLLJ();
        double totalBayarMobil = hitungPajak();
        
        System.out.println("+--------------------------------------------------+");
        System.out.println("|             RINCIAN PAJAK KENDARAAN (MOBIL)      |");
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| No. Polisi          : %-26s |\n", this.noPolisi);
        System.out.printf("| Merek Kendaraan     : %-26s |\n", this.merek);
        System.out.printf("| Tahun Perakitan     : %-26s |\n", this.tahun);
        System.out.printf("| Kapasitas Mesin     : %-26s |\n", this.kapasitasMesin + " CC");
        System.out.printf("| Kepemilikan Ke-     : %-26s |\n", this.urutanKepemilikan);
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| Nilai Jual Estimasi (NJKB)   : Rp %,19.0f |\n", njkbAkhirMobil);
        System.out.printf("| Tarif PKB Progresif : %,22.1f%% |\n", tarifPersenMobil);
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| 1. Pokok PKB        : Rp %,19.0f |\n", pkbPokokMobil);
        System.out.printf("| 2. SWDKLLJ          : Rp %,19.0f |\n", getSWDKLLJ());
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| TOTAL PEMBAYARAN PAJAK     : Rp %,19.0f |\n", totalBayarMobil);
        System.out.println("+--------------------------------------------------+");
        
    }
   public int getKapasitasMesin() { return kapasitasMesin; }
}
