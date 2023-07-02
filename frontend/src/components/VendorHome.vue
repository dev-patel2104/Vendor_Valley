<template>
    <div class="w-100 bg-black px-4 py-2 d-flex justify-content-between">
        <div>
           <h3 @click="goHome" class="Home-Text">Vendor Valley</h3>
          
        </div>
    <div class="d-flex justify-content-center">
        <router-link to="/login" class="btn btn-light px-4 mx-2">Login</router-link>
        <router-link to="/signup" class="btn btn-light px-4">Register</router-link>
    </div>  
</div>
</template>

<script>
import router from '@/router'
export default {
    name: "NavBar",

    methods: {
        goHome() {
            router.push('/')
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