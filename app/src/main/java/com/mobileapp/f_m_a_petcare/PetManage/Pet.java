package com.mobileapp.f_m_a_petcare.PetManage;

import java.io.Serializable;
import java.util.ArrayList;

public class Pet implements Serializable {
    private String id;
    private String tenThu;
    private String tenGiong;
    private String gioiTinh;
    private String ngaySinh;
    private String mauSac;
    private String canNang;
    private String chieuCao;
    private ArrayList<String> imagePaths;
    private String imagePath;

    public String getTenGiong() {
        return tenGiong;
    }

    public void setTenGiong(String tenGiong) {
        this.tenGiong = tenGiong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenThu() {
        return tenThu;
    }

    public void setTenThu(String tenThu) {
        this.tenThu = tenThu;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getChieuCao() {
        return chieuCao;
    }

    public void setChieuCao(String chieuCao) {
        this.chieuCao = chieuCao;
    }

    public String getCanNang() {
        return canNang;
    }

    public void setCanNang(String canNang) {
        this.canNang = canNang;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        if (this.imagePaths == null) {
            this.imagePaths = new ArrayList<>();
        }
        this.imagePaths.clear();
        if (imagePath != null) {
            this.imagePaths.add(imagePath);
        }
    }

    public ArrayList<String> getImagePaths() {
        return imagePaths != null ? imagePaths : new ArrayList<>();
    }

    public void setImagePaths(ArrayList<String> imagePaths) {
        this.imagePaths = imagePaths;
        if (imagePaths != null && !imagePaths.isEmpty()) {
            this.imagePath = imagePaths.get(0);
        } else {
            this.imagePath = null;
        }
    }

    // Phương thức tiện ích để thêm một hình ảnh mới
    public void addImagePath(String imagePath) {
        if (this.imagePaths == null) {
            this.imagePaths = new ArrayList<>();
        }
        this.imagePaths.add(imagePath);
        if (this.imagePath == null) {
            this.imagePath = imagePath;
        }
    }

    public Pet(String id, String tenThu, String tenGiong, String gioiTinh, String ngaySinh, String mauSac, String canNang, String chieuCao, String imagePath) {
        this.id = id;
        this.tenThu = tenThu;
        this.tenGiong = tenGiong;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.mauSac = mauSac;
        this.canNang = canNang;
        this.chieuCao = chieuCao;
        this.imagePath = imagePath;
        this.imagePaths = new ArrayList<>();
        if (imagePath != null) {
            this.imagePaths.add(imagePath);
        }
    }

    @Override
    public String toString() {
        return this.tenThu; // hoặc bất kỳ thông tin nào bạn muốn hiển thị
    }
}
