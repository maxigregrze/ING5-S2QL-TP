package robinh.s2qltp;
import java.time.LocalDateTime;

public record Order(String customerId, double total, LocalDateTime createdAt) {}
