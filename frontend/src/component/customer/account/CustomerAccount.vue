<script setup>
import { ref, onMounted } from "vue";
import { getThongTin, updateThongTin } from "@/api/customer/thongTinApi";
import { donHangCuaToi, huyDonHang } from "@/api/customer/hoaDonCustomerApi";
import { hoaDonChiTiet } from "@/api/customer/hoaDonCustomerApi";
const activeTab = ref("info"); // info hoặc orders
import aophong1 from "@/assets/images/aophong1.png";
import aophong2 from "@/assets/images/aophong2.png";
///////////////////////// ĐƠN HÀNG
const orders = ref([]);
const loadOrders = async () => {
  try {
    const res = await donHangCuaToi();
    orders.value = res.data;
  } catch (e) {
    console.error(e);
  }
};

onMounted(() => {
  loadOrders();
});
const images = [aophong1, aophong2];

const getRandomImage = () => {
  return images[Math.floor(Math.random() * images.length)];
};

const selectedOrder = ref(null);
const orderDetails = ref([]);
const isDrawerOpen = ref(false);

const openDetail = async (order) => {
  try {
    const res = await hoaDonChiTiet(order.id);
    orderDetails.value = res.data.items;
    selectedOrder.value = order;
    isDrawerOpen.value = true;
  } catch (e) {
    console.error("Error loading details", e);
    alert("COULD NOT LOAD DETAILS.");
  }
};

const closeDrawer = () => {
  isDrawerOpen.value = false;
};

const customer = ref({
  tenKhachHang: "",
  soDienThoai: "",
  email: "",
  ngaySinh: "",
});

const saveInfo = async () => {
  const ok = confirm("CONFIRM UPDATING YOUR PROFILE?");
  if (!ok) return;

  try {
    const email = localStorage.getItem("email");
    if (!customer.value.tenKhachHang) {
      alert("NAME CANNOT BE EMPTY.");
      return;
    }
    const res = await updateThongTin(customer.value, email);
    customer.value = res.data;
    alert("PROFILE UPDATED.");
  } catch (e) {
    console.error(e);
    alert("UPDATE FAILED.");
  }
};

onMounted(async () => {
  try {
    const res = await getThongTin();
    customer.value = res.data;
  } catch (e) {
    console.error("Error loading info", e);
  }
});

const handleCancelOrder = async () => {
  if (!selectedOrder.value) return;

  const reason = prompt("LÝ DO HỦY ĐƠN HÀNG:");
  if (reason === null) return; // Nhấn cancel ở prompt

  try {
    // Gọi API hủy đơn
    await huyDonHang(selectedOrder.value.id, reason);
    alert("HỦY ĐƠN HÀNG THÀNH CÔNG.");

    isDrawerOpen.value = false; // Đóng panel
    loadOrders(); // Load lại danh sách đơn để cập nhật trạng thái mới
  } catch (e) {
    console.error(e);
    alert("HỦY ĐƠN THẤT BẠI. VUI LÒNG THỬ LẠI.");
  }
};
</script>

<template>
  <div class="studio-profile-wrapper">
    <div class="bg-blur-element"></div>

    <div class="container py-5">
      <div class="profile-layout shadow-sm">
        <aside class="profile-sidebar">
          <div class="profile-brand">
            <span class="brand-text">CHIWIWIS</span>
            <div class="status-indicator"></div>
          </div>

          <div class="user-id-card">
            <div class="avatar-wrapper">
              <img
                src="https://api.dicebear.com/7.x/avataaars/svg?seed=Felix"
                class="avatar-img"
              />
              <button class="avatar-upload-btn">
                <i class="bi bi-plus"></i>
              </button>
            </div>
            <div class="user-meta">
              <h4 class="user-name">
                {{ customer.tenKhachHang || "STUDIO USER" }}
              </h4>
              <span class="membership-tag">GOLD MEMBER</span>
            </div>
          </div>

          <nav class="sidebar-nav">
            <button
              :class="['nav-link', { active: activeTab === 'info' }]"
              @click="activeTab = 'info'"
            >
              <span class="nav-num">01</span> THÔNG TIN
            </button>
            <button
              :class="['nav-link', { active: activeTab === 'orders' }]"
              @click="activeTab = 'orders'"
            >
              <span class="nav-num">02</span> LỊCH SỬ
            </button>

            <div class="nav-spacer"></div>

            <button
              class="nav-link security-link"
              @click="alert('Feature coming soon')"
            >
              <i class="bi bi-shield-lock-fill me-2"></i> BẢO MẬT
            </button>
          </nav>
        </aside>

        <main class="profile-content">
          <transition name="fade-slide" mode="out-in">
            <div v-if="activeTab === 'info'" key="info" class="tab-pane">
              <div class="pane-header">
                <h2 class="pane-title">THÔNG TIN CÁ NHÂN</h2>
                <div class="pane-line"></div>
              </div>

              <div class="bento-grid-2026">
                <div class="bento-item span-2">
                  <label class="studio-label">HỌ TÊN</label>
                  <input
                    type="text"
                    v-model="customer.tenKhachHang"
                    class="studio-input"
                    placeholder="ENTER NAME..."
                  />
                  <div class="input-focus-line"></div>
                </div>

                <div class="bento-item">
                  <label class="studio-label">SỐ ĐIỆN THOẠI</label>
                  <input
                    type="tel"
                    v-model="customer.soDienThoai"
                    class="studio-input"
                    placeholder="+84 ..."
                  />
                  <div class="input-focus-line"></div>
                </div>

                <div class="bento-item">
                  <label class="studio-label">EMAIL </label>
                  <input
                    type="email"
                    v-model="customer.email"
                    class="studio-input readonly"
                    readonly
                  />
                </div>

                <div class="bento-item">
                  <label class="studio-label">NGÀY SINH</label>
                  <input
                    type="date"
                    :value="customer.ngaySinh?.substring(0, 10)"
                    class="studio-input"
                  />
                  <div class="input-focus-line"></div>
                </div>

                <div class="bento-item empty-bento">
                  <div class="studio-watermark">CWW</div>
                </div>
              </div>

              <div class="pane-footer">
                <button class="btn-save-studio" @click="saveInfo">
                  LƯU
                  <i class="bi bi-arrow-right ms-2"></i>
                </button>
              </div>
            </div>

            <div
              v-else-if="activeTab === 'orders'"
              key="orders"
              class="tab-pane"
            >
              <div class="pane-header">
                <h2 class="pane-title">LỊCH SỬ MUA SẮM</h2>
                <div class="pane-line"></div>
              </div>

              <div class="order-list-container">
                <div class="order-table-header d-none d-md-grid">
                  <div class="header-item">MÃ ĐƠN HÀNG</div>
                  <div class="header-item">NGÀY ĐẶT</div>
                  <div class="header-item">TRẠNG THÁI</div>
                  <div class="header-item text-end">TỔNG TIỀN</div>
                </div>

                <div class="order-items-scroll">
                  <div
                    v-for="item in orders"
                    :key="item.id"
                    class="order-row-studio"
                  >
                    <div class="order-id">
                      <span class="mobile-label d-md-none">ID:</span>
                      #CWW-2026-00{{ item.maHoaDon }}
                    </div>
                    <div class="order-date">
                      <span class="mobile-label d-md-none">DATE:</span>
                      12/04/2026
                    </div>
                    <div class="order-status">
                      <span
                        class="status-badge"
                        :class="
                          item.trangThai === 1 ? 'processing' : 'completed'
                        "
                      >
                        {{ item.trangThai === 1 ? "CHỜ XÁC NHẬN" : "DONE" }}
                      </span>
                    </div>

                    <div class="order-total-price text-md-end">
                      {{
                        new Intl.NumberFormat("vi-VN").format(item.tongTien)
                      }}₫
                    </div>
                    <div class="order-action">
                      <button
                        class="btn-detail-minimal"
                        @click="openDetail(item)"
                      >
                        CHI TIẾT
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </transition>
        </main>
      </div>
    </div>
    <div
      :class="['studio-drawer-overlay', { active: isDrawerOpen }]"
      @click="closeDrawer"
    ></div>
    <aside :class="['studio-drawer', { open: isDrawerOpen }]">
      <div class="drawer-header">
        <button class="btn-close-drawer" @click="closeDrawer">
          <i class="bi bi-x-lg"></i>
        </button>
        <h3 class="drawer-title">ORDER DETAILS</h3>
        <p class="drawer-subtitle" v-if="selectedOrder">
          #{{ selectedOrder.maHoaDon }}
        </p>
      </div>

      <div class="drawer-content">
        <div v-if="orderDetails.length > 0">
          <div
            v-for="detail in orderDetails"
            :key="detail.idSanPhamChiTiet"
            class="detail-item"
          >
            <div class="detail-img-box">
              <img :src="detail.hinhAnh || getRandomImage()" alt="product" />
            </div>

            <div class="detail-info">
              <p class="prod-name">{{ detail.tenSanPham }}</p>

              <p class="prod-meta">Số lượng: {{ detail.soLuong }}</p>
            </div>

            <div class="detail-price">
              {{ new Intl.NumberFormat("vi-VN").format(detail.thanhTien) }}₫
            </div>
          </div>
          <div class="drawer-summary">
            <div class="summary-line">
              <span>TẠM TÍNH</span>
              <span
                >{{
                  new Intl.NumberFormat("vi-VN").format(
                    selectedOrder?.tongTien,
                  )
                }}₫</span
              >
            </div>
            <div class="summary-line total">
              <span>TỔNG TIỀN</span>
              <span
                >{{
                  new Intl.NumberFormat("vi-VN").format(
                    selectedOrder?.tongTien,
                  )
                }}₫</span
              >
            </div>
            <button
              v-if="selectedOrder?.trangThai === 1"
              class="btn-cancel-order"
              @click="handleCancelOrder"
            >
              HỦY ĐƠN HÀNG
            </button>
          </div>
        </div>
        <div v-else class="text-center py-5">LOADING...</div>
      </div>
    </aside>
  </div>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Syncopate:wght@700&family=Plus+Jakarta+Sans:wght@300;400;600;800&display=swap");
.btn-cancel-order {
  width: 100%;
  margin-top: 25px;
  background: none;
  border: 1px solid #ff4444;
  color: #ff4444;
  padding: 15px;
  font-size: 0.7rem;
  font-weight: 800;
  letter-spacing: 2px;
  transition: 0.3s;
  cursor: pointer;
}

.btn-cancel-order:hover {
  background: #ff4444;
  color: #fff;
  box-shadow: 0 5px 15px rgba(255, 68, 68, 0.2);
}
/* DRAWER SYSTEM */
.studio-drawer-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.3);
  backdrop-filter: blur(4px);
  z-index: 1000;
  opacity: 0;
  visibility: hidden;
  transition: 0.4s;
}
.studio-drawer-overlay.active {
  opacity: 1;
  visibility: visible;
}

.studio-drawer {
  position: fixed;
  top: 0;
  right: -500px;
  width: 450px;
  height: 100%;
  background: #fff;
  z-index: 1001;
  transition: 0.5s cubic-bezier(0.4, 0, 0.2, 1);
  padding: 40px;
  box-shadow: -10px 0 30px rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
}
.studio-drawer.open {
  right: 0;
}
.detail-img-box img {
  width: 100%;
  height: 100%;
  object-fit: cover; /* hoặc contain nếu muốn thấy full ảnh */
  display: block;
}

.btn-close-drawer {
  background: none;
  border: none;
  font-size: 1.5rem;
  padding: 0;
  margin-bottom: 30px;
  cursor: pointer;
}

.drawer-title {
  font-family: "Syncopate", sans-serif;
  font-size: 1.2rem;
  letter-spacing: 2px;
}
.drawer-subtitle {
  font-size: 0.7rem;
  color: #888;
  font-weight: 800;
  margin-bottom: 40px;
}

.detail-item {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px 0;
  border-bottom: 1px solid #f0f0f0;
}
.detail-img-box {
  width: 60px;
  height: 60px;
  background: #f9f9f9;
  border: 1px solid #eee;
}
.prod-name {
  font-weight: 800;
  font-size: 0.85rem;
  margin-bottom: 5px;
  text-transform: uppercase;
}
.prod-meta {
  font-size: 0.7rem;
  color: #999;
  font-weight: 600;
}
.detail-price {
  margin-left: auto;
  font-family: "Syncopate", sans-serif;
  font-size: 0.75rem;
  font-weight: 700;
}

.drawer-summary {
  margin-top: auto;
  padding-top: 30px;
}
.summary-line {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
  font-size: 0.75rem;
  font-weight: 600;
}
.summary-line.total {
  border-top: 2px solid #000;
  padding-top: 20px;
  font-weight: 800;
  font-size: 1rem;
}

@media (max-width: 576px) {
  .studio-drawer {
    width: 100%;
    right: -100%;
  }
}
.studio-profile-wrapper {
  background: #fcfcfc;
  min-height: 100vh;
  font-family: "Plus Jakarta Sans", sans-serif;
  position: relative;
  overflow: hidden;
}

.bg-blur-element {
  position: absolute;
  top: -10%;
  right: -5%;
  width: 400px;
  height: 400px;
  background: radial-gradient(circle, rgba(0, 0, 0, 0.02) 0%, transparent 70%);
  z-index: 0;
}

.profile-layout {
  display: grid;
  grid-template-columns: 320px 1fr;
  background: #fff;
  border: 1px solid #000;
  min-height: 750px;
  position: relative;
  z-index: 1;
}

/* SIDEBAR */
.profile-sidebar {
  background: #464242;
  color: #fff;
  padding: 50px 30px;
  display: flex;
  flex-direction: column;
}

.profile-brand {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 60px;
}

.brand-text {
  font-family: "Syncopate", sans-serif;
  letter-spacing: 4px;
  font-size: 0.8rem;
}

.status-indicator {
  width: 6px;
  height: 6px;
  background: #00ff88;
  border-radius: 50%;
  box-shadow: 0 0 10px #00ff88;
}

.user-id-card {
  margin-bottom: 50px;
}

.avatar-wrapper {
  position: relative;
  width: 80px;
  height: 80px;
  margin-bottom: 20px;
}

.avatar-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border: 1px solid #333;
  padding: 5px;
}

.avatar-upload-btn {
  position: absolute;
  bottom: -5px;
  right: -5px;
  background: #fff;
  color: #000;
  border: none;
  width: 26px;
  height: 26px;
  font-size: 1.1rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

.user-name {
  font-family: "Syncopate", sans-serif;
  font-size: 0.85rem;
  margin-bottom: 5px;
  text-transform: uppercase;
}

.membership-tag {
  font-size: 0.6rem;
  letter-spacing: 2px;
  color: #888;
  font-weight: 800;
}

/* NAVIGATION */
.sidebar-nav {
  display: flex;
  flex-direction: column;
  gap: 5px;
  flex-grow: 1;
}

.nav-link {
  background: none;
  border: none;
  color: #666;
  text-align: left;
  padding: 18px 0;
  font-size: 0.7rem;
  font-weight: 800;
  letter-spacing: 2px;
  transition: 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
}

.nav-num {
  margin-right: 15px;
  color: #e52626;
  font-family: "Syncopate", sans-serif;
  font-size: 0.65rem;
}

.nav-link.active {
  color: #fff;
}
.nav-link:hover:not(.active) {
  color: #fff;
  padding-left: 10px;
}

.nav-spacer {
  flex-grow: 1;
}

.security-link {
  border-top: 1px solid #222;
  padding-top: 25px;
  color: #ff4444;
}

/* MAIN CONTENT */
.profile-content {
  padding: 60px;
  background: #fff;
}

.pane-header {
  margin-bottom: 50px;
}
.pane-title {
  font-family: "Syncopate", sans-serif;
  font-size: 1.1rem;
  letter-spacing: 4px;
  margin-bottom: 15px;
}
.pane-line {
  width: 35px;
  height: 3px;
  background: #000;
}

/* BENTO GRID */
.bento-grid-2026 {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
}
.span-2 {
  grid-column: span 2;
}
.bento-item {
  position: relative;
  border-bottom: 1px solid #f0f0f0;
  padding-bottom: 5px;
}
.studio-label {
  display: block;
  font-size: 0.65rem;
  font-weight: 800;
  letter-spacing: 2px;
  color: #000;
  margin-bottom: 12px;
}
.studio-input {
  width: 100%;
  border: none;
  background: transparent;
  font-size: 0.95rem;
  font-weight: 600;
  padding: 10px 0;
  outline: none;
  color: #1a1a1a;
}
.readonly {
  color: #bbb;
  cursor: not-allowed;
}
.input-focus-line {
  position: absolute;
  bottom: -1px;
  left: 0;
  width: 0;
  height: 1.5px;
  background: #000;
  transition: 0.4s ease;
}
.studio-input:focus ~ .input-focus-line {
  width: 100%;
}
.empty-bento {
  border: 1px dashed #eee;
  display: flex;
  align-items: center;
  justify-content: center;
  padding-bottom: 0;
  min-height: 80px;
}
.studio-watermark {
  font-family: "Syncopate", sans-serif;
  font-size: 1.8rem;
  color: #fafafa;
  user-select: none;
}

/* ORDER HISTORY SPECIFIC */
.order-table-header {
  display: grid;
  grid-template-columns: 1.5fr 1fr 1fr 1fr 0.5fr;
  padding: 15px 0;
  border-bottom: 2px solid #000;
  margin-bottom: 10px;
}
.header-item {
  font-size: 0.65rem;
  font-weight: 800;
  letter-spacing: 2px;
  color: #000;
}
.order-row-studio {
  display: grid;
  grid-template-columns: 1.5fr 1fr 1fr 1fr 0.5fr;
  padding: 25px 0;
  border-bottom: 1px solid #f0f0f0;
  align-items: center;
  transition: 0.3s;
}
.order-row-studio:hover {
  background: #fafafa;
}
.order-id {
  font-weight: 700;
  font-size: 0.85rem;
  letter-spacing: 1px;
}
.order-date {
  font-size: 0.8rem;
  color: #888;
}
.status-badge {
  font-size: 0.6rem;
  font-weight: 800;
  padding: 4px 12px;
  letter-spacing: 1px;
  display: inline-block;
}
.status-badge.processing {
  background: #fff4e5;
  color: #d4a017;
}
.status-badge.completed {
  background: #f0fdf4;
  color: #16a34a;
}
.order-total-price {
  font-family: "Syncopate", sans-serif;
  font-size: 0.85rem;
  font-weight: 700;
}
.btn-detail-minimal {
  background: none;
  border: none;
  font-size: 0.65rem;
  font-weight: 800;
  text-decoration: underline;
  cursor: pointer;
  padding: 0;
}

/* FOOTER */
.pane-footer {
  margin-top: 60px;
  display: flex;
  justify-content: flex-end;
}
.btn-save-studio {
  background: #5c5151;
  color: #fff;
  border: none;
  padding: 20px 45px;
  font-weight: 800;
  font-size: 0.75rem;
  letter-spacing: 3px;
  transition: 0.3s;
  cursor: pointer;
}
.btn-save-studio:hover {
  background: #8a5c5c;
  transform: translateY(-3px);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
}

/* TRANSITIONS */
.fade-slide-enter-active,
.fade-slide-leave-active {
  transition: 0.4s;
}
.fade-slide-enter-from {
  opacity: 0;
  transform: translateY(15px);
}
.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(-15px);
}

@media (max-width: 992px) {
  .profile-layout {
    grid-template-columns: 1fr;
  }
  .profile-sidebar {
    padding: 40px;
    min-height: auto;
  }
  .profile-content {
    padding: 40px 25px;
  }
  .order-row-studio {
    grid-template-columns: 1fr;
    gap: 10px;
    border: 1px solid #eee;
    padding: 20px;
    margin-bottom: 15px;
  }
  .mobile-label {
    font-weight: 800;
    font-size: 0.65rem;
    margin-right: 5px;
    color: #999;
  }
}
</style>
