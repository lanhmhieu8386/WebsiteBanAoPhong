<template>
  <div class="header">
    <div class="right">
      <i class="bi bi-bell icon"></i>

      <div class="user">
        <div class="name">Đoàn Hoàng Long</div>
        <div class="role">Admin</div>
      </div>

      <i class="bi bi-person-circle avatar"></i>
    </div>
  </div>
</template>

<style scoped>
.header {
  height: 70px;
  background: white;
  border-bottom: 1px solid #eee;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0 24px;
}

.right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.icon {
  font-size: 22px;
}

.avatar {
  font-size: 34px;
}

.name {
  font-weight: 600;
}

.role {
  font-size: 13px;
  color: #888;
}
</style>
