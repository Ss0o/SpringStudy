package study.springstudy.concurrency.threadlocal;

public final class UserContextHolder {

    private static final ThreadLocal<String> USER_HOLDER =
            new ThreadLocal<>();

    private UserContextHolder() {
    }

    public static void setUser(String user) {
        USER_HOLDER.set(user);
    }

    public static String getUser() {
        return USER_HOLDER.get();
    }

    public static void clear() {
        USER_HOLDER.remove();
    }
}
