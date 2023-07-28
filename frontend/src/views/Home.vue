<template>
  <div class="home">

    <!-- landing main -->
    <div class="w-100 d-flex align-self-start p-4">

      <!-- details and review -->
      <div class="w-50 d-flex justify-content-center flex-column align-self-stretch">
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
          <div>
            <p class="text-center">What users all over the globe has to say about VendorValley</p>
          </div>
          <div class="d-flex flex-row review-custom">


            <div class="review-card1 p-4 rounded">
              <div><p class="fs-5">Wonderful!</p></div>
              <div><p>Lorem ipsum dolor sit amet consectetur adipisicing elit. emporibus! Ducimus, mollitia enim quae
                 dignis provident nobis pariatur fuga?</p></div>
              <div class="d-flex">
                <div class="w-25"><img class="w-100 rounded-circle" src="../../public\static\images\profile.jpg" alt=""></div>
                <div class="">
                  <div>lorem ipsum</div>
              </div>
              </div>
            </div>


            <div class="review-card2 p-4 rounded">
              <div><p class="fs-5">WOW.</p></div>
              <div><p>Lorem ipsum dolor sit amet consectetur adipisicing elit. emporibus! Ducimus, mollitia enim quae
                 dignis provident nobis pariatur fuga?</p></div>
              <div class="d-flex">
                <div class="w-25"><img class="w-100 rounded-circle" src="../../public\static\images\profile.jpg" alt=""></div>
                <div class="">
                  <div>lorem ipsum</div>
              </div>
              </div>
            </div>

           <div class="review-card3 p-4 rounded">
              <div><p class="fs-5">Awesome service..</p></div>
              <div><p>Lorem ipsum dolor sit amet consectetur adipisicing elit. emporibus! Ducimus, mollitia enim quae
                 dignis provident nobis pariatur fuga?</p></div>
              <div class="d-flex">
                <div class="w-25"><img class="w-100 rounded-circle" src="../../public\static\images\profile.jpg" alt=""></div>
                <div class="">
                  <div>lorem ipsum</div>
              </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="w-50 d-flex flex-column">
      <!-- trending image section -->
      <div class="w-100 h-50 d-flex">
        <div class="w-50"><img class="w-100 h-100" src="../../public/static/images/landing_1.png" alt=""></div>
        <div class="w-50"><img class="w-100 h-100" src="../../public/static/images/landing_2.png" alt=""></div>
      </div>

      <div class="w-100 d-flex">
        <div class="w-100"><img class="w-100 h-100" src="../../public/static/images/landing_3.png" alt=""></div>
      </div>
    </div>
      
    </div>
    <div class="footer w-100 d-flex justify-content-center mt-4">
     <div class="w-75 my-4 d-flex justify-content-between">
      <div>
        <ul class="list-unstyled text-white">
          <li>link 1</li>
          <li>link 2</li>
          <li>link 3</li>
        </ul>
      </div>
      <div>
        <ul class="list-unstyled text-white">
          <li>link 1</li>
          <li>link 2</li>
          <li>link 3</li>
        </ul>
      </div>
      <div>
        <ul class="list-unstyled text-white">
          <li>link 1</li>
          <li>link 2</li>
          <li>link 3</li>
        </ul>
      </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Home",
  data() {
    return {
      homepage: "",
    };
  },

  mounted() {
    axios.get("https://vendor-valley.onrender.com/").then((response) => {
      this.homepage = response.data;
    }).catch((error)=>{
      console.log(error.response.data);
    });
  },
};
</script>

<style>
.home{
  height: 100%;
  width: 100%;
  background-color: #ebeef6;
}

img:hover{
  cursor: pointer;
}
.d-flex{
  gap:20px;
}

.review-card1{
  background-color: #e9f2f8;
}

.review-card2{
  background-color: #ebeef6;
}


.review-card3{
  background-color: #effaf0;
}

.review-custom {
  gap: 20px;
}


.footer{
  background-color: #257fbc;
}
</style>