// File: Notification.java
class Notification implements INotification {
    public void notifyUser(String message) {
        System.out.println("[" + message + "]");
    }
}