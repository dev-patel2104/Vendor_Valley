<template>
  <div class="form-signin d-flex flex-column rounded shadow p-3 mb-5 bg-white mt-4" >
    <div class="text-center">
        <h6>Enter the code sent to your email</h6>
      </div>
       
        <input type="text" id="inputCode" class="form-control"  v-model="form.code" placeholder="code" required >
        <button class="btn btn-lg btn-primary btn-block" v-on:click="checkCode()">Verify</button>
        <p class="text-danger">{{ msg }}</p>
      
  </div>
  </template>
  
  <script>
  import router from '@/router'
  import axios from 'axios'
  
  
  export default{
      name: 'resetPassword',
      data(){
          return {
              form: {
                email: '',
                  code: '',
              },
              msg: ""
          }
      },
      methods: {
        async checkCode(){
          // verifyConfirmationCode
          this.form.email = localStorage.getItem('userEmail')
          await axios.post('https://vendor-valley.onrender.com/verifyConfirmationCode', this.form)
          .then(()=>{
            router.push('/changepassword')
          }).catch((error)=>{
            this.msg  = error.response.data
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
  