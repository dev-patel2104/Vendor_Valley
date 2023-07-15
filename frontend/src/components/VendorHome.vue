<template>
    <div class="w-100 bg-black px-4 py-2 d-flex justify-content-between">
        <div>
           <h3 @click="goHome" class="Home-Text">Vendor Valley</h3>
          
        </div>
        <div class="d-flex justify-content-center">
            <router-link v-if="!showLogout" to="/login" class="btn btn-light px-4 mx-2">Login</router-link>
            <router-link v-if="!showLogout" to="/signup" class="btn btn-light px-4">Register</router-link>
            <button v-if="showLogout" @click.prevent="logout()" class="btn btn-light px-4">Log out</button>
        </div>  
    </div>
</template>

<script>
import router from '@/router'

export default {
    name: "NavBar",
    data() {
    return {
        showLogout: "",
    };
  },

    methods: {
        goHome() {
            router.push('/')
        },
        logout(){
            localStorage.removeItem("userID")
            localStorage.removeItem("userEmail")
            localStorage.removeItem("password")
            localStorage.removeItem("token")
            router.push('/login')
        }
    },
    mounted() {
        if(localStorage.getItem('userID') != null){
            this.showLogout = true
            router.push('/homepage')
        }
    }
}


</script>

<style scoped>

.Home-Text{
    color: white;

}

.Home-Text:hover {
    cursor: pointer;
}
</style>