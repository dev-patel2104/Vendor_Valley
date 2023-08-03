<template>
  <div class="d-flex">
    <el-menu default-active="1" class="py-4">
      <el-menu-item index="1">
        <span class="h6" @click="userProfile()"
          ><i class="fa-solid fa-user mx-2"></i>Account</span
        >
      </el-menu-item>
      <el-menu-item index="2">
        <span class="h6" @click="userBooking()"
          ><i class="fa-solid fa-bag-shopping mx-2"></i>My bookings</span
        >
      </el-menu-item>
    </el-menu>
    <div v-if="showProfile" class="w-100 m-4 p-4 border rounded">
      <div class="mb-4"><h4>Personal details</h4></div>
      <el-form :model="form" label-width="120px">
        <el-form-item label="First name">
          <el-input v-model="form.first_name" />
        </el-form-item>
        <el-form-item label="Last name">
          <el-input v-model="form.last_name" />
        </el-form-item>
        <el-form-item label="Street">
          <el-input v-model="form.street" />
        </el-form-item>
        <el-form-item label="city">
          <el-input v-model="form.city" />
        </el-form-item>
        <el-form-item label="province">
          <el-input v-model="form.province" />
        </el-form-item>
        <el-form-item label="Country">
          <el-input v-model="form.country" />
        </el-form-item>
        <el-form-item label="mobile">
          <el-input v-model="form.mobile" />
        </el-form-item>
        <el-form-item label="email">
          <el-input v-model="form.email"/>
        </el-form-item>
       
      </el-form>
      <div class="d-flex flex-row justify-content-end" >
        <button class="btn btn-primary" @click="updateProfile()">
          Update
        </button>
      </div>
    </div>

    <!-- booking -->

    <div v-if="showBooking" class="w-100 m-4 p-4 border rounded">
      <div class="mb-4"><h4>Bookings</h4></div>
  
      <div>
        <el-table :data="bookingList" style="width: 100%">
          <el-table-column prop="user" label="Service">
            <template v-slot="{ row }">
              <div>
                <h6>{{ row.serviceName }}</h6>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="Booking ID"
            prop="bookingId"

          ></el-table-column>
          <el-table-column
            prop="startDate"
            label="Start date"
          ></el-table-column>
          <el-table-column prop="endDate" label="End date"></el-table-column>
          <el-table-column
            prop="bookingStatus"
            label="Status"
            width="180"
          ></el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { ElLoading } from "element-plus";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "customerprofile",
  data() {
    return {
      showProfile: true,
      form: {
        userId: "",
        first_name: "",
        last_name: "",
        mobile: "",
        street: "",
        city: "",
        province: "",
        country: "",
        email: "",
        password: "",
        is_vendor: 0
      },
      bookingList: []
    };
  },
  methods: {
    updateProfile(){
      axios.put('https://vendor-valley.onrender.com/edit/profile', this.form ).then((res)=>{
        console.log(res.data);
        this.$toast.success(`Profile Updated`, {position:"top", duration: 2000, });
      }).catch((err)=>{
        console.log(err.response);
      })
    },
    userProfile() {
      this.showProfile = true;
      this.showBooking = false;
    },

    userBooking() {
      this.showBooking = true;
      this.showProfile = false;
    },

    async getUserDetails() {
      const loading = ElLoading.service({
        lock: true,
        background: "rgba(255, 255, 255, 1)",
      });
      await axios
        .get("https://vendor-valley.onrender.com/profile", {
            headers: {
              'jwtToken': localStorage.getItem("token"),
            },
          })
        .then((res) => {
          console.log(res.data);
          this.form.userId = res.data.user_id;
          this.form.first_name =  res.data.first_name;
          this.form.last_name = res.data.last_name;
          this.form.mobile = res.data.mobile;
          this.form.email = res.data.email;
          this.form.city = res.data.city;
          this.form.province = res.data.province;
          this.form.street = res.data.street;
          this.form.country = res.data.country;
          this.form.password = res.data.password;

          loading.close();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getCustomerBookings(){
      axios.get('https://vendor-valley.onrender.com/bookings', {
            headers: {
              'jwtToken': localStorage.getItem("token"),
            },
          }
      ).then((res)=>{
        this.bookingList = res.data
        console.log(this.bookingList);
      }).catch((err)=>{
        console.log(err);
      })
    }
  },
  mounted() {
    this.getUserDetails();
    this.getCustomerBookings();
  },
};
</script>

<style scoped></style>
