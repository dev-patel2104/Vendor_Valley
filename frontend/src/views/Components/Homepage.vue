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
                    <el-select
                      v-model="value"
                      value-key="id"
                      placeholder="filter"
                    >
                      <el-option
                        v-for="item in options"
                        :key="item.id"
                        :label="item.label"
                        :value="item.desc"
                      />
                    </el-select>
                  </div>
                  <div>
                    <button class="btn btn-primary" @click.prevent="filter()">Filter</button>
                  </div>
                </div>
              </div>
            </div>
  
            <!-- showing result -->
            <div
              class="bg-white rounded d-flex justify-content-center flex-column p-4"
              v-if="resultModal"
            >
              <h3>Result</h3>
              <div class="d-flex flex-column review-custom">
                <div>{{ result.length?result.length+ " results found for "+ "\""+searchQuery+"\"":"No result found" }}</div>
                <div class="review-card1 p-4 rounded" v-for="item in result" v-bind:key="item.userId">
                  <div>
                    <div>
                      <h5>{{ item.serviceName }}</h5>
                    </div>
                    <div>
                      <p>{{ item.serviceDescription }}</p>
                    </div>
                    <div>
                      <p>price- {{ item.servicePrice }}$</p>
                    </div>
                    <div>
                      <p>{{ item.companyStreet }}, {{ item.companyProvince }}, {{ item.companyCity }}, {{ item.companyCountry }}</p>
                    </div>
                    <div>
                      <p>Rating: {{ item.averageRating }}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
  
            <div
              class="bg-white rounded d-flex justify-content-center flex-column p-4"
              v-if="showFeatured"
            >
              <h3>Featured</h3>
              <div class="d-flex flex-row review-custom">
                <div v-for="item in featured" v-bind:key="item.categoryId">
                  <div class="review-card1 p-4 rounded">
                    <div>
                      <h5>{{ item.categoryName }}</h5>
                    </div>
                    <div>
                      <p>{{ item.categoryDescription }}</p>
                    </div>
                    <div>
                      <p>{{ item.totalServices }} services</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
  
            <div
              class="bg-white rounded d-flex justify-content-center flex-column p-4"
              v-if="showTrending"
            >
              <h3>Trending</h3>
              <div class="d-flex flex-row review-custom">
                <div v-for="item in trending" v-bind:key="item.userId">
                  <div class="review-card1 p-4 rounded">
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
  
  export default {
    name: "HomePage",
    data() {
      return {
        showFeatured: false,
        showTrending: false,
        resultModal: false,
        featured: {},
        trending: {},
        searchQuery: "",
        result: {},
        value: "",
        options: [{id: 1, label: 'Price: low to high', desc: ["price", "asc"]},{id: 2, label: 'Price: high to low', desc: ["price", "desc"]},
        {id: 3, label: 'Rating: low to high', desc: ["rating", "asc"]},{id: 4, label: 'Rating: high to low', desc: ["rating", "desc"]}]
      };
    },
  
    methods: {
      //sort?sortParam=rating&sortOrder=asd
      filter(){
        axios
          .post("https://vendor-valley.onrender.com/sort?sortParam="+this.value[0]+"&sortOrder="+this.value[1], this.result)
          .then((response) => {
            this.result = response.data;
          })
          .catch((error) => {
            this.msg = error.response.data;
          });
      },
      searchUserQuery() {
        
        this.showFeatured = false;
        this.showTrending = false;
        
        axios
          .post("https://vendor-valley.onrender.com/search", {
            searchParam: this.searchQuery,
          })
          .then((response) => {
            this.result = response.data;
            this.resultModal = true
          })
          .catch((error) => {
            this.msg = error.response.data;
          });
      },
  
      toggle() {
        this.result = {};
        this.showFeatured = true;
        this.showTrending = true;
        this.resultModal= false;
      
      },
    },
  
    async mounted() {
      await axios
        .get("https://vendor-valley.onrender.com/featured")
        .then((response) => {
          this.featured = response.data;
          if (this.featured.length) {
            this.showFeatured = true;
          }
        })
        .catch((error) => {
          console.log(error.response.data);
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
          console.log(error.response.data);
        });
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
  </style>
  