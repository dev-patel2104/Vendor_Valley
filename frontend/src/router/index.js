import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/Home.vue"
import Login from '@/views/Login.vue'
import Signup from '@/views/Signup.vue'
import Forgotpassword from '@/views/Forgotpassword.vue'
import Enterverificationcode from '@/views/Enterverificationcode.vue'
import Changepassword from '@/views/Changepassword.vue'


const routes = [
  {path: '/', component: Home},
  {path: '/login', component: Login},
  {path: '/signup', component: Signup},
  {path: '/forgotpassword', component: Forgotpassword},
  {path: '/Enterverificationcode', component: Enterverificationcode},
  {path: '/Changepassword', component: Changepassword},
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
