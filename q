[1mdiff --git a/src/main/java/com/example/BusTicketBookingBackend/service/VeXeService.java b/src/main/java/com/example/BusTicketBookingBackend/service/VeXeService.java[m
[1mindex ff2742a..b0f1e89 100644[m
[1m--- a/src/main/java/com/example/BusTicketBookingBackend/service/VeXeService.java[m
[1m+++ b/src/main/java/com/example/BusTicketBookingBackend/service/VeXeService.java[m
[36m@@ -18,4 +18,5 @@[m [mpublic interface VeXeService {[m
     Integer huyVeXe(Integer maVeXe);[m
 [m
     void huyVeTheoChuyenXe(Integer chuyenXeId);[m
[32m+[m
 }[m
[1mdiff --git a/src/main/java/com/example/BusTicketBookingBackend/service/impl/VeXeServiceImpl.java b/src/main/java/com/example/BusTicketBookingBackend/service/impl/VeXeServiceImpl.java[m
[1mindex 6b0f4fb..477230e 100644[m
[1m--- a/src/main/java/com/example/BusTicketBookingBackend/service/impl/VeXeServiceImpl.java[m
[1m+++ b/src/main/java/com/example/BusTicketBookingBackend/service/impl/VeXeServiceImpl.java[m
[36m@@ -107,10 +107,11 @@[m [mpublic class VeXeServiceImpl implements VeXeService {[m
 [m
             veXe.setTrangThaiVe(TrangThaiVe.CANCELED);[m
             veXeRepository.save(veXe);[m
[31m-[m
         }[m
     }[m
 [m
[32m+[m
[32m+[m
     @Override[m
     public TrangThaiDonDat kiemTraTrangDonDat(Integer maDonDat){[m
         List<Vexe> veXes = veXeRepository.findAllByDonDatVeWithIDMaDonDat[m
[36m@@ -175,5 +176,6 @@[m [mpublic class VeXeServiceImpl implements VeXeService {[m
         }[m
     }[m
 [m
[32m+[m
 }[m
 [m
