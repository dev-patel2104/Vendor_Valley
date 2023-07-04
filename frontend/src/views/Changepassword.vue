<template>
<div class="form-signin d-flex flex-column" >
 
      <label for="inputPassword" class="sr-only">Enter new password</label>
      <input type="password" id="inputPassword" class="form-control"  v-model="form.password" placeholder="Password" required >
      <button class="btn btn-lg btn-primary btn-block" v-on:click="changePassword()">Submit</button>

</div>
</template>


<script>
import axios from 'axios'

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
        .then((res)=>{
          alert(res)
        })
        
      }
    },

    mounted() {
       
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
