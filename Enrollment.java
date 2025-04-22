// File: Enrollment.java
import java.util.*;

class Enrollment implements IEnrollment {
    private final List<Integer> enrolledCourses = new ArrayList<>();
    private final ICourse course;
    private final Map<Integer, List<String>> reviews = new HashMap<>();

    public Enrollment(ICourse course) {
        this.course = course;
    }

    public void enrollCourse(int id) {
        if (course.exists(id)) {
            if (!enrolledCourses.contains(id)) {
                enrolledCourses.add(id);
                System.out.println("[Berhasil daftar ke kursus.]");
            } else {
                System.out.println("[Anda sudah terdaftar di kursus ini.]");
            }
        } else {
            System.out.println("[Kursus tidak ditemukan.]");
        }
    }

    public void viewMyCourses() {
        if (enrolledCourses.isEmpty()) {
            System.out.println("[Anda belum terdaftar di kursus manapun.]");
        } else {
            System.out.println("============== KURSUS YANG DIDAFTARKAN ==============");
            for (int id : enrolledCourses) {
                System.out.println("ID: " + id + " - Judul: " + course.getCourseTitle(id));
            }
        }
    }

    public void giveReview(int id, int rating, String comment) {
        if (!course.exists(id)) {
            System.out.println("[Kursus tidak ditemukan.]");
            return;
        }
        if (!enrolledCourses.contains(id)) {
            System.out.println("[Anda belum mendaftar di kursus ini.]");
            return;
        }
        if (comment.trim().isEmpty()) {
            System.out.println("[Komentar tidak boleh kosong. Tulis minimal satu kalimat.]");
            return;
        }

        String review = "Rating: " + rating + " - Komentar: " + comment;
        reviews.putIfAbsent(id, new ArrayList<>());
        reviews.get(id).add(review);
        System.out.println("[Review berhasil dikirim.]");
    }
}