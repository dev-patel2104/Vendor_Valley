<template>
  <div class="text-center">
    <h1>Login Page</h1>
  </div>
  <div>
    <form class="form-signin d-flex flex-column" @submit.prevent="login()">
      <label for="inputEmail" class="sr-only">Email</label>
      <input
        type="email"
        id="inputEmail"
        class="form-control"
        placeholder="Email address"
        v-model="form.email"
        required
        autofocus
      />
      <label for="inputPassword" class="sr-only">Password</label>
      <input
        type="password"
        id="inputPassword"
        class="form-control"
        v-model="form.password"
        placeholder="Password"
        required
      />

      <div class="mb-2">
        <router-link to="#">
          <a><small>Forgot password?</small></a>
        </router-link>
      </div>

      <button type="submit" class="btn btn-lg btn-primary btn-block">
        Login
      </button>
      <p class="text-danger">{{ msg }}</p>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LoginPage",
  data() {
    return {
      form: {
        email: "",
        password: "",
      },
      msg: "",
    };
  },
  methods: {
    login() {
      console.log(this.form);

      axios
        .post("https://vendor-valley.onrender.com/login", this.form)
        .then(() => {
          alert("Login successful");
          this.msg = "";
        })
        .catch((error) => {
          console.error("error", error);
          this.msg = error.response.data;
        });
    },
  },

  mounted() {
    this.msg = "";
  },
};
</script>

<style>
html,
body {
  height: 100%;
}

.form-signin {
  width: 100%;
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .checkbox {
  font-weight: 400;
}
.form-signin .form-control {
  position: relative;
  box-sizing: border-box;
  height: auto;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}
.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}
.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>
