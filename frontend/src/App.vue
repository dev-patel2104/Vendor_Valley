<template>
  <NavBar />

  <router-view />
  
</template>

<script>
import NavBar from '@/components/VendorHome'
export default {
  components: {NavBar}
}

</script>