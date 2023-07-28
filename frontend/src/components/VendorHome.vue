<template>
  <div
    class="w-100 bg-black px-4 py-2 d-flex justify-content-between align-items-center"
  >
    <div>
      <i
        v-if="showLogout"
        @click="goDashboard"
        class="fa-solid fa-house Home-Text"
        style="color: #ffffff"
      ></i>
      <h3 v-if="!showLogout" @click="goHome" class="Home-Text">
        Vendor Valley
      </h3>
    </div>
    <div class="d-flex justify-content-center align-items-center">
      <div>
        <i
          v-if="showLogout"
          @click="gotoProfile()"
          class="fa-solid fa-user"
          style="color: #ffffff"
        ></i>
      </div>
      <div>
        <div class="d-flex justify-content-center">
          <router-link
            v-if="!showLogout"
            to="/login"
            class="btn btn-light px-4 mx-2"
            >Login</router-link
          >
          <router-link
            v-if="!showLogout"
            to="/signup"
            class="btn btn-light px-4"
            >Register</router-link
          >
          <button
            v-if="showLogout"
            @click.prevent="logout()"
            class="btn btn-light px-4"
          >
            Log out
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import router from "@/router";

export default {
  name: "NavBar",
  data() {
    return {
      showLogout: "",
    };
  },

  methods: {
    goDashboard() {
      if (localStorage.getItem("role") == 0) {
        router.push("/homepage");
      } else {
        router.push("/vendorhome");
      }
    },
    gotoProfile() {
      if (localStorage.getItem("role") == 0) {
        router.push("/customerprofile");
      } else {
        router.push("/vendorprofile");
      }
    },
    goHome() {
      router.push("/");
    },
    logout() {
      localStorage.removeItem("userID");
      localStorage.removeItem("email");
      localStorage.removeItem("token");
      localStorage.removeItem("role");
      this.showLogout = false;
      router.push("/login");
    },
  },
  mounted() {
    if (localStorage.getItem("userID") != null) {
      this.showLogout = true;
      this.goDashboard();
    }
  },
};
</script>

<style scoped>
.Home-Text {
  color: white;
}

.Home-Text:hover {
  cursor: pointer;
}
</style>
