package collaboration.groupe.collaboration.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "file")
public class File {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        @Column(length = 255)
    private String name;

    private String contentType;

    private Long size;

    private Long userId;

}
