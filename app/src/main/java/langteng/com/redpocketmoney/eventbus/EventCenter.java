package langteng.com.redpocketmoney.eventbus;

public class EventCenter {

    public Object value;
    public Object value1;
    public String opreatId;

    public EventCenter(String opreatId, Object value) {
        this.value = value;
        this.opreatId = opreatId;
    }

    public EventCenter(String opreatId, Object value, Object value1) {
        this.value = value;
        this.value1 = value1;
        this.opreatId = opreatId;
    }

}
