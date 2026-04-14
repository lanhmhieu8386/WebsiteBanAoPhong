<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";

import {
  getChiTietBySanPhamId,
  getSanPhamChiTietById,
  addSanPhamChiTiet,
  updateSanPhamChiTiet,
} from "@/api/admin/sanphamchitiet/sanPhamChiTietApi";
import { getAllDanhMuc } from "@/api/admin/danhmuc/danhMucApi";
import { getAllThuongHieu } from "@/api/admin/thuonghieu/ThuongHieuApi";
import { getAllChatLieu } from "@/api/admin/chatlieu/ChatLieuApi";
import { getAllMauSac } from "@/api/admin/mausac/MauSacApi";
import { getAllKichCo } from "@/api/admin/kichco/KichCoApi";
import { updateSanPham, getSanPhamById } from "@/api/admin/sanpham/sanPhamApi";

const showEdit = ref(false);
const editId = ref(null);

const router = useRouter();
const route = useRoute();

const sanPham = ref(null);
const chiTietList = ref([]);

const danhMucList = ref([]);
const thuongHieuList = ref([]);
const chatLieuList = ref([]);
const mauSacList = ref([]);
const kichCoList = ref([]);

const formEdit = ref({
  maSanPhamChiTiet: "",
  idMauSac: "",
  idKichCo: "",
  gia: 0,
  soLuongTon: 0,
  trangThai: true,
  hinh: "",
});

const quayLai = () => {
  router.push("/admin/san-pham");
};

const loadSanPham = async () => {
  try {
    const id = Number(route.params.id);
    if (!id) return;

    const spRes = await getSanPhamById(id);
    const spData = spRes.data?.data || spRes.data;

    sanPham.value = {
      id: spData.id,
      maSanPham: spData.maSanPham,
      ten: spData.tenSanPham,
      idDanhMuc: spData.idDanhMuc || spData.danhMuc?.id,
      idThuongHieu: spData.idThuongHieu || spData.thuongHieu?.id,
      idChatLieu: spData.idChatLieu || spData.chatLieu?.id,
      moTa: spData.moTa,
      trangThai: spData.trangThai,
    };

    const res = await getChiTietBySanPhamId(id);
    const data = res.data?.data || res.data || [];

    chiTietList.value = data.map((ct) => ({
      id: ct.id,
      maSanPhamChiTiet: ct.maSanPhamChiTiet || "",
      tenMauSac: ct.tenMauSac,
      tenKichCo: ct.tenKichCo,
      gia: ct.gia,
      soLuongTon: ct.soLuong ?? ct.soLuongTon ?? 0,
      trangThai: ct.trangThai ?? true,
      hinh: ct.urlAnh || "https://via.placeholder.com/60",
    }));
  } catch (error) {
    console.error("Lỗi load sản phẩm:", error);
  }
};

const loadDanhMuc = async () => {
  try {
    const res = await getAllDanhMuc();
    danhMucList.value = res.data || [];
  } catch (e) {
    console.error("Lỗi danh mục", e);
  }
};

const loadThuongHieu = async () => {
  try {
    const res = await getAllThuongHieu();
    thuongHieuList.value = res.data || [];
  } catch (e) {
    console.error("Lỗi thương hiệu", e);
  }
};

const loadChatLieu = async () => {
  try {
    const res = await getAllChatLieu();
    chatLieuList.value = res.data || [];
  } catch (e) {
    console.error("Lỗi chất liệu", e);
  }
};

const loadMauSac = async () => {
  try {
    const res = await getAllMauSac();
    mauSacList.value = res.data || [];
  } catch (e) {
    console.error("Lỗi màu sắc", e);
  }
};

const loadKichCo = async () => {
  try {
    const res = await getAllKichCo();
    kichCoList.value = res.data || [];
  } catch (e) {
    console.error("Lỗi kích cỡ", e);
  }
};

const saveSanPham = async () => {
  try {
    if (
      !sanPham.value.ten ||
      !sanPham.value.idDanhMuc ||
      !sanPham.value.idThuongHieu ||
      !sanPham.value.idChatLieu
    ) {
      alert("Vui lòng nhập đầy đủ thông tin");
      return;
    }

    const data = {
      maSanPham: sanPham.value.maSanPham,
      tenSanPham: sanPham.value.ten,
      idDanhMuc: Number(sanPham.value.idDanhMuc),
      idThuongHieu: Number(sanPham.value.idThuongHieu),
      idChatLieu: Number(sanPham.value.idChatLieu),
      moTa: sanPham.value.moTa,
      trangThai: sanPham.value.trangThai ?? true,
    };

    await updateSanPham(sanPham.value.id, data);
    alert("Cập nhật thành công");
    await loadSanPham();
  } catch (error) {
    console.error(error);
    alert("Lỗi cập nhật sản phẩm");
  }
};

const addVariant = () => {
  editId.value = null;
  formEdit.value = {
    maSanPhamChiTiet: "",
    idMauSac: "",
    idKichCo: "",
    gia: 0,
    soLuongTon: 0,
    trangThai: true,
    hinh: "",
  };
  showEdit.value = true;
};

const editVariant = async (ct) => {
  editId.value = ct.id;

  try {
    const res = await getSanPhamChiTietById(ct.id);
    const data = res.data?.data || res.data;

    const mau = mauSacList.value.find((m) => m.tenMauSac === data.tenMauSac);
    const size = kichCoList.value.find((k) => k.tenKichCo === data.tenKichCo);

    formEdit.value = {
      maSanPhamChiTiet: data.maSanPhamChiTiet || "",
      idMauSac: mau?.id || "",
      idKichCo: size?.id || "",
      gia: data.gia || 0,
      soLuongTon: data.soLuong || 0,
      trangThai: data.trangThai ?? true,
      hinh: data.urlAnh || "",
    };

    showEdit.value = true;
  } catch (error) {
    console.error(error);
    alert("Không load được chi tiết biến thể");
  }
};

const saveEdit = async () => {
  try {
    if (!formEdit.value.idMauSac || !formEdit.value.idKichCo) {
      alert("Vui lòng chọn màu sắc và kích cỡ");
      return;
    }

    const data = {
      maSanPhamChiTiet: formEdit.value.maSanPhamChiTiet,
      soLuongTon: Number(formEdit.value.soLuongTon),
      gia: Number(formEdit.value.gia),
      trangThai: formEdit.value.trangThai,
      idSanPham: sanPham.value.id,
      idMauSac: Number(formEdit.value.idMauSac),
      idKichCo: Number(formEdit.value.idKichCo),
    };

    if (editId.value) {
      await updateSanPhamChiTiet(editId.value, data);
    } else {
      await addSanPhamChiTiet(data);
    }

    alert(
      editId.value
        ? "Cập nhật biến thể thành công"
        : "Thêm biến thể thành công",
    );
    showEdit.value = false;
    await loadSanPham();
  } catch (error) {
    console.error(error);
    alert("Lỗi khi lưu biến thể");
  }
};

const onFileChange = (e) => {
  const file = e.target.files[0];
  if (file) {
    formEdit.value.hinh = URL.createObjectURL(file);
  }
};

onMounted(async () => {
  await loadDanhMuc();
  await loadThuongHieu();
  await loadChatLieu();
  await loadMauSac();
  await loadKichCo();
  await loadSanPham();
});
</script>

<template>
  <div class="box" v-if="sanPham">
    <div class="header">
      <h2>Chi tiết sản phẩm</h2>
      <button class="btn-back" @click="quayLai">← Quay lại</button>
    </div>

    <div class="info">
      <div class="form-group">
        <label>Tên sản phẩm</label>
        <input v-model="sanPham.ten" />
      </div>

      <div class="form-group">
        <label>Danh mục</label>
        <select v-model="sanPham.idDanhMuc">
          <option value="">Chọn danh mục</option>
          <option v-for="dm in danhMucList" :key="dm.id" :value="dm.id">
            {{ dm.tenDanhMuc }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label>Chất liệu</label>
        <select v-model="sanPham.idChatLieu">
          <option value="">Chọn chất liệu</option>
          <option v-for="cl in chatLieuList" :key="cl.id" :value="cl.id">
            {{ cl.tenChatLieu }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label>Thương hiệu</label>
        <select v-model="sanPham.idThuongHieu">
          <option value="">Chọn thương hiệu</option>
          <option v-for="th in thuongHieuList" :key="th.id" :value="th.id">
            {{ th.tenThuongHieu }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label>Mô tả</label>
        <textarea v-model="sanPham.moTa"></textarea>
      </div>

      <button class="btn-save" @click="saveSanPham">Lưu sản phẩm</button>
    </div>

    <div class="variant-header">
      <h3>Biến thể sản phẩm</h3>
      <button class="btn-save" @click="addVariant">+ Thêm biến thể</button>
    </div>

    <table class="table">
      <thead>
        <tr>
          <th>Ảnh</th>
          <th>Màu</th>
          <th>Size</th>
          <th>Giá</th>
          <th>Số lượng</th>
          <th>Thao tác</th>
        </tr>
      </thead>

      <tbody v-if="chiTietList.length > 0">
        <tr v-for="ct in chiTietList" :key="ct.id">
          <td><img :src="ct.hinh" class="img" /></td>
          <td>{{ ct.tenMauSac }}</td>
          <td>{{ ct.tenKichCo }}</td>
          <td>{{ ct.gia ? Number(ct.gia).toLocaleString() + " đ" : "-" }}</td>
          <td>{{ ct.soLuongTon }}</td>
          <td>
            <button class="btn-edit" @click="editVariant(ct)">Sửa</button>
          </td>
        </tr>
      </tbody>

      <tbody v-else>
        <tr>
          <td colspan="6">Chưa có biến thể sản phẩm</td>
        </tr>
      </tbody>
    </table>

    <div v-if="showEdit" class="modal-overlay">
      <div class="modal-box">
        <h3>
          {{ editId ? "Sửa biến thể sản phẩm" : "Thêm biến thể sản phẩm" }}
        </h3>

        <div class="modal-form">
          <div class="img-upload">
            <img
              :src="formEdit.hinh || 'https://via.placeholder.com/80'"
              class="preview"
            />
            <input type="file" @change="onFileChange" />
            <small
              >Ảnh hiện chỉ preview, chưa lưu vào DB vì backend SPCT chưa nhận
              urlAnh/idHinhAnh</small
            >
          </div>

          <div class="form-row">
            <label>Mã sản phẩm chi tiết</label>
            <input v-model="formEdit.maSanPhamChiTiet" />
          </div>

          <div class="form-row">
            <label>Màu</label>
            <select v-model="formEdit.idMauSac">
              <option value="">Chọn màu sắc</option>
              <option v-for="ms in mauSacList" :key="ms.id" :value="ms.id">
                {{ ms.tenMauSac }}
              </option>
            </select>
          </div>

          <div class="form-row">
            <label>Size</label>
            <select v-model="formEdit.idKichCo">
              <option value="">Chọn kích cỡ</option>
              <option v-for="kc in kichCoList" :key="kc.id" :value="kc.id">
                {{ kc.tenKichCo }}
              </option>
            </select>
          </div>

          <div class="form-row">
            <label>Giá</label>
            <input type="number" v-model="formEdit.gia" />
          </div>

          <div class="form-row">
            <label>Số lượng</label>
            <input type="number" v-model="formEdit.soLuongTon" />
          </div>

          <div class="form-row">
            <label>Trạng thái</label>
            <select v-model="formEdit.trangThai">
              <option :value="true">Hoạt động</option>
              <option :value="false">Ngừng</option>
            </select>
          </div>

          <div class="modal-actions">
            <button class="btn-cancel" @click="showEdit = false">Hủy</button>
            <button class="btn-save" @click="saveEdit">Lưu</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.box {
  background: white;
  padding: 20px;
  border-radius: 10px;
}
.info {
  max-width: 500px;
}
.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 14px;
}
.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  max-width: 500px;
  height: 36px;
  border-radius: 6px;
  border: 1px solid #ddd;
  padding: 5px 10px;
}
textarea {
  height: 80px;
}
.btn-save {
  background: #da1432;
  color: white;
  border: none;
  padding: 10px 16px;
  border-radius: 8px;
  cursor: pointer;
}
.table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}
.table th {
  background: #e9f2fb;
  padding: 10px;
}
.table td {
  padding: 10px;
  border-bottom: 1px solid #eee;
  text-align: center;
}
.img {
  width: 50px;
  height: 50px;
  object-fit: cover;
  border-radius: 6px;
}
.btn-edit {
  background: #f4f6f8;
  border: none;
  padding: 6px 10px;
  border-radius: 6px;
  cursor: pointer;
}
.btn-edit:hover {
  background: #e2e6ea;
}
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
.modal-box {
  background: white;
  padding: 20px;
  border-radius: 10px;
  width: 380px;
}
.modal-form {
  display: flex;
  flex-direction: column;
  gap: 12px;
}
.form-row {
  display: flex;
  flex-direction: column;
  gap: 5px;
}
.form-row input,
.form-row select {
  height: 36px;
  border-radius: 6px;
  border: 1px solid #ddd;
  padding: 5px 10px;
}
.img-upload {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.preview {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 6px;
  margin-bottom: 6px;
}
.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 15px;
}
.btn-cancel {
  background: #ddd;
  border: none;
  padding: 8px 14px;
  border-radius: 6px;
  cursor: pointer;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}
.btn-back {
  background: #da1432;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 8px;
  cursor: pointer;
}
.variant-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}
</style>
