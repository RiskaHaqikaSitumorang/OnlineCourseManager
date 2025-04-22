// File: IAuth.java
interface IAuth {
    boolean login(String username, String password);
    void logout();
    boolean isAdmin();
}