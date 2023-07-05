<template>
  <form class="form-signin d-flex flex-column rounded shadow p-3 mb-5 bg-white mt-4" @submit.prevent="changePassword()">
    <div class="text-center">
        <h6>Enter new password</h6>
      </div>
       
        <input type="password" id="inputPassword" class="form-control"  v-model="form.password" placeholder="Password" required >
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        <p class="text-danger">{{ msg }}</p>
  
    </form>
  </template>
  
  
  <script>
  import axios from 'axios'
  import router from '@/router'
  
  export default{
      name: 'resetPassword',
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
        async changePassword(){
          // updatePassword
          this.form.email = localStorage.getItem('userEmail')
          await axios.post('https://vendor-valley.onrender.com/updatePassword', this.form)
          .then(()=>{
            this.$toast.success('Password changed', {duration: 1000, position: "top"})
            router.push('/login');
          }).catch((error)=>{
            this.msg = error.response.data
          })
          
        }
      },
  
      mounted() {
         this.msg = ''
      }
  }
  </script>
  <style>
  html,
  body {
    height: 100%;
  }
  
  .form-signin {
    width: 100%;
    max-width: 330px;
    padding: 15px;
    margin: 0 auto;
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
  