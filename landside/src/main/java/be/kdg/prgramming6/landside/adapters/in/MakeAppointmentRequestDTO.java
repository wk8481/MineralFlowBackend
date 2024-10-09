package be.kdg.prgramming6.landside.adapters.in;

import com.fasterxml.jackson.annotation.JsonProperty; // Import the necessary annotation
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class MakeAppointmentRequestDTO {

    @JsonProperty("sellerId") // Optional, use only if you have different names in JSON
    private UUID sellerId; // Assuming sellerId is passed as a String (UUID in string format)

    @JsonProperty("licensePlate") // Optional, use only if you have different names in JSON
    private String licensePlate;

    @JsonProperty("materialType") // Optional, use only if you have different names in JSON
    private String materialType;

    @JsonProperty("appointmentWindowStart") // Optional, use only if you have different names in JSON
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // Ensures correct parsing of date-time
    private LocalDateTime appointmentWindowStart;

    @JsonProperty("appointmentWindowEnd") // Optional, use only if you have different names in JSON
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // Ensures correct parsing of date-time
    private LocalDateTime appointmentWindowEnd;

    // Constructors
    public MakeAppointmentRequestDTO() {}

    public MakeAppointmentRequestDTO(UUID sellerId, String licensePlate, String materialType,
                                     LocalDateTime appointmentWindowStart, LocalDateTime appointmentWindowEnd) {
        this.sellerId = sellerId;
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.appointmentWindowStart = appointmentWindowStart;
        this.appointmentWindowEnd = appointmentWindowEnd;
    }

    // Getters and Setters
    public UUID getSellerId() {
        return sellerId;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public LocalDateTime getAppointmentWindowStart() {
        return appointmentWindowStart;
    }

    public void setAppointmentWindowStart(LocalDateTime appointmentWindowStart) {
        this.appointmentWindowStart = appointmentWindowStart;
    }

    public LocalDateTime getAppointmentWindowEnd() {
        return appointmentWindowEnd;
    }

    public void setAppointmentWindowEnd(LocalDateTime appointmentWindowEnd) {
        this.appointmentWindowEnd = appointmentWindowEnd;
    }
}
