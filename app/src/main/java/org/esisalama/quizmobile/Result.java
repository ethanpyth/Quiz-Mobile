package org.esisalama.quizmobile;

import java.util.List;

public class Result {
    public List<User> getItems() {
        return items;
    }

    public void setItems(List<User> items) {
        this.items = items;
    }

    private List<User> items;
}
