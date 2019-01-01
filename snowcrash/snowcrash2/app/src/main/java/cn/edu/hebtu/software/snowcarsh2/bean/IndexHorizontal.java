package cn.edu.hebtu.software.snowcarsh2.bean;

public class IndexHorizontal {
    /*
     * 首页横向列表
     * id        id
     * image     图片
     * introduce 简介
     * title     标题
     * */
    private int id;
    private String title;
    private String introduce;
    private String imgUrl;
    private String linkUrl;
    private String time;

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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public IndexHorizontal(int id, String title, String introduce, String imgUrl, String linkUrl, String time) {
        this.id = id;
        this.title = title;
        this.introduce = introduce;
        this.imgUrl = imgUrl;
        this.linkUrl = linkUrl;
        this.time = time;
    }

    public IndexHorizontal() {
    }

    @Override
    public String toString() {
        return "IndexHorizontal{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", introduce='" + introduce + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
