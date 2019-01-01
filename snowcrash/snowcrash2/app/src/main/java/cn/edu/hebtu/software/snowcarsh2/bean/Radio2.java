package cn.edu.hebtu.software.snowcarsh2.bean;

public class Radio2 {
    private int id;
    private String title;
    private String info;
    private String date;
    private String img;
    private String uri;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Radio2(int id, String title, String info, String date, String img, String uri) {
        this.id = id;
        this.title = title;
        this.info = info;
        this.date = date;
        this.img = img;
        this.uri = uri;
    }

    public Radio2() {
    }

    @Override
    public String toString() {
        return "Radio2{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", info='" + info + '\'' +
                ", date='" + date + '\'' +
                ", img='" + img + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
