<template>
  <div class="d-flex">
    <el-menu :default-active="activeNo" class="py-4">
      <el-menu-item index="1">
        <span class="h6" @click="userProfile()"
          ><i class="fa-solid fa-user mx-2"></i>Account</span
        >
      </el-menu-item>
      <el-menu-item index="2">
        <span class="h6" @click="service()"
          ><i class="fa-solid fa-store mx-2"></i>Services</span
        >
      </el-menu-item>
      <el-menu-item index="3">
        <span class="h6" @click="business()"
          ><i class="fa-solid fa-briefcase mx-2"></i>My Business</span
        >
      </el-menu-item>
      <el-menu-item index="4">
        <span class="h6" @click="userBooking()"
          ><i class="fa-solid fa-bag-shopping mx-2"></i>My bookings</span
        >
      </el-menu-item>
    </el-menu>

    <!-- profile -->
    <div v-if="showProfile" class="w-100 m-4 p-4 border rounded">
      <el-scrollbar style="max-height: 700px;">
      <div class="mb-4"><h4>Personal details</h4></div>
      <el-form :model="personalInfo" label-width="120px">
        <el-form-item label="First name">
          <el-input v-model="personalInfo.first_name" />
        </el-form-item>
        <el-form-item label="Last name">
          <el-input v-model="personalInfo.last_name" />
        </el-form-item>
        <el-form-item label="Street">
          <el-input v-model="personalInfo.street" />
        </el-form-item>
        <el-form-item label="city">
          <el-input v-model="personalInfo.city" />
        </el-form-item>
        <el-form-item label="province">
          <el-input v-model="personalInfo.province" />
        </el-form-item>
        <el-form-item label="Country">
          <el-input v-model="personalInfo.country" />
        </el-form-item>
        <el-form-item label="mobile">
          <el-input v-model="personalInfo.mobile" />
        </el-form-item>
        <el-form-item label="email">
          <el-input v-model="personalInfo.email" />
        </el-form-item>
      </el-form>
      <div class="w-100 justify-content-end d-flex">
        <button class="btn btn-primary" @click="updatePerPro()">Update</button>
      </div>
      </el-scrollbar>
    </div>

    <!-- my business -->
    <div v-if="showBusiness" class="w-100 m-4 p-4 border rounded">
      <el-scrollbar style="max-height:700px;">
      <div class="mb-4"><h4>My Business</h4></div>
      <el-form :model="companyInfo" label-width="150px">
        <el-form-item label="Role">
          <el-input v-model="companyInfo.user_role" />
        </el-form-item>
        <el-form-item label="Company name">
          <el-input v-model="companyInfo.company_name" />
        </el-form-item>
        <el-form-item label="Registration number">
          <el-input v-model="companyInfo.company_registration_number" />
        </el-form-item>
        <el-form-item label="Street">
          <el-input v-model="companyInfo.company_street" />
        </el-form-item>
        <el-form-item label="City">
          <el-input v-model="companyInfo.company_city" />
        </el-form-item>
        <el-form-item label="Province">
          <el-input v-model="companyInfo.company_province" />
        </el-form-item>
        <el-form-item label="Country">
          <el-input v-model="companyInfo.company_country" />
        </el-form-item>
        <el-form-item label="Email">
          <el-input v-model="companyInfo.company_email" />
        </el-form-item>
        <el-form-item label="Mobile">
          <el-input v-model="companyInfo.company_mobile" />
        </el-form-item>
        
      </el-form>
      <div class="w-100 justify-content-end d-flex">
        <button class="btn btn-primary" @click="updateComPro()">Update</button>
      </div>
      </el-scrollbar>
    </div>
    <!-- business end -->

    <!-- booking -->

    <div v-if="showBooking && !showProfile && !showService && !showBusiness" class="w-100 m-4 p-4 border rounded">
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
            width="100px"
          ></el-table-column>
          <el-table-column
            label="Customers"
            prop="user.fullName"
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
          <el-table-column width="120">
            <template v-slot="{ row }">
              <a href="#" @click="openModal(row)">show more</a>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- services -->
    <div v-if="showService" class="w-100 m-4 p-4 border rounded">
      <div class="d-flex flex-row justify-content-between">
        <div class="mb-4"><h4>Your services</h4></div>
        <div>
          <button class="btn btn-primary" @click="openAddService()">
            + Add service
          </button>
        </div>
      </div>

      <modal  v-model:show="showAddServiceModal" class="w-100">
        <div class="p-4">
        <div class="mb-4">
            <h4>Add new service</h4>
          </div>
        
          <div>
            <el-form :model="form" label-width="120px">
              <el-form-item label="Serive name">
                <el-input v-model="form.serviceName" />
              </el-form-item>
              <el-form-item label="Description">
                <el-input v-model="form.serviceDescription" type="textarea" />
              </el-form-item>

              <el-form-item label="Category">
                <el-select
                  v-model="form.categoryNames"
                  multiple
                  placeholder="Categories"
                  class="w-100"
                >
                  <el-option
                    v-for="item in categories"
                    :key="item"
                    :label="item"
                    :value="item"
                  />
                </el-select>
              </el-form-item>

              <el-form-item label="Price">
                <el-input v-model="form.servicePrice" class="w-25" />
              </el-form-item>

              <el-form-item label="Upload images">
                <el-upload
                  drag
                  type="file"
                  accept="image/*"
                  :on-change="onchange"
                  ref="upload"
                >
                  <i class="fa fa-cloud-arrow-up" style="color: lightgreen"></i>
                  <div class="el-upload__text">
                    Drop file here or <em>click to upload</em>
                  </div>
                  <template #tip>
                    <div class="el-upload__tip">
                      Max file size : 5MB. File format : pdf, docx, png, jpeg,
                      ...
                    </div>
                  </template>
                </el-upload>
                <el-image v-if="fileList[0]"
                  style="width: 100px; height: 100px"
                  :src="fileList[0]"
                  :preview-src-list="fileList"
                >
                </el-image>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="addService()"
                  >Submit</el-button
                >
                <el-button @click="closeServiceModal()"
                  >Cancel</el-button
                >
              </el-form-item>
            </el-form>
          </div>
        </div>
      </modal>

      <!-- edit service modal -->
      <modal v-model:show="showEditServiceModal">
        <div class="p-4">
          <div class="mb-4">
            <h4>Edit service details</h4>
          </div>
          <el-form :model="editServiceForm" label-width="120px">
              <el-form-item label="Serive name">
                <el-input v-model="editServiceForm.serviceName" />
              </el-form-item>
              <el-form-item label="Description">
                <el-input v-model="editServiceForm.serviceDescription" type="textarea" />
              </el-form-item>

              <el-form-item label="Category">
                <el-select
                  v-model="editServiceForm.categoryNames"
                  multiple
                  placeholder="Categories"
                  class="w-100"
                >
                  <el-option
                    v-for="item in categories"
                    :key="item"
                    :label="item"
                    :value="item"
                  />
                </el-select>
              </el-form-item>

              <el-form-item label="Price">
                <el-input v-model="editServiceForm.servicePrice" class="w-25" />
              </el-form-item>

              <el-form-item label="Upload images">
                <el-upload
                  drag
                  type="file"
                  accept="image/*"
                  :on-change="onchange"
                  ref="upload"
                >
                  <i class="fa fa-cloud-arrow-up" style="color: lightgreen"></i>
                  <div class="el-upload__text">
                    Drop file here or <em>click to upload</em>
                  </div>
                  <template #tip>
                    <div class="el-upload__tip">
                      Max file size : 5MB. File format : pdf, docx, png, jpeg,
                      ...
                    </div>
                  </template>
                </el-upload>
                <el-image v-if="fileList[0]"
                  style="width: 100px; height: 100px"
                  :src="fileList[0]"
                  :preview-src-list="fileList"
                >
                </el-image>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="editService()"
                  >Submit</el-button
                >
                <el-button @click="showEditServiceModal = false, fileList = []"
                  >Cancel</el-button
                >
              </el-form-item>
            </el-form>
          
        </div>
      </modal>

      <el-divider />
      <!-- services -->
      <div class="w-100">
        <div>
          <h5>All the services</h5>
        </div>
        <el-table :data="serviceList">
          <el-table-column
            label="Services"
            prop="serviceName"
          ></el-table-column>
          <el-table-column
            label="service Description"
            prop="serviceDescription"
          ></el-table-column>
          <el-table-column label="Price" prop="servicePrice"> </el-table-column>

          <el-table-column>
           
            <template v-slot="{ row }">
              <div class="d-flex flex-row">
              <button class="btn btn-danger" href="#" @click.prevent="Delete(row)">
                Delete
              </button>
              <button class="btn btn-primary" href="#" @click.prevent="EditService(row)">
                Edit
              </button>
            </div>
            </template>
      
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- modal -->
    <modal v-model:show="showModal">
      <div>
        <div>
          <h5>Booking ref #{{ selectedItem.bookingId }}</h5>
        </div>
        <div>
          <p>
            <b>Booking date: {{ selectedItem.bookingDate }}</b>
          </p>
        </div>
        <div>
          <p><b>Service:</b> {{ selectedItem.serviceName }}</p>
        </div>
        <div>
          <p><b>Customer:</b> {{ customer }}</p>
        </div>
        <div>
          <el-button
            type="success"
            @click="request(selectedItem.bookingId, selectedItem.serviceId, 'accepted')"
            >Accept</el-button
          >
          <el-button
            type="danger"
            @click="request(selectedItem.bookingId, selectedItem.serviceId, 'declined')"
            >Reject</el-button
          >
        </div>
      </div>
    </modal>
  </div>
</template>

<script>
import 'resize-observer-polyfill';
import axios from "axios";
import { ElLoading } from "element-plus";
import Modal from "@/components/Modal.vue";

export default {
  components: { Modal },
  // eslint-disable-next-line vue/multi-word-component-names
  name: "customerprofile",
  data() {
    return {
      editfileList: [],
      fileList: [],
      myimage: [],
      activeNo: "",
      showService: false,
      showAddServiceModal: false,
      customer: "",
      showModal: false,
      bookingList: [],
      showProfile: true,
      showBooking: false,
      showBusiness: false,
      categories: [],
      selectedItem: [],
      serviceList: [],
      editServiceForm: {
        serviceId: "",
        serviceName: "",
        serviceDescription: "",
        servicePrice: "",
        images: [],
        categoryNames: [],
      },
      showEditServiceModal: false,
      form: {
        userId: "",
        serviceName: "",
        serviceDescription: "",
        servicePrice: "",
        images: [],
        categoryNames: [],
      },
      personalInfo: {
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
        is_vendor: "",
      },
      companyInfo: {
        userId: "",
        user_role: "",
        company_name: "",
        company_email: "",
        company_registration_number: "",
        company_mobile: "",
        company_street: "",
        company_city: "",
        company_province: "",
        company_country: "",
      },
    };
  },
  methods: {
    request(bId, sId, status) {
      const body = {
        bookingID: bId,
        serviceID: sId,
        bookingStatus: status,
      };

      axios.post('https://vendor-valley.onrender.com/booking/respond', body, {headers:{
        'jwtToken': localStorage.getItem('token')
      }}).then((res)=>{
        console.log(res.data);
        this.bookingList = []
        this.showBooking = false
        this.getBookings()
      }).catch((err)=>{
        console.log(err);
      })
      this.showModal = false
     
    },
    Delete(row) {
      axios
        .delete("https://vendor-valley.onrender.com/delete/service", {
          data: {
            serviceId: row.serviceId,
          },
        })
        .then((res) => {
          console.log(res.data);
          this.$toast.info(`Service deleted`, {
            position: "top",
            duration: 2000,
          });
          this.getVendorServices()
        })
        .catch((err) => {
          console.log(err);
        });
    },
    EditService(row){
         
      this.editServiceForm.serviceId = row.serviceId
      this.editServiceForm.serviceName = row.serviceName
      this.editServiceForm.servicePrice = row.servicePrice
      this.editServiceForm.serviceDescription = row.serviceDescription
      this.editServiceForm.categoryNames = row.categoryNames 
      this.showEditServiceModal = true
      
    },
    onchange(file) {
      const reader = new FileReader();
      reader.readAsDataURL(file.raw);

      reader.onload = () => {
        this.fileList.push(reader.result);
        console.log(this.fileList[0]);
      };
     
    },
    editService(){
      if (this.fileList[0].startsWith("data:image/png;base64,")) {
        this.editServiceForm.images[0] =
          this.fileList[0].replace("data:image/png;base64,", "")
        
      } else if (this.fileList[0].startsWith("data:image/jpg;base64,")) {
        this.editServiceForm.images[0] =
          this.fileList[0].replace("data:image/jpg;base64,", "")
  
      } else if (this.fileList[0].startsWith("data:image/jpeg;base64,")) {
        this.editServiceForm.images[0] = 
          this.fileList[0].replace("data:image/jpeg;base64,", "")
      
      }
      console.log(this.editServiceForm);
      axios.put('https://vendor-valley.onrender.com/edit/service', this.editServiceForm).then((res)=>{
        console.log(res.data);
        this.$toast.success(`Services Updated`, {
            position: "top",
            duration: 2000,
          });
          this.showEditServiceModal = false
          this.fileList = []
          this.getVendorServices()
      }).catch((err)=>{
        console.log(err);
      })
      
    },
    userProfile() {
      this.showProfile = true;
      this.showBooking = false;
      this.showService = false;
      this.showBusiness = false;
    },

    userBooking() {
      this.showBooking = true;
      this.showProfile = false;
      this.showService = false;
      this.showBusiness = false;
    },

    service() {
      this.showService = true;
      this.showProfile = false;
      this.showBooking = false;
      this.showBusiness = false;
    },

    business() {
      this.showBusiness = true;
      this.showProfile = false;
      this.showBooking = false;
      this.showService = false;
    },
    addService() {
      if (this.fileList[0].startsWith("data:image/png;base64,")) {
        this.form.images.push(
          this.fileList[0].replace("data:image/png;base64,", "")
        );
      } else if (this.fileList[0].startsWith("data:image/jpg;base64,")) {
        this.form.images.push(
          this.fileList[0].replace("data:image/jpg;base64,", "")
        );
      } else if (this.fileList[0].startsWith("data:image/jpeg;base64,")) {
        this.form.images.push(
          this.fileList[0].replace("data:image/jpeg;base64,", "")
        );
      }
      console.log(this.form);
      axios
        .post("https://vendor-valley.onrender.com/addService", this.form)
        .then(() => {
          this.$toast.info(`Service added`, {
            position: "top",
            duration: 2000,
          });
          this.form.serviceName = "";
          this.form.serviceDescription = "";
          this.form.servicePrice = "";
          this.form.images = [];
          this.form.categoryNames = [];
          this.fileList = [];
          this.showAddServiceModal = false
          this.getVendorServices()
        })
        .catch((err) => {
          console.log(err);
        });
    },
    updatePerPro() {
      axios
        .put(
          "https://vendor-valley.onrender.com/edit/profile",
          this.personalInfo
        )
        .then((res) => {
          console.log(res.data);
          this.$toast.success(`Profile Updated`, {
            position: "top",
            duration: 2000,
          });
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    updateComPro() {
      axios
        .put(
          "https://vendor-valley.onrender.com/edit/company",
          this.companyInfo
        )
        .then((res) => {
          console.log(res.data);
          this.$toast.success(`Profile Updated`, {
            position: "top",
            duration: 2000,
          });
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    openAddService() {
      
        this.showAddServiceModal = true;
    },

    openModal(item) {
      console.log(item);
      this.selectedItem = item;
      this.customer = this.selectedItem.user.email;
      this.showModal = true;
    },
    getBookings() {
 
      axios
        .get("https://vendor-valley.onrender.com/bookings", {
          headers: {
            jwtToken:
              localStorage.getItem('token'),
          },
        })
        .then((res) => {
          this.bookingList = res.data;
          this.showBooking = true
        })
        .catch((err) => {
          console.log(err.message);
        });
    },

    async getUserDetails() {
      const loading = ElLoading.service({
        lock: true,
        background: "rgba(255, 255, 255, 1)",
      });
      await axios
        .get("https://vendor-valley.onrender.com/profile", {
          headers: {
            jwtToken: localStorage.getItem("token"),
          },
        })
        .then((res) => {
          this.form.userId = res.data.user_id;
          this.personalInfo.userId = res.data.user_id;
          this.personalInfo.first_name = res.data.first_name;
          this.personalInfo.last_name = res.data.last_name;
          this.personalInfo.mobile = res.data.mobile;
          this.personalInfo.email = res.data.email;
          this.personalInfo.city = res.data.city;
          this.personalInfo.province = res.data.province;
          this.personalInfo.street = res.data.street;
          this.personalInfo.country = res.data.country;
          this.personalInfo.is_vendor = res.data.is_vendor;
          this.personalInfo.password = res.data.password;

          // company
          this.companyInfo.userId = res.data.user_id;
          this.companyInfo.user_role = res.data.user_role;
          this.companyInfo.company_name = res.data.company_name;
          this.companyInfo.company_registration_number =
            res.data.company_registration_number;
          this.companyInfo.company_street = res.data.company_street;
          this.companyInfo.company_city = res.data.company_city;
          this.companyInfo.company_province = res.data.company_province;
          this.companyInfo.company_country = res.data.company_country;
          this.companyInfo.company_email = res.data.company_email;
          this.companyInfo.company_mobile = res.data.company_mobile;

          loading.close();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getCategories() {
      axios
        .get("https://vendor-valley.onrender.com/categories")
        .then((res) => {
          res.data.forEach((element) => {
            this.categories.push(element.categoryName);
          });
        })
        .catch((err) => {
          console.log(err.message);
        });
    },
    getVendorServices() {
      axios
        .get("https://vendor-valley.onrender.com/services", {
          headers: {
            jwtToken: localStorage.getItem("token"),
          },
        })
        .then((res) => {
          this.serviceList = res.data;
          console.log(this.serviceList);
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    closeServiceModal(){
        this.form.userId= ""
        this.form.serviceName= ""
        this.form.serviceDescription= ""
        this.form.servicePrice= ""
        this.form.images= []
        this.form.categoryNames= []
        this.fileList = []
        this.showAddServiceModal = false
    },
    closeEditService(){
      this.showEditServiceModal = false
    }
  },
  mounted() {
    this.showProfile = true
    this.showBooking = false
    this.showBusiness = false
    this.showService = false
    this.activeNo = "1";
    const show = this.$route.query.show;
    if (show == "true") {
      this.activeNo = "2";
      this.service();
    }
    this.getUserDetails();
    this.getBookings();
    this.getCategories();
    this.getVendorServices();
  },
};
</script>

<style scoped></style>
