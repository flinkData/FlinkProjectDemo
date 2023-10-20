package bean.input;

public class Sensor {
    private String id ;
    private Double sensorTemp ;

    public Sensor() {
    }

    public Sensor(String id, Double sensorTemp) {
        this.id = id;
        this.sensorTemp = sensorTemp;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id='" + id + '\'' +
                ", sensorTemp=" + sensorTemp +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getSensorTemp() {
        return sensorTemp;
    }

    public void setSensorTemp(Double sensorTemp) {
        this.sensorTemp = sensorTemp;
    }
}
