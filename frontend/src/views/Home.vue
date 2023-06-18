<template>
  <h1 class="text-center">{{ homepage ? homepage : "WelcomeHome" }}</h1>

  <div>
    <div class="d-flex">
      <div class="w-50">
        <div class="p-4">
          <div class="text-center">
            <h4>Connecting you with the best vendors for your needs</h4>
          </div>
          <div class="mt-4">
            <p>
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
          <div class="w-100 d-flex justify-content-end">
            <button type="button" class="btn btn-outline-primary">
              Know more
            </button>
          </div>
        </div>
      </div>
      <div class="w-50">
        <div>
          <img
            class="w-100"
            src="https://vendorcentric.com/wp-content/uploads/2022/01/whatisvendormanagemet-01.png"
            alt=""
          />
        </div>
      </div>
    </div>
    <div></div>
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
