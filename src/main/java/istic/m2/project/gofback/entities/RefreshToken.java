package istic.m2.project.gofback.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Getter
@Setter
@Table(name = "refresh_token")
@AttributeOverride(name = "id", column = @Column(name = "refresh_token_id"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RefreshToken extends Auditable<String> {
    @OneToOne
    @JoinColumn(name = "cavalier_id", referencedColumnName = "cavalier_id", nullable = false)
    private Cavalier cavalier;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;
}
