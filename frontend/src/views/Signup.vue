<template>
  <div class="text-center">
    <h1>Register Page</h1>
  </div>



  <form class="form-signin d-flex flex-column" @submit.prevent="signup()">

    <label for="firstname" class="sr-only">First name</label>
    <input
      type="text"
      id="firstname"
      class="form-control"
      placeholder="First name"
      v-model="form.first_name"
      required
      autofocus
    />

    <label for="lastname" class="sr-only">Last name</label>
    <input
      type="text"
      id="lastname"
      class="form-control"
      placeholder="Last name"
      v-model="form.last_name"
      required
      autofocus
    />

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

    <label for="inputMobile" class="sr-only">Mobile number</label>
    <input
      type="text"
      id="inputMobile"
      class="form-control"
      placeholder="Mobile number"
      v-model="form.mobile"
      required
      autofocus
    />

    <label for="Street" class="sr-only">Street</label>
    <input
      type="text"
      id="Street"
      class="form-control"
      placeholder="Street"
      v-model="form.street"
      required
      autofocus
    />

    <label for="City" class="sr-only">City</label>
    <input
      type="text"
      id="City"
      class="form-control"
      placeholder="City"
      v-model="form.city"
      required
      autofocus
    />

    <label for="Province" class="sr-only">Province</label>
    <input
      type="text"
      id="Province"
      class="form-control"
      placeholder="Province"
      v-model="form.province"
      required
      autofocus
    />

    <label for="Country" class="sr-only">Country</label>
    <input
      type="text"
      id="Country"
      class="form-control"
      placeholder="Country"
      v-model="form.country"
      required
      autofocus
    />

    <div class="d-flex align-items-center justify-content-between">
      <div>Are you a admin?</div>
      <div>
        <el-switch
          v-model="isAdmin"
          size="large"
          active-text=""
          inactive-text=""
        />
      </div>
    </div>

    <div v-if="isAdmin">
      <label for="Company" class="sr-only">Company</label>
      <input
        type="text"
        id="Company"
        class="form-control"
        placeholder="Company Name"
        v-model="form.company_name"
        required
        autofocus
      />

      <label for="CompanyRole" class="sr-only">Company role</label>
      <input
        type="text"
        id="CompanyRole"
        class="form-control"
        placeholder="Company Role"
        v-model="form.user_role"
        required
        autofocus
      />

      <label for="CompanyId" class="sr-only">Company Id</label>
      <input
        type="text"
        id="CompanyId"
        class="form-control"
        placeholder="Company Id"
        v-model="form.company_registration_number"
        required
        autofocus
      />

      <label for="CompanyEmail" class="sr-only">Company Email</label>
      <input
        type="email"
        id="CompanyEmail"
        class="form-control"
        placeholder="Company Email"
        v-model="form.company_email"
        required
        autofocus
      />

      <label for="CompanyMobile" class="sr-only">Company Mobile</label>
      <input
        type="text"
        id="CompanyMobile"
        class="form-control"
        placeholder="Company Mobile"
        v-model="form.company_mobile"
        required
        autofocus
      />

      <label for="CompanyStreet" class="sr-only">Company Street</label>
      <input
        type="text"
        id="CompanyStreet"
        class="form-control"
        placeholder="Company Street"
        v-model="form.company_street"
        required
        autofocus
      />

      <label for="CompanyCity" class="sr-only">Company City</label>
      <input
        type="text"
        id="CompanyCity"
        class="form-control"
        placeholder="Company city"
        v-model="form.company_city"
        required
        autofocus
      />

      <label for="CompanyProvince" class="sr-only">Company Province</label>
      <input
        type="text"
        id="inputEmail"
        class="form-control"
        placeholder="Company Province"
        v-model="form.company_province"
        required
        autofocus
      />

      <label for="CompanyCountry" class="sr-only">Company Country</label>
      <input
        type="text"
        id="Company Country"
        class="form-control"
        placeholder="Company Country"
        v-model="form.company_country"
        required
        autofocus
      />
    </div>

    <label for="inputPassword" class="sr-only">Password</label>
    <input
      type="password"
      id="inputPassword"
      class="form-control"
      placeholder="Password"
      v-model="form.password"
      required
    />

    <button class="btn btn-lg btn-primary btn-block" type="submit">
      Sign up
    </button>

    <p class="text-danger">{{ msg }}</p>
  </form>
</template>

<script>
import router from "@/router";
import axios from "axios";
import { ElSwitch } from "element-plus";

// "first_name": "John",
// "last_name": "Doe",
// "mobile": "1234567890",
// "is_vendor": 0,
// "street": "123 Main St",
// "city": "Springfield",
// "province": "IL",
// "country": "USA",
// "email": "johndoe.doe@email.com",
// "password": "password123",
// "user_role": "Manager",
// "company_name": "xyz Sol",
// "company_registration_number": "116",
// "company_street": "11-35/6",
// "company_city": "Halifax",
// "company_province": "NS",
// "company_country": "Ca",
// "company_email": "xyz@sol.com",
// "company_mobile": "7412358960"
export default {
  name: "signUp",
  components: { ElSwitch },
  data() {
    return {
      form: {
        first_name: "",
        last_name: "",
        mobile: "",
        is_vendor: 0,
        street: "",
        city: "",
        province: "",
        country: "",
        email: "",
        password: "",
        user_role: "",
        company_name: "",
        company_registration_number: "",
        company_street: "",
        company_city: "",
        company_province: "",
        company_country: "",
        company_email: "",
        company_mobile: ""
        // first_name: "John",
        // last_name: "Doe",
        // mobile: "1234567890",
        // is_vendor: 0,
        // street: "123 Main St",
        // city: "Springfield",
        // province: "IL",
        // country: "USA",
        // email: "johndoe.doe@email.com",
        // password: "password123",
        // user_role: "Manager",
        // company_name: "xyz Sol",
        // company_registration_number: "116",
        // company_street: "11-35/6",
        // company_city: "Halifax",
        // company_province: "NS",
        // company_country: "Ca",
        // company_email: "xyz@sol.com",
        // company_mobile: "7412358960"
      },
      msg: "",
      isAdmin: false,
    };
  },
  methods: {
    async signup() {
      if(this.isAdmin){
        this.form.is_vendor = 1
      }
      console.log(this.form);
      await axios
        .post("https://vendor-valley.onrender.com/register", this.form)
        .then((resp) => {
          console.log(resp.data);
          router.push("/login");
        })
        .catch((error) => {
          console.error("error", error.response);
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
