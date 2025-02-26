package eci.ieti.safezone.exception;

public class ApplicantException extends Exception{
    public static final String INVALID = "User or Offer not found.";
    public static final String CLOSED_OFFER = "Offer is closed.";
    public static final String NOT_FOUND = "Applicant not found.";

    public ApplicantException(String message) {
        super(message);
    }
}
