import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/Home.vue"
import Login from '@/views/Login.vue'
import Signup from '@/views/Signup.vue'
import Forgotpassword from '@/views/Forgotpassword.vue'
import Enterverificationcode from '@/views/Enterverificationcode.vue'
import Changepassword from '@/views/Changepassword.vue'
import Homepage from '@/views/Components/Homepage.vue'
import selectVendorService from '@/views/Components/SelectVendorService.vue'
import vendorHomepage from '@/views/Components/VendorHomepage.vue'
import customerProfile from '@/views/Components/CustomerProfile.vue'
import customerList from '@/views/Components/CustomerList.vue'
import vendorProfile from '@/views/Components/VendorProfile.vue'
import NotFound from '@/views/PageNotFound.vue'


const routes = [
  {path: '/', component: Home},
  {path: '/login', component: Login},
  {path: '/signup', component: Signup},
  {path: '/forgotpassword', component: Forgotpassword},
  {path: '/Enterverificationcode', component: Enterverificationcode},
  {path: '/Changepassword', component: Changepassword},
  { 
    path: '/homepage',
    component: Homepage,
    beforeEnter: (to, from, next) => {
      const token = localStorage.getItem('token');
      const role = localStorage.getItem('role');
      if (token) {
        if(role == 0){
          next();
        }
      } else {
        next('/');
      }
    }
  },
  { 
    path: '/selectvendorservice',
    name: 'selectVendorService',
    component: selectVendorService,
    beforeEnter: (to, from, next) => {
      const token = localStorage.getItem('token');
      if (token) {
        next(); 
      } else {
        next('/');
      }
    }
  },
  {
    path: '/vendorhome',
    component: vendorHomepage,
    beforeEnter: (to, from, next) => {
      const token = localStorage.getItem('token');
      const role = localStorage.getItem('role');
      if (token) {
        if(role == 1){
          next(); 
        }
        
      } else {
        next('/');
      }
    }
  },
  {
    path: '/customerprofile',
    component: customerProfile,
    beforeEnter: (to, from, next) => {
      const token = localStorage.getItem('token');
      const role = localStorage.getItem('role');
      if (token) {
        if(role == 0){
          next(); 
        } 
      } else {
        next('/');
      }
    }
  },
  {
    path: '/customerlist',
    component: customerList,
    beforeEnter: (to, from, next) => {
      const token = localStorage.getItem('token');
      const role = localStorage.getItem('role');
      if (token) {
        if(role == 1){
          next(); 
        } 
      } else {
        next('/');
      }
    }
  },
  {
    path: '/vendorprofile',
    component: vendorProfile,
    beforeEnter: (to, from, next) => {
      const token = localStorage.getItem('token');
      const role = localStorage.getItem('role');
      if (token) {
        if(role == 1){
          next(); 
        } 
      } else {
        next('/');
      }
    }
  },
  
  {
    path: "/404",
    component: NotFound
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404",
  },
]


const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})


export default router
