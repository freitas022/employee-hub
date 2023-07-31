package dto;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.freitas.employeehub.dto.EmployeeDTO;

import lombok.Builder;

@Builder
public class EmployeeDTOBuilder {
    
    @Builder.Default
    private Long id = 12345L;
    @Builder.Default
    private String name = "Alex";
    @Builder.Default
    private String email = "alex@example.com";
    @Builder.Default
    private String password = "password";
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("UTC"));
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now(ZoneId.of("UTC"));

    public EmployeeDTO toDto() {
        return new EmployeeDTO(id, name, email, password, createdAt, updatedAt);
    }
}
