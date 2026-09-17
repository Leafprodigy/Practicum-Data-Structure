package project_oop_uas;

import java.io.*;
import java.util.regex.Pattern;

public class FileRepository2 {

    private static final String USER_FILE     = "data_user.txt";
    private static final String HISTORY_FILE  = "history_kendaraan.txt";
    private static final String SEPARATOR     = "|";

    // ================================================================
    // USER — cekLogin
    // ================================================================
    public static boolean cekLogin(String username, String password) {
        if (username == null || password == null) return false;
        File file = new File(USER_FILE);
        if (!file.exists()) return false;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String baris;
            while ((baris = br.readLine()) != null) {
                String[] bagian = baris.split(Pattern.quote(SEPARATOR));
                if (bagian.length != 2) continue;
                String usernameFile = bagian[0].trim();
                String passwordFile = bagian[1].trim();
                if (usernameFile.equals(username) && passwordFile.equals(password))
                    return true;
            }
        } catch (IOException e) {
            throw new PajakException("FILE_ERROR", "Gagal membaca file: " + e.getMessage());
        } finally {
            if (br != null) {
                try { br.close(); } catch (IOException e) {}
            }
        }
        return false;
    }

    // ================================================================
    // USER — cekUsernameAda
    // ================================================================
    public static boolean cekUsernameAda(String username) {
        if (username == null) return false;
        File file = new File(USER_FILE);
        if (!file.exists()) return false;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String baris;
            while ((baris = br.readLine()) != null) {
                String[] bagian = baris.split(Pattern.quote(SEPARATOR));
                if (bagian.length >= 1 && bagian[0].trim().equals(username.trim()))
                    return true;
            }
        } catch (IOException e) {
            throw new PajakException("FILE_ERROR", "Gagal membaca data user: " + e.getMessage());
        } finally {
            if (br != null) {
                try { br.close(); } catch (IOException e) {}
            }
        }
        return false;
    }

    // ================================================================
    // USER — simpanUser
    // ================================================================
    public static void simpanUser(String username, String password) {
        if (username == null || username.trim().isEmpty())
            throw new PajakException("INVALID_USER", "Username tidak boleh kosong!");
        if (password == null || password.length() < 4)
            throw new PajakException("INVALID_PASS", "Password minimal 4 karakter!");
        if (cekUsernameAda(username))
            throw new PajakException("USER_EXISTS", "Username '" + username + "' sudah terdaftar!");

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(USER_FILE, true));
            bw.write(username.trim() + SEPARATOR + password.trim());
            bw.newLine();
        } catch (IOException e) {
            throw new PajakException("FILE_ERROR", "Gagal menyimpan user: " + e.getMessage());
        } finally {
            if (bw != null) {
                try { bw.close(); } catch (IOException e) {}
            }
        }
    }

    // ================================================================
    // KENDARAAN — simpanKendaraan
    // Format: username|jenis|noPolisi|merek|tahun|urutan|cc
    // ================================================================
    public static void simpanKendaraan(String username, ClassKendaraan k) {
        String jenis;
        int cc;

        if (k instanceof Mobil) {
            jenis = "MOBIL";
            cc = ((Mobil) k).getKapasitasMesin();
        } else if (k instanceof Motor) {
            jenis = "MOTOR";
            cc = ((Motor) k).getKapasitasMesin();
        } else {
            return;
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(HISTORY_FILE, true));
            bw.write(username.trim()
                    + SEPARATOR + jenis
                    + SEPARATOR + k.getNoPolisi()
                    + SEPARATOR + k.getMerek()
                    + SEPARATOR + k.getTahun()
                    + SEPARATOR + k.getUrutanKepemilikan()
                    + SEPARATOR + cc);
            bw.newLine();
        } catch (IOException e) {
            throw new PajakException("FILE_ERROR", "Gagal menyimpan kendaraan: " + e.getMessage());
        } finally {
            if (bw != null) {
                try { bw.close(); } catch (IOException e) {}
            }
        }
    }

    // ================================================================
    // KENDARAAN — listKendaraan (filter by username)
    // Return jumlah kendaraan milik user tsb
    // ================================================================
    public static int listKendaraan(String username) {
        File file = new File(HISTORY_FILE);
        if (!file.exists()) {
            System.out.println("[!] Anda belum menambahkan kendaraan!\n");
            return 0;
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String baris;
            int nomor = 1;
            boolean adaData = false;

            System.out.println("\n+----+-------+-------------+------------------+-------+-----+");
            System.out.println("|           LIST KENDARAAN - User: " + String.format("%-26s", username) + "|");
            System.out.println("+----+-------+-------------+------------------+-------+-----+");
            System.out.printf("| %-3s| %-6s| %-12s| %-17s| %-6s| %-4s|\n",
                    "No", "Jenis", "No.Polisi", "Merek", "Tahun", "CC");
            System.out.println("+----+-------+-------------+------------------+-------+-----+");

            while ((baris = br.readLine()) != null) {
                if (baris.trim().isEmpty()) continue;
                String[] data = baris.split(Pattern.quote(SEPARATOR));
                if (data.length < 7) continue;

                // filter hanya milik username ini
                if (!data[0].trim().equals(username.trim())) continue;

                System.out.printf("| %-3s| %-6s| %-12s| %-17s| %-6s| %-4s|\n",
                        nomor++, data[1], data[2], data[3], data[4], data[6]);
                adaData = true;
            }

            System.out.println("+----+-------+-------------+------------------+-------+-----+");

            if (!adaData) {
                System.out.println("[!] Anda belum menambahkan kendaraan!\n");
                return 0;
            }

            return nomor - 1;

        } catch (IOException e) {
            throw new PajakException("FILE_ERROR", "Gagal membaca list: " + e.getMessage());
        } finally {
            if (br != null) {
                try { br.close(); } catch (IOException e) {}
            }
        }
    }

    // ================================================================
    // KENDARAAN — hitungPajakByNomor (filter by username)
    // ================================================================
    public static void hitungPajakByNomor(String username, int nomor) {
        File file = new File(HISTORY_FILE);
        if (!file.exists()) {
            System.out.println("[!] Anda belum menambahkan kendaraan!\n");
            return;
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String baris;
            int index = 1;

            while ((baris = br.readLine()) != null) {
                if (baris.trim().isEmpty()) continue;
                String[] data = baris.split(Pattern.quote(SEPARATOR));
                if (data.length < 7) continue;

                // filter hanya milik username ini
                if (!data[0].trim().equals(username.trim())) continue;

                if (index == nomor) {
                    String jenis = data[1].trim();
                    String nopol = data[2].trim();
                    String merek = data[3].trim();
                    int tahun    = Integer.parseInt(data[4].trim());
                    int urutan   = Integer.parseInt(data[5].trim());
                    int cc       = Integer.parseInt(data[6].trim());

                    if (jenis.equals("MOBIL")) {
                        Mobil m = new Mobil(nopol, merek, tahun, urutan, cc);
                        m.tampilkanInfo();
                    } else if (jenis.equals("MOTOR")) {
                        Motor mt = new Motor(nopol, merek, tahun, urutan, cc);
                        mt.tampilkanInfo();
                    }
                    return;
                }
                index++;
            }

            System.out.println("[!] Nomor kendaraan tidak ditemukan!\n");

        } catch (NumberFormatException e) {
            System.out.println("[!] Data kendaraan rusak / format salah!\n");
        } catch (IOException e) {
            throw new PajakException("FILE_ERROR", "Gagal membaca data: " + e.getMessage());
        } finally {
            if (br != null) {
                try { br.close(); } catch (IOException e) {}
            }
        }
    }

    // ================================================================
    // KENDARAAN — hapusKendaraan (filter by username, hapus by nomor)
    // ================================================================
    public static void hapusKendaraan(String username, int nomor) {
        File file     = new File(HISTORY_FILE);
        File fileBaru = new File("history_temp.txt");

        if (!file.exists()) {
            System.out.println("[!] Belum ada kendaraan yang tersimpan.\n");
            return;
        }

        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader(file));
            bw = new BufferedWriter(new FileWriter(fileBaru));

            String baris;
            int index   = 1;
            boolean ketemu = false;

            while ((baris = br.readLine()) != null) {
                if (baris.trim().isEmpty()) continue;
                String[] data = baris.split(Pattern.quote(SEPARATOR));

                // kalau bukan milik user ini, langsung tulis ulang
                if (data.length < 7 || !data[0].trim().equals(username.trim())) {
                    bw.write(baris);
                    bw.newLine();
                    continue;
                }

                // kalau milik user ini tapi bukan nomor yang dihapus, tulis ulang
                if (index != nomor) {
                    bw.write(baris);
                    bw.newLine();
                } else {
                    ketemu = true; // baris ini yang dihapus, skip
                }
                index++;
            }

            if (!ketemu) {
                System.out.println("[!] Nomor kendaraan tidak ditemukan!\n");
            } else {
                System.out.println(">> Kendaraan berhasil dihapus!\n");
            }

        } catch (IOException e) {
            throw new PajakException("FILE_ERROR", "Gagal menghapus kendaraan: " + e.getMessage());
        } finally {
            if (br != null) { try { br.close(); } catch (IOException e) {} }
            if (bw != null) { try { bw.close(); } catch (IOException e) {} }
        }

        // ganti file lama dengan file baru
        file.delete();
        fileBaru.renameTo(file);
    }
}