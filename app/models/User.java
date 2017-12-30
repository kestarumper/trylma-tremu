package models;

import java.util.Objects;

public class User {
    private final String name;
    private final String csrf;

    public User(String name, String csrf) {
        this.name = name;
        this.csrf = csrf;
    }

    public String getName() {
        return name;
    }

    public String getCsrf() {
        return csrf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
