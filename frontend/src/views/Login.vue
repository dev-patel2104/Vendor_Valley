<template>


  <form class="form-signin d-flex flex-column rounded shadow p-3 mb-5 bg-white mt-4" @submit.prevent="login()">
  
      <div class="text-center">
        <h2>Login</h2>
      </div>
   
       
      <div>
        <label for="inputEmail" class="sr-only">Email</label>
        <input type="email" id="inputEmail" class="form-control" placeholder="Email address" v-model="form.email" required autofocus>
      </div>
  
      <div>
  
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control"  v-model="form.password" placeholder="Password" required >
      </div>
      
      
      <div class="mb-2">
          <router-link to="/forgotpassword">
            <a><small>Forgot password?</small></a>
          </router-link>
      </div>
  
      <div>
        <button class="w-100 btn btn-lg btn-primary btn-block" type="submit">Login</button>
      </div>
  
      <p class="text-danger">{{msg}}</p>
    </form>
  
  </template>
  
  <script>
  import axios from 'axios'
  import router from '@/router';
  
  export default{
      name: 'LoginPage',
      data(){
          return {
              form: {
                  email: '',
                  password: '',
              },
              msg: ""
          }
      },
      methods: {
        login(){   
          axios.post('https://vendor-valley.onrender.com/login', this.form)
            .then((response) => {
              localStorage.setItem('name', response.data.name)
              localStorage.setItem('userID', this.form.email);
              localStorage.setItem('email', this.form.email);
              localStorage.setItem('token',response.data.token);
              localStorage.setItem('role',response.data.role);
              this.msg = ""
              this.$toast.success(`Login sucessful`, {position:"top", duration: 2000, });
              if(localStorage.getItem('role') == 1){
                router.push('/vendorhome');
               
              }else if(localStorage.getItem('role') == 0){
                router.push('/homepage')

              } 
              location.reload();
            })
            .catch((error) => {
              console.log(error.response)
              this.msg = error.response.data.error
            });
        }
      },
      mounted() {
         this.msg = ''
      }
  }
  </script>
  
  <style scoped>
  html,
  body {
    height: 100%;
  }
  
  
  .form-signin {
    width: 100%;
    max-width: 330px;
    padding: 15px;
    margin: 0 auto;
    gap: 10px;
  }
  .form-signin .checkbox {
    font-weight: 400;
  }
  .form-signin .form-control {
    position: relative;
    box-sizing: border-box;
    height: auto;
    padding: 10px;
    font-size: 16px;
  }
  .form-signin .form-control:focus {
    z-index: 2;
  }
  .form-signin input[type="email"] {
    margin-bottom: -1px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
  }
  .form-signin input[type="password"] {
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  }
  </style>
  