package online.goodr.ngo.food;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Artist {
    private String userId;
    private String userName;
    private String food;
    private String userNumber;
    private String userQuantity;
    private String userAddress;



    public Artist(){

    }

    public Artist(String userId, String userName, String food , String userNumber , String userQuantity , String userAddress) {
        this.userId = userId;
        this.userName = userName;
        this.food = food;
        this.userNumber = userNumber;
        this.userQuantity = userQuantity;
        this.userAddress = userAddress;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFood() {
        return food;
    }

    public  String getUserNumber(){
        return userNumber;
    }

    public String getUserQuantity(){
        return userQuantity;
    }

    public String getUserAddress(){
        return userAddress;
    }


}
