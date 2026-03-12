<script setup>
import { ref, reactive, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
// import { getProvinces, getProvinceDetail } from "@/api/diachi/diaChiApi";
// import { useUtils } from "@/utils/helper";
// import { registerCustomer } from "@/api/customer/registerApi";
//const { notify, nprogress } = useUtils();
const router = useRouter();
const loading = ref(false);

const form = reactive({
  fullname: "",
  phone: "",
  email: "",
  birthday: "",
  gender: "Nam",
  password: "",
  confirmPassword: "",
  province: "",
  ward: "",
  street: "",
});
const provinces = ref([]);
const combinedWards = ref([]);

onMounted(async () => {
  try {
    //  const res = await getProvinces();
    console.log("DATA TỈNH:", res);
    provinces.value = res;
  } catch (error) {
    console.error("lỗi lấy tỉnh thành:", error);
  }
});
watch(
  () => form.province,
  async (newVal) => {
    combinedWards.value = [];
    form.ward = "";

    if (!newVal) return;

    try {
      const selectedProv = provinces.value.find((p) => p.name === newVal);

      if (selectedProv) {
        // const res = await getProvinceDetail(selectedProv.code);

        const flatList = [];

        res.districts.forEach((district) => {
          district.wards.forEach((ward) => {
            flatList.push({
              fullAddress: `${ward.name}, ${district.name}`,
              code: ward.code,
            });
          });
        });

        combinedWards.value = flatList.sort((a, b) =>
          a.fullAddress.localeCompare(b.fullAddress),
        );
      }
    } catch (error) {
      console.error("lỗi lấy dữ liệu địa chỉ:", error);
    }
  },
);
const dangKyKhachHang = async () => {
  if (form.password !== form.confirmPassword) {
    notify.error("Mật khẩu xác nhận không khớp!");
    return;
  }

  if (!(await notify.confirm("Bạn muốn đăng ký tài khoản này?"))) return;

  nprogress.start();

  try {
    const payload = {
      hoTen: form.fullname,
      soDienThoai: form.phone.replace(/\s/g, ""),
      email: form.email,
      ngaySinh: form.birthday,
      gioiTinh: form.gender === "Nam",
      matKhau: form.password,
      diaChiList: [
        {
          tinhThanh: form.province,
          xaPhuong: form.ward,
          diaChiChiTiet: form.street,
          macDinh: true,
        },
      ],
    };

    const formData = new FormData();

    formData.append(
      "data",
      new Blob([JSON.stringify(payload)], {
        type: "application/json",
      }),
    );

    const res = await registerCustomer(formData);

    notify.success(res.data?.message || "Đăng ký thành công!");

    router.push("/dang-nhap");
  } catch (e) {
    console.error("REGISTER ERROR:", e);

    notify.error(
      e.response?.data?.message ||
        e.response?.data ||
        e.message ||
        "Lỗi hệ thống",
    );
  } finally {
    nprogress.done();
  }
};
const goLogin = () => {
  router.push("/dang-nhap");
};
</script>

<template>
  <div class="login-wrapper">
    <div class="bg-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
    </div>

    <div
      class="container d-flex align-items-center justify-content-center min-vh-100 py-5"
    >
      <div class="register-card">
        <div class="text-center mb-4">
          <h2 class="premium-title">Đăng Ký Tài Khoản</h2>
          <div class="title-divider mx-auto"></div>
          <p class="text-secondary mt-2 small">
            Mở ra hành trình ẩm thực đặc sắc dành riêng cho bạn
          </p>
        </div>

        <form @submit.prevent="dangKyKhachHang" class="premium-form">
          <div class="row g-4">
            <div class="col-md-6 border-end-custom">
              <h5 class="section-title">
                <i class="bi bi-person-lines-fill me-2"></i>Thông tin cá nhân
              </h5>

              <div class="input-group-premium mb-3">
                <label>Họ và tên <span class="required">*</span></label>
                <div class="input-wrapper">
                  <i class="bi bi-person"></i>
                  <input
                    v-model="form.fullname"
                    type="text"
                    placeholder="Nguyễn Văn A"
                    required
                  />
                </div>
              </div>

              <div class="input-group-premium mb-3">
                <label>Số điện thoại <span class="required">*</span></label>
                <div class="input-wrapper">
                  <i class="bi bi-telephone"></i>
                  <input
                    v-model="form.phone"
                    type="tel"
                    placeholder="0987 654 321"
                    required
                  />
                </div>
              </div>

              <div class="input-group-premium mb-3">
                <label>Email <span class="required">*</span></label>
                <div class="input-wrapper">
                  <i class="bi bi-envelope"></i>
                  <input
                    v-model="form.email"
                    type="email"
                    placeholder="example@gmail.com"
                    required
                  />
                </div>
              </div>
              <div class="col-sm-5">
                <div class="input-group-premium mb-3">
                  <label>Giới tính</label>
                  <div class="gender-selector">
                    <label class="gender-option">
                      <input type="radio" value="Nam" v-model="form.gender" />
                      <span class="custom-radio">Nam</span>
                    </label>
                    <label class="gender-option">
                      <input type="radio" value="Nữ" v-model="form.gender" />
                      <span class="custom-radio">Nữ</span>
                    </label>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-7">
                  <div class="input-group-premium mb-3">
                    <label>Ngày sinh <span class="required">*</span></label>
                    <div class="input-wrapper">
                      <i class="bi bi-calendar-event"></i>
                      <input v-model="form.birthday" type="date" required />
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-md-6">
              <h5 class="section-title">
                <i class="bi bi-geo-alt me-2"></i>Địa chỉ nhận hàng
              </h5>

              <div class="row g-3 mb-3">
                <div class="col-md-6">
                  <label class="small fw-semibold">Tỉnh / Thành phố</label>
                  <div class="select-wrapper">
                    <select
                      v-model="form.province"
                      class="form-control-premium"
                    >
                      <option value="">Chọn tỉnh / thành</option>
                      <option
                        v-for="p in provinces"
                        :key="p.code"
                        :value="p.name"
                      >
                        {{ p.name }}
                      </option>
                    </select>
                  </div>
                </div>

                <div class="col-md-6">
                  <label class="small fw-semibold">Phường / Xã</label>
                  <div class="select-wrapper">
                    <select
                      v-model="form.ward"
                      class="form-control-premium"
                      :disabled="!form.province"
                    >
                      <option value="">Chọn phường / xã</option>
                      <option
                        v-for="item in combinedWards"
                        :key="item.code"
                        :value="item.fullAddress"
                      >
                        {{ item.fullAddress }}
                      </option>
                    </select>
                  </div>
                </div>
              </div>

              <div class="input-group-premium mb-4">
                <label>Địa chỉ cụ thể</label>
                <div class="input-wrapper">
                  <i class="bi bi-house"></i>
                  <input
                    v-model="form.street"
                    type="text"
                    placeholder="Số nhà, tên đường"
                  />
                </div>
              </div>

              <div class="security-section">
                <h5 class="section-title">
                  <i class="bi bi-shield-lock me-2"></i>Bảo mật tài khoản
                </h5>

                <div class="input-group-premium mb-3">
                  <label>Mật khẩu <span class="required">*</span></label>
                  <div class="input-wrapper">
                    <i class="bi bi-lock"></i>
                    <input
                      v-model="form.password"
                      type="password"
                      placeholder="••••••••"
                      required
                    />
                  </div>
                </div>

                <div class="input-group-premium mb-3">
                  <label
                    >Xác nhận mật khẩu <span class="required">*</span></label
                  >
                  <div class="input-wrapper">
                    <i class="bi bi-shield-check"></i>
                    <input
                      v-model="form.confirmPassword"
                      type="password"
                      placeholder="••••••••"
                      required
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="footer-actions mt-5 text-center">
            <button class="btn-premium" type="submit" :disabled="loading">
              <span v-if="!loading"
                >Hoàn tất <i class="bi bi-arrow-right ms-2"></i
              ></span>
              <span v-else>
                <span class="spinner-border spinner-border-sm me-2"></span>ĐANG
                XỬ LÝ...
              </span>
            </button>
            <p class="mt-4 text-muted small">
              Đã có tài khoản?
              <span @click="goLogin" class="login-link">Đăng nhập ngay</span>
            </p>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.security-section {
  margin-top: 50px;
}

/* Toàn bộ style của bạn được giữ nguyên */
@import url("https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700;800&display=swap");

.login-wrapper {
  --primary-red: #8b1e1e;
  --bg-soft: #f8f9fa;
  font-family: "Plus Jakarta Sans", sans-serif;
  min-height: 100vh;
  background: var(--bg-soft);
  background-image:
    linear-gradient(135deg, rgba(255, 255, 255, 0.9), rgba(245, 245, 245, 0.8)),
    url("@/assets/images/bg-ao-2.jpg");
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
  position: relative;
  overflow-x: hidden;
}

.bg-decoration .circle {
  position: absolute;
  border-radius: 50%;
  filter: blur(100px);
  z-index: 0;
  opacity: 0.5;
}
.circle-1 {
  width: 400px;
  height: 400px;
  background: rgba(139, 30, 30, 0.15);
  top: -10%;
  right: -5%;
}
.circle-2 {
  width: 350px;
  height: 350px;
  background: rgba(197, 160, 89, 0.1);
  bottom: -5%;
  left: -5%;
}

.register-card {
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(25px);
  width: 100%;
  max-width: 950px;
  padding: 40px;
  border-radius: 32px;
  border: 1px solid rgba(255, 255, 255, 0.6);
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.12);
  z-index: 1;
}

.main-logo {
  width: 260px;
  display: block;
  margin: 0 auto -15px auto;
  filter: drop-shadow(0 5px 15px rgba(0, 0, 0, 0.08));
}

.premium-title {
  font-weight: 800;
  font-size: 30px;
  letter-spacing: -0.5px;
  background: linear-gradient(135deg, #1a1a1a, #8b1e1e);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.title-divider {
  width: 50px;
  height: 4px;
  background: var(--primary-red);
  border-radius: 10px;
}

.section-title {
  font-size: 0.9rem;
  font-weight: 700;
  text-transform: uppercase;
  color: var(--primary-red);
  margin-bottom: 1.5rem;
  letter-spacing: 0.5px;
}

.input-group-premium label {
  font-size: 0.8rem;
  font-weight: 700;
  color: #444;
  margin-bottom: 6px;
  display: block;
}

.input-wrapper {
  position: relative;
}
.input-wrapper i {
  position: absolute;
  left: 16px;
  top: 50%;
  transform: translateY(-50%);
  color: #999;
}

.input-wrapper input,
.form-control-premium {
  width: 100%;
  padding: 12px 16px 12px 45px;
  border-radius: 14px;
  border: 1.5px solid #ececec;
  background: white;
  transition: 0.3s;
  font-size: 0.95rem;
}

.form-control-premium {
  padding-left: 15px;
  margin-bottom: 12px;
}

.input-wrapper input:focus,
.form-control-premium:focus {
  border-color: var(--primary-red);
  box-shadow: 0 0 0 4px rgba(139, 30, 30, 0.05);
  outline: none;
}

.gender-selector {
  display: flex;
  gap: 8px;
}
.gender-option {
  flex: 1;
  cursor: pointer;
}
.gender-option input {
  display: none;
}
.custom-radio {
  display: block;
  text-align: center;
  padding: 11px;
  border-radius: 12px;
  background: white;
  border: 1.5px solid #ececec;
  font-weight: 600;
  font-size: 0.9rem;
  transition: 0.3s;
}
.gender-option input:checked + .custom-radio {
  background: var(--primary-red);
  color: white;
  border-color: var(--primary-red);
}

.btn-premium {
  background: linear-gradient(135deg, #8b1e1e, #d43f3f);
  color: white;
  border: none;
  padding: 16px 45px;
  border-radius: 16px;
  font-weight: 700;
  transition: 0.3s;
  box-shadow: 0 10px 20px rgba(139, 30, 30, 0.2);
}

.btn-premium:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 15px 30px rgba(139, 30, 30, 0.3);
}

.login-link {
  color: var(--primary-red);
  font-weight: 700;
  cursor: pointer;
}
.login-link:hover {
  text-decoration: underline;
}

.border-end-custom {
  border-right: 1px solid rgba(0, 0, 0, 0.05);
}

.required {
  color: var(--primary-red);
}

@media (max-width: 768px) {
  .register-card {
    padding: 30px 20px;
  }
  .border-end-custom {
    border-right: none;
    border-bottom: 1px solid #eee;
    margin-bottom: 20px;
    padding-bottom: 20px;
  }
}
</style>
