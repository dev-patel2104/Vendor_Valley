<template>
  <div class="home">
    <div class="d-flex justify-content-center flex-column p-4">
      <div class="input-group mb-3 p-4">
        <input
          type="text"
          class="form-control"
          placeholder="Seach vendor, service etc "
          aria-label="Recipient's username"
          aria-describedby="basic-addon2"
          v-model="searchQuery"
        />
        <div class="input-group-append">
          <button
            class="btn btn-primary"
            type="button"
            @click.prevent="searchUserQuery()"
          >
            Search
          </button>
        </div>
      </div>
      <div class="w-100 d-flex align-self-start p-4">
        <div
          class="w-100 d-flex justify-content-center flex-column align-self-stretch"
        >
          <div class="d-flex justify-content-between" v-if="resultModal">
            <div>
              <button
                class="btn btn-primary custom-button"
                type="button"
                @click.prevent="toggle()"
              >
                Back
              </button>
            </div>
            <div>
              <div class="d-flex justify-content-between">
                <div>
                  <!-- <el-dropdown trigger="click">
                    <el-button type="primary">
                      Filter<el-icon class="el-icon--right"
                        ><arrow-down
                      /></el-icon>
                    </el-button>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item>
                          <el-select
                            v-model="catValue"
                            value-key="id"
                            placeholder="Categories"
                          >
                            <el-option
                              @click="filterbycategories()"
                              v-for="item in categories"
                              :key="item.id"
                              :label="item"
                              :value="item"
                            />
                          </el-select>
                        </el-dropdown-item>
                        <el-dropdown-item>Action 2</el-dropdown-item>
                        <el-dropdown-item>Action 3</el-dropdown-item>
                        <el-dropdown-item>Action 4</el-dropdown-item>
                        <el-dropdown-item>Action 5</el-dropdown-item>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown> -->
                  <el-select
                    v-model="catValue"
                    value-key="id"
                    placeholder="Categories"
                  >
                    <el-option
                      @click="filterbycategories()"
                      v-for="item in categories"
                      :key="item.id"
                      :label="item"
                      :value="item"
                    />
                  </el-select>
                </div>
                <div>
                  <el-select v-model="value" value-key="id" placeholder="Sort">
                    <el-option
                      @click.prevent="sort()"
                      v-for="item in options"
                      :key="item.id"
                      :label="item.label"
                      :value="item.desc"
                    />
                  </el-select>
                </div>
               
              </div>
            </div>
          </div>

          <!-- showing result -->
          <div
            class="bg-white rounded d-flex justify-content-center flex-column p-4"
            v-if="resultModal"
          >
            <h4>Result</h4>
            <div class="d-flex flex-column review-custom" v-loading="searchloading" element-loading-background="rgba(255, 255, 255, 1)">
              <div>
                {{
                  result.length
                    ? result.length +
                      " results found for " +
                      '"' +
                      searchResult +
                      '"'
                    : "No result found"
                }}
              </div>

              <div
                class="cusor-custom review-card1 p-4 rounded"
                v-for="item in result"
                v-bind:key="item.userId"
              >
                <div class="d-flex">
                  <div class="w-25">
                    <img
                      v-if="item.images.length"
                      class="w-100"
                      :src="'data:image/png;base64,' + item.images[0]"
                      alt=""
                    />

                    <div v-else>
                      <el-empty description="No image"/>
                    </div>
                  </div>
                  <div
                    @click="openModals(item)"
                    class="w-100 d-flex flex-column"
                  >
                    <div>
                      <h5 class="custom-text">{{ item.serviceName }}</h5>
                    </div>
                    <div>
                      <p class="custom-text">{{ item.serviceDescription }}</p>
                    </div>
                    <div>
                      <p class="custom-text">price- {{ item.servicePrice }}$</p>
                    </div>
                    <div>
                      <p class="custom-text">
                        {{ item.companyStreet }}, {{ item.companyProvince }},
                        {{ item.companyCity }}, {{ item.companyCountry }}
                      </p>
                    </div>
                    <div>
                      <p>Rating: {{ item.averageRating }}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- modal -->
          <!-- <modal v-model:show="modals">
            <div>
              <div>
                <h5>{{ selectedItem.serviceName }}</h5>
              </div>
              <div>
                <p>{{ selectedItem.serviceDescription }}</p>
              </div>
              <div>
                <p>price- {{ selectedItem.servicePrice }}$</p>
              </div>
              <div>
                <p>
                  {{ selectedItem.companyStreet }},
                  {{ selectedItem.companyProvince }},
                  {{ selectedItem.companyCity }},
                  {{ selectedItem.companyCountry }}
                </p>
              </div>
              <div>
                <p>Rating: {{ selectedItem.averageRating }}</p>
              </div>
            </div>
          </modal> -->
          <!-- modal ends here -->

          <!-- Featured -->
          <div
            class="bg-white rounded d-flex justify-content-center flex-column p-4"
            v-if="showFeatured"
          >
            <h3>Featured</h3>
            <div class="w-100 d-flex flex-row flex-wrap">
              <div
                style="width: 250px"
                v-for="item in featured"
                v-bind:key="item.categoryId"
              >
                <div style="min-height: 300px; max-height: 500px;" class="review-card1 rounded p-3">
                  <div class="w-100 mb-2">
                    <img
                  
                      class="w-100 rounded"
                      v-bind:src="'data:image/png;base64,' + item.base64Image"
                      alt=""
                    />
                    <!-- <div v-else>
                      <el-empty/>
                    </div>
                     -->
                  </div>
                  <div>
                    <h5>{{ item.categoryName }}</h5>
                  </div>
                  <div>
                    <p>{{ item.categoryDescription }}</p>
                  </div>
                  <div>
                    <a href="#" @click="featuredSearch(item.categoryName)">
                      {{ item.totalServices }} services
                    </a>
                    
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- trending -->

          <div
            class="bg-white rounded d-flex justify-content-center flex-column p-4"
            v-if="showTrending"
          >
            <h3>Trending</h3>
            <div class="d-flex flex-row review-custom">
              <div class="trending" v-for="item in trending" v-bind:key="item.userId">
                <div @click="openModals(item)" class="review-card1 p-4 rounded">
                  <div>
                    <h5>{{ item.serviceName }}</h5>
                  </div>
                  <div>
                    <p>{{ item.serviceDescription }}</p>
                  </div>
                  <div>
                    <p>Price - {{ item.servicePrice }}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
//{ "categoryId": 1, "categoryName": "Weddings", "categoryDescription": "Marriage ceremony", "totalServices": 4 }
//   {"serviceId":11,"userId":0,"serviceName":"DC Decorators","serviceDescription":"Batman","servicePrice":"1000",
//"companyStreet":null,"companyProvince":null,"companyCity":null,"companyCountry":null,"averageRating":null,"totalBookings":null}
import axios from "axios";
// import Modal from "@/components/Modal";
import { ElLoading } from "element-plus";

export default {
  components: {
    // Modal,
  },
  name: "HomePage",
  data() {
    return {
      searchloading: false,
      catValue: "",
      loading: true,
      modals: false,
      msg: "",
      category: "",
      img: "",
      showFeatured: false,
      showTrending: false,
      resultModal: false,
      featured: [],
      trending: [],
      searchQuery: "",
      searchResult: "",
      selectedItem: [],
      result: [],
      filteredData: [],
      unsortedCategories: [],
      value: "",
      categories: ["All"],
      options: [
        { id: 1, label: "Price: low to high", desc: ["price", "true"] },
        { id: 2, label: "Price: high to low", desc: ["price", "false"] },
        { id: 3, label: "Rating: low to high", desc: ["rating", "true"] },
        { id: 4, label: "Rating: high to low", desc: ["rating", "false"] },
        { id: 5, label: "Booking: low to high", desc: ["booking", "true"] },
        { id: 6, label: "Booking: high to low", desc: ["booking", "false"] },
      ],
    };
  },

  methods: {
    filterbycategories() {
      this.searchloading = true
      if (this.catValue == "All") {
        this.searchQuery();
      }
      const catParam = { category: this.catValue };
      const jsonstring = JSON.stringify(catParam);
      console.log(encodeURIComponent(jsonstring));
      axios
        .post("https://vendor-valley.onrender.com/filter", this.result, {
          params: {
            filterParam: jsonstring,
          },
        })
        .then((res) => {
          console.log(res.data);
          this.result = res.data;
          this.searchloading = false
        })
        .catch((res) => {
          console.log(res.message);
        });
    },
    openModals(item) {    
      const encodedItem = btoa(JSON.stringify(item));
      this.$router.push({
        name: "selectVendorService",
        query: { item: encodedItem },
      });
    },

    //sort?sortParam=price&sortOrder=false
    sort() {
      this.searchloading = true
      this.filteredData = this.result;
      this.result = [];
      axios
        .post(
          "https://vendor-valley.onrender.com/sort?sortParam=" +
            this.value[0] +
            "&sortOrder=" +
            this.value[1],
          this.filteredData
        )
        .then((response) => {
          this.result = response.data;
          this.searchloading = false
        })
        .catch((error) => {
          this.msg = error.message;
        });
    },
    featuredSearch(search){
      if(search.length){
        this.searchQuery = search
        this.searchUserQuery()
      }
    },
    searchUserQuery() {
      this.searchloading = true
      
      if (this.searchQuery.length) {
        this.showFeatured = false;
        this.showTrending = false;
        this.resultModal = true;
        this.result = [];

        axios
          .post("https://vendor-valley.onrender.com/search", {
            searchParam: this.searchQuery,
          })
          .then((response) => {
            console.log(response.data);
            this.result = response.data;
           

            this.searchResult = this.searchQuery;
            response.data.forEach((element) =>
              this.unsortedCategories.push(...element.categoryNames)
            );
            this.categories = [...new Set(this.unsortedCategories)];

            
            this.searchloading = false
          })
          .catch((error) => {
            this.msg = error.message;
            console.log(this.msg);
          });
      }
    },

    toggle() {
      this.result = {};
      this.showFeatured = true;
      this.showTrending = true;
      this.resultModal = false;
    },
  },

  async mounted() {
    const loading = ElLoading.service({
      lock: true,
      background: "rgba(255, 255, 255, 1)",
    });
    await axios
      .get("https://vendor-valley.onrender.com/featured")
      .then((response) => {
        this.featured = response.data;
        // this.featured.forEach((element, index) => {
        //   this.featured[index].base64Image = atob(this.featured[index].base64Image)
        // });
    
        console.log(this.featured);
        if (this.featured.length) {
          this.showFeatured = true;
        }
      })
      .catch((error) => {
        console.log(error.message);
      });

    await axios
      .get("https://vendor-valley.onrender.com/trending")
      .then((response) => {
        this.trending = response.data;
        if (this.trending.length) {
          this.showTrending = true;
        }
      })
      .catch((error) => {
        console.log(error.message);
      });

    loading.close();
  },
};
</script>

<style scoped>
.home {
  height: 100%;
  width: 100%;
  background-color: #ebeef6;
}

.custom-button {
  width: 100px;
}
.d-flex {
  gap: 20px;
}

.review-card1 {
  background-color: #e9f2f8;
}

.review-custom {
  gap: 20px;
}

.cusor-custom:hover {
  cursor: pointer;
}

.custom-text {
  margin: 0;
}

.trending:hover{
  cursor: pointer;
}
</style>
