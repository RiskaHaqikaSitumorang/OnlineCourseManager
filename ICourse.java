// File: ICourse.java
interface ICourse {
    void addCourse(String title, String description, String instructor, String schedule);
    void editCourse(int id, String newTitle, String newDescription, String newInstructor, String newSchedule);
    void deleteCourse(int id);
    void viewAllCourses();
    void searchCourse(String keyword);
    String getCourseTitle(int id);
    boolean exists(int id);
}