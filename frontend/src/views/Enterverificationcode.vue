<template>
<div class="form-signin d-flex flex-column" >
    <p>{{msg}}</p>
 
      <label for="inputCode" class="sr-only">Enter verification code</label>
      <input type="text" id="inputCode" class="form-control"  v-model="form.code" placeholder="code" required >
      <button class="btn btn-lg btn-primary btn-block" v-on:click="checkCode()">Verify</button>

    
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
        })
        
      }
    },

    mounted() {
       this.msg = 'Enter the code sent to your email'
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
