// File: Auth.java
class Auth implements IAuth {
    private boolean isAdmin = false;

    public boolean login(String user, String pass) {
        if (("admin".equals(user) && "admin123".equals(pass)) || ("siswa".equals(user) && "siswa123".equals(pass))) {
            isAdmin = "admin".equals(user);
            return true;
        }
        return false;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void logout() {
        isAdmin = false;
    }
}