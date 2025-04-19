package com.example.BusTicketBookingBackend.models;

import com.example.BusTicketBookingBackend.enums.KieuThanhToan;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "dondatve")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonDatVe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "id_nguoiDung", referencedColumnName = "id")
    NguoiDung nguoiDung;

    @Column(name = "thoigiandat")
    LocalDateTime thoiGianDat;

    @Column(name = "tongtien")
    int tongTien;

    @Enumerated(EnumType.STRING)
    @Column(name = "kieuthanhtoan")
    KieuThanhToan kieuThanhToan;

    @Column(name = "trangthaithanhtoan")
    int trangThaiThanhToan;

    @Column(name = "khuhoi")
    int khuHoi;

    @Column(name = "ghichu", columnDefinition = "TEXT")
    String ghiChu;

    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
