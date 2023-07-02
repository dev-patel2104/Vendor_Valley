<template>
  <div class="home">

    <!-- landing main -->
    <div class="w-100 d-flex">

      <!-- details and review -->
      <div class="w-50 d-flex justify-content-center flex-column">
        <div class="bg-white rounded d-flex justify-content-center flex-column p-4">
          <div><p class="fs-3 text-center">Connecting you with the best vendors for your needs</p></div>
          <div><p>
              Introducing Vendor Valley: A platform that connects customers with
              vendors. It acts as an intermediary, allowing customers to post
              their desired products or services. The application provides a
              user-friendly interface for customers to compare offers from
              different vendors, considering factors such as price, quality, and
              reputation. This platform streamlines the process of vendor
              selection and provides customers with a wide range of options
              while enabling vendors to showcase their products or services.
            </p>
          </div>
          <div class="d-flex justify-content-end"><button type="button" class="btn btn-outline-primary">
              Know more
            </button></div>
        </div>
        <div class="bg-white rounded d-flex justify-content-center flex-column p-4">
          <div><p class="fs-3 text-center">Connecting you with the best vendors for your needs</p></div>
          <div><p>
              Introducing Vendor Valley: A platform that connects customers with
              vendors. It acts as an intermediary, allowing customers to post
              their desired products or services. The application provides a
              user-friendly interface for customers to compare offers from
              different vendors, considering factors such as price, quality, and
              reputation. This platform streamlines the process of vendor
              selection and provides customers with a wide range of options
              while enabling vendors to showcase their products or services.
            </p>
          </div>
          <div class="d-flex justify-content-end"><button type="button" class="btn btn-outline-primary">
              Know more
            </button></div>
        </div>
      </div>

      <!-- trending image section -->
      <div class="w-50 d-flex">
        <div class="w-50"><img class="w-100 h-100" src="../../public/static/images/landing_1.png" alt=""></div>
        <div class="w-50"><img class="w-100 h-100" src="../../public/static/images/landing_2.png" alt=""></div>
      </div>
      
    </div>
    <div class="w-100 bg-info d-flex justify-content-center mt-4">
     <div class="w-75 my-4 d-flex justify-content-between">
      <div>
        <ul class="">
          <li>abc</li>
          <li>abc</li>
          <li>abc</li>
        </ul>
      </div>
      <div>
        <ul>
          <li>abc</li>
          <li>abc</li>
          <li>abc</li>
        </ul>
      </div>
      <div>
        <ul>
          <li>abc</li>
          <li>abc</li>
          <li>abc</li>
        </ul>
      </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  name: "HomePage",
  data() {
    return {
      homepage: "",
    };
  },

  mounted() {
    axios.get("https://vendor-valley.onrender.com/").then((response) => {
      console.log(response);
      this.homepage = response.data;
    });
  },
};
</script>

<style scoped>
.home{
  height: 100%;
  width: 100%;
  background-color: #ebeef6;
  padding: 20px;
}

.d-flex{
  gap:20px;
}
</style>