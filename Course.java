// File: Course.java
import java.util.*;

class Course implements ICourse {
    private final Map<Integer, String[]> courses = new HashMap<>();
    private int idCounter = 1;

    public void addCourse(String title, String description, String instructor, String schedule) {
        if (title.isEmpty() || description.isEmpty() || instructor.isEmpty() || schedule.isEmpty()) {
            System.out.println("[Semua kolom harus diisi!]");
            return;
        }
        courses.put(idCounter++, new String[]{title, description, instructor, schedule});
        System.out.println("[Kursus berhasil ditambahkan.]");
    }

    public void editCourse(int id, String newTitle, String newDescription, String newInstructor, String newSchedule) {
        if (courses.containsKey(id)) {
            courses.put(id, new String[]{newTitle, newDescription, newInstructor, newSchedule});
            System.out.println("[Kursus berhasil diubah.]");
        } else {
            System.out.println("[Kursus tidak ditemukan.]");
        }
    }

    public void deleteCourse(int id) {
        if (courses.containsKey(id)) {
            courses.remove(id);
            System.out.println("[Kursus berhasil dihapus.]");
        } else {
            System.out.println("[Kursus tidak ditemukan.]");
        }
    }

    public void viewAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("[Tidak ada kursus tersedia.]");
        } else {
            System.out.println("============== DAFTAR KURSUS ==============");
            for (Map.Entry<Integer, String[]> entry : courses.entrySet()) {
                System.out.println("ID: " + entry.getKey());
                System.out.println("Judul: " + entry.getValue()[0]);
                System.out.println("Deskripsi: " + entry.getValue()[1]);
                System.out.println("Instruktur: " + entry.getValue()[2]);
                System.out.println("Jadwal: " + entry.getValue()[3]);
                System.out.println("-------------------------------------------");
            }
        }
    }

    public void searchCourse(String keyword) {
        boolean found = false;
        System.out.println("============== HASIL PENCARIAN ==============");
        for (Map.Entry<Integer, String[]> entry : courses.entrySet()) {
            if (entry.getValue()[0].toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("ID: " + entry.getKey());
                System.out.println("Judul: " + entry.getValue()[0]);
                System.out.println("Deskripsi: " + entry.getValue()[1]);
                System.out.println("Instruktur: " + entry.getValue()[2]);
                System.out.println("Jadwal: " + entry.getValue()[3]);
                System.out.println("-------------------------------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("[Kursus tidak ditemukan.]");
        }
    }

    public String getCourseTitle(int id) {
        if (courses.containsKey(id)) {
            return courses.get(id)[0];
        }
        return "";
    }

    public boolean exists(int id) {
        return courses.containsKey(id);
    }
}
