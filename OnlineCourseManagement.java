// File: OnlineCourseManagement.java
import java.util.*;

public class OnlineCourseManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IAuth auth = new Auth();
        ICourse course = new Course();
        IEnrollment enrollment = new Enrollment(course);
        INotification notif = new Notification();

        System.out.println("============== SISTEM MANAJEMEN KURSUS ONLINE ==============");

        while (true) {
            System.out.print("Login sebagai (admin/siswa): ");
            String user = sc.nextLine();
            System.out.print("Password: ");
            String pass = sc.nextLine();

            if (!auth.login(user, pass)) {
                notif.notifyUser("Login gagal. Username atau password salah.");
                continue;
            }

            notif.notifyUser("Login berhasil sebagai " + user);
            boolean loggedIn = true;

            while (loggedIn) {
                try {
                    if (auth.isAdmin()) {
                        System.out.println("============== MENU ADMIN ==============");
                        System.out.println("1. Tambah Kursus");
                        System.out.println("2. Edit Kursus");
                        System.out.println("3. Hapus Kursus");
                        System.out.println("4. Lihat Semua Kursus");
                        System.out.println("5. Cari Kursus");
                        System.out.println("6. Logout");
                        System.out.print("Pilih menu: ");
                        String pilihan = sc.nextLine();

                        switch (pilihan) {
                            case "1":
                                System.out.print("Judul kursus: ");
                                String title = sc.nextLine();
                                System.out.print("Deskripsi kursus: ");
                                String description = sc.nextLine();
                                System.out.print("Nama instruktur: ");
                                String instructor = sc.nextLine();
                                System.out.print("Jadwal kursus: ");
                                String schedule = sc.nextLine();
                                course.addCourse(title, description, instructor, schedule);
                                break;
                            case "2":
                                course.viewAllCourses();
                                if (courseIsEmpty(course)) break;
                                System.out.print("ID kursus yang ingin diedit: ");
                                int idEdit = Integer.parseInt(sc.nextLine());
                                System.out.print("Judul baru: ");
                                String newTitle = sc.nextLine();
                                System.out.print("Deskripsi baru: ");
                                String newDesc = sc.nextLine();
                                System.out.print("Instruktur baru: ");
                                String newInstructor = sc.nextLine();
                                System.out.print("Jadwal baru: ");
                                String newSchedule = sc.nextLine();
                                course.editCourse(idEdit, newTitle, newDesc, newInstructor, newSchedule);
                                break;
                            case "3":
                                course.viewAllCourses();
                                if (courseIsEmpty(course)) break;
                                System.out.print("ID kursus yang ingin dihapus: ");
                                int idDel = Integer.parseInt(sc.nextLine());
                                course.deleteCourse(idDel);
                                break;
                            case "4":
                                course.viewAllCourses();
                                break;
                            case "5":
                                System.out.print("Kata kunci: ");
                                String keyword = sc.nextLine();
                                course.searchCourse(keyword);
                                break;
                            case "6":
                                auth.logout();
                                notif.notifyUser("Logout berhasil.");
                                loggedIn = false;
                                break;
                            default:
                                notif.notifyUser("Menu tidak tersedia.");
                        }
                    } else {
                        System.out.println("============== MENU SISWA ==============");
                        System.out.println("1. Lihat Semua Kursus");
                        System.out.println("2. Daftar Kursus");
                        System.out.println("3. Beri Rating dan Review");
                        System.out.println("4. Lihat Kursus Saya");
                        System.out.println("5. Logout");
                        System.out.print("Pilih menu: ");
                        String pilihan = sc.nextLine();

                        switch (pilihan) {
                            case "1":
                                course.viewAllCourses();
                                break;
                            case "2":
                                course.viewAllCourses();
                                if (courseIsEmpty(course)) break;
                                System.out.print("ID kursus yang ingin didaftar: ");
                                int idEnroll = Integer.parseInt(sc.nextLine());
                                enrollment.enrollCourse(idEnroll);
                                break;
                            case "3":
                                enrollment.viewMyCourses();
                                System.out.print("ID kursus yang ingin direview: ");
                                int idReview = Integer.parseInt(sc.nextLine());
                                System.out.print("Rating (1-5): ");
                                int rating = Integer.parseInt(sc.nextLine());
                                System.out.print("Komentar: ");
                                String comment = sc.nextLine();
                                enrollment.giveReview(idReview, rating, comment);
                                break;
                            case "4":
                                enrollment.viewMyCourses();
                                break;
                            case "5":
                                auth.logout();
                                notif.notifyUser("Logout berhasil.");
                                loggedIn = false;
                                break;
                            default:
                                notif.notifyUser("Menu tidak tersedia.");
                        }
                    }
                } catch (Exception e) {
                    notif.notifyUser("Terjadi kesalahan input.");
                    sc.nextLine(); // clear buffer
                }
            }
        }
    }

    private static boolean courseIsEmpty(ICourse course) {
        if (!course.exists(1)) { // asumsi ID mulai dari 1
            System.out.println("[Belum ada kursus yang tersedia.]");
            return true;
        }
        return false;
    }
}