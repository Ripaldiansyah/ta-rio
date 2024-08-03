package ta_rio.UI.Notification;

import javax.swing.JOptionPane;

public class NotificationCustom extends JOptionPane {
    String app;

    public NotificationCustom(String app) {
        this.app = app;
    }

    public void successUpdateNotification() {
        JOptionPane.showMessageDialog(null,
                app + " Berhasil diubah",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void successCreateNotification() {
        JOptionPane.showMessageDialog(null,
                app + " Berhasil dibuat",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void IsnAvailableNotification() {
        JOptionPane.showMessageDialog(null,
                "Masukan nama " + app + " lain",
                "Nama " + app + " telah terdaftar",
                JOptionPane.ERROR_MESSAGE);
    }

    public void errorPasswordNotification() {
        JOptionPane.showMessageDialog(null,
                "Periksa kembali kata sandi",
                "Kata sandi tidak sesuai",
                JOptionPane.ERROR_MESSAGE);
    }

    public void wrongAccount() {
        JOptionPane.showMessageDialog(null,
                "Periksa kembali Username dan kata sandi",
                "Kesalahan",
                JOptionPane.ERROR_MESSAGE);
    }

    public void deleteNotification() {
        JOptionPane.showMessageDialog(null,
                app + " Berhasil dihapus",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void fieldBlankNotification() {
        JOptionPane.showMessageDialog(null,
                "Pastikan semua telah terisi",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

}
