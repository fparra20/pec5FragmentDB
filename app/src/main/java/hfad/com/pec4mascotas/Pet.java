package hfad.com.pec4mascotas;

import java.io.Serializable;
import java.util.ArrayList;

// Implementa serializable lo cual nos permite pasar arraylist entre activities
public class Pet implements Serializable {
    private String name;
    private int photo;
    private int rating;
    private int pet_id;

    public Pet(int pet_id, int photo, String name, int rating) {
        this.pet_id=pet_id;
        this.name = name;
        this.photo = photo;
        this.rating = rating;
    }

    public int getPet_id() {
        return pet_id;
    }

    public String getName() {
        return name;
    }

    public int getPhoto() {
        return photo;
    }

    public int getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
