package couriermanagement;

public class Courier {

    String courierId;
    String senderName;
    String senderAddress;
    String senderPhone;
    String receiverName;
    String receiverAddress;
    String receiverPhone;
    String courierType;
    String weight;
    String bookingDate;
    String deliveryStatus;

    public Courier(String courierId, String senderName,
                   String senderAddress, String senderPhone,
                   String receiverName, String receiverAddress,
                   String receiverPhone, String courierType,
                   String weight, String bookingDate,
                   String deliveryStatus) {

        this.courierId = courierId;
        this.senderName = senderName;
        this.senderAddress = senderAddress;
        this.senderPhone = senderPhone;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverPhone = receiverPhone;
        this.courierType = courierType;
        this.weight = weight;
        this.bookingDate = bookingDate;
        this.deliveryStatus = deliveryStatus;
    }
}
