
package project_oop_uas;


public class Motor extends ClassKendaraan
{
     private int kapasitasMesin;
     private double SWDKLLJ;
     
     private static final double NJKB_DIBAWAH_150CC = 15_000_000;
     private static final double NJKB_150_250CC     = 25_000_000;
     private static final double NJKB_DIATAS_250CC  = 45_000_000;

     private static final double SWDKLLJ_MOTOR = 35_000;

    public Motor (String noPolisi, String merek, int tahun, int urutanKepemilikan, int kapasitasMesin)
    {
        super(noPolisi, merek, tahun, urutanKepemilikan);
        if (kapasitasMesin <= 0)
        throw new PajakException("INVALID_CC", "Kapasitas mesin harus lebih dari 0 CC!");
        if (kapasitasMesin > 2000)
        throw new PajakException("INVALID_CC", "Kapasitas mesin tidak realistis!");
        this.kapasitasMesin = kapasitasMesin;
        this.SWDKLLJ        = SWDKLLJ_MOTOR;
        
    }
    @Override
     public double getNJKB()
     {
      double hargaDasar;
        if (kapasitasMesin < 150)
            hargaDasar = NJKB_DIBAWAH_150CC;
        else if (kapasitasMesin <= 250)
            hargaDasar = NJKB_150_250CC;
        else
            hargaDasar = NJKB_DIATAS_250CC;  
        
        return hargaDasar * getKoefisienPenyusutan();  //
     }
     
    @Override
     public double getSWDKLLJ() { return SWDKLLJ; }
     
     @Override
    public double hitungPajak() 
    {           //PKB Pokok + SWDKLLJ
     return (getNJKB() * getTarifPKB()) + getSWDKLLJ();
    }
    @Override
    public void tampilkanInfo()
    {
        double njkbAkhirMotor = getNJKB();
        double tarifPersenMotor = getTarifPKB() * 100; // di konversi ke persen
        double pkbPokokMotor = getNJKB() * getTarifPKB(); //PKB murni
        double totalBayarMOtor = hitungPajak();//include SWDKLLJ
        
        
        System.out.println("+--------------------------------------------------+");
        System.out.println("|             RINCIAN PAJAK KENDARAAN (MOTOR)      |");
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| No. Polisi          : %-26s |\n", this.noPolisi);
        System.out.printf("| Merek Kendaraan     : %-26s |\n", this.merek);
        System.out.printf("| Tahun Perakitan     : %-26s |\n", this.tahun);
        System.out.printf("| Kapasitas Mesin     : %-26s |\n", this.kapasitasMesin + " CC");
        System.out.printf("| Kepemilikan Ke-     : %-26s |\n", this.urutanKepemilikan);
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| Nilai Jual Estimasi (NJKB)   : Rp %,19.0f |\n", njkbAkhirMotor);
        System.out.printf("| Tarif PKB Progresif : %,22.1f%% |\n", tarifPersenMotor);
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| 1. Pokok PKB        : Rp %,19.0f |\n", pkbPokokMotor);
        System.out.printf("| 2. SWDKLLJ          : Rp %,19.0f |\n", getSWDKLLJ());
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| TOTAL PEMBAYARAN PAJAK     : Rp %,19.0f |\n", totalBayarMOtor);
        System.out.println("+--------------------------------------------------+");
        
            
    }
    public int getKapasitasMesin() { return kapasitasMesin; }
} 
