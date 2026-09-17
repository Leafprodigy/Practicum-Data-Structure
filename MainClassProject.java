package project_oop_uas;

import java.util.Scanner;

public class MainClassProject {

    private static Scanner input = new Scanner(System.in);
    private static String userAktif = null;

    public static void main(String[] args) {
        tampilkanBanner();
        tampilanMenuLogin();
    }

    // ================================================================
    // BANNER
    // ================================================================
    private static void tampilkanBanner() {
        System.out.println(" =================================================  ");
        System.out.println("      PEMBAYARAN PAJAK KENDARAAN MOBIL MOTOR         ");
        System.out.println("                 (Project UAS OOP)                  ");
        System.out.println(" =================================================  ");
    }

    // ================================================================
    // MENU LOGIN
    // ================================================================
    private static void tampilanMenuLogin() {
        boolean running = true;
        while (running) {
            System.out.println("\n");
            System.out.println("       +--------------------------------+");
            System.out.println("       |           MENU UTAMA           |");
            System.out.println("       +--------------------------------+");
            System.out.println("       |  1. Login                      |");
            System.out.println("       |  2. Daftar Akun Baru           |");
            System.out.println("       |  3. Keluar                     |");
            System.out.println("       +--------------------------------+");
            System.out.print("\n       Pilih menu: ");

            try {
                int pilihan = Integer.parseInt(input.nextLine().trim());
                switch (pilihan) {
                    case 1 -> prosesLogin();
                    case 2 -> prosesDaftar();
                    case 3 -> {
                        System.out.println("\nTerima kasih. Sampai jumpa!");
                        running = false;
                    }
                    default -> System.out.println("[!] Pilihan tidak valid!\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("[!] Masukkan angka yang valid!\n");
            } catch (PajakException e) {
                System.out.println("[!] Error: " + e.getMessage() + "\n");
            }
        }
    }

    private static void prosesLogin() {
        System.out.println("\n---------- LOGIN ----------");
        System.out.print("Username : ");
        String username = input.nextLine().trim();
        System.out.print("Password : ");
        String password = input.nextLine().trim();

        try {
            if (FileRepository2.cekLogin(username, password)) {
                userAktif = username;
                System.out.println("Login berhasil! Selamat datang, " + username + "!\n");
                tampilanMenuUtama();
            } else {
                System.out.println("[!] Username atau password salah!\n");
            }
        } catch (PajakException e) {
            System.out.println("[!] " + e.getMessage() + "\n");
        }
    }

    private static void prosesDaftar() {
        System.out.println("\n---------- DAFTAR AKUN BARU ----------");
        System.out.print("Username baru : ");
        String username = input.nextLine().trim();
        System.out.print("Password baru : ");
        String password = input.nextLine().trim();

        try {
            FileRepository2.simpanUser(username, password);
            System.out.println("Akun berhasil dibuat! Silakan login.\n");
        } catch (PajakException e) {
            System.out.println("[!] " + e.getMessage() + "\n");
        }
    }

    // ================================================================
    // MENU UTAMA
    // ================================================================
    private static void tampilanMenuUtama() {
        boolean running = true;
        while (running) {
            System.out.println("\n");
            System.out.println("       +--------------------------------------+");
            System.out.println("       |         MENU PAJAK KENDARAAN         |");
            System.out.println("       |    User : " + String.format("%-28s", userAktif) + "|");
            System.out.println("       +--------------------------------------+");
            System.out.println("       |  1. Tambah Kendaraan Mobil           |");
            System.out.println("       |  2. Tambah Kendaraan Motor           |");
            System.out.println("       |  3. List Kendaraan                   |");
            System.out.println("       |  4. Hitung Pajak Kendaraan           |");
            System.out.println("       |  5. Hapus Kendaraan                  |");
            System.out.println("       |  6. Logout                           |");
            System.out.println("       +--------------------------------------+");
            System.out.print("\n         Pilih menu: ");

            try {
                int pilihan = Integer.parseInt(input.nextLine().trim());
                switch (pilihan) {
                    case 1 -> inputMobil();
                    case 2 -> inputMotor();
                    case 3 -> {
                        FileRepository2.listKendaraan(userAktif);
                        System.out.println("Tekan Enter untuk kembali ke menu...");
                        input.nextLine();
                    }
                    case 4 -> prosesHitungPajak();
                    case 5 -> prosesHapusKendaraan();
                    case 6 -> {
                        userAktif = null;
                        System.out.println("\nLogout berhasil. Sampai jumpa!\n");
                        running = false;
                    }
                    default -> System.out.println("[!] Pilihan tidak valid!\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("[!] Masukkan angka yang valid!\n");
            } catch (PajakException e) {
                System.out.println("[!] " + e.getMessage() + "\n");
            }
        }
    }

    // ================================================================
    // INPUT MOBIL
    // ================================================================
    private static void inputMobil() {
        System.out.println("\n---------- Tambah Mobil ----------");
        try {
            System.out.print("No. Polisi                : ");
            String nopol = input.nextLine();

            System.out.print("Merek                     : ");
            String merek = input.nextLine();

            System.out.print("Tahun                     : ");
            int tahun = Integer.parseInt(input.nextLine().trim());

            System.out.print("Kepemilikan ke- (1/2/3/4) : ");
            int urutan = Integer.parseInt(input.nextLine().trim());

            System.out.print("Kapasitas Mesin (CC)      : ");
            int cc = Integer.parseInt(input.nextLine().trim());

            Mobil mbl = new Mobil(nopol, merek, tahun, urutan, cc);
            FileRepository2.simpanKendaraan(userAktif, mbl);
            System.out.println(">> Data Mobil berhasil disimpan!\n");

        } catch (NumberFormatException e) {
            System.out.println("[!] Input angka tidak valid!\n");
        } catch (PajakException e) {
            System.out.println("[!] " + e.getMessage() + " [" + e.getKode() + "]\n");
        }
    }

    // ================================================================
    // INPUT MOTOR
    // ================================================================
    private static void inputMotor() {
        System.out.println("\n---------- Tambah Motor ----------");
        try {
            System.out.print("No. Polisi                : ");
            String nopol = input.nextLine();

            System.out.print("Merek                     : ");
            String merek = input.nextLine();

            System.out.print("Tahun                     : ");
            int tahun = Integer.parseInt(input.nextLine().trim());

            System.out.print("Kepemilikan ke- (1/2/3/4) : ");
            int urutan = Integer.parseInt(input.nextLine().trim());

            System.out.print("Kapasitas Mesin (CC)      : ");
            int cc = Integer.parseInt(input.nextLine().trim());

            Motor mtr = new Motor(nopol, merek, tahun, urutan, cc);
            FileRepository2.simpanKendaraan(userAktif, mtr);
            System.out.println(">> Data Motor berhasil disimpan!\n");

        } catch (NumberFormatException e) {
            System.out.println("[!] Input angka tidak valid!\n");
        } catch (PajakException e) {
            System.out.println("[!] " + e.getMessage() + " [" + e.getKode() + "]\n");
        }
    }

    // ================================================================
    // HITUNG PAJAK — tampil list milik user, pilih nomor
    // ================================================================
    private static void prosesHitungPajak() {
        int jumlah = FileRepository2.listKendaraan(userAktif);
        if (jumlah == 0) return;

        try {
            System.out.print("\nPilih nomor kendaraan (1-" + jumlah + ") : ");
            int nomor = Integer.parseInt(input.nextLine().trim());

            if (nomor < 1 || nomor > jumlah) {
                System.out.println("[!] Nomor tidak valid!\n");
                return;
            }

            FileRepository2.hitungPajakByNomor(userAktif, nomor);
            System.out.println("\nTekan Enter untuk kembali ke menu...");
            input.nextLine();

        } catch (NumberFormatException e) {
            System.out.println("[!] Masukkan angka yang valid!\n");
        } catch (PajakException e) {
            System.out.println("[!] " + e.getMessage() + "\n");
        }
    }

    // ================================================================
    // HAPUS KENDARAAN — tampil list milik user, pilih nomor
    // ================================================================
    private static void prosesHapusKendaraan() {
        int jumlah = FileRepository2.listKendaraan(userAktif);
        if (jumlah == 0) return;

        try {
            System.out.print("\nPilih nomor kendaraan yang dihapus (1-" + jumlah + ") : ");
            int nomor = Integer.parseInt(input.nextLine().trim());

            if (nomor < 1 || nomor > jumlah) {
                System.out.println("[!] Nomor tidak valid!\n");
                return;
            }

            // konfirmasi dulu sebelum hapus
            System.out.print("Yakin ingin hapus kendaraan no-" + nomor + "? (y/n) : ");
            String jawab = input.nextLine().trim().toLowerCase();

            if (jawab.equals("y")) {
                FileRepository2.hapusKendaraan(userAktif, nomor);
            } else {
                System.out.println(">> Hapus kendaraan dibatalkan.\n");
            }

        } catch (NumberFormatException e) {
            System.out.println("[!] Masukkan angka yang valid!\n");
        } catch (PajakException e) {
            System.out.println("[!] " + e.getMessage() + "\n");
        }
    }
}