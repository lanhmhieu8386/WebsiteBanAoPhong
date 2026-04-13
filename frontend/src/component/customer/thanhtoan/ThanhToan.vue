<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { thanhToanDonHang } from "@/api/customer/hoaDonCustomerApi";
const router = useRouter();

const loading = ref(false);

const address = ref({
  name: "Lãnh Minh Hiếu",
  phone: "(+84) 332 654 198",
  detail:
    "Số 405, Ngõ 68 Đường Phú Diễn, Phường Phú Diễn, Quận Bắc Từ Liêm, Hà Nội",
});

const cartItems = ref([]);
const shippingFee = ref(30000);
const voucherDiscount = ref(0);

onMounted(() => {
  const data = localStorage.getItem("checkout_items");
  if (data) {
    cartItems.value = JSON.parse(data);
  } else {
    router.push("/gio-hang");
  }
});

const productTotal = computed(() => {
  return cartItems.value.reduce(
    (total, item) => total + item.gia * item.soLuong,
    0,
  );
});

const finalTotal = computed(() => {
  return productTotal.value + shippingFee.value - voucherDiscount.value;
});

const formatPrice = (price) =>
  new Intl.NumberFormat("vi-VN").format(price) + "₫";

const placeOrder = async () => {
  try {
    loading.value = true;

    const payload = {
      tenNguoiNhan: address.value.name,
      soDienThoai: address.value.phone,
      diaChi: address.value.detail,
    };

    const res = await thanhToanDonHang(payload);

    alert("Đặt hàng thành công!");

    console.log("Hóa đơn:", res.data);

    localStorage.removeItem("checkout_items");

    router.push("/dat-hang-thanh-cong");
  } catch (err) {
    console.error(err);
    alert("Checkout lỗi rồi ");
  } finally {
    loading.value = false;
  }
};
const getImageUrl = (imageName) => {
  if (!imageName) return "https://via.placeholder.com/80";
  if (imageName.startsWith("http")) return imageName;
  return new URL(`../assets/images/${imageName}`, import.meta.url).href;
};
</script>

<template>
  <div class="checkout-minimal">
    <div class="container py-5">
      <div class="header-box mb-5">
        <h1 class="title">CHECKOUT</h1>
        <div class="line"></div>
      </div>

      <div class="row g-0 main-content shadow-sm">
        <div class="col-lg-7 left-panel p-5">
          <section class="mb-5">
            <h5 class="step-title">01 / SHIPPING ADDRESS</h5>
            <div class="address-card mt-3">
              <div class="d-flex justify-content-between">
                <p class="mb-1 fw-bold">
                  {{ address.name }} — {{ address.phone }}
                </p>
                <button class="btn-text">CHANGE</button>
              </div>
              <p class="text-muted small mb-0">{{ address.detail }}</p>
            </div>
          </section>

          <section class="mb-5">
            <h5 class="step-title">02 / PAYMENT METHOD</h5>
            <div class="d-flex gap-3 mt-3">
              <label class="pay-label">
                <input type="radio" name="pay" checked />
                <div class="pay-tile">CASH ON DELIVERY</div>
              </label>
              <label class="pay-label">
                <input type="radio" name="pay" />
                <div class="pay-tile">BANK TRANSFER</div>
              </label>
            </div>
          </section>

          <section>
            <h5 class="step-title">03 / ORDER NOTES</h5>
            <textarea
              class="minimal-input mt-3"
              rows="2"
              placeholder="Ghi chú cho Studio..."
            ></textarea>
          </section>
        </div>

        <div class="col-lg-5 right-panel p-5">
          <h5 class="step-title mb-4">YOUR BAG</h5>

          <div class="product-list">
            <div
              v-for="item in cartItems"
              :key="item.id"
              class="product-item d-flex gap-3 mb-4"
            >
              <img :src="getImageUrl(item.hinhAnh)" class="p-img" />
              <div class="p-info flex-grow-1">
                <p class="p-name">{{ item.tenSanPham }}</p>
                <p class="p-meta">
                  {{ item.tenMauSac }} / {{ item.tenKichCo }} (x{{
                    item.soLuong
                  }})
                </p>
              </div>
              <div class="p-price">
                {{ formatPrice(item.gia * item.soLuong) }}
              </div>
            </div>
          </div>

          <div class="billing-details pt-4 mt-4 border-top">
            <div class="d-flex justify-content-between mb-2">
              <span class="text-muted">Subtotal</span>
              <span>{{ formatPrice(productTotal) }}</span>
            </div>
            <div class="d-flex justify-content-between mb-4">
              <span class="text-muted">Shipping</span>
              <span>{{ formatPrice(shippingFee) }}</span>
            </div>
            <div class="d-flex justify-content-between align-items-end">
              <span class="fw-bold fs-5">TOTAL</span>
              <span class="final-price">{{ formatPrice(finalTotal) }}</span>
            </div>
          </div>

          <button
            class="btn-black-studio w-100 mt-5"
            @click="placeOrder"
            :disabled="loading"
          >
            {{ loading ? "PROCESSING..." : "PLACE ORDER NOW" }}
          </button>

          <p class="policy-text text-center mt-4">
            By placing your order, you agree to ChiWiWis’ Terms & Conditions.
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Syncopate:wght@700&family=Plus+Jakarta+Sans:wght@300;400;600;700&display=swap");

.checkout-minimal {
  background-color: #fcfcfc;
  min-height: 100vh;
  font-family: "Plus Jakarta Sans", sans-serif;
  color: #1a1a1a;
}

/* HEADER STYLE */
.header-box .title {
  font-family: "Syncopate", sans-serif;
  font-size: 1.8rem;
  letter-spacing: 5px;
  text-align: center;
  margin: 0;
}
.header-box .line {
  width: 50px;
  height: 2px;
  background: #000;
  margin: 15px auto;
}

/* MAIN LAYOUT */
.main-content {
  background: #fff;
  border: 1px solid #f0f0f0;
}

.left-panel {
  border-right: 1px solid #f0f0f0;
}
.right-panel {
  background: #fafafa;
}

/* COMPONENTS */
.step-title {
  font-size: 0.75rem;
  font-weight: 800;
  letter-spacing: 2px;
  color: #999;
}

.address-card {
  padding: 20px;
  border: 1px solid #f0f0f0;
  background: #fff;
}

.btn-text {
  background: none;
  border: none;
  font-size: 0.65rem;
  font-weight: 800;
  text-decoration: underline;
  letter-spacing: 1px;
}

/* PAYMENT BUTTONS */
.pay-label {
  flex: 1;
  cursor: pointer;
}
.pay-label input {
  display: none;
}
.pay-tile {
  border: 1px solid #eee;
  padding: 15px;
  text-align: center;
  font-size: 0.75rem;
  font-weight: 700;
  background: #fff;
  transition: 0.2s;
}
.pay-label input:checked + .pay-tile {
  border-color: #000;
  background: #000;
  color: #fff;
}

.minimal-input {
  width: 100%;
  border: 1px solid #eee;
  padding: 15px;
  font-size: 0.9rem;
  outline: none;
}
.minimal-input:focus {
  border-color: #000;
}

/* PRODUCT ITEMS */
.p-img {
  width: 60px;
  height: 80px;
  object-fit: cover;
  border-radius: 0;
}
.p-name {
  font-size: 0.85rem;
  font-weight: 700;
  margin: 0;
  text-transform: uppercase;
}
.p-meta {
  font-size: 0.75rem;
  color: #888;
  margin: 0;
}
.p-price {
  font-size: 0.9rem;
  font-weight: 700;
}

/* FOOTER & BUTTON */
.final-price {
  font-family: "Syncopate", sans-serif;
  font-size: 1.8rem;
  letter-spacing: -1px;
}

.btn-black-studio {
  background: #000;
  color: #fff;
  border: none;
  padding: 20px;
  font-size: 0.85rem;
  font-weight: 700;
  letter-spacing: 3px;
  transition: 0.3s;
}
.btn-black-studio:hover {
  background: #333;
}

.policy-text {
  font-size: 0.65rem;
  color: #bbb;
  letter-spacing: 0.5px;
}
</style>
