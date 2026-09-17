package project_oop_uas;



public class PajakException extends RuntimeException{

    private final String kode;

    // Constructor 1 parameter
    public PajakException(String pesan) {
        super(pesan);
        this.kode = "PAJAK_ERROR";
    }

    // Constructor 2 parameter
    public PajakException(String kode, String pesan) {
        super(pesan);
        this.kode = kode;
    }

    public String getKode() {
        return kode;
    }

    @Override
    public String toString() {
        return "[" + kode + "] " + getMessage();
    }
}