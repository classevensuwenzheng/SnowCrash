package cn.edu.hebtu.software.snowcarsh2.bean;

public class content {
    private int id;
    private String img;
    private String info;
    private String price;
    private String uri;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public content(int id, String img, String info, String price, String uri) {
        super();
        this.id = id;
        this.img = img;
        this.info = info;
        this.price = price;
        this.uri = uri;
    }
    public content() {
        super();

    }



}
