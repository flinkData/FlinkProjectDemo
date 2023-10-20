package bean.output;

/**
 * 存储评分信息
 */
public class DTOHotel {
    private Integer id ;

    private String name ;
    private String point ;

    public DTOHotel() {
    }

    public DTOHotel(Integer id, String name, String point) {
        this.id = id;
        this.name = name;
        this.point = point;
    }

    @Override
    public String toString() {
        return "DTOHotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", point='" + point + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
