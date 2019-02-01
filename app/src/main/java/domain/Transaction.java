package domain;

/**
 * Created by Ragos Bros on 2/12/2017.
 */
public class Transaction {

    private final long id;
    private final String sender, message, carrierName, phoneNum;

    public Transaction(long id, String sender, String message, String carrierName, String phoneNum) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.carrierName = carrierName;
        this.phoneNum = phoneNum;
    }

    public long getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

}
