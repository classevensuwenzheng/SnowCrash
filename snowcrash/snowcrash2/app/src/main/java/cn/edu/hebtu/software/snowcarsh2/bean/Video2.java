package cn.edu.hebtu.software.snowcarsh2.bean;

public class Video2 {
    private int id;
    private String title;
    private String info;
    private String text;
    private String uri;
    private String img;
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
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    @Override
    public String toString() {
        return "video [id=" + id + ", title=" + title + ", info=" + info + ", text=" + text + ", uri=" + uri + ", img="
                + img + "]";
    }
    public Video2(int id, String title, String info, String text, String uri, String img) {
        super();
        this.id = id;
        this.title = title;
        this.info = info;
        this.text = text;
        this.uri = uri;
        this.img = img;
    }
    public Video2() {
        super();

    }



}
