<script setup>
import Sidebar from "@/component/admin/Sidebar.vue";
import Header from "@/component/admin/Header.vue";
</script>

<template>
  <div class="admin-layout">
    <!-- sidebar -->
    <Sidebar />

    <div class="main">
      <!-- header -->
      <Header />

      <!-- content -->
      <div class="content">
        <RouterView />
      </div>
    </div>
  </div>
</template>

<style scoped>
body {
  margin: 0;
  background: #f5f7fb;
}
.admin-layout {
  display: flex;
  height: 100vh;
  width: 100%;
}

.main {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: #f5f7fb;
}

.content {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
}

.sidebar {
  min-width: 240px;
  height: 100vh;
  background: white;
  border-right: 1px solid #eee;
}
</style>
