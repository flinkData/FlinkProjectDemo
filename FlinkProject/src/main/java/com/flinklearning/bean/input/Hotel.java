package bean.input;



public class Hotel {

    private Integer id ;
    private String name ;
    private String type ;
    private String city ;
    private String area ;
    private String location ;
    private String point ;
    private String people ;
    private String price ;
    private String priceGrade ;
    private String pupGrade ;

    public Hotel() {
    }

    public Hotel(Integer id, String name, String type, String city, String area, String location, String point, String people, String price, String priceGrade, String pupGrade) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.city = city;
        this.area = area;
        this.location = location;
        this.point = point;
        this.people = people;
        this.price = price;
        this.priceGrade = priceGrade;
        this.pupGrade = pupGrade;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", location='" + location + '\'' +
                ", point='" + point + '\'' +
                ", people='" + people + '\'' +
                ", price='" + price + '\'' +
                ", priceGrade='" + priceGrade + '\'' +
                ", pupGrade='" + pupGrade + '\'' +
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceGrade() {
        return priceGrade;
    }

    public void setPriceGrade(String priceGrade) {
        this.priceGrade = priceGrade;
    }

    public String getPupGrade() {
        return pupGrade;
    }

    public void setPupGrade(String pupGrade) {
        this.pupGrade = pupGrade;
    }
}
