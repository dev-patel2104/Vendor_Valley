<template>
<div class="text-center">
    <h1>Login Page</h1>
</div>
<div class="form-signin d-flex flex-column" >
 
     
      <label for="inputEmail" class="sr-only">Email address</label>
      <input type="email" id="inputEmail" class="form-control" placeholder="Email address" v-model="form.email" required autofocus>
      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" id="inputPassword" class="form-control"  v-model="form.password" placeholder="Password" required >

      <div class="mb-2">
          <router-link to="/forgotpassword">
            <a><small>Forgot password?</small></a>
          </router-link>
        </div>

      <button class="btn btn-lg btn-primary btn-block" v-on:click="login()">Login</button>
     
  

    <p class="text-danger">{{msg}}</p>
</div>
</template>

<script>
import axios from 'axios'


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
        console.log(this.form);
   
        axios.post('https://vendor-valley.onrender.com/login', this.form)
  .then((resp) => {
    
    localStorage.setItem('userID', this.form.email);
    localStorage.setItem('password', this.form.password);
    this.msg = ""
    alert('success',resp.data);
  })
  .catch((error) => {
    console.error("error", error);
  this.msg = "Invalid email or password"

  });
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
