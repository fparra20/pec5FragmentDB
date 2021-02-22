package hfad.com.pec4mascotas;

import java.io.Serializable;

// Implementa serializable lo cual nos permite pasar arraylist entre activities
public class Pet implements Serializable {
    private String name;
    private int photo;
    private String rating;

    public Pet(int photo, String name, String rating) {
        this.name = name;
        this.photo = photo;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getPhoto() {
        return photo;
    }

    public String getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}
