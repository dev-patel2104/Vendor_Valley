<template>
  <div v-if="!loading" class="home">
    <div class="d-flex flex-column">
      <div class="p-4 d-flex justify-content-between rounded bg-white">
        <div>
          <h4>Hi {{name?name: "user"}}</h4>
        </div>
        <div>
          <button class="btn btn-primary" @click="manageservice()">
            Manage your service
            <i class="fa-solid fa-arrow-right fa-xs" style="color: #ffffff"></i>
          </button>
        </div>
      </div>

      <!-- dashboard -->
      <div
        class="p-4 d-flex flex-column justify-content-between rounded bg-white"
      >
        <h4>Statistics</h4>
        <p>
          Lorem ipsum, dolor sit amet consectetur adipisicing elit. Tempore
          reprehenderit magnam odit, dolorem corrupti iure, temporibus hic
          assumenda sunt id eius eaque non nostrum consequuntur ut numquam? Nam,
          tenetur reiciendis?
        </p>
      </div>

      <div class="d-flex w-100">
        <div class="w-50 p-4 d-flex flex-column rounded bg-white">
          <div>
            <h4>Service summary</h4>
          </div>
        
          <div>
            <ul class="list-unstyled">
              <li>
                <p>Total customers</p>
                <p>{{ result.totalCustomers }}</p>
              </li>
              <el-divider />
              <li>
                <p>Total Bookings</p>
                <p>{{ result.totalBookings }}</p>
              </li>
              <el-divider />
              <li>
                <p>Cancelled Bookings</p>
                <p>{{ result.cancelledBookings }}</p>
              </li>
              <el-divider />
              <li>
                <p>Completed Bookings</p>
                <p>{{ result.acceptedBookings }}</p>
              </li>
              <el-divider />
              <li>
                <p>Upcoming Bookings</p>
                <p>{{ result.awaitingBookings }}</p>
              </li>
              <el-divider />
              <li>
                <p>This month</p>
                <p>{{ result.thisMonthBookings }}</p>
              </li>
              <el-divider />
            </ul>
          </div>
        </div>

        <!-- graph -->
        <div class="w-50 d-flex flex-column">
          <div
            class="d-flex p-4 flex-column justify-content-between rounded bg-white"
          >
            <h4>Graphical summary</h4>
            <apexchart
              width="100%"
              type="area"
              :options="chartOptions"
              :series="series"
            ></apexchart>
          </div>

          <!-- active users -->
          <div
            class="d-flex p-4 flex-column justify-content-between rounded bg-white"
          >
            <div>
              <h4>Customer information</h4>
            </div>
            <div>
              <div>
                <h4>{{ activeUsers }}</h4>
                <p class="small">Active</p>
              </div>
            </div>
            <div>
              <button @click="customerList()" class="btn btn-outline-primary">
                View all
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { ElLoading } from "element-plus";
export default {
  name: "vendorHome",
  data() {
    return {
      name: "",
      loading: true,
      activeUsers: "",
      result: {},
      chartOptions: {
        chart: {
          id: "vuechart-example",
        },
        xaxis: {
          categories: [
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec",
          ],
        },
      },
      series: [
        {
          data: [],
        },
      ],
    };
  },
  methods: {
    manageservice(){
      this.$router.push({
        path: "/vendorprofile",
        query:{
          show: true
        }
      })
    },
    customerList() {
      this.$router.push({
        path: "/customerlist", 
        query: {
          list: encodeURIComponent(JSON.stringify(this.result.userIds)),
        },
      });
    },
    async getStatistics() {
      const loading = ElLoading.service({
        lock: true,
        background: "rgba(255, 255, 255, 1)",
      });
      await axios
        .post("https://vendor-valley.onrender.com/getStatistics",{ },{
            headers: {
              'jwtToken': localStorage.getItem("token"),
            },
          })
        .then((res) => {
          if(res.status === 204){
            loading.close();
            this.loading = false;
          }
          else{
          console.log(res.data);
          this.result = res.data;
          this.activeUsers = this.result.userIds.length;
          for (const i in this.result.yearActivity) {
            this.series[0].data.push(this.result.yearActivity[i]);
          }

          loading.close();
          this.loading = false;
        }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  mounted() {
    // const userToken = localStorage.getItem('token')
    this.getStatistics();
    this.name = localStorage.getItem('name')
  },
};
</script>

<style scoped>
.home {
  height: 100%;
  width: 100%;
  background-color: #ebeef6;
  padding: 20px;
  margin: 0;
}
p,
h2,
h3,
h4,
h5 {
  margin: 0;
}

li {
  display: flex;
  justify-content: space-between;
  flex-direction: row;
}
</style>
