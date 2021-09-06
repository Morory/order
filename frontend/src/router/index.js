import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login.vue'
import Home from '@/components/Home.vue'
import Register from '@/components/Register.vue'

Vue.use(Router)

const mode = 'history'

const base = process.env.BASE_URL

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  }
]

const router = new Router({
  mode,
  base,
  routes
})

router.beforeEach((to, from, next) => {
  const publicPages = ['Login', 'Register'];
  const authRequired = !publicPages.includes(to.name);
  const loggedIn = localStorage.getItem('user');

  if(authRequired && !loggedIn) {
    router.push({ name: 'Login', query: { to: to.path }});
  } else {
    next();
  }
})

export default router
