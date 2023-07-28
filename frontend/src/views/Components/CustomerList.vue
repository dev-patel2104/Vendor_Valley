<template>
    <div class="bg p-4">
        <div class="mb-4">
            <router-link class="btn btn-primary" to="/vendorhome">back</router-link>
        </div>
        <div class="bg-white rounded p-4">
        
        <div>
            <h4>Customers</h4>
        </div>
        <div v-for="item in customerList"  v-bind:key="item.userId">
            <div class="cusor-custom review-card1 p-4 mb-4 rounded">
                <p><b>Name:</b> {{ item.first_name }} {{ item.last_name }}</p>
                <p><b>Email:</b> {{ item.email }}</p>
                <p><b>Country:</b> {{ item.country }}</p>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "customerList",
  data() {
    return {
      customerList: []
    };
  },
  methods: {
    getCustomerList(list){
        axios.post("https://vendor-valley.onrender.com/getCustomerInfo", list).then((response) => {
      this.customerList = response.data;
    }).catch((error)=>{
      console.log(error.response.data);
    })
    }
  },

  mounted() {
    const queryString = this.$route.query.list;
    const list = JSON.parse(decodeURIComponent(queryString));
    this.getCustomerList(list)
  }
};
</script>

<style scoped>
.bg{
    background-color: #ebeef6;
}
</style>