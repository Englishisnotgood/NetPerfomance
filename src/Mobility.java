//NS2中mobility数据类，一行表示一次移动
public class Mobility {
    private float time;
    private int id;
    private float x;
    private float y;
    public Mobility(){
        this.time = -1;
        this.id = -1;
        this.x = -1;
        this.y = -1;
    }
    public Mobility(float time, int id, float x, float y){
        this.time = time;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
