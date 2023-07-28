<template>
  <div class="home" v-if="show">
    <div class="bg-white rounded d-flex justify-content-center flex-column p-5">
      <!-- user sec1 -->
      <div class="d-flex">
        <div class="w-75 h-100 d-flex flex-row">
          <div class="w-100 h-100">
            <div v-if="item.images.length">
              <img
                class="w-100 h-100 rounded"
                :src="'data:image/jpeg;base64,' + item.images[0]"
                alt=""
              />
            </div>

            <div v-else><el-empty /></div>
          </div>
          <!-- <div class="w-25 h-100 d-flex flex-column">
            <div>
              <img
                class="w-100 rounded"
                src="../../../public\static\images\landing_3.png"
                alt=""
              />
            </div>
            <div>
              <img
                class="w-100 rounded"
                src="../../../public\static\images\landing_3.png"
                alt=""
              />
            </div>
            <div>
              <img
                class="w-100 rounded"
                src="../../../public\static\images\landing_3.png"
                alt=""
              />
            </div>
          </div> -->
        </div>
        <div class="w-25">
          <div class="w-100">
            <!-- vendor details and booking -->
            <div class="w-100 rounded border d-flex flex-column p-4">
              <div class="w-100">
                <h3>{{ item.serviceName }}</h3>
              </div>
              <div class="d-flex align-items-center">
                <i class="fa-solid fa-location-dot" style="color: #000000"></i>
                <p>
                  {{ item.companyStreet }}, {{ item.companyProvince }},
                  {{ item.companyCity }},
                  {{ item.companyCountry }}
                </p>
              </div>
              <div>
                <el-rate
                  v-model="item.averageRating"
                  disabled
                  show-score
                  text-color="#ff9900"
                  score-template="{value}"
                />
              </div>
              <div class="d-flex justify-content-center">
                <button
                  class="btn btn-primary rounded-pill"
                  @click="showBookingModal = true"
                >
                  Book Serivce
                </button>
              </div>
            </div>

            <!-- booking modal -->
            <modal v-model:show="showBookingModal">
              <div class="p-4 d-flex flex-column">
                <div>
                  <h4>Book a service</h4>
                </div>
                <div>
                  <p class="small">
                    Select start and end dates you want to book the service
                  </p>
                </div>
                <el-date-picker
                  class="w-100"
                  v-model="date"
                  type="daterange"
                  range-separator="To"
                  start-placeholder="Start date"
                  end-placeholder="End date"
                  size="large"
                  format="YYYY/MM/DD"
                  value-format="YYYY-MM-DD"
                />
                <div>
                  <p>
                    {{ date.length ? "Start date: " + date[0] : "" }}
                  </p>
                  <p>
                    {{ date.length ? "End date: " + date[1] : "" }}
                  </p>
                </div>
                <div>
                  <el-button type="primary" @click="BookService"
                    >Book</el-button
                  >
                  <el-button @click="showBookingModal = false"
                    >Cancel</el-button
                  >
                </div>
              </div>
            </modal>

            <!-- send mail to vendor -->
            <div class="w-100 rounded border d-flex flex-column p-4 mt-2">
              <div class="w-100">
                <h6>Send message to vendor</h6>
              </div>
              <div class="d-flex align-items-center">
                <el-input
                  v-model="writeEmail"
                  :autosize="{ minRows: 4, maxRows: 6 }"
                  type="textarea"
                  placeholder="Please input"
                />
              </div>

              <div class="d-flex justify-content-center">
                <button
                  class="btn btn-primary rounded-pill"
                  @click.prevent="sendMail()"
                >
                  Send message
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- user sec1 ends here -->

      <el-divider />
      <!-- user sec2 -->
      <div class="d-flex flex-column">
        <div>
          <h4>Description</h4>
        </div>
        <div>
          <p>{{ item.serviceDescription }}</p>
        </div>
      </div>

      <!-- user sec2 ends here -->

      <el-divider />

      <!-- user sec3 starts -->
      <div class="d-flex flex-column">
        <div>
          <h4>Specialities</h4>
        </div>
        <div class="d-flex justify-content-around">
          <div>
            <ul class="list-unstyled">
              <li v-for="item in item.categoryNames" :key="item.userId">
                <i class="fa-solid fa-fan" style="color: #000000"></i>
                <p>{{ item }}</p>
              </li>
              <li>
                <i
                  class="fa-regular fa-face-grin-hearts"
                  style="color: #000000"
                ></i>
                <p>Memorial designs</p>
              </li>
              <li>
                <i
                  class="fa-solid fa-champagne-glasses"
                  style="color: #000000"
                ></i>
                <p>Enchanting atmosphere</p>
              </li>
            </ul>
          </div>
          <div>
            <ul class="list-unstyled">
              <li>
                <i
                  class="fa-solid fa-wand-magic-sparkles"
                  style="color: #000000"
                ></i>
                <p>Stunning arrangments</p>
              </li>
              <li>
                <i
                  class="fa-solid fa-compass-drafting"
                  style="color: #000000"
                ></i>
                <p>Impeccable craftsmanship</p>
              </li>
              <li>
                <i class="fa-solid fa-medal" style="color: #000000"></i>
                <p>Execptional service</p>
              </li>
            </ul>
          </div>
          <div>
            <ul class="list-unstyled">
              <li>
                <i class="fa-solid fa-leaf"></i>
                <p>Eco-Friendly Practices</p>
              </li>
              <li>
                <i class="fa-solid fa-people-group" style="color: #000000"></i>
                <p>Expert Team</p>
              </li>
              <li>
                <i class="fa-regular fa-gem" style="color: #000000"></i>
                <p>Premium quality</p>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <!-- ends here -->

      <el-divider />

      <!-- review sec -->
      <div class="d-flex flex-column">
        <div>
          <h4>Write review</h4>
        </div>
        <div class="d-flex flex-column">
          <el-input
            label="Booking Id"
            placeholder="Booking Id"
            v-model="writeReview.bookingId"
          />
          <el-input placeholder="Title" v-model="writeReview.reviewTitle" />
          <el-input
            v-model="writeReview.reviewComment"
            :autosize="{ minRows: 6, maxRows: 10 }"
            type="textarea"
            placeholder="Comment"
          />
          <div>
            <el-rate
              v-model="writeReview.reviewRating"
              size="large"
              allow-half
            />{{ writeReview.reviewRating }}
          </div>
        </div>
        <div class="w-100 d-flex justify-content-end">
          <button
            class="btn btn-primary rounded-pill"
            @click="writeuserReview()"
          >
            Post review
          </button>
        </div>
      </div>

      <!-- review sec ends here -->

      <el-divider />

      <!-- users review list -->

      <div class="d-flex flex-column">
        <div>
          <h4>{{ reviewList.length }} Customer reviews</h4>
        </div>
        <div class="scrollable-div">
          <div v-for="item in newList" v-bind:key="item.serviceId">
            <div class="d-flex align-items-centers">
              <div>
                <img
                  style="width: 50px; height: 50px"
                  class="rounded-circle"
                  src="../../../public/static/images/user-profile.jpeg"
                  alt=""
                />
              </div>
              <div>
                <p class="small">
                  <b>{{ item.reviewerName }}</b>
                </p>
                <p>
                  <em>
                    <q>
                      {{ item.reviewComment }}
                    </q>
                  </em>
                </p>
              </div>
            </div>
            <el-divider />
          </div>
        </div>
        <div class="text-center">
          <button
            v-if="newList.length < reviewList.length"
            @click="changeList()"
            class="btn btn-primary"
          >
            view all
          </button>
        </div>
      </div>
    </div>
  </div>
  <!-- <div>
    <p>{{ item.serviceId }}</p>
    <p>{{ item.userId }}</p>
    <p>{{ item.serviceName }}</p>
    <p>{{ item.serviceDescription }}</p>
    <p>{{ item.servicePrice }}</p>
    <p>{{ item.categoryNames }}</p>
    <p>{{ item.companyStreet }}</p>
    <p>{{ item.companyProvince }}</p>
    <p>{{ item.companyCity }}</p>
    <p>{{ item.companyCountry }}</p>
    <p>{{ item.totalBookings }}</p>


  </div> -->
</template>

<script>
import axios from "axios";
import Modal from "@/components/Modal.vue";
export default {
  components: { Modal },
  name: "SelectVendorService",
  data() {
    return {
      show: false,
      date: [],
      bookingDates: {
        serviceID: "",
        bookingDate: "",
        startDate: "",
        endDate: "",
      },
      todayDate: "",
      showBookingModal: false,
      writeEmail: "",
      writeReview: {
        serviceId: "",
        reviewTitle: "",
        reviewComment: "",
        reviewRating: "",
        bookingId: "",
      },
      item: {},
      reviewList: {},
      newList: {},
    };
  },
  methods: {
    BookService() {
      this.bookingDates.serviceID = this.item.serviceId;
      this.bookingDates.startDate = this.date[0];
      this.bookingDates.endDate = this.date[1];
      this.bookingDates.bookingDate = new Date().toISOString().slice(0, 10);
      console.log(this.bookingDates, typeof(this.bookingDates.bookingDate));
      axios
        .post(
          "https://vendor-valley.onrender.com/booking",this.bookingDates,
          {
            headers: {
              'jwtToken': localStorage.getItem("token"),
            },
          }
          
        )
        .then((res) => {
          console.log(res.data);
          this.bookingDates.startDate = ''
          this.bookingDates.endDate = ''
          this.bookingDates.bookingDate = ''

          this.showBookingModal = false
          this.$toast.info(`Booking request sent`, {
            position: "top",
            duration: 1000,
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    writeuserReview() {
      this.writeReview.serviceId = this.item.serviceId;
      console.log(this.writeReview);
      axios
        .post(
          "https://vendor-valley.onrender.com/writeReviews",
          this.writeReview,
          {
            params: {
              JWTToken: localStorage.getItem("token"),
            },
          }
        )
        .then(() => {
          this.$toast.info(`Review sent`, {
            position: "top",
            duration: 1000,
          });
          this.writeReview.serviceId = "";
          this.writeReview.bookingId = "";
          this.writeReview.reviewTitle = "";
          this.writeReview.reviewRating = "";
          this.writeReview.reviewComment = "";
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    changeList() {
      this.newList = this.reviewList;
    },
    sendMail() {
      if (this.writeEmail.length) {
        axios
          .post("https://vendor-valley.onrender.com/sendEmail", {
            serviceId: this.item.serviceId,
            JWTToken: localStorage.getItem("token"),
            emailText: this.writeEmail,
          })
          .then(() => {
            this.$toast.info(`Message sent to vendor`, {
              position: "top",
              duration: 1000,
            });
            this.writeEmail = "";
          })
          .catch((error) => {
            console.log(error.message);
          });
      } else {
        this.$toast.error(`Please enter message`, {
          position: "top",
          duration: 1000,
        });
      }
    },
    getReview() {
      axios
        .post("https://vendor-valley.onrender.com/getReviews", {
          serviceId: this.item.serviceId,
        })
        .then((response) => {
          console.log(response.data);
          this.reviewList = response.data;
          this.newList = this.reviewList.slice(0, 5);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },

  mounted() {
    const encodedItem = this.$route.query.item;

    const decodedItem = JSON.parse(atob(encodedItem || "{}"));
    this.item = decodedItem;

    this.getReview();

    this.show = true;
  },
};
</script>

<style scoped>
.home {
  height: 100%;
  width: 100%;
  background-color: #ebeef6;
}
p {
  margin: 0;
}
ul {
  display: flex;
  gap: 20px;
  flex-flow: column;
}
li {
  display: flex;
  justify-content: start;
  align-items: center;
  gap: 20px;
}

.scrollable-div {
  max-height: 400px;
  overflow-y: scroll;
}

*::-webkit-scrollbar {
  width: 5px;
}

*::-webkit-scrollbar-track {
  background: rgb(255, 255, 255);
}

*::-webkit-scrollbar-thumb {
  background-color: rgb(149, 149, 149);
  border-radius: 20px;
}
</style>
