package eci.ieti.safezone.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Applicant {
    private String id;
    private String offerId;
    private boolean chosen;
    private String userId;
    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
