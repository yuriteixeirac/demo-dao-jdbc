package model.entities;

import java.io.Serial;
import java.io.Serializable;

public class Department implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

    public Department() {}

    public Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Department department) {
        return this.id.equals(department.getId());
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
