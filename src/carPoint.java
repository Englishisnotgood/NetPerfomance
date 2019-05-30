public class carPoint {
    private float x,y;
    private int id;
    private float departTime;
    carPoint(){
        this.x = 0;
        this.y = 0;
        this.id = -1;
        this.departTime = -1;
    }
    carPoint(int id, float x, float y, float departTime){
        this.x = x;
        this.y = y;
        this.id = id;
        this.departTime = departTime;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDepartTime() {
        return departTime;
    }

    public void setDepartTime(float departTime) {
        this.departTime = departTime;
    }

    //汽车移动方法
    public void move(float toX, float toY){
        this.x = toX;
        this.y = toY;
    }
}
