package cn.edu.hebtu.software.snowcarsh2.bean;

public class DataRead {
    private int id;
    private String title;
    private String info;
    private String img;
    private String uri;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DataRead(int id, String title, String info, String img, String uri, String date) {
        this.id = id;
        this.title = title;
        this.info = info;
        this.img = img;
        this.uri = uri;
        this.date = date;
    }

    public DataRead() {
    }
}
