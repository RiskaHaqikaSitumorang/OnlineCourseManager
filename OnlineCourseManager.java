import java.util.*;
import java.util.stream.Collectors;

interface CourseInterface {
    void tambahKursus();
    void hapusKursus();
    void editKursus();
    void tampilkanKursus();
    void cariKursus();
}

interface UserInterface {
    void daftarKursus();
    void beriRatingReview();
    void lihatKursusDidaftarkan();
}

class User {
    private String username;
    private String role;
    private List<String> enrolledCourses = new ArrayList<>();

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }
    public List<String> getEnrolledCourses() { return enrolledCourses; }

    public void enrollCourse(String course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }
}

class Course {
    private String name;
    private String category;
    private List<Integer> ratings = new ArrayList<>();
    private List<String> reviews = new ArrayList<>();

    public Course(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }

    public void addRating(int rating) { ratings.add(rating); }
    public double getAverageRating() {
        return ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public void addReview(String review) { reviews.add(review); }

    public void displayCourseDetails() {
        System.out.println("Kursus: " + name + " | Kategori: " + category);
        System.out.println("Rating: " + getAverageRating());
        System.out.println("Ulasan: " + reviews);
    }

    public void updateCourse(String newName, String newCategory) {
        this.name = newName;
        this.category = newCategory;
    }
}

class AdminMenu implements CourseInterface {
    private Scanner scanner;
    private List<Course> courses;

    public AdminMenu(Scanner scanner, List<Course> courses) {
        this.scanner = scanner;
        this.courses = courses;
    }

    @Override
    public void tambahKursus() {
        System.out.print("Nama kursus: ");
        String nama = scanner.nextLine();
        System.out.print("Kategori kursus: ");
        String kategori = scanner.nextLine();
        courses.add(new Course(nama, kategori));
        System.out.println("Kursus berhasil ditambahkan.");
    }

    @Override
    public void hapusKursus() {
        tampilkanKursus();
        if (courses.isEmpty()) return;
        System.out.print("Nomor kursus yang ingin dihapus: ");
        int idx = scanner.nextInt(); scanner.nextLine();
        if (idx >= 1 && idx <= courses.size()) {
            courses.remove(idx - 1);
            System.out.println("Kursus dihapus.");
        } else {
            System.out.println("Nomor tidak valid.");
        }
    }

    @Override
    public void editKursus() {
        tampilkanKursus();
        if (courses.isEmpty()) return;
        System.out.print("Nomor kursus yang ingin diedit: ");
        int idx = scanner.nextInt(); scanner.nextLine();
        if (idx >= 1 && idx <= courses.size()) {
            Course c = courses.get(idx - 1);
            System.out.print("Nama baru: ");
            String newName = scanner.nextLine();
            System.out.print("Kategori baru: ");
            String newCat = scanner.nextLine();
            c.updateCourse(newName, newCat);
            System.out.println("Kursus diperbarui.");
        } else {
            System.out.println("Nomor tidak valid.");
        }
    }

    @Override
    public void tampilkanKursus() {
        if (courses.isEmpty()) {
            System.out.println("Belum ada kursus.");
            return;
        }
        int i = 1;
        for (Course c : courses) {
            System.out.println(i++ + ". " + c.getName() + " | " + c.getCategory());
        }
    }

    @Override
    public void cariKursus() {
        System.out.print("Cari nama/kategori: ");
        String keyword = scanner.nextLine().toLowerCase();
        List<Course> hasil = courses.stream()
                .filter(c -> c.getName().toLowerCase().contains(keyword) ||
                             c.getCategory().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        if (hasil.isEmpty()) {
            System.out.println("Tidak ditemukan.");
        } else {
            hasil.forEach(Course::displayCourseDetails);
        }
    }
}

class StudentMenu implements UserInterface {
    private Scanner scanner;
    private List<Course> courses;
    private User currentUser;

    public StudentMenu(Scanner scanner, List<Course> courses, User currentUser) {
        this.scanner = scanner;
        this.courses = courses;
        this.currentUser = currentUser;
    }

    @Override
    public void daftarKursus() {
        tampilkanKursus();
        if (courses.isEmpty()) return;
        System.out.print("Nomor kursus yang ingin didaftarkan: ");
        int idx = scanner.nextInt(); scanner.nextLine();
        if (idx >= 1 && idx <= courses.size()) {
            String namaKursus = courses.get(idx - 1).getName();
            if (currentUser.getEnrolledCourses().contains(namaKursus)) {
                System.out.println("Sudah terdaftar.");
            } else {
                currentUser.enrollCourse(namaKursus);
                System.out.println("Berhasil mendaftar.");
            }
        } else {
            System.out.println("Nomor tidak valid.");
        }
    }

    @Override
    public void beriRatingReview() {
        List<String> daftar = currentUser.getEnrolledCourses();
        if (daftar.isEmpty()) {
            System.out.println("Belum terdaftar di kursus apapun.");
            return;
        }

        for (int i = 0; i < daftar.size(); i++) {
            System.out.println((i + 1) + ". " + daftar.get(i));
        }

        System.out.print("Pilih nomor kursus: ");
        int idx = scanner.nextInt(); scanner.nextLine();
        if (idx < 1 || idx > daftar.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        String nama = daftar.get(idx - 1);
        Course course = courses.stream()
                .filter(c -> c.getName().equals(nama)).findFirst().orElse(null);

        if (course != null) {
            System.out.print("Rating (1-5): ");
            int rate = scanner.nextInt(); scanner.nextLine();
            if (rate < 1 || rate > 5) {
                System.out.println("Rating tidak valid.");
                return;
            }
            System.out.print("Review: ");
            String review = scanner.nextLine();
            course.addRating(rate);
            course.addReview(review);
            System.out.println("Rating & review ditambahkan.");
        }
    }

    @Override
    public void lihatKursusDidaftarkan() {
        List<String> daftar = currentUser.getEnrolledCourses();
        if (daftar.isEmpty()) {
            System.out.println("Belum ada kursus yang didaftarkan.");
        } else {
            daftar.forEach(course -> System.out.println("- " + course));
        }
    }

    private void tampilkanKursus() {
        if (courses.isEmpty()) {
            System.out.println("Belum ada kursus.");
            return;
        }
        int i = 1;
        for (Course c : courses) {
            System.out.println(i++ + ". " + c.getName() + " | " + c.getCategory());
        }
    }
}

public class OnlineCourseManager {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, User> users = new HashMap<>();
    private static List<Course> courses = new ArrayList<>();
    private static User currentUser;

    public static void main(String[] args) {
        while (true) {
            if (currentUser == null) {
                loginMenu();
            } else {
                mainMenu();
            }
        }
    }

    private static void loginMenu() {
        System.out.println("\n===== LOGIN =====");
        System.out.print("Pilih peran (admin/siswa): ");
        String role = scanner.nextLine().toLowerCase();

        if (!role.equals("admin") && !role.equals("siswa")) {
            System.out.println("Peran tidak valid!");
            return;
        }

        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();

        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.getRole().equals(role)) {
                currentUser = user;
                System.out.println("Login berhasil sebagai " + role);
            } else {
                System.out.println("Peran tidak cocok dengan akun tersebut!");
            }
        } else {
            User newUser = new User(username, role);
            users.put(username, newUser);
            currentUser = newUser;
            System.out.println("Akun baru dibuat dan login sebagai " + role);
        }
    }

    private static void mainMenu() {
        System.out.println("\n===== MENU UTAMA =====");

        if (currentUser.getRole().equals("admin")) {
            AdminMenu admin = new AdminMenu(scanner, courses);
            System.out.println("1. Tambah Kursus");
            System.out.println("2. Hapus Kursus");
            System.out.println("3. Edit Kursus");
            System.out.println("4. Lihat Semua Kursus");
            System.out.println("5. Cari Kursus");
            System.out.println("9. Logout");
            System.out.print("Pilih opsi: ");
            int pilihan = scanner.nextInt(); scanner.nextLine();

            switch (pilihan) {
                case 1: admin.tambahKursus(); break;
                case 2: admin.hapusKursus(); break;
                case 3: admin.editKursus(); break;
                case 4: admin.tampilkanKursus(); break;
                case 5: admin.cariKursus(); break;
                case 9: currentUser = null; break;
                default: System.out.println("Pilihan tidak valid!");
            }

        } else {
            StudentMenu siswa = new StudentMenu(scanner, courses, currentUser);
            System.out.println("6. Daftar ke Kursus");
            System.out.println("7. Beri Rating & Review");
            System.out.println("8. Lihat Kursus yang Didaftarkan");
            System.out.println("9. Logout");
            System.out.print("Pilih opsi: ");
            int pilihan = scanner.nextInt(); scanner.nextLine();

            switch (pilihan) {
                case 6: siswa.daftarKursus(); break;
                case 7: siswa.beriRatingReview(); break;
                case 8: siswa.lihatKursusDidaftarkan(); break;
                case 9: currentUser = null; break;
                default: System.out.println("Pilihan tidak valid!");
            }
        }
    }
}