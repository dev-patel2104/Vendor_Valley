import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/Home.vue"
import Login from '@/views/Login.vue'
import Signup from '@/views/Signup.vue'
import Forgotpassword from '@/views/Forgotpassword.vue'
import Enterverificationcode from '@/views/Enterverificationcode.vue'
import Changepassword from '@/views/Changepassword.vue'
import Homepage from '@/views/Components/Homepage.vue'


const routes = [
  {path: '/', component: Home},
  {path: '/login', component: Login},
  {path: '/signup', component: Signup},
  {path: '/forgotpassword', component: Forgotpassword},
  {path: '/Enterverificationcode', component: Enterverificationcode},
  {path: '/Changepassword', component: Changepassword},
  {path: '/homepage', component: Homepage},
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})


// function guardMyroute(to, from, next) {
//   var isAuthenticated = false;

//   if (JSON.parse(localStorage.getItem("userID")).token) isAuthenticated = true;
//   else isAuthenticated = false;
//   if (isAuthenticated) {
//     next(); // allow to enter route
//   } else {
//     next("/login"); // go to '/login';
//   }
// }
export default router
