import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/Home.vue"
import Login from '@/views/Login.vue'
import Signup from '@/views/Signup.vue'


const routes = [
  {path: '/', component: Home},
  {path: '/login', component: Login},
  {path: '/signup', component: Signup},
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
