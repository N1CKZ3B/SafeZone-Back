package eci.ieti.safezone.exception;

public class OfferException extends Exception{
    public static final String NOT_FOUND = "Offer not found";

    public OfferException(String message) {
        super(message);
    }
}
